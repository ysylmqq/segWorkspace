/*
********************************************************************************************
Discription:  坐席请求挂警
			  
			  
Written By:   ZXZ
Date:         2014-08-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.websocket;

import cc.chinagps.gboss.alarmarray.AlarmInfo;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.CancelPauseAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.CancelPauseAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class CancelPauseAlarmHandler extends ClientBaseHandler {

    public String username = "";       //坐席登录名
    public String callLetter = "";     //终端呼号
    public String alarmsn = "";        //警情唯一序列号
    public String slaver = "";				//应用程序或通信中心(Slaver)名称, 分布式通信中心用

    public CancelPauseAlarmHandler(ComCenterBaseMessage basemsg, SeatClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			CancelPauseAlarm cancelpausealarm = CancelPauseAlarm.parseFrom(msgcontent);
			username = cancelpausealarm.getUsername().trim();
			assert username.equals(((SeatClientInfo)clientinfo).username);
			callLetter = cancelpausealarm.getCallLetter().trim();
			alarmsn = cancelpausealarm.getAlarmsn().trim();
			if (cancelpausealarm.hasSlaver()) {
				slaver = cancelpausealarm.getSlaver().trim();
			}
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		System.out.println("CancelPauseAlarm: " + alarmsn + ", " + callLetter + ", " + username);
		AlarmInfo alarminfo = AlarmManager.alarmmanager.findAlarmInfo(callLetter); 
		if (alarminfo == null) {
			retcode = ResultCode.AlarmNoExist_Error;
			retmsg = "警单不存在或已经被处理";
			return;
		}
		if (alarminfo.getstatus() != AlarmInfo.PAUSESTATUS) {
			retcode = ResultCode.AlarmNoExist_Error;
			retmsg = "警单没被挂警";
			return;
		}
		alarminfo.setstatus(AlarmInfo.ALLOTEDSTATUS);
		AlarmManager.alarmmanager.cancelPauseAlarm(this);
	}

	@Override
	public byte[] response() {
		CancelPauseAlarm_ACK.Builder cancelpauseack = CancelPauseAlarm_ACK.newBuilder();
		cancelpauseack.setRetcode(retcode);
		cancelpauseack.setRetmsg(retmsg);
		cancelpauseack.setUsername(((SeatClientInfo)clientinfo).username);
		cancelpauseack.setCallLetter(callLetter);
		cancelpauseack.setAlarmsn(alarmsn);
		return Serialize(MessageType.CancelPauseAlarm_ACK, cancelpauseack.build().toByteString()); 
	}

}
