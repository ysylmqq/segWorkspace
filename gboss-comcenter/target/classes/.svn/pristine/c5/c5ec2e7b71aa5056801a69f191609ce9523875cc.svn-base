/*
 ********************************************************************************************
Discription: 警情队列管理
			  警情处理流程:
			  本警情分析线程分析出警情后，或者通信接口收到其他程序送来的警情
			 （1）添加到警情队列，同时保存到数据库，（如果保存数据库(连接断开)不成功，用线程再保存）
			 （2）将新警情分配给坐席
			 （3）每条警情状态，在所有的通信中心（分布式）中结构和内容一样，这样保证Master Down后
			 
			 分配警情是由Master统一分配
			 服务器同步坐席状态、警情列表
			 
			但是坐席列表、警情列表则是各自服务器返回，所以有可能会出现不同步
			  
Written By:  ZXZ
Date:        2014-08-22
Version:     1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
 */
package cc.chinagps.gboss.alarmarray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.google.protobuf.ByteString;
import cc.chinagps.gboss.alarmarray.UdpHandler.AlarmUDP;
import cc.chinagps.gboss.alarmarray.UdpHandler.SeatStatusACKHandler;
import cc.chinagps.gboss.alarmarray.UdpHandler.SeatStatusHandler;
import cc.chinagps.gboss.alarmarray.UdpHandler.UDPAllotAlarmACKHandler;
import cc.chinagps.gboss.alarmarray.UdpHandler.UDPAllotTransferAlarmACKHandler;
import cc.chinagps.gboss.alarmarray.UdpHandler.UDPAskSyncAlarmListACKHandler;
import cc.chinagps.gboss.alarmarray.UdpHandler.UDPAskSyncAlarmListHandler;
import cc.chinagps.gboss.alarmarray.UdpHandler.UDPCancelPauseAlarmHandler;
import cc.chinagps.gboss.alarmarray.UdpHandler.UDPNewAlarmHandler;
import cc.chinagps.gboss.alarmarray.UdpHandler.UDPPauseAlarmHandler;
import cc.chinagps.gboss.alarmarray.UdpHandler.UDPTransferAlarmACKHandler;
import cc.chinagps.gboss.alarmarray.UdpHandler.UDPTransferAlarmHandler;
import cc.chinagps.gboss.alarmarray.websocket.AllotAlarmACKHandler;
import cc.chinagps.gboss.alarmarray.websocket.AllotTransferAlarmACKHandler;
import cc.chinagps.gboss.alarmarray.websocket.CancelPauseAlarmHandler;
import cc.chinagps.gboss.alarmarray.websocket.HandleAlarmHandler;
import cc.chinagps.gboss.alarmarray.websocket.PauseAlarmHandler;
import cc.chinagps.gboss.alarmarray.websocket.TransferAlarmHandler;
import cc.chinagps.gboss.alarmarray.AlarmAnalyse.AlarmResult;
import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AllotAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AllotAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.CancelPauseAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.AskSyncAlarmList;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.AskSyncAlarmList_ACK;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.SeatStatus;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.TransferAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.HandleAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.UDPNewAlarm;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsSimpleInfo;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.websocket.NewAlarmHandler;
import cc.chinagps.gboss.database.DBUtil;
import cc.chinagps.lib.util.SystemConfig;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AllotTransferAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AllotTransferAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.PauseAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.TransferAlarm_ACK;

public class AlarmManager extends Thread {
	/*
	 * 坐席处警时间
	 */
	private static class HandleSeatName {
		private String seatname;
		private long handletime;

		private HandleSeatName(String seatname) {
			this.seatname = seatname;
			handletime = System.currentTimeMillis();
		}
	};

	// 广播发送信息记录
	private static class multicastInfo {
		private String sn;
		private int msgtype;
		private byte[] content;
		private long sendtime;
		private boolean sended;

		public multicastInfo(String sn, int msgtype, byte[] content) {
			sendtime = System.currentTimeMillis();
			this.sn = sn;
			this.msgtype = msgtype;
			this.content = content;
			this.sended = false;
		}
	};

	public static AlarmManager alarmmanager = null;
	public static AlarmAnalyse alarmanalyse; // 主要分析过程, 在别的类中要调用，所以用public
	public static final short AlarmListCount = 3; // 警情队列数量
	// 监控信息
	public static int maxseatcount; // 最大坐席连接数
	public static int seatcount;    // 最新坐席连接数

	private AlarmUDP alarmudp; // UDP广播协议
	private ConcurrentHashMap<String, multicastInfo> multicastmap; // 已经广播的队列，主要是担心广播有不成功的可能

	private ArrayList<ArrayList<AlarmInfo>> alarmlists; // 警情队列分三级，最高级为1级(在alarmlist[0])，最低为3级(在alarmlist[2])
	private ReentrantReadWriteLock alarmRWLock;

	private ConcurrentHashMap<String, SeatClientInfo> seatclientMap;
	// 终端和坐席处警的对应关系，终端历史处警记录，只保留最近后3天的, 每个终端只保留最后三个不同的坐席
	private ConcurrentHashMap<String, ArrayList<HandleSeatName>> handleseatMap;
	private ReentrantReadWriteLock handleseatRWLock;

	// 构造函数，初始华警情队列
	public AlarmManager() {
		super("AlarmManager"); // 线程名称
		this.alarmudp = null;
		maxseatcount = 0;
		seatcount = 0;
		// 读写锁
		this.alarmRWLock = new ReentrantReadWriteLock();
		this.seatclientMap = new ConcurrentHashMap<String, SeatClientInfo>();
		this.alarmlists = new ArrayList<ArrayList<AlarmInfo>>();

		this.handleseatMap = new ConcurrentHashMap<String, ArrayList<HandleSeatName>>();
		this.handleseatRWLock = new ReentrantReadWriteLock();

		for (int i = 0; i < AlarmListCount; i++) {
			alarmlists.add(new ArrayList<AlarmInfo>());
		}
		alarmanalyse = new AlarmAnalyse();
		if (ComCenter.isdistributed) {
			// 只有分布式，才要广播消息
			this.multicastmap = new ConcurrentHashMap<String, multicastInfo>();
			// 分布式才用UDP广播
			int udpport = Integer.valueOf(SystemConfig.getAlarmProperties("udpport"));
			String multicastip = SystemConfig.getAlarmProperties("multicastip");
			alarmudp = new AlarmUDP(multicastip, udpport);
			try {
				alarmudp.start();
				System.out.println("alarmudp start");
			} catch (Exception e) {
				System.out.println("alarmudp not start: ");
				e.printStackTrace();
			}
		}
	}

	// 初始化，生成变量
	public boolean init() {
		try {
			readHistoryFromDB();
			// 开始线程, 重发广播消息
			start();
			alarmanalyse.start();
			return true;
		} catch (Exception e) {
			System.out.println("alarmudp not start: ");
			e.printStackTrace();
		}
		return false;
	}

	// [start] 坐席相关方法
	/****************************************************************************************************
	 * 管理坐席
	 * 
	 */
	// 增加坐席
	public int appendSeat(SeatClientInfo seat) {
		try {
			seat.getAlarmOrgs();	//读坐席可处理警情的公司列表
			SeatClientInfo oldseat = seatclientMap.get(seat.username);
			// 客户端发了重复登录报文
			if (oldseat == seat) {
				return ResultCode.OK;
			}
			if (oldseat != null) {
				if (!oldseat.isClosed()) {
					// 如果坐席在本通信中心已经登录
					return ResultCode.SeatExist_Error;
				}
				seat.copyseat(oldseat);
				seatcount--;
			}
			seatclientMap.put(seat.username, seat);
			seatcount++;
			if (maxseatcount < seatcount) {
				maxseatcount = seatcount;
			}
			
			if (ComCenter.isdistributed) {
				if (oldseat != null) {
					ZooKeeperAlarm.zookeeperalarm.updateSeat(seat);
					multicastSeatInfo(seat);
				} else if (!ZooKeeperAlarm.zookeeperalarm.appendSeat(seat)) {
					// 如果坐席在其他通信中心已经登录
					return ResultCode.ZooKeeper_Error;
				}
			}
			return ResultCode.OK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultCode.ComCenter_Error;
	}
	public String getSeatIpaddr(String seatname) {
		SeatClientInfo seat = seatclientMap.get(seatname);
		// 客户端发了重复登录报文
		if (seat != null) {
			return seat.ipaddr;
		}
		return "";
	}

	// 增加ZooKeeper中的坐席，先判断坐席是否已经存在
	public void appendZKSeat(SeatClientInfo seat) {
		try {
			if (seatclientMap.get(seat.username) == null) {
				seatclientMap.put(seat.username, seat);
				seatcount++;
				if (maxseatcount < seatcount) {
					maxseatcount = seatcount;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 先删除断开的坐席, 因为不清楚，改变是否有删除的坐席. 要循环判断
	// 有可能队列内有很多数据，但ZK来不及反应，数量较少, 不能删除
	public void adjustZKSeat(List<String> children) {
		try {
			for (String seatname : seatclientMap.keySet()) {
				if (!children.contains(seatname)) {
					// 有可能队列内有很多数据，但ZK来不及反应，数量较少, 不能删除
					SeatClientInfo seat = findSeat(seatname);
					if (!seat.isLoginSelf()) {
						//只能删除不是本服务的坐席，本服务器坐席在Logout, 
						closeSeat(seat);	//removeSeat(seat);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除坐席, 坐席先设置关闭时间。如果30秒内没有重连（用线程判断），则真正删除，
	// 删除时，要把被分配的警情重新分配
	// 设置坐席断开时间
	public void closeSeat(SeatClientInfo seat) {
		seat.setClosed(true);
		seat.closetime = System.currentTimeMillis();
	}

	// 重新分配已断开30秒坐席的警情(包括被其挂起的警情)
	public void reallocSeatAlarm(String seatname) {
		alarmRWLock.writeLock().lock();
		try {
			// 查找队列中是否已经有此呼号的警情
			for (int i = 0; i < AlarmListCount; i++) {
				ArrayList<AlarmInfo> tmplist = alarmlists.get(i);
				for (int j = 0; j < tmplist.size(); j++) {
					AlarmInfo alarminfo = tmplist.get(j);
					if (alarminfo.getseatname().equals(seatname)) {
						alarminfo.setseat(null);
						alarminfo.setstatus(AlarmInfo.NEWSTATUS);
					}
				}
			}
		} finally {
			alarmRWLock.writeLock().unlock();
		}
	}
	
	public void removeSeat(SeatClientInfo seat) {
		try {
			seat.setstatus(SeatClientInfo.LOGOUTSTATUS);
			reallocSeatAlarm(seat.username);
			if (seatclientMap.remove(seat.username) != null) {
				seatcount--;
			}
			if (ComCenter.isdistributed && seat.isLoginSelf()) {
				// 删除ZK中记录
				ZooKeeperAlarm.zookeeperalarm.removeSeat(seat);
				// 广播删除坐席
				multicastSeatInfo(seat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查找坐席
	public SeatClientInfo findSeat(String username) {
		if (username == null || username.isEmpty())
			return null;
		return seatclientMap.get(username);
	}

	/*
	 * seatname 请求坐席名， callLetter 对某呼号能处理警情的坐席 isidle 是否要空闲 noself 是否包括请求坐席本身
	 */
	public ArrayList<SeatClientInfo> askSeatList(String seatname, String callLetter, boolean isidle, boolean noself) {
		ArrayList<SeatClientInfo> ret = new ArrayList<SeatClientInfo>();
		// 先不考虑callLetter
		for (SeatClientInfo seat : seatclientMap.values()) {
			if ((!noself || !seatname.equals(seat.username)) && // 如果不包括自己
					(!isidle || !seat.isbusy())) { // 如果不要求空闲
				ret.add(seat);
			}
		}
		return ret;
	}

	// 广播坐席状态
	public void multicastSeatInfo(SeatClientInfo seat) {
		if (seat.getstatus() == SeatClientInfo.INITSTATUS) {
			//初始化时不用同步
			return;
		}
		SeatStatus.Builder statusbuilder = SeatStatus.newBuilder();
		statusbuilder.setSlaver(ComCenter.systemname);
		statusbuilder.setSeatname(seat.username);
		statusbuilder.setSeatid(seat.userid);
		statusbuilder.setClienttype(seat.clienttype1);
		statusbuilder.setClientversion(seat.clientversion);
		statusbuilder.setClosed(seat.isClosed());
		statusbuilder.setIpaddr(seat.ipaddr);
		if (seat.busycallLetter != null && !seat.busycallLetter.isEmpty()) {
			statusbuilder.setBusycallLetter(seat.busycallLetter);
		}
		statusbuilder.setStatustype(seat.getstatus());
		statusbuilder.setAllot(seat.getallot());
		statusbuilder.setIdlestarttime(seat.idlestarttime);
		if (seat.alarmcallletterlist != null && seat.alarmcallletterlist.size() > 0) {
			statusbuilder.addAllAlarmcallLetter(seat.alarmcallletterlist);
		}
		String strsn = SeatStatusACKHandler.sn(seat.username, seat.getstatus(), seat.getallot());
		multicast(strsn, MessageType.SeatStatus, statusbuilder.build().toByteString());
	}

	//接收广播坐席状态后，设置坐席状态
	public void setSeatStatus(SeatStatusHandler seatstatushandler) {
		try {
			if (seatstatushandler.status == SeatClientInfo.LOGOUTSTATUS) {
				reallocSeatAlarm(seatstatushandler.seatname);
				if (seatclientMap.remove(seatstatushandler.seatname) != null) {
					seatcount--;
				}
			} else {
				SeatClientInfo seat = seatclientMap.get(seatstatushandler.seatname);
				if (seat != null) {
					seat.setstatus(seatstatushandler);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ZooKeeper重新连接成功后，要把连接到本通信中心的坐席重新写到ZK
	public void createSeats() {
		for (SeatClientInfo seat : seatclientMap.values()) {
			if (seat.isLoginSelf() && ComCenter.isdistributed) {
				// 如果坐席登录自己，则写到ZK
				ZooKeeperAlarm.zookeeperalarm.appendSeat(seat);
			}
		}
	}

	// 添加最后处警记录
	public void SetHandleSeat(String callletter, String seatname) {
		try {
			ArrayList<HandleSeatName> seatlist = handleseatMap.get(callletter);
			if (seatlist == null) {
				seatlist = new ArrayList<HandleSeatName>();
				seatlist.add(new HandleSeatName(seatname));
				handleseatMap.put(callletter, seatlist);
			} else {
				handleseatRWLock.writeLock().lock();
				try {
					// 处警信息按先后顺序逆序排列
					for (int i = 0; i < seatlist.size(); i++) {
						if (seatlist.get(i).seatname.equals(seatname)) {
							seatlist.remove(i);
							break;
						}
					}
					seatlist.add(0, new HandleSeatName(seatname));
				} finally {
					handleseatRWLock.writeLock().unlock();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// [end] 坐席相关方法

	// [start] 申请同步Master服务上的警情列表
	public void askSyncAlarmList() {
		AskSyncAlarmList.Builder builder = AskSyncAlarmList.newBuilder();
		builder.setSlaver(ComCenter.systemname);
		UUID uuid = UUID.randomUUID();
		String sn = uuid.toString();
		builder.setSn(sn);
		multicast(sn, MessageType.AskSyncAlarmList, builder.build().toByteString());
	}
	
	//master处理slaver申请同步警情
	public void AskSyncAlarmList(UDPAskSyncAlarmListHandler askhandler) {
		AskSyncAlarmList_ACK.Builder ackbuilder = AskSyncAlarmList_ACK.newBuilder();
		ackbuilder.setSlaver(askhandler.slaver);
		ackbuilder.setSn(askhandler.sn);
		alarmRWLock.readLock().lock();
		try {
			for (ArrayList<AlarmInfo> tmplist : alarmlists) {
				for(AlarmInfo alarminfo: tmplist) {
					AskSyncAlarmList_ACK.SyncAlarmInfo.Builder infobuilder = ackbuilder.addAlarmlistBuilder();
					infobuilder.setCallLetter(alarminfo.callLetter);
					infobuilder.setAlarmsn(alarminfo.alarmsn);
					infobuilder.setAlarmname(alarminfo.alarmname);
					infobuilder.setAlarmTime(alarminfo.alarmtime);
					infobuilder.setAlarmid(alarminfo.alarmid);
					infobuilder.setLevel(alarminfo.level);
					infobuilder.setStatus(alarminfo.getstatus());
					infobuilder.setSavedb(alarminfo.savedb);
					infobuilder.setHandletime(alarminfo.handletime);
					String seatname = alarminfo.getseatname();
					if (seatname != null && !seatname.isEmpty()) {
						infobuilder.setSeatname(seatname);
					}
					ArrayList<Integer> appendidlist = alarminfo.getappendid();
					if (appendidlist != null && appendidlist.size() > 0) {
						infobuilder.addAllAppendidlist(appendidlist);
					}
					if (alarminfo.gpsinfo != null) {
						infobuilder.setGpsinfo(alarminfo.gpsinfo);
					}
				}
			}
		} finally {
			alarmRWLock.readLock().unlock();
		}
		askhandler.response = ackbuilder.build().toByteString();
	}
	//Master应答，Slaver设置
	public void SyncAlarmListACK(UDPAskSyncAlarmListACKHandler syncackhandler) {
		//先删除广播消息
		removeMulticast(syncackhandler.sn, MessageType.AskSyncAlarmList);
		alarmRWLock.writeLock().lock();
		try {
			for(AskSyncAlarmList_ACK.SyncAlarmInfo syncalarminfo: syncackhandler.syncalarmlist) {
				if (findAlarmInfo(syncalarminfo.getCallLetter()) != null) {
					continue;
				}
				AlarmInfo alarminfo = new AlarmInfo(syncalarminfo);
				if (alarminfo.level > 0 && alarminfo.level <= AlarmListCount) {
					alarmlists.get(alarminfo.level-1).add(alarminfo);
				}
			}
		} finally {
			alarmRWLock.writeLock().unlock();
		}
	}
	// [end]
	
	// [start] 添加、删除、查询警情
	/****************************************************************************************************
	 * 坐席请求警情列表 seatname: 坐席登录名 onlyseat: true:只是此坐席能处理的警情, false:全部警情
	 */
	public ArrayList<AlarmInfo> askAlarmList(SeatClientInfo seatinfo, boolean onlyseat) {
		ArrayList<AlarmInfo> ret = new ArrayList<AlarmInfo>();
		alarmRWLock.readLock().lock();
		try {
			if (onlyseat) {	//统计只能处理自己的警
				for (ArrayList<AlarmInfo> tmplist : alarmlists) {
					for(AlarmInfo alarminfo : tmplist) {
						if (seatinfo.canHandleAlarm(alarminfo.unitinfo)) {
							ret.add(alarminfo);
						}
					}
				}
			} else {
				for (ArrayList<AlarmInfo> tmplist : alarmlists) {
					ret.addAll(tmplist);
				}
			}
		} finally {
			alarmRWLock.readLock().unlock();
		}
		return ret;
	}
	public int askAlarmCount(SeatClientInfo seatinfo, boolean onlyseat) {
		int ret = 0;
		alarmRWLock.readLock().lock();
		try {
			if (onlyseat) {	//统计只能处理自己的警
				for (ArrayList<AlarmInfo> tmplist : alarmlists) {
					for(AlarmInfo alarminfo : tmplist) {
						if (seatinfo.canHandleAlarm(alarminfo.unitinfo)) {
							ret ++;
						}
					}
				}
			} else {
				for (ArrayList<AlarmInfo> tmplist : alarmlists) {
					ret += tmplist.size();
				}
			}
		} finally {
			alarmRWLock.readLock().unlock();
		}
		return ret;
	}

	/****************************************************************************************************
	 * 根据呼号和序列号，查找警情 bzk 表示是否要从Zookeeper读（Slaver没有警情队列，只能从ZK读）
	 */
	public AlarmInfo findAlarmInfo(String callLetter) {
		alarmRWLock.readLock().lock();
		try {
			for (int i = 0; i < AlarmListCount; i++) {
				for (AlarmInfo alarminfo : alarmlists.get(i)) {
					if (alarminfo.callLetter.equals(callLetter)) {
						return alarminfo;
					}
				}
			}
		} finally {
			alarmRWLock.readLock().unlock();
		}
		return null;
	}

	/*************************************
	 * 从警情队列删除某呼号的警情
	 */
	public void removeAlarm(String callLetter, String alarmsn) {
		alarmRWLock.writeLock().lock();
		try {
			// 查找队列中是否已经有此呼号的警情
			for (int i = 0; i < AlarmListCount; i++) {
				ArrayList<AlarmInfo> tmplist = alarmlists.get(i);
				for (int j = 0; j < tmplist.size(); j++) {
					if (tmplist.get(j).callLetter.equals(callLetter)) {
						tmplist.remove(j);
						return;
					}
				}
			}
		} finally {
			alarmRWLock.writeLock().unlock();
		}
	}

	/****************************************************************************************************
	 * 插入新警情(分析结果, 或者第三方发来的警情)
	 */
	// 警情分析线程分析出来的新警情，
	public boolean appendAlarm(AlarmResult alarmresult, GpsInfo gpsinfo) {
		// 警情级别错误
		if (alarmresult.alarm.level <= 0 || alarmresult.alarm.level > AlarmListCount)
			return false;
		AlarmInfo alarminfo = new AlarmInfo(alarmresult, gpsinfo);
		// 如果警情只保存数据库
		if (alarmresult.flag == AlarmAnalyse.DATABASEFLAG) {
			saveNewAlarmToDB(alarminfo, null);
			return true;
		}
		// 如果是要处警的警情
		if (alarmresult.flag == AlarmAnalyse.ALARMFLAG) {
			return appendAlarm(alarminfo);
		}
		return true;
	}

	// 从通信接口收到第三方（或者收到Slaver的警情）
	public boolean appendAlarm(NewAlarmHandler alarmhandler) {
		short level = (short) (alarmhandler.level - 1);
		if (level < 0 || level >= AlarmListCount)
			return false;

		AlarmInfo alarminfo = new AlarmInfo(alarmhandler);
		return appendAlarm(alarminfo);
	}

	// 插入新警情公共过程
	// 每个通信中心都有一个队列
	// 警情添加流程：
	// 先判断是否已经存在相同的警，有，不处理
	// 如果是新警情，先保存到数据库，再广播消息，让其他通信中心也增加到其警情队列, 如果是Master, 还要分配警情
	// 后判断终端是否有警，但警情不同，是否添加
	private boolean appendAlarm(AlarmInfo alarminfo) {
		try {
			// 检查此警情呼号是否在警情队列中
			int retexist = existAlarm(alarminfo);
			if (retexist == NewAlarmHandler.EXIST) {
				// 表示已经存在相同的警情ID
				return true;
			}
			if (ComCenter.isdistributed) {
				// 广播新警情消息, 其他通信中心警情队列同步更新, 如果是Master, 分配警情
				UDPNewAlarm.Builder udpnewalarm = UDPNewAlarm.newBuilder();
				udpnewalarm.setSlaver(ComCenter.systemname);
				udpnewalarm.setAlarmid(alarminfo.alarmid);
				udpnewalarm.setLevel(alarminfo.level);
				udpnewalarm.setAlarmsn(alarminfo.alarmsn);
				udpnewalarm.setAlarmTime(alarminfo.alarmtime);
				udpnewalarm.setCallLetter(alarminfo.callLetter);
				udpnewalarm.setGpsinfo(alarminfo.gpsinfo);
				udpnewalarm.setExistalarm(retexist);
				multicast(alarminfo.alarmsn, MessageType.NewAlarm, udpnewalarm.build().toByteString());
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// UDP广播新警情处理
	public void appendAlarm(UDPNewAlarmHandler udpnewalarm) {
		if (udpnewalarm.slaver.equals(ComCenter.systemname)) {
			return;
		}
		AlarmInfo oldalarm = findAlarmInfo(udpnewalarm.callLetter);
		if (udpnewalarm.existalarm == NewAlarmHandler.APPEND) {
			// 此呼号没有警情在警情队列
			if (oldalarm == null) {
				return;
			}
			// 添加新警情ID到老警情中，并且保存到数据库
			oldalarm.appendalarmid(udpnewalarm.alarmid);

			// 判断老警情是否已经分配给坐席, 如果坐席登录到本通信中心，则追加给坐席
			SeatClientInfo seat = oldalarm.getseat();
			if (seat != null && seat.getstatus() != SeatClientInfo.LOGOUTSTATUS && seat.isLoginSelf()) {
				AlarmInfo alarminfo = new AlarmInfo(udpnewalarm);
				allotAlarmToSeat(seat, alarminfo, true);
			}
			return;
		}
		// 同呼号同时有可能有多个警情在不同的通信中心生成，所以还必须判断是否已经有警情
		// 此呼号没有警情在警情队列
		if (oldalarm != null) {
			// 添加新警情ID到老警情中，并且保存到数据库
			oldalarm.appendalarmid(udpnewalarm.alarmid);
			return;
		}
		// 如果是新警情，则插入警情队列
		AlarmInfo newalarminfo = new AlarmInfo(udpnewalarm);
		newalarminfo.savedb = true;	//不用保存，原通信中心保存
		insertNewAlarm(newalarminfo);
	}

	// 插入新警情到队列
	private void insertNewAlarm(AlarmInfo newalarminfo) {
		alarmRWLock.writeLock().lock();
		try {
			alarmlists.get(newalarminfo.level - 1).add(newalarminfo);
		} finally {
			alarmRWLock.writeLock().unlock();
		}
	}

	// 判断警情是否已经存在
	// 0: 已经存在
	// 1: 新警情
	// 2: 呼号有警情，但是要添加警情ID（有其他警情）
	private int existAlarm(AlarmInfo alarminfo) {
		AlarmInfo oldalarm = findAlarmInfo(alarminfo.callLetter);
		// 此呼号没有警情在警情队列
		if (oldalarm == null) {
			saveNewAlarmToDB(alarminfo, null);
			insertNewAlarm(alarminfo);
			return NewAlarmHandler.NEW;
		}
		// 如果警情相同，则不处理
		// 如果警情已经在追加的警情中，则也不处理
		if (oldalarm.appendalarmid(alarminfo.alarmid) == 0) {
			return NewAlarmHandler.EXIST;
		}
		appendAlarmStatusIDToDB(oldalarm);
		// 如果已根本坐席，并且坐席登录到自己，则把新警情添加给坐席
		SeatClientInfo seat = oldalarm.getseat();
		if (seat != null && seat.getstatus() != SeatClientInfo.LOGOUTSTATUS && seat.isLoginSelf()) {
			allotAlarmToSeat(seat, alarminfo, true);
		}
		return NewAlarmHandler.APPEND;
	}

	// [end] 添加、删除、查询警情

	// [start] 分配警情给某坐席
	/****************************************************************************************************
	 * 分配警情给某坐席（有可能是后续添加的同呼号警情，不在警情队列）
	 * 
	 * @param seat
	 * @param alarminfo
	 */
	public void allotAlarmToSeat(SeatClientInfo seat, AlarmInfo alarminfo, boolean append) {
		// 表示坐席是连接到本通信中心（Master）
		alarminfo.setseat(seat);
		alarminfo.setstatus(AlarmInfo.ALLOTINGSTATUS); // 正在分配中
		// 正在分配不用保存到ZK，因为分配只有Master进行, 但是转警时，请求空闲坐席可能有问题
		// 不管什么时候，如果没有Master调度
		seat.setallot(SeatClientInfo.Alloting, alarminfo.callLetter, false);

		if (seat.isLoginSelf()) {
			// 分配警情到坐席
			sendAlarmToSeat(seat, alarminfo.callLetter, alarminfo.alarmsn, alarminfo.alarmtime, alarminfo.alarmid, alarminfo.alarmname, alarminfo.gpsinfo, append);
		} else {
			// 通知Slaver分配警情给坐席, 正在分配不用同步到各通信中心，只要分配结束要同步到各通信中心
			// 只有等到坐席应答后，才删除
			AllotAlarm.Builder allot = AllotAlarm.newBuilder();
			allot.setUsername(seat.username);
			allot.setCallLetter(alarminfo.callLetter);
			allot.setAlarmsn(alarminfo.alarmsn);
			allot.setAlarmTime(alarminfo.alarmtime);
			allot.setAlarmid(alarminfo.alarmid);
			allot.setAlarmname(alarminfo.alarmname);
			allot.setGpsinfo(alarminfo.gpsinfo);
			allot.setAlarmcount(askAlarmCount(seat, true));
			allot.setAppend(append);
			multicast(alarminfo.alarmsn, MessageType.AllotAlarm, allot.build().toByteString());
		}
	}

	// 发送警情信息给坐席
	public void sendAlarmToSeat(SeatClientInfo seat, String callLetter, String alarmsn, long alarmtime, int alarmid, String alarmname, GpsSimpleInfo gpsinfo, boolean append) {
		System.out.println("Allot Alarm:" + alarmsn + ", " + callLetter + " To Seat " + seat.username);
		AllotAlarm.Builder allot = AllotAlarm.newBuilder();
		allot.setUsername(seat.username);
		allot.setCallLetter(callLetter);
		allot.setAlarmsn(alarmsn);
		allot.setAlarmTime(alarmtime);
		allot.setAlarmid(alarmid);
		allot.setAlarmname(alarmname);
		allot.setGpsinfo(gpsinfo);
		allot.setAlarmcount(askAlarmCount(seat, true));
		allot.setAppend(append);

		ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsgbuilder.setId(MessageType.AllotAlarm);
		basemsgbuilder.setContent(allot.build().toByteString());
		seat.appendSendMessage(basemsgbuilder.build());
	}

	//如果坐席置闲，判断坐席是否已经分配警情，如果已经分配警情，则重发一次
	public void reallocSeatAlarm(SeatClientInfo seat) {
		for(String callletter : seat.alarmcallletterlist) {
			AlarmInfo alarminfo = findAlarmInfo(callletter);
			if (alarminfo != null) {
				if (alarminfo.getstatus() == AlarmInfo.ALLOTEDSTATUS &&
					alarminfo.getseatname().equals(seat.username)) {
					sendAlarmToSeat(seat, alarminfo.callLetter, alarminfo.alarmsn, alarminfo.alarmtime, alarminfo.alarmid, alarminfo.alarmname, alarminfo.gpsinfo, false);
					break;
				}
			}
		}
	}
	// [end] 分配警情给某坐席

	// [start] 收到坐席分配警情的应答
	/***************************************************************************************************
	 * 收到坐席分配警情的应答 如果坐席返回OK，则表示坐席开始处理，不能再重新分配, 如果返回失败，再必须再重新分配
	 */
	public void allotAlarmACK(AllotAlarmACKHandler allotalarmack) {
		//不是同一个警，不处理
		AlarmInfo alarminfo = findAlarmInfo(allotalarmack.callLetter);
		if (alarminfo == null) {
			return;
		}
		if (!alarminfo.alarmsn.equals(allotalarmack.alarmsn)) {
			return;
		}
		if (!alarminfo.getseatname().equals(allotalarmack.username)) {
			return;
		}
		SeatClientInfo seat = (SeatClientInfo) (allotalarmack.clientinfo);
		if (allotalarmack.getRetcode() == ResultCode.OK) {
			// 先保存到数据库
			acceptAlarmToDB(allotalarmack);
			seat.setallot(SeatClientInfo.Alloted, allotalarmack.callLetter, true);
		} else {
			// 如果拒绝，则重新计算空闲时间, 先设置
			seat.resetidle();
			seat.setallot(SeatClientInfo.Noallot, allotalarmack.callLetter, false);
		}
		// 如果是分布式系统，因为UDPAllotAlarmACKHandler和AllotAlarmACKHandler内容没区别
		// 下面语句将执行二次（但不影响结果）
		allotAlarmACK(allotalarmack.callLetter, allotalarmack.alarmsn, allotalarmack.username, allotalarmack.getRetcode());

		if (ComCenter.isdistributed) {
			// 广播给其他通信中心,包括自己
			AllotAlarm_ACK.Builder allotack = AllotAlarm_ACK.newBuilder();
			allotack.setRetcode(allotalarmack.getRetcode());
			allotack.setRetmsg(allotalarmack.getRetmsg());
			allotack.setUsername(allotalarmack.username);
			allotack.setCallLetter(allotalarmack.callLetter);
			allotack.setAlarmsn(allotalarmack.alarmsn);
			multicast(allotalarmack.alarmsn, MessageType.AllotAlarm_ACK, allotack.build().toByteString());
		}
	}

	public void allotAlarmACK(UDPAllotAlarmACKHandler udpack) {
		removeMulticast(udpack.alarmsn, MessageType.AllotAlarm);
		// 改变警情状态
		allotAlarmACK(udpack.callLetter, udpack.alarmsn, udpack.username, udpack.getRetcode());
	}

	// 收到坐席（或者Slaver）接受警情的应答，改变警情状态
	private void allotAlarmACK(String callLetter, String alarmsn, String seatname, int allotret) {
		AlarmInfo alarminfo = findAlarmInfo(callLetter);
		if (alarminfo == null) {
			return;
		}
		// 接受（坐席一般必须接受）
		if (allotret == ResultCode.OK) {
			// 如果警情坐席名称不对，则更新警情处理坐席信息
			SeatClientInfo seat = alarminfo.getseat();
			if (seat == null || !seat.username.equals(seatname)) {
				alarminfo.setseat(findSeat(seatname));
			}
			alarminfo.setstatus(AlarmInfo.ALLOTEDSTATUS); // 设置已分配
		} else { // 如果拒绝，重新分配
			alarminfo.setseat(null);
			alarminfo.setstatus(AlarmInfo.NEWSTATUS);
		}
	}

	// [end] 收到坐席分配警情的应答

	// [start] 收到坐席处警结果
	// 收到坐席处警结果，更新数据库，ZOOKEEPER，及删除警情队列
	// 多次收到同一处警信息怎么回复
	public void handleAlarm(HandleAlarmHandler handlealarm) {
		//不是同一个警，不处理
		AlarmInfo alarminfo = findAlarmInfo(handlealarm.callLetter);
		if (alarminfo == null) {
			return;
		}
		if (!alarminfo.alarmsn.equals(handlealarm.alarmsn)) {
			return;
		}
		if (!alarminfo.getseatname().equals(handlealarm.username)) {
			return;
		}
		// 删除警情
		removeAlarm(handlealarm.callLetter, handlealarm.alarmsn);
		// 更新最后处理记录，为下次优先分配
		SetHandleSeat(handlealarm.callLetter, handlealarm.username);
		// 改变坐席处警呼号
		if (handlealarm.clientinfo instanceof SeatClientInfo) {
			SeatClientInfo seat = (SeatClientInfo) (handlealarm.clientinfo);
			seat.setallot(SeatClientInfo.Noallot, handlealarm.callLetter, true);
			if (ComCenter.isdistributed) {
				// 广播消息通知所有通信中心,
				HandleAlarm.Builder handle = HandleAlarm.newBuilder();
				handle.setHandlecode(handlealarm.handlecode);
				handle.setHandlemsg(handlealarm.handlemsg);
				handle.setUsername(handlealarm.username);
				handle.setCallLetter(handlealarm.callLetter);
				handle.setAlarmsn(handlealarm.alarmsn);
				handle.setSlaver(ComCenter.systemname);
				multicast(handlealarm.alarmsn, MessageType.HandleAlarm, handle.build().toByteString());
			}
		} else {
			// UDP广播消息, 同步坐席状态
			SeatClientInfo seat = seatclientMap.get(handlealarm.username);
			if (seat != null) {
				seat.setallot(SeatClientInfo.Noallot, handlealarm.callLetter, false);
			}
		}
	}

	// [end] 收到坐席处警结果

	// [start] 收到坐席挂警
	/***************************************************************************************************
	 * 收到坐席挂警, 设置坐席处警状态，重新分配警情
	 */
	public void pauseAlarm(PauseAlarmHandler pause) {
		//不是同一个警，不处理
		AlarmInfo alarminfo = findAlarmInfo(pause.callLetter);
		if (alarminfo == null) {
			return;
		}
		if (!alarminfo.alarmsn.equals(pause.alarmsn)) {
			return;
		}
		if (!alarminfo.getseatname().equals(pause.username)) {
			return;
		}
		// 改变坐席处警状态
		SeatClientInfo seat = (SeatClientInfo) (pause.clientinfo);
		seat.setallot(SeatClientInfo.Noallot, pause.callLetter, true);
		alarminfo = pauseAlarm(pause.callLetter, seat);
		if (alarminfo != null) {
			// 改变数据库记录状态
			updateAlarmHandleStatus(alarminfo);

			if (ComCenter.isdistributed) {
				// 通知全部通信中心要挂警
				PauseAlarm.Builder handle = PauseAlarm.newBuilder();
				handle.setUsername(pause.username);
				handle.setCallLetter(pause.callLetter);
				handle.setAlarmsn(pause.alarmsn);
				handle.setPausemsg(pause.pausemsg);
				handle.setSlaver(ComCenter.systemname);
				multicast(pause.alarmsn, MessageType.PauseAlarm, handle.build().toByteString());
			}
		}
	}

	// 其他通信中心同步坐席挂警
	public void pauseAlarm(UDPPauseAlarmHandler udppause) {
		SeatClientInfo seat = findSeat(udppause.username);
		/* 前面
		 * seat.setallot(SeatClientInfo.Noallot, pause.callLetter, true)
		 * 已经同步了坐席状态, 可以不用再同步，有可能挂警后马上分配警情，会后收到此消息，
		 */
		//seat.setallot(SeatClientInfo.Noallot, udppause.callLetter, false);
		// 如果警情坐席名称不对，则更新警情处理坐席信息
		pauseAlarm(udppause.callLetter, seat);
	}

	private AlarmInfo pauseAlarm(String callLetter, SeatClientInfo seat) {
		AlarmInfo alarminfo = findAlarmInfo(callLetter);
		if (alarminfo != null) {
			SeatClientInfo oldseat = alarminfo.getseat();
			if (oldseat == null || !oldseat.username.equals(seat.username)) {
				alarminfo.setseat(seat);
			}
			alarminfo.setstatus(AlarmInfo.PAUSESTATUS); // 设置挂警状态
		}
		return alarminfo;
	}

	// [end] 收到坐席挂警

	// [start] 收到坐席取消挂警
	/***************************************************************************************************
	 * 收到坐席取消挂警, 设置坐席处警状态，重新分配警情
	 */
	public void cancelPauseAlarm(CancelPauseAlarmHandler cancelpause) {
		// 改变坐席处警状态
		SeatClientInfo seat = (SeatClientInfo) (cancelpause.clientinfo);
		seat.setallot(SeatClientInfo.Alloted, cancelpause.callLetter, true);
		AlarmInfo alarminfo = cancelPauseAlarm(cancelpause.callLetter, seat);
		if (alarminfo != null) {
			// 改变数据库记录状态
			updateAlarmHandleStatus(alarminfo);
			if (ComCenter.isdistributed) {
				// 通知全部通信中心
				CancelPauseAlarm.Builder handle = CancelPauseAlarm.newBuilder();
				handle.setUsername(cancelpause.username);
				handle.setCallLetter(cancelpause.callLetter);
				handle.setAlarmsn(cancelpause.alarmsn);
				handle.setSlaver(ComCenter.systemname);
				multicast(cancelpause.alarmsn, MessageType.CancelPauseAlarm, handle.build().toByteString());
			}
		}
	}

	// 其他通信中心同步取消挂警
	public void cancelPauseAlarm(UDPCancelPauseAlarmHandler udpcancelpause) {
		SeatClientInfo seat = findSeat(udpcancelpause.username);
		//seat.setallot(SeatClientInfo.Alloted, udpcancelpause.callLetter, false);
		cancelPauseAlarm(udpcancelpause.callLetter, seat);
	}

	private AlarmInfo cancelPauseAlarm(String callLetter, SeatClientInfo seat) {
		AlarmInfo alarminfo = findAlarmInfo(callLetter);
		if (alarminfo != null) {
			// 如果警情坐席名称不对，则更新警情处理坐席信息, 可能是其他坐席取消挂警
			SeatClientInfo oldseat = alarminfo.getseat();
			if (oldseat == null || !oldseat.username.equals(seat.username)) {
				alarminfo.setseat(seat);
			}
			alarminfo.setstatus(AlarmInfo.ALLOTEDSTATUS); // 设置分配状态
		}
		return alarminfo;
	}

	// [end] 收到坐席取消挂警

	// [start] 转警
	// 发送转警信息给目的坐席
	private void sendTransferAlarmToDstSeat(TransferAlarmHandler transferalarm, AlarmInfo alarminfo, SeatClientInfo dstseat) {
		// 改变目的坐席处警状态(正在分配，等应答后才正式更新为已分配或未分本)
		dstseat.setallot(SeatClientInfo.Alloting, alarminfo.callLetter, true);
		alarminfo.setstatus(AlarmInfo.TRANSFERSTATUS);
		
		// 转警到目的坐席
		AllotTransferAlarm.Builder allottransferalarm = AllotTransferAlarm.newBuilder();
		allottransferalarm.setUsername(transferalarm.dstusername);
		allottransferalarm.setCallLetter(alarminfo.callLetter);
		allottransferalarm.setAlarmsn(alarminfo.alarmsn);
		allottransferalarm.setAlarmid(alarminfo.alarmid);
		allottransferalarm.setAlarmTime(alarminfo.alarmtime);
		allottransferalarm.setAlarmname(alarminfo.alarmname);
		allottransferalarm.setGpsinfo(alarminfo.gpsinfo);
		// allottransferalarm.setAlarmcount();
		allottransferalarm.setSrcusername(transferalarm.srcusername);
		allottransferalarm.setTransfermsg(transferalarm.transfermsg);

		ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsgbuilder.setId(MessageType.AllotTransferAlarm);
		basemsgbuilder.setContent(allottransferalarm.build().toByteString());
		// 发送给坐席(添加到发送队列)
		dstseat.appendSendMessage(basemsgbuilder.build());
	}

	// 回复原坐席，转警是否成功
	private void responseTranserAlarmSrcSeat(SeatClientInfo srcseat, int retcode, String retmsg, String srcusername, String dstusername, String callLetter, String alarmsn) {
		// 转警到目的坐席
		TransferAlarm_ACK.Builder transferalarmack = TransferAlarm_ACK.newBuilder();
		transferalarmack.setRetcode(retcode);
		transferalarmack.setRetmsg(retmsg);
		transferalarmack.setSrcusername(srcusername);
		transferalarmack.setDstusername(dstusername);
		transferalarmack.setCallLetter(callLetter);
		transferalarmack.setAlarmsn(alarmsn);
		ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsgbuilder.setId(MessageType.TransferAlarm_ACK);
		basemsgbuilder.setContent(transferalarmack.build().toByteString());
		// 发送给坐席(添加到发送队列)
		srcseat.appendSendMessage(basemsgbuilder.build());
	}

	/***************************************************************************************************
	 * 收到坐席转警, 先判断目的坐席是不是也连接到自己（通信中心）
	 */
	public void transferAlarm(TransferAlarmHandler transferalarm, AlarmInfo alarminfo, SeatClientInfo dstseat) {
		if (dstseat.isLoginSelf()) {
			// 如果目的坐席连接到自己
			sendTransferAlarmToDstSeat(transferalarm, alarminfo, dstseat);
		} else if (ComCenter.isdistributed) {
			// 如果不是，则广播消息，通知其他通信中心发送转警到坐席
			TransferAlarm.Builder handle = TransferAlarm.newBuilder();
			handle.setSrcusername(transferalarm.srcusername);
			handle.setDstusername(transferalarm.dstusername);
			handle.setCallLetter(transferalarm.callLetter);
			handle.setAlarmsn(transferalarm.alarmsn);
			handle.setTransfermsg(transferalarm.transfermsg);
			handle.setSlaver(ComCenter.systemname);
			multicast(transferalarm.alarmsn, MessageType.TransferAlarm, handle.build().toByteString());
		}
	}

	public void transferAlarm(UDPTransferAlarmHandler udptransferalarm, AlarmInfo alarminfo, SeatClientInfo dstseat) {
		// 改变目的坐席处警状态(正在分配，等应答后才正式更新为已分配或未分本)
		if (dstseat.isLoginSelf()) {
			// 如果目的坐席连接到自己
			sendTransferAlarmToDstSeat(udptransferalarm, alarminfo, dstseat);
		}
	}

	// 其他通信中心返回申请转警失败(成功不返回，要等目的坐席返回)
	public void transferAlarmACK(UDPTransferAlarmACKHandler udpack) {
		// 删除广播消息
		removeMulticast(udpack.alarmsn, MessageType.TransferAlarm);
		if (udpack.getRetcode() != ResultCode.OK) {
			// 如果失败，则
			AlarmInfo alarminfo = findAlarmInfo(udpack.callLetter);
			if (alarminfo != null) {
				if (alarminfo.getstatus() != AlarmInfo.TRANSFERSTATUS) {
					//如果不是正在转警的警情，则不处理
					return;
				}
				alarminfo.setstatus(AlarmInfo.ALLOTEDSTATUS);
			}
			SeatClientInfo srcseat = AlarmManager.alarmmanager.findSeat(udpack.srcusername);
			if (srcseat == null) {
				return;
			}
			if (!srcseat.isLoginSelf()) {
				return;
			}
			// 返回原坐席转警失败
			responseTranserAlarmSrcSeat(srcseat, udpack.getRetcode(), udpack.getRetmsg(), udpack.srcusername, udpack.dstusername, udpack.callLetter, udpack.alarmsn);
		}
	}

	/***************************************************************************************************
	 * 收到坐席转警分配给他的应答 如果坐席返回OK，则表示坐席开始处理，不管被转警坐席返回什么，都要通知原坐席
	 */
	public void allotTransferAlarmACK(AllotTransferAlarmACKHandler ack) {
		// 先删除原转警广播消息（可能有）
		removeMulticast(ack.alarmsn, MessageType.TransferAlarm);
		SeatClientInfo dstseat = (SeatClientInfo) (ack.clientinfo);
		SeatClientInfo srcseat = null;
		AlarmInfo alarminfo = findAlarmInfo(ack.callLetter);
		if (alarminfo != null) {
			if (alarminfo.getstatus() != AlarmInfo.TRANSFERSTATUS) {
				//如果不是正在转警的警情，则不处理
				System.out.println("Alarm is not transfering, " + ack.alarmsn);
				return;
			}
			alarminfo.setstatus(AlarmInfo.ALLOTEDSTATUS);
			srcseat = alarminfo.getseat();
			if (srcseat == null) {
				srcseat = findSeat(ack.srcusername);
			}
		}
		if (ack.getRetcode() == ResultCode.OK) {
			if (srcseat != null) {
				srcseat.setallot(SeatClientInfo.Noallot, ack.callLetter, true);
			}
			// 目的坐席同意转警
			dstseat.setallot(SeatClientInfo.Alloted, ack.callLetter, true);
			alarminfo.setseat(dstseat);
			if (ComCenter.isdistributed) {
				// 广播消息,通知其他通信中心更新处警坐席
				AllotTransferAlarm_ACK.Builder ackhandle = AllotTransferAlarm_ACK.newBuilder();
				ackhandle.setRetcode(ack.getRetcode());
				ackhandle.setRetmsg(ack.getRetmsg());
				ackhandle.setSrcusername(srcseat != null ? srcseat.username : ack.srcusername);
				ackhandle.setUsername(ack.username);
				ackhandle.setCallLetter(ack.callLetter);
				ackhandle.setAlarmsn(ack.alarmsn);
				ackhandle.setSlaver(ComCenter.systemname);
				multicast(ack.alarmsn, MessageType.AllotTransferAlarm_ACK, ackhandle.build().toByteString());
			}
		} else {
			// 目的坐席不同意转警, 原坐席状态不用改变
			dstseat.setallot(SeatClientInfo.Noallot, ack.callLetter, true);
			//if (srcseat != null) {
			//	srcseat.setallot1(SeatClientInfo.Alloted, ack.callLetter, true);
			//}
		}
		if (srcseat != null && srcseat.isLoginSelf()) {
			// 如果原坐席登录到自己，则返回转警结果
			responseTranserAlarmSrcSeat(srcseat, ack.getRetcode(), ack.getRetmsg(), ack.srcusername, ack.username, ack.callLetter, ack.alarmsn);
		}
	}

	public void allotTransferAlarmACK(UDPAllotTransferAlarmACKHandler udpack) {
		// 收到广播转警应答消息，删除广播转警应答消息，（自己发，自己删，有可能出问题）
		removeMulticast(udpack.alarmsn, MessageType.AllotTransferAlarm_ACK);
		if (!udpack.slaver.equals(ComCenter.systemname)) {
			// 不是自己发送的
			SeatClientInfo srcseat = findSeat(udpack.srcusername);
			// 收到坐席的转警应答
			if (srcseat != null && srcseat.isLoginSelf()) {
				// 如果原坐席连接到本通信中心，则返回
				responseTranserAlarmSrcSeat(srcseat, udpack.getRetcode(), udpack.getRetmsg(), udpack.srcusername, udpack.username, udpack.callLetter, udpack.alarmsn);
			}
			if (udpack.getRetcode() == ResultCode.OK) {
				// 如果返回转警成功, 设置警情为新坐席
				AlarmInfo alarminfo = findAlarmInfo(udpack.callLetter);
				SeatClientInfo dstseat = findSeat(udpack.username);
				if (alarminfo != null && dstseat != null) {
					alarminfo.setseat(dstseat);
				} else {
					System.out.println("转警时，警情设置为目的坐席失败");
				}
			}
		}
	}

	// [end] 转警

	// [start] UDP广播，重新广播、收到应答删除已发广播等
	/****************************************************************************************************
	 * 如果收到其他通信中心应答（slave是master应答， master是 slave应答） 则删除已发送队列中对应的记录
	 */
	public void removeMulticast(String sn, int msgtype) {
		if (ComCenter.isdistributed) {
			String mapkey = sn + "_" + msgtype;
			//System.out.println("remove multicast: " + mapkey);
			multicastmap.remove(mapkey);
		}
	}

	/***************************************************************************************************
	 * 广播protobuf格式消息ComCenterMessage 添加到广播队列，等待应答，如果10秒内没有应答，则重发
	 */
	private void multicast(String sn, int msgtype, ByteString bytestring) {
		if (ComCenter.isdistributed) {
			// 分布式时才支持
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(msgtype);
			basemsg.setContent(bytestring);
			ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
			msg.addMessages(basemsg.build());
			multicastInfo info = new multicastInfo(sn, msgtype, msg.build().toByteArray());
			// 是否发送失败，
			info.sended = alarmudp.multicast(info.content);
			if (info.sended) {
				System.out.println("UDP multicast:" + sn + ", " + msgtype);
			} else {
				System.out.println("FALSE multicast:" + sn + ", " + msgtype);
			}
			// 添加到发送队列
			String mapkey = sn + "_" + msgtype;
			multicastmap.put(mapkey, info);
		}
	}

	// 判断已广播队列中是否要重新广播
	private void remulticast() {
		if (!ComCenter.isdistributed) {
			return;
		}
		for (multicastInfo info : multicastmap.values()) {
			try {
				long delta = System.currentTimeMillis() - info.sendtime;
				int timeout = 3000; // 3秒
				if (info.sended) {
					timeout = 30000; // 30秒
					if (info.msgtype == MessageType.AllotAlarm || info.msgtype == MessageType.TransferAlarm) {
						// 分配警情和转警因为要等坐席返回，所以时间长些
						timeout = 60000; // 60秒
					}
				}
				if (delta > timeout) { // 如果30秒都没收到应答，则重发
					System.out.println("remulti udp message:" + info.sn + ", " + info.msgtype);
					info.sendtime = System.currentTimeMillis();
					info.sended = alarmudp.multicast(info.content);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	/***************************************************************************************************
	 * 广播一般消息
	 */
	public void multicast(byte[] sendbuf) {
		if (ComCenter.isdistributed) {
			// 分布式才支持
			alarmudp.multicast(sendbuf);
		}
	}

	// [end] UDP广播，重新广播、收到应答删除已发广播等

	// [start] 数据库操作
	/**********************************************************************************************
	 * 保存到MySQL数据库 参数：con: 有可能多个一起保存时，不要每次都去连接池取连接 返回：成功：true，失败：false
	 * 
	 */
	private void saveNewAlarmToDB(AlarmInfo alarminfo, Connection con) {
		// 如果已经保存到数据库，则不再保存
		if (alarminfo.savedb) {
			return;
		}
		Connection tmpcon = con;
		PreparedStatement pst = null;
		String sql = "INSERT t_al_alarm " + "(alarm_sn, unit_id, call_letter, alarm_time, alarm_id, alarm_name, LEVEL, " +
		             "gps_time, lon, lat, speed, course, loc_state, status_ids, total_mile, oil) " +
				     "VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			if (tmpcon == null) {
				tmpcon = DBUtil.openConnection();
			}
			pst = tmpcon.prepareStatement(sql);
			pst.setString(1, alarminfo.alarmsn);
			pst.setLong(2, alarminfo.unitinfo.unitid);
			pst.setString(3, alarminfo.callLetter);
			// pst.setTimestamp(4, new Timestamp(alarminfo.alarmtime));
			pst.setInt(4, alarminfo.alarmid);
			pst.setString(5, alarminfo.alarmname);
			pst.setShort(6, alarminfo.level);
			pst.setTimestamp(7, new Timestamp(alarminfo.gpsinfo.getGpsTime()));
			pst.setDouble(8, alarminfo.gpsinfo.getLng() / 1000000.0);
			pst.setDouble(9, alarminfo.gpsinfo.getLat() / 1000000.0);
			pst.setDouble(10, alarminfo.gpsinfo.getSpeed() / 10.0);
			pst.setInt(11, alarminfo.gpsinfo.getCourse());
			pst.setInt(12, alarminfo.gpsinfo.getLoc() ? 1 : 0);
			StringBuilder statusid = new StringBuilder();
			for (Integer status : alarminfo.gpsinfo.getStatusList()) {
				if (statusid.length() > 0)
					statusid.append(',');
				statusid.append(status);
			}
			pst.setString(13, statusid.toString());
			pst.setInt(14, alarminfo.gpsinfo.getTotalDistance());
			pst.setDouble(15, alarminfo.gpsinfo.getOil() / 100.0);
			pst.executeUpdate();
			alarminfo.savedb = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con == null) {
				DBUtil.closeDB(pst, tmpcon);
			} else {
				DBUtil.closeDB(pst, null);
			}
		}
	}

	// 坐席接受警情后，更新警情
	private void acceptAlarmToDB(AllotAlarmACKHandler allotalarmack) {
		// 如果拒绝接受，则不保存数据库
		if (allotalarmack.getRetcode() != ResultCode.OK) {
			return;
		}
		// 更新接警时间及接警响应率（时长）
		String sql = "UPDATE t_al_alarm SET accept_time=NOW(), accepttime_span=TIME_TO_SEC(TIMEDIFF(NOW(), alarm_time)), handle_seatid=?, handle_seatname=?, handle_status=2 WHERE alarm_sn=?";
		Connection con = null;
		PreparedStatement pst = null;
		try {
			// 获取数据库连接
			con = DBUtil.openConnection();
			// 创建PreparedStatement
			SeatClientInfo seat = (SeatClientInfo) (allotalarmack.clientinfo);
			pst = con.prepareStatement(sql);
			pst.setString(1, seat.userid);
			pst.setString(2, seat.username);
			pst.setString(3, allotalarmack.alarmsn);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(pst, con);
		}
	}

	// 更新后续警情ID到数据库MySQL,不成功，不再保存, 外部不调用
	private void appendAlarmStatusIDToDB(AlarmInfo oldalarm) {
		// 如果生成时没有保存成功，则返回
		if (!oldalarm.savedb) {
			return;
		}
		String sql = "UPDATE t_al_alarm SET append_alarm=? WHERE alarm_sn=?";
		Connection con = null;
		PreparedStatement pst = null;
		try {
			// 获取数据库连接
			con = DBUtil.openConnection();
			// 创建PreparedStatement
			pst = con.prepareStatement(sql);
			// 设置参数
			pst.setString(1, oldalarm.appendids());
			pst.setString(2, oldalarm.alarmsn);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(pst, con);
		}
	}

	// 挂警、取消挂警时，更新处理状态
	private void updateAlarmHandleStatus(AlarmInfo alarm) {
		// 更新接警时间及接警响应率（时长）
		String sql = "UPDATE t_al_alarm SET handle_seatid=?, handle_seatname=?, handle_status=? WHERE alarm_sn=?";
		Connection con = null;
		PreparedStatement pst = null;
		try {
			// 获取数据库连接
			con = DBUtil.openConnection();
			// 创建PreparedStatement
			SeatClientInfo seat = alarm.getseat();
			pst = con.prepareStatement(sql);
			pst.setInt(1, Integer.valueOf(seat.userid));
			pst.setString(2, seat.username);
			pst.setInt(3, alarm.getstatus());
			pst.setString(4, alarm.alarmsn);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(pst, con);
		}
	}

	// 线程开始时从数据库读最近1天的处警记录，初始化坐席和终端的对应关系
	private void readHistoryFromDB() {
		String sql = "SELECT call_letter, handle_seatname, handle_time FROM t_al_alarm WHERE handle_time IS NOT NULL AND handle_time >= DATE_ADD(SYSDATE(),INTERVAL -1 DAY) ORDER BY call_letter, handle_seatname, handle_time DESC";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			// 获取数据库连接
			con = DBUtil.openConnection();
			// 创建PreparedStatement
			pst = con.prepareStatement(sql);
			// 查询
			rs = pst.executeQuery();
			String callletter = null;
			HandleSeatName handleseat = null;
			ArrayList<HandleSeatName> listhandle = null; // new
															// ArrayList<HandleSeatName>();
			// 遍历结果集
			while (rs.next()) {
				String tmp = DBUtil.GetStringFromColumn(rs, 1);
				// 如果刚开始或者呼号改变
				if (callletter == null || !callletter.equals(tmp)) {
					callletter = tmp;
					handleseat = new HandleSeatName(DBUtil.GetStringFromColumn(rs, 2));
					Timestamp handletime = rs.getTimestamp(3);
					if (handletime == null) {
						handleseat.handletime = 0;
					} else {
						handleseat.handletime = handletime.getTime();
					}
					listhandle = new ArrayList<HandleSeatName>();
					listhandle.add(handleseat);
					handleseatMap.put(callletter, listhandle);
				} else {
					// 如果呼号相同, 则判断坐席名是否相同
					String seatname = DBUtil.GetStringFromColumn(rs, 2);
					// 坐席名相同，只取第一个, 后面的不要
					if (seatname.equals(handleseat.seatname)) {
						continue;
					}
					// 不同继续添加坐席处警历史
					handleseat = new HandleSeatName(seatname);
					Timestamp handletime = rs.getTimestamp(3);
					if (handletime == null) {
						handleseat.handletime = 0;
					} else {
						handleseat.handletime = handletime.getTime();
						// handleseat.handletime = rs.getTimestamp(3).getTime();
					}
					listhandle.add(handleseat);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(rs, pst, con);
		}
	}

	// [end] 数据库操作

	// [start] 分配警情到坐席
	// 分配警情到坐席
	private void allotNewAlarm() {
		alarmRWLock.readLock().lock();
		try {
			for (int i = 0; i < AlarmListCount; i++) {
				for (AlarmInfo alarminfo : alarmlists.get(i)) {
					// 如果是新警情，则每条逐一分配
					if (alarminfo.getstatus() == AlarmInfo.NEWSTATUS) {
						// 先检查是否有置忙坐席，呼号匹配
						SeatClientInfo seatclient = getBusySeat(alarminfo);
						if (seatclient == null) {
							// 后先看有没有最近坐席处理过这终端
							seatclient = getLastHandleSeat(alarminfo);
							if (seatclient == null) {
								// 再查找最长空闲坐席
								seatclient = getLongIdleSeat(alarminfo);
							}
						}
						if (seatclient != null) {
							// 如果没有坐席空闲, 则不再分配, 否则将警情分配给坐席
							allotAlarmToSeat(seatclient, alarminfo, false);
						}
					}
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			alarmRWLock.readLock().unlock();
		}
	}

	// 判断某个坐席已经置忙，但是该坐席已经在接听某客户的电话, 优先把此客户车辆的警情分给他
	private SeatClientInfo getBusySeat(AlarmInfo alarminfo) {
		try {
			// 如果某坐席置忙，并且有置忙呼号，则把这警情分给该坐席
			for (SeatClientInfo seatclient : seatclientMap.values()) {
				if (seatclient.isbusy() && alarminfo.callLetter.equals(seatclient.busycallLetter)) {
					return seatclient;
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	// 判断是否有空闲坐席，最近处理过这个终端
	private SeatClientInfo getLastHandleSeat(AlarmInfo alarminfo) {
		try {
			ArrayList<HandleSeatName> seatlist = handleseatMap.get(alarminfo.callLetter);
			if (seatlist == null)
				return null;

			handleseatRWLock.readLock().lock();
			try {
				// 从先后顺序取
				for (int i = 0; i < seatlist.size(); i++) {
					SeatClientInfo seatclient = seatclientMap.get(seatlist.get(i).seatname);
					if (seatclient != null) {
						//如果此坐席不忙，则分配给此坐席
						if (!seatclient.isbusy()) {
							return seatclient;
						}
					}
				}
			} finally {
				handleseatRWLock.readLock().unlock();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 取最长时间空闲的坐席，
	private SeatClientInfo getLongIdleSeat(AlarmInfo alarminfo) {
		SeatClientInfo ret = null;
		long minidletime = System.currentTimeMillis();
		try {
			// 如果某坐席置忙，并且有置忙呼号，则把这警情分给该坐席
			for (SeatClientInfo seatclient : seatclientMap.values()) {
				// 找到最长空闲坐席
				if (!seatclient.isbusy() && seatclient.canHandleAlarm(alarminfo.unitinfo)) {
					if (ret == null || minidletime > seatclient.idlestarttime) {
						ret = seatclient;
						minidletime = seatclient.idlestarttime;
					}
				}
			}
		} catch (Exception e) {
		}
		return ret;
	}

	// [end] 分配警情到坐席

	// [start] 线程主函数
	/****************************************************************************************************
	 * 线程，5秒中判断一次发送队列中的信息，如果超时30秒没有应答，重发， 如果有发送失败，也要重发
	 * 
	 */
	@Override
	public void run() {
		int loopcount = 0;
		while (true) {
			if (ComCenter.isdistributed) {
				// 先检测分布式时，是否有广播消息要重发
				try {
					remulticast();
				} catch (Throwable e) {
					System.out.println("remulticast:");
					e.printStackTrace();
				}
			}
			if (!ComCenter.isdistributed || ZooKeeperAlarm.zookeeperalarm.isMasterd) {
				// 分配警情
				try {
					allotNewAlarm();
				} catch (Throwable e) {
					System.out.println("AllotAlarm:");
					e.printStackTrace();
				}
			}

			if (((++loopcount) % 50) == 1) {
				try {
					// 5秒循环一次, 重新分配坐席断开15秒的警情
					long lnow = System.currentTimeMillis() - 15000; // 15秒
					for (SeatClientInfo seat : seatclientMap.values()) {
						if (seat.isClosed() && seat.closetime < lnow) {
							removeSeat(seat);
						}
					}
				} catch (Throwable e) {
				}
			}

			if (((++loopcount) % 30000) == 1) {
				// 50分钟循环一次，删除坐席处警的对应关系
				long lnow = System.currentTimeMillis() - 24 * 3600000; //一天前
				try {
					for (Entry<String, ArrayList<HandleSeatName>> entry : handleseatMap.entrySet()) {
						ArrayList<HandleSeatName> listseat = entry.getValue();
						handleseatRWLock.writeLock().lock();
						try {
							for (int i = listseat.size() - 1; i >= 0; i--) {
								// 如果处警时间是3天前
								if (listseat.get(i).handletime < lnow) {
									listseat.remove(i);
								}
							}
						} finally {
							handleseatRWLock.writeLock().unlock();
						}
						if (listseat.isEmpty()) {
							handleseatMap.remove(entry.getKey());
						}
					}
				} catch (Throwable e) {
				}
			}

			// 等待100毫秒，重新循环
			try {
				Thread.sleep(100);
			} catch (Throwable e) {
			}
		}
	}
	// [end]
}
