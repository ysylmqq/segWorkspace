/*
********************************************************************************************
Discription:  分配坐席警情, 通信中心不调用，不过UDP广播时，要继承此类
			  
			  
Written By:   ZXZ
Date:         2014-09-05
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.websocket;

import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AllotAlarm;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsSimpleInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class AllotAlarmHandler extends ClientBaseHandler {
    public String username = "";   //坐席登录名
    public String callLetter = ""; //已经注册的全部呼号（包括以前注册的）
    public String alarmsn = "";    //警情唯一序列号
    public long alarmtime = 0;     //警情发生时间，不一定是GPS时间, 以系统判断时间为准
    public int alarmid = 0;        //警情类别ID
    public String alarmname = "";  //警情名称
    public GpsSimpleInfo gpsinfo = null; //gps位置
    public int alarmcount = 0;      //未处理警情的总数（包括本条）
    public boolean append = false;  //是否是正在处理的终端后续上报，追加的警情（true:是追加, false(不是追加)，默认false）

    public AllotAlarmHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public int decode() {
		try {
			AllotAlarm udpallot = AllotAlarm.parseFrom(msgcontent);
			username = udpallot.getUsername().trim();
			callLetter = udpallot.getCallLetter().trim();
			alarmsn = udpallot.getAlarmsn().trim();
			alarmtime = udpallot.getAlarmTime();
			alarmid = udpallot.getAlarmid();
			alarmname = udpallot.getAlarmname().trim();
			if (udpallot.hasGpsinfo()) 
				gpsinfo = udpallot.getGpsinfo();
			if (udpallot.hasAlarmcount())
				alarmcount = udpallot.getAlarmcount();
			if (udpallot.hasAppend())
				append = udpallot.getAppend();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		//解码失败时，也要执行返回
		return ResultCode.OK;
	}

	@Override
	public void run() {
	}

	@Override
	public byte[] response() {
		return null;
	}

}
