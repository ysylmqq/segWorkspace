/*
********************************************************************************************
Discription:  警情分配类
			  
Written By:   ZXZ
Date:         2014-10-23
Version:      1.0

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
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import cc.chinagps.gboss.database.DBUtil;

public class AlarmAllot extends Thread {
	/*
	 * 坐席处警时间
	 */
	private static class HandleSeat {
		private String seatname;
		private long handletime;
		private HandleSeat(String seatname) {
			this.seatname = seatname;
			handletime = System.currentTimeMillis();
		}
	};

	//坐度信息，从AlarmManager得来引用
	private ConcurrentHashMap<String, SeatClientInfo> seatclientMap;
	
	//终端和坐席处警的对应关系，终端历史处警记录，只保留最近后3天的, 每个终端只保留最后三个不同的坐席
	private ConcurrentHashMap<String, ArrayList<HandleSeat>> handleseatMap;
	private ReentrantReadWriteLock handleseatRWLock;
	
	//构造函数
	public AlarmAllot(ConcurrentHashMap<String, SeatClientInfo> seatclientMap) {
		super("HandleAlarm-HisorySeat");
		this.seatclientMap = seatclientMap;
		this.handleseatMap = new ConcurrentHashMap<String, ArrayList<HandleSeat>>();
		this.handleseatRWLock = new ReentrantReadWriteLock();
	}
	
	//分配新的警情到坐席，分配策略请参考
	public SeatClientInfo allotNewAlarm(AlarmInfo alarminfo) {
		//先检查是否有置忙坐席，呼号匹配
		SeatClientInfo seatclient = getBusySeat(alarminfo);
		if (seatclient == null) {
			//后先看有没有最近坐席处理过这终端
			seatclient = getLastHandleSeat(alarminfo);
			if (seatclient == null) {
				//再查找最长空闲坐席
				seatclient = getLongIdleSeat(alarminfo);
			}
		}
		return seatclient;
	}
	
	//添加最后处警记录
	public void SetHandleSeat(String callletter, String seatname) {
		try {
			ArrayList<HandleSeat> seatlist = handleseatMap.get(callletter);
			if (seatlist == null) {
				seatlist = new ArrayList<HandleSeat>();
				seatlist.add(new HandleSeat(seatname));
				handleseatMap.put(callletter, seatlist);
			} else {
				handleseatRWLock.writeLock().lock();
				try {
					//先删除一天前的记录
					long nowtime = System.currentTimeMillis() - 24*3600000;
					for(int i=seatlist.size()-1; i>=0; i--) {
						if (seatlist.get(i).handletime < nowtime) {
							seatlist.remove(i);
							break;
						}
					}
					//处警信息按先后顺序逆序排列
					for(int i=0; i<seatlist.size(); i++) {
						if (seatlist.get(i).seatname.equals(seatname)) {
							seatlist.remove(i);
							break;
						}
					}
					seatlist.add(0, new HandleSeat(seatname));
				} finally {
					handleseatRWLock.writeLock().unlock();
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//判断某个坐席已经置忙，但是该坐席已经在接听某客户的电话, 优先把此客户车辆的警情分给他
	private SeatClientInfo getBusySeat(AlarmInfo alarminfo) {
		try {
			//如果某坐席置忙，并且有置忙呼号，则把这警情分给该坐席
			for(SeatClientInfo seatclient: seatclientMap.values()) {
				if (seatclient.isbusy() && alarminfo.callLetter.equals(seatclient.busycallLetter)) {
					return seatclient;
				}
			}
		} catch(Exception e){
		}
		return null;
	}
	
	//判断是否有空闲坐席，最近处理过这个终端
	private SeatClientInfo getLastHandleSeat(AlarmInfo alarminfo) {
		try {
			ArrayList<HandleSeat> seatlist = handleseatMap.get(alarminfo.callLetter);
			if (seatlist == null)
				return null;

			handleseatRWLock.readLock().lock();
			try {
				//从先后顺序取
				long nowtime = System.currentTimeMillis() - 24 * 3600000;
				for(int i=0; i<seatlist.size(); i++) {
					if (seatlist.get(i).handletime < nowtime) {
						return null;
					}
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
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	//取最长时间空闲的坐席，
	private SeatClientInfo getLongIdleSeat(AlarmInfo alarminfo) {
		SeatClientInfo ret = null;
		long minidletime = System.currentTimeMillis();
		try {
			//如果某坐席置忙，并且有置忙呼号，则把这警情分给该坐席
			for(SeatClientInfo seatclient: seatclientMap.values()) {
				//找到最长空闲坐席
				if (!seatclient.isbusy()) {
					if (ret == null || minidletime > seatclient.idlestarttime) {
						ret = seatclient;
						minidletime = seatclient.idlestarttime;
					}
				}
			}
		} catch(Exception e){
		}
		return ret;
	}
	
	//从数据库读某终端最后处理的二个坐席，如果都忙
	@Override
	public void start() {
		readHistoryFromDB();
		super.start();
	}

	//线程开始时从数据库读最近3天的处警记录，初始化坐席和终端的对应关系
    private void readHistoryFromDB() {
		String sql = "SELECT call_letter, handle_seatname, handle_time FROM t_al_alarm WHERE handle_time IS NOT NULL AND handle_time >= DATE_ADD(SYSDATE(),INTERVAL -3 DAY) ORDER BY call_letter, handle_seatname, handle_time DESC";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			//获取数据库连接
			con = DBUtil.openConnection();
			//创建PreparedStatement
			pst = con.prepareStatement(sql);
			//查询
			rs = pst.executeQuery();
			String callletter = null;
			HandleSeat handleseat = null;
			ArrayList<HandleSeat> listhandle = null; //new ArrayList<HandleSeat>();
			//遍历结果集
			while(rs.next()){
				String tmp = DBUtil.GetStringFromColumn(rs, 1);
				//如果刚开始或者呼号改变
				if (callletter == null || !callletter.equals(tmp)) {
					callletter = tmp;
					handleseat = new HandleSeat(DBUtil.GetStringFromColumn(rs, 2));
					Timestamp handletime = rs.getTimestamp(3);
					if (handletime == null) {
						handleseat.handletime = 0;
					} else {
						handleseat.handletime = handletime.getTime();
					}
					listhandle = new ArrayList<HandleSeat>();
					listhandle.add(handleseat);
					handleseatMap.put(callletter, listhandle);
				} else {
					//如果呼号相同, 则判断坐席名是否相同
					String seatname = DBUtil.GetStringFromColumn(rs, 2);
					//坐席名相同，只取第一个, 后面的不要
					if (seatname.equals(handleseat.seatname)) {
						continue;
					}
					//不同继续添加坐席处警历史
					handleseat = new HandleSeat(seatname);
					Timestamp handletime = rs.getTimestamp(3);
					if (handletime == null) {
						handleseat.handletime = 0;
					} else {
						handleseat.handletime = handletime.getTime();
						//handleseat.handletime = rs.getTimestamp(3).getTime();
					}
					listhandle.add(handleseat);
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		DBUtil.closeDB(rs, pst, con);
    }
	
	/****************************************************************************************************
	 * 线程，删除过时的坐席和终端的处警对应关系
	 * 
	 */
	@Override
	public void run() {
		while(true) {
			try {
				for(Entry<String, ArrayList<HandleSeat>> entry: handleseatMap.entrySet()) {
					ArrayList<HandleSeat> listseat = entry.getValue();
					for(int i = listseat.size() - 1; i >= 0; i--) {
						//如果处警时间是3天前
						if (listseat.get(i).handletime < System.currentTimeMillis() - 3*24*3600000) {
							listseat.remove(i);
						}
					}
					if (listseat.isEmpty()) {
						handleseatMap.remove(entry.getKey());
					}
				}
			} catch (Throwable e) {
			}
			//1小时循环
			try {
				Thread.sleep(3600000);
			} catch (Throwable e) {
			}
		}
	}
}
