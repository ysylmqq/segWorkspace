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

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.PauseAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.PauseAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class PauseAlarmHandler extends ClientBaseHandler {

    public String username = "";    //坐席登录名
    public String callLetter = "";  //终端呼号
    public String alarmsn = "";     //警情唯一序列号
    public String pausemsg = "";    //挂警原因
    public String slaver = "";		//应用程序或通信中心(Slaver)名称, 分布式通信中心用

    public PauseAlarmHandler(ComCenterBaseMessage basemsg, SeatClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			PauseAlarm pausealarm = PauseAlarm.parseFrom(msgcontent);
			username = pausealarm.getUsername().trim();
			assert username.equals(((SeatClientInfo)clientinfo).username);
			callLetter = pausealarm.getCallLetter().trim();
			alarmsn = pausealarm.getAlarmsn().trim();
			if (pausealarm.hasPausemsg()) {
				pausemsg = pausealarm.getPausemsg().trim();
			}
			if (pausealarm.hasSlaver()) {
				slaver = pausealarm.getSlaver().trim();
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
		System.out.println("PauseAlarm: " + alarmsn + ", " + callLetter + ", " + username);
		if (AlarmManager.alarmmanager.findAlarmInfo(callLetter) == null) {
			retcode = ResultCode.AlarmNoExist_Error;
			retmsg = "警单不存在或已经被处理";
		} else {
			AlarmManager.alarmmanager.pauseAlarm(this);
		}
	}

	@Override
	public byte[] response() {
		PauseAlarm_ACK.Builder pauseack = PauseAlarm_ACK.newBuilder();
		pauseack.setRetcode(retcode);
		if (retcode == ResultCode.OK) {
			pauseack.setRetmsg(pausemsg);
		} else {
			pauseack.setRetmsg(retmsg);
		}
		pauseack.setUsername(((SeatClientInfo)clientinfo).username);
		pauseack.setCallLetter(callLetter);
		pauseack.setAlarmsn(alarmsn);
		return Serialize(MessageType.PauseAlarm_ACK, pauseack.build().toByteString()); 
	}

}
