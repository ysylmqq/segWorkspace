/*
********************************************************************************************
Discription:  警情基本信息
              
		  
			  
Written By:   ZXZ
Date:         2014-08-25
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray;

import java.util.ArrayList;
import java.util.UUID;

import cc.chinagps.gboss.alarmarray.AlarmAnalyse.AlarmResult;
import cc.chinagps.gboss.comcenter.UnitInfoManager;
import cc.chinagps.gboss.comcenter.UnitInfoManager.UnitInfo;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.AskSyncAlarmList_ACK;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsSimpleInfo;
import cc.chinagps.gboss.comcenter.websocket.NewAlarmHandler;

public class AlarmInfo {
	//警情处理状态********************************************************************
	public static final short NEWSTATUS = 0;        //刚生成
	public static final short ALLOTINGSTATUS = 1;   //正在分配
	public static final short ALLOTEDSTATUS = 2;    //已经分配
	public static final short PAUSESTATUS = 3;      //用户已经申请挂起
	public static final short HANDLESTATUS = 4;		//用户处理完
	public static final short TRANSFERSTATUS = 5;  	//正在转警

	//警情信息***********************************************************************
	public UnitInfo unitinfo;			//终端ID
	public String callLetter;			//终端呼号
	public String alarmsn;      		//警情唯一序列号（应用程序生成，但是增加成功后，警情队列将用自己的队列）
	public long alarmtime;      		//警情发生时间，不一定是GPS时间, 以系统判断时间为准
	public int alarmid;					//警情id(状态id）
	public String alarmname;			//警情名称(与id一致)
	public short level;					//警情级别
	public GpsSimpleInfo gpsinfo;		//gps位置
	public boolean savedb;				//是否保存到数据库
	private short status;				//警情处理状态(参考前面定义),
	public long handletime;				//表示状态改变的开始时间, 有时坐席断开后不登录，要重新分配
	//因为坐席有可能断开重连，所以只能用坐席名称代替坐席
	//private SeatClientInfo handleseat;	//处警坐席(坐席中同时也包含状态), 一个坐席可能有多个警情，状态不一样
	private String seatname;			//处警坐席名称
	private ArrayList<Integer> appendidlist;	//处理过程中，后续添加的警情
	
    /**********************************************************************************************
     * 构造函数
     */
    public AlarmInfo() {
    	init();
    	this.callLetter = "";
		UUID uuid = UUID.randomUUID();
		this.alarmsn = uuid.toString();
		this.alarmtime = System.currentTimeMillis();
		this.alarmid = 0;
		this.alarmname = "";
		this.level = 1;
		this.gpsinfo = null;
    }

    public AlarmInfo(NewAlarmHandler udpalarm) {
    	init();
    	this.callLetter = udpalarm.callLetter;
   		this.unitinfo = UnitInfoManager.unitinfomanager.getUnitInfo(callLetter);
        this.alarmsn = udpalarm.alarmsn;
        this.alarmtime = udpalarm.alarmtime;
        this.alarmid = udpalarm.alarmid;
        this.alarmname = AlarmManager.alarmanalyse.getAlarmName(alarmid);
        this.level = (short)udpalarm.level;
        this.gpsinfo = GpsSimpleInfo.newBuilder(udpalarm.gpsinfo).build();	//复制
    }

    public AlarmInfo(AskSyncAlarmList_ACK.SyncAlarmInfo syncalarminfo) {
    	init();
    	this.callLetter = syncalarminfo.getCallLetter();
   		this.unitinfo = UnitInfoManager.unitinfomanager.getUnitInfo(callLetter);
		this.alarmsn = syncalarminfo.getAlarmsn();
		this.alarmname = syncalarminfo.getAlarmname();
		this.alarmtime = syncalarminfo.getAlarmTime();
		this.alarmid = syncalarminfo.getAlarmid();
		this.level = (short)(syncalarminfo.getLevel());
		this.status = (short)(syncalarminfo.getStatus());
		this.savedb = true;	//不用保存数据库
		this.handletime = syncalarminfo.getHandletime();
		String seatname = syncalarminfo.getSeatname();
		if (seatname != null && !seatname.isEmpty()) {
			this.seatname = seatname;
		}
		if (syncalarminfo.getAppendidlistCount() > 0) {
			this.appendidlist.addAll(syncalarminfo.getAppendidlistList());
		}
		if (syncalarminfo.hasGpsinfo()) {
	        this.gpsinfo = GpsSimpleInfo.newBuilder(syncalarminfo.getGpsinfo()).build();	//复制
		}
	}

    public AlarmInfo(AlarmResult alarmresult, GpsInfo gps) {
    	init();
    	this.unitinfo = alarmresult.unitinfo;
    	this.callLetter = gps.getCallLetter();
		UUID uuid = UUID.randomUUID();
        this.alarmsn = uuid.toString();
        this.alarmtime = System.currentTimeMillis();
        this.alarmid = alarmresult.alarm.statusid;
        this.alarmname = alarmresult.alarm.statusname;
        //this.alarmname = AlarmManager.alarmanalyse.GetAlarmName(alarmid);
        this.level = alarmresult.alarm.level;
        GpsBaseInfo baseinfo = gps.getBaseInfo();
        GpsSimpleInfo.Builder builder = GpsSimpleInfo.newBuilder();
        builder.setGpsTime(baseinfo.getGpsTime());
        builder.setLoc(baseinfo.getLoc());
        builder.setLat(baseinfo.getLat());
        builder.setLng(baseinfo.getLng());
        builder.setSpeed(baseinfo.getSpeed());
        builder.setCourse(baseinfo.getCourse());
        builder.addAllStatus(baseinfo.getStatusList());
        builder.setTotalDistance(baseinfo.getTotalDistance());
        builder.setOil(baseinfo.getOil());
        this.gpsinfo = builder.build(); 
    }
    private void init() {
    	this.unitinfo = null;
		this.savedb = false;
		this.status = NEWSTATUS;
		this.handletime = System.currentTimeMillis();
		this.appendidlist = new ArrayList<Integer>();
		this.seatname = "";
    }
    
    //因为状态改变，要保存到数据库和ZK，所以变成私有
    public short getstatus() {
    	return this.status;
    }
    public void setstatus(short status) {
    	if (this.status != status) {
    		this.status = status;
    	}
    }
    //因为坐席改变，要保存到数据库和ZK，所以变成私有
    public String getseatname() {
    	return this.seatname;
    }
    public SeatClientInfo getseat() {
    	return AlarmManager.alarmmanager.findSeat(seatname);
    }
    public void setseat(SeatClientInfo seat) {
    	if (seat == null) {
    		this.seatname = "";
    	} else {
    		this.seatname = seat.username;
    	}
    }
    
    public int appendalarmid(int alarmid) {
    	if (this.alarmid == alarmid) {
    		return 0;
    	}
    	for(int id : appendidlist) {
    		if (id == alarmid) {
 	    		return 0;
    		}
    	}
		//添加新警情ID到老警情中，并且保存到数据库
		appendidlist.add(alarmid);
		return alarmid;
    }
    
    public String appendids() {
		StringBuilder stralarms = new StringBuilder(64);
		for(Integer alarmid : appendidlist) {
			if (stralarms.length() > 0)
				stralarms.append(',');
			stralarms.append(alarmid);
		}
		return stralarms.toString();
    }
    
    public ArrayList<Integer> getappendid() {
    	return this.appendidlist;
    }
}
