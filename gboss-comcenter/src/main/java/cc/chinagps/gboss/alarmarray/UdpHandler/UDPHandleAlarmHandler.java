/*
********************************************************************************************
Discription:  UDP广播警情处理结果
			  
			  
Written By:   ZXZ
Date:         2014-08-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.UdpHandler;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.websocket.HandleAlarmHandler;
import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.HandleAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class UDPHandleAlarmHandler extends HandleAlarmHandler {

    public UDPHandleAlarmHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public void run() {
		if (slaver.equals(ComCenter.systemname))
			return;
		AlarmManager.alarmmanager.handleAlarm(this);
	}

	@Override
	public byte[] response() {
		if (ZooKeeperAlarm.zookeeperalarm.isMasterd) {
			HandleAlarm_ACK.Builder handleack = HandleAlarm_ACK.newBuilder();
			handleack.setRetcode(retcode);
			handleack.setUsername(username);
			handleack.setCallLetter(callLetter);
			handleack.setAlarmsn(alarmsn);
			return Serialize(MessageType.HandleAlarm_ACK, handleack.build().toByteString()); 
		}
		return null;
	}

}
