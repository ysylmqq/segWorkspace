/*
********************************************************************************************
Discription:  坐席基本信息，继承于客户端的基本信息，主要是分配警情用
			  
			  
Written By:   ZXZ
Date:         2014-08-22
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
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.alarmarray.UdpHandler.SeatStatusHandler;
import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.UnitInfoManager.UnitInfo;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.SeatStatus;
import cc.chinagps.gboss.database.DBUtil;

public class SeatClientInfo extends WebsocketClientInfo {

	//坐席5种状态
	public static final short INITSTATUS = 1;	//初始化
	public static final short LOGINSTATUS = 2;  //登录
	public static final short BUSYSTATUS = 3;   //忙碌
	public static final short IDLESTATUS = 4;   //空闲
	public static final short LOGOUTSTATUS = 5; //退录

	//坐席三种处警状态
	public static final short Noallot = 1;  //未分配警情(包括挂警)
	public static final short Alloting = 2; //正在分配警情
	public static final short Alloted = 3;  //已分配警情
	
	public String busycallLetter;	//坐席忙时能接收某辆车的警情（例如：电话正在处理的警情）
	private String comcentername;	//登录到那个通信中心
	public long idlestarttime;		//空闲开始时间, 如果为0表示忙碌
	private short status; 			//登录忙闲状态
	private short allot;			//处警状态
	public ArrayList<String> alarmcallletterlist;	//一般情况下，一坐席只处理一个警情，但取消挂起后，可以处多个警情，表示正在处警的呼号
	public long closetime;			//连接断开关闭时间，坐席断开30秒重连，警情不被重分给其他人
	public ConcurrentHashMap<String, Boolean> alarmorgs;	//能处警的公司（组织架构对应关系） 
	
	public SeatClientInfo() {
		this.busycallLetter = null;
		this.comcentername = ComCenter.systemname;
		this.idlestarttime = 0;		//System.currentTimeMillis();
		this.status = INITSTATUS;	//初始化状态
		this.allot = Noallot;
		this.alarmcallletterlist = new ArrayList<String>();
		this.closetime = Long.MAX_VALUE;
		this.clienttype1 = "seatclient";
		this.alarmorgs = new ConcurrentHashMap<String, Boolean>();
	}

	public SeatClientInfo(byte[] content) {
		try {
			SeatStatus status = SeatStatus.parseFrom(content);
			if (status.hasBusycallLetter()) {
				this.busycallLetter = status.getBusycallLetter();
			}
			this.comcentername = status.getSlaver();
			this.username = status.getSeatname();
			this.userid = status.getSeatid();
			this.clienttype1 = status.getClienttype();
			this.clientversion = status.getClientversion();
			this.closed = status.getClosed();
			this.ipaddr = status.getIpaddr();
			this.status = (short)status.getStatustype();
			this.allot = (short)status.getAllot();
			this.idlestarttime = status.getIdlestarttime();
			this.alarmcallletterlist = new ArrayList<String>();
			if (status.getAlarmcallLetterCount() > 0) {
				this.alarmcallletterlist.addAll(status.getAlarmcallLetterList());
			}
			this.closetime = Long.MAX_VALUE;
			
			this.alarmorgs = new ConcurrentHashMap<String, Boolean>();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//设置坐席连接断开，并同步到ZK
	public void setZkClosed() {
		this.closed = true;
		if (ComCenter.isdistributed) {
			SeatClientInfo seat = AlarmManager.alarmmanager.findSeat(username); 
			if (seat != null && seat.isLoginSelf()) {
				ZooKeeperAlarm.zookeeperalarm.updateSeat(this);
				AlarmManager.alarmmanager.multicastSeatInfo(this);
			}
		}
	}
	
	//复制老坐席信息
	//close状态不要复制
	public void copyseat(SeatClientInfo oldseat) {
		this.busycallLetter = oldseat.busycallLetter;
		this.status = oldseat.status;
		this.allot = oldseat.allot;
		this.idlestarttime = oldseat.idlestarttime;
		this.alarmcallletterlist = oldseat.alarmcallletterlist;
		this.allUnitSInfoType = oldseat.allUnitSInfoType;
		this.monitorcallletterMap = oldseat.monitorcallletterMap;
		this.sendmsglist = oldseat.sendmsglist;
		this.alarmorgs = oldseat.alarmorgs;
	}
	
	//序列化
	public byte[] serialize() {
		SeatStatus.Builder statusbuilder = SeatStatus.newBuilder();
		statusbuilder.setSlaver(comcentername);
		statusbuilder.setSeatname(username);
		statusbuilder.setSeatid(userid);
		statusbuilder.setClienttype(clienttype1);
		statusbuilder.setClientversion(clientversion);
		statusbuilder.setClosed(closed);
		statusbuilder.setIpaddr(ipaddr);
		statusbuilder.setStatustype(status);
		statusbuilder.setAllot(allot);
		statusbuilder.setIdlestarttime(idlestarttime);
		if (busycallLetter != null && !busycallLetter.isEmpty()) {
			statusbuilder.setBusycallLetter(busycallLetter);
		}
		if (alarmcallletterlist != null && !alarmcallletterlist.isEmpty()) {
			statusbuilder.addAllAlarmcallLetter(alarmcallletterlist);
		}
		return statusbuilder.build().toByteArray();
	}

	
	public void resetidle() {
		idlestarttime = 0;
	}
	
	//设置警情分配状态, 同时应改变空闲时间
	public void setallot(short vallot, String handlecallletter, boolean savezk) {
		if (vallot == Alloted) {
			this.allot = Alloted;
			System.out.println("Seat: " + username + " allot status is Alloted, and savezk is " + savezk);
			synchronized(alarmcallletterlist) {
				if (!alarmcallletterlist.contains(handlecallletter)) {
					//只要加一次, 同呼号
					alarmcallletterlist.add(handlecallletter);
				}
			}
			idlestarttime = 0;
		} else if (vallot == Noallot) {
			synchronized(alarmcallletterlist) {
				for(int i=alarmcallletterlist.size()-1; i>=0; i--) {
					if (alarmcallletterlist.get(i).equals(handlecallletter)) {
						alarmcallletterlist.remove(i);
					}
				}
			}
			if (alarmcallletterlist.isEmpty()) {
				System.out.println("Seat: " + username + " allot status is Noallot, and savezk is " + savezk);
				this.allot = Noallot;
				if (idlestarttime == 0) {
					idlestarttime = System.currentTimeMillis();
				}
			}
		} else {
			//正在分配
			synchronized(alarmcallletterlist) {
				if (!alarmcallletterlist.contains(handlecallletter)) {
					//只要加一次, 同呼号
					alarmcallletterlist.add(handlecallletter);
				}
			}
			if (this.allot != Alloted) {
				//正在分配
				System.out.println("Seat: " + username + " allot status is alloting, and savezk is " + savezk);
				this.allot = vallot;
			}
		}
		if (savezk && ComCenter.isdistributed) {
			ZooKeeperAlarm.zookeeperalarm.updateSeat(this);
			AlarmManager.alarmmanager.multicastSeatInfo(this);
		}
	}
	public short getallot() {
		return this.allot;
	}
	
	//设置处警置忙状态，因为要同步更新ZK，所以把status变成私有
	public void setstatus(short status) {
		this.status = status;
		if (status == BUSYSTATUS) {
			idlestarttime = 0;
		} else if (idlestarttime == 0) {
			idlestarttime = System.currentTimeMillis();
		}
	}
	public void setstatus(short status, String busycaller) {
		setstatus(status);
		if (status == BUSYSTATUS) {
			this.busycallLetter = busycaller; 
		} else {
			this.busycallLetter = null;
		}
		if (ComCenter.isdistributed) {
			if (busycaller == null) {
				System.out.println("Seat: " + username + " status is " + status + ", and busycaller is " + busycaller);
			} else {
				System.out.println("Seat: " + username + " status is " + status + ", and busycaller is null");
			}
			ZooKeeperAlarm.zookeeperalarm.updateSeat(this);
			AlarmManager.alarmmanager.multicastSeatInfo(this);
		}
	}
	//接收UDP广播消息，同步坐席状态
	public void setstatus(SeatStatusHandler udphandler) {
		setstatus(udphandler.status);
		if (status == BUSYSTATUS) {
			this.busycallLetter = udphandler.busycallLetter; 
		} else {
			this.busycallLetter = null;
		}
		if (!isLoginSelf()) {
			this.comcentername = udphandler.slaver;
		}
		this.allot = udphandler.allot;
		this.idlestarttime = udphandler.idlestarttime;
		this.username = udphandler.seatname;
		this.userid = udphandler.seatid;
		this.ipaddr = udphandler.ipaddr;
	    this.clienttype1 = udphandler.clienttype;
		this.clientversion = udphandler.clientversion;
		this.closed = udphandler.closed;
		if (this.alarmcallletterlist == null) {
			this.alarmcallletterlist = new ArrayList<String>();
		} else {
			this.alarmcallletterlist.clear();
		}
		if (udphandler.alarmcallLetterlist.size() > 0) {
			this.alarmcallletterlist.addAll(udphandler.alarmcallLetterlist);
		}
	}

	//返回处警置忙状态
	public short getstatus() {
		return status;
	}
	
	//坐席是否忙碌, 能否分配警情
	public boolean isbusy() {
		return (status != IDLESTATUS || allot != Noallot);
	}

	//是否登录自己
	public boolean isLoginSelf() {
		return comcentername.equals(ComCenter.systemname);
	}
	
	//能否处理某终端的警情
	public boolean canHandleAlarm(UnitInfo unitinfo) {
		if (unitinfo == null) {
			return false;
		}
		//如果公司名称正好相同，则可以
		if (alarmorgs.contains(unitinfo.orgcodes)) {
			return true;
		}
		//如果以某公司开始，也可以
		for(String orgcode : alarmorgs.keySet()) {
			if (unitinfo.orgcodes.startsWith(orgcode)) {
				return true;
			}
		}
		return false;
	}
	
	// [start] 从数据库读能处理警情的机构列表
	public void getAlarmOrgs() throws Exception {
		Connection readcon = null; // 使用连接池
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String strSQL = "SELECT a.op_id, a.org_id, o.org_no, o.org_type, o.org_code FROM t_ba_op_org a, t_ba_org o WHERE a.org_id=o.org_id AND a.op_id=?";
			// 创建PreparedStatement
			readcon = DBUtil.getConnection();
			pst = readcon.prepareStatement(strSQL);
			pst.setString(1, userid);
			// 查询
			rs = pst.executeQuery();
			while (rs.next()) {
				String org_no = DBUtil.GetStringFromColumn(rs, 3);
				String org_type = DBUtil.GetStringFromColumn(rs, 4);
				String org_code = DBUtil.GetStringFromColumn(rs, 5);
				org_no += org_type; 
				//集团用户或私家车
				if (org_code.length() > 1) {
					org_no += org_code;
				}
				//添加到可处警公司队列
				alarmorgs.put(org_no, true);
			}
			// 如果有新的状态设置
		} finally {
			DBUtil.closeDB(rs, pst, readcon);
		}
	}
	// [end]
	
}
