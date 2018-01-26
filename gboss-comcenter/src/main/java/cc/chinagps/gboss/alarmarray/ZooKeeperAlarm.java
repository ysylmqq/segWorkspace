/*
********************************************************************************************
Discription:  警情队列分布调度， 主要从以下几个方面设计
	(1) 警情队列是由多个程序分布式部署（警情队列，包含在通信中心内），所以通信中心的份布也要管理，每个通信中心，应该知道其他通信中心是否启动或down机
	              多个通信中心，有一个主通信中心，负责坐席队列、警情队列的维护及警情的分配
	(2) 坐席队列， 每个坐席可能登录到不同的通信中心，要统一调度管理，由主通信中心负责所有坐席维护，次通信中心只负责连接到自己的坐席队列
	(3) 警情判断，因为每个通信中心都从ActiveMQ读GPS。 所以由通信中心分布式判断，对呼号采用一致性Hash算法，分配由那个通信中心判断。
	(4) 警情分配：由主通信中心统一分配
			  
Written By:   ZXZ
Date:         2014-08-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/

package cc.chinagps.gboss.alarmarray;

import java.io.IOException;
import java.util.List;
import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.KeeperException.SessionExpiredException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.lib.util.ConsistentHash;
import cc.chinagps.lib.util.SystemConfig;

public class ZooKeeperAlarm implements Watcher {
	public static ZooKeeperAlarm zookeeperalarm = null;
	
	private static String alarmrootdir;	//警情根目录
	private static String nodedir;	//通信中心节点目录，包含master及 各节点名称 
	private static String seatdir;	//已登录坐席信息 
	private ZooKeeper zookeeper;
	public boolean isMasterd;		//是否是主通信中心
	private ConsistentHash<String> nodehash;
	
	public ZooKeeperAlarm() {
		alarmrootdir = SystemConfig.getAlarmProperties("alarmrootdir");
		if (alarmrootdir == null) {
			alarmrootdir = "/chinagps-alarm";
		}
		nodedir = alarmrootdir + "/comcenters"; 
		seatdir = alarmrootdir + "/seats"; 
		//建立一致性Hash，判断
		nodehash = new ConsistentHash<String>(5);
		nodehash.add(ComCenter.systemname);
	}

	/** 
     * 创建ZK连接 
     */ 
    private boolean connect() { 
        this.close(); 
        try { 
			String hostPort = SystemConfig.getAlarmProperties("zookeeperhost");
			int timeout = Integer.valueOf(SystemConfig.getAlarmProperties("zksessiontimeout"));
			zookeeper = new ZooKeeper(hostPort, timeout, this);
			return true;
        } catch (IOException e) { 
            System.out.println( "连接创建失败，发生 IOException" ); 
            e.printStackTrace(); 
        } catch (Exception e) { 
            System.out.println("连接创建失败，发生 Exception" ); 
            e.printStackTrace(); 
        }
        return false;
    } 

	/**
	 * 初始化根目录及三个子目录
	 * @throws Exception
	 */
    private void createRootPath() throws Exception {
		try {
			zookeeper.create(alarmrootdir, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (NodeExistsException e) {
		}
		try {
			zookeeper.create(nodedir, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (NodeExistsException e) {
		}
		try {
			zookeeper.create(seatdir, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (NodeExistsException e) {
		}
    }
    
	/**********************************************************************************
	 * zk 初始化 
	 * @throws IOException
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public boolean start() {
		try {
			if (!connect()) {
				return false;
			}
			createRootPath();

			if (!createNode()) {
				close();
				return false;
			}
			getNodes();
			getSeats();
	    	System.out.println("ZooKeeper Alarm start.");
			return true;
		} catch (Exception e) {
	    	System.out.println("ZooKeeper Alarm not start: ");
			e.printStackTrace();
		}
		close();
		return false;
	}
	
	/**********************************************************************************
	 * 关闭zookeeper
	 */
	public void close() {
		if (zookeeper != null) {
			try {
				zookeeper.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void restart() {
		this.close();

		if (!connect()) {
			return;
		}
		try {
			createRootPath();
			createNode();
			AlarmManager.alarmmanager.createSeats();
			getNodes();
			getSeats();
		} catch (Exception e) {
	    	System.out.println("ZooKeeper Alarm not start: ");
			e.printStackTrace();
		}
	}
	
	/**********************************************************************************
	 * 生成主节点回调, 主节点宕机时，自动山删除 //检查主节点是否改变
	 * Create 回调，宕机时没反应
	 */
	//创建主节点
	private void createMaster() {
		synchronized(zookeeper) {
			//创建主节点, 主节点是否存在
			zookeeper.create(alarmrootdir + "/master", ComCenter.systemname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, masterCreateCallback, null);
		}
		masterExists();
	}
	StringCallback masterCreateCallback = new StringCallback() {
		public void processResult(int rc, String path, Object ctx, String name) {
			switch(Code.get(rc)) {
			case CONNECTIONLOSS:
				createMaster();
				return;
			case OK:
				isMasterd = true;
		    	System.out.println("this is master.");
		    	initSeats();
				break;
			default:
				isMasterd = false;
		    	System.out.println("this is slaver.");
		    	AlarmManager.alarmmanager.askSyncAlarmList();
				break;
			}
		}
	};
	
	//创建节点, 先创建主节点，再创建节点
	public boolean createNode() {
		try {
			//创建主节点, 主节点是否存在
			createMaster();
			synchronized(zookeeper) {
				//创建节点
				zookeeper.create(nodedir + "/" + ComCenter.systemname, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			}
			return true;
		} catch (NodeExistsException e) {
			System.out.println("通信中心" + ComCenter.systemname + "已经存在，通信中心名称不能重复");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**********************************************************************************
	 * 检查主节点是否改变
	 * Create 回调，宕机时没反应
	 */
	void masterExists() {
		synchronized(zookeeper) {
			zookeeper.exists(alarmrootdir + "/master", masterExistsWatcher,  masterExistsCallback, null);
		}
	}
	Watcher masterExistsWatcher = new Watcher() {
		public void process(WatchedEvent e) {
			if(e.getType() == EventType.NodeDeleted) {
				createMaster();
			}
		}
	};	
	StatCallback masterExistsCallback = new StatCallback() {
		public void processResult(int rc, String path, Object ctx, Stat stat) {
			switch (Code.get(rc)) {
			case CONNECTIONLOSS:
				masterExists();
				break;
			case OK:
				break;
			default:
				break;
			}
		}
	};
	
	/**********************************************************************************
	 * 检查节点变化情况
	 */
	void getNodes() {
		synchronized(zookeeper) {
			zookeeper.getChildren(nodedir, nodeChangeWatcher, nodeCheckCallback, null);
		}
	}
	Watcher nodeChangeWatcher = new Watcher() {
		public void process(WatchedEvent e) {
			if(e.getType() == EventType.NodeChildrenChanged) {
				getNodes();
			}
		}
	};
	ChildrenCallback nodeCheckCallback  = new ChildrenCallback() {
        public void processResult(int rc, String path, Object ctx, List<String> children) {
        	switch (Code.get(rc)) {
        	case CONNECTIONLOSS:
        		getNodes();
        		break;
        	case OK:
        		for(String nodename : children) {
        			nodehash.add(nodename);
        		}
        		break;
        	default:
        		KeeperException.create(Code.get(rc), path);
        		break;
        	}
        }
    };
	
	/**********************************************************************************
	 * 坐席登录后，添加坐席信息到zookeeper
	 * @param seatinfo
	 */
	//添加坐席
	public boolean appendSeat(SeatClientInfo seatinfo) {
		try {
			String seatnode = seatdir + "/" + seatinfo.username;
			byte[] content = seatinfo.serialize();
			if (content == null) {
				return false;
			}
			synchronized(zookeeper) {
				try {
					zookeeper.create(seatnode, content, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
				} catch (NodeExistsException e) {
					return false;
				}
				return true;
			}
		}catch (ConnectionLossException e) {
			restart();
		}catch (SessionExpiredException e) {
			restart();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//更新坐席
	public boolean updateSeat(SeatClientInfo seatinfo) {
		String seatnode = seatdir + "/" + seatinfo.username;
		byte[] content = seatinfo.serialize();
		synchronized(zookeeper) {
			try {
				zookeeper.setData(seatnode, content, -1);
				return true;
			} catch (NoNodeException e) {
				if (!seatinfo.isClosed()) {	//如果坐席已经关闭，则不用添加节点
					appendSeat(seatinfo);
				}
			}catch (ConnectionLossException e) {
				restart();
			}catch (SessionExpiredException e) {
				restart();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	//删除坐席
	public boolean removeSeat(SeatClientInfo seatinfo) {
		try {
			String seatnode = seatdir + "/"  + seatinfo.username;
			synchronized(zookeeper) {
				try {
					//删除时要判断，坐席是不是连接到自己,
					byte[] content = zookeeper.getData(seatnode, false, null);
					if (content != null && content.length > 0) {
						SeatClientInfo oldseat = new SeatClientInfo(content);
						if (oldseat.isLoginSelf()) {
							zookeeper.delete(seatnode, -1);
						}
					}
				} catch (NoNodeException e) {
				}
				return true;
			}
		}catch (ConnectionLossException e) {
			restart();
		}catch (SessionExpiredException e) {
			restart();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**********************************************************************************
	 * 如果是从Slave变为Master, 从ZK读坐席信息，初始化坐席
	 */
	private void appendZKSeat(String seatname) {
		try {
			//如果坐席名称已经存在，则不用添加
			if (AlarmManager.alarmmanager.findSeat(seatname) != null) {
				return;
			}
			String seatnode = seatdir + "/"  + seatname;
			synchronized(zookeeper) {
				byte[] content = zookeeper.getData(seatnode, false, null);
				if (content != null && content.length > 0) {
					SeatClientInfo seat = new SeatClientInfo(content);
					AlarmManager.alarmmanager.appendZKSeat(seat);
				}
			}
		} catch (NoNodeException e) {
			//节点不存在，不用处理
		}catch (ConnectionLossException e) {
			restart();
		}catch (SessionExpiredException e) {
			restart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initSeats() {
		try {
			synchronized(zookeeper) {
				List<String> children = zookeeper.getChildren(seatdir, false);
				for(String seatname : children) {
					appendZKSeat(seatname);
				}
			}
		}catch (ConnectionLossException e) {
			restart();
		}catch (SessionExpiredException e) {
			restart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**********************************************************************************
	 * 检查坐席变化情况
	 */
	private void getSeats() {
		synchronized(zookeeper) {
			zookeeper.getChildren(seatdir, seatChangeWatcher, seatCheckCallback, null);
		}
	}
	private Watcher seatChangeWatcher = new Watcher() {
		public void process(WatchedEvent e) {
			if(e.getType() == EventType.NodeChildrenChanged) {
				getSeats();
			}
		}
	};
	private ChildrenCallback seatCheckCallback  = new ChildrenCallback() {
        public void processResult(int rc, String path, Object ctx, List<String> children) {
        	switch (Code.get(rc)) {
        	case CONNECTIONLOSS:
        		getSeats();
        		break;
        	case OK:
    			//先删除断开的坐席, 因为不清楚，改变是否有删除的坐席. 要循环判断
        		//坐席删除用消息通知
				AlarmManager.alarmmanager.adjustZKSeat(children);
    			synchronized(zookeeper) {
    				for(String seatname : children) {
    					//从ZK读坐席信息
						appendZKSeat(seatname);
        			}
				}
        		break;
        	default:
        		KeeperException.create(Code.get(rc), path);
        		break;
        	}
        }
    };
    
	/**********************************************************************************
	 * zk连接后事件
	 */
	@Override
	public void process(WatchedEvent event) {
    	//System.out.println("Got an event " + event.toString());
	}

	/**********************************************************************************
	 * 判断终端的警情分析是否是属于自己判断 (有多个通信中心), 警情要永久保存
	 */
	public boolean callLetterBelong(String callLetter) {
		return nodehash.get(callLetter).equals(ComCenter.systemname);
	}
}
