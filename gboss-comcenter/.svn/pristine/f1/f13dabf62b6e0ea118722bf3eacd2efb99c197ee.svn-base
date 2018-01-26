/*
********************************************************************************************
Discription:  UDP广播取消挂警
			  
			  
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
import cc.chinagps.gboss.alarmarray.websocket.CancelPauseAlarmHandler;
import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.CancelPauseAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class UDPCancelPauseAlarmHandler extends CancelPauseAlarmHandler {
    public UDPCancelPauseAlarmHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public void run() {
		if (slaver.equals(ComCenter.systemname)) {
			return;
		}
		AlarmManager.alarmmanager.cancelPauseAlarm(this);
	}

	@Override
	public byte[] response() {
		if (ZooKeeperAlarm.zookeeperalarm.isMasterd) {
			//Master确认收到
			CancelPauseAlarm_ACK.Builder cancelpauseack = CancelPauseAlarm_ACK.newBuilder();
			cancelpauseack.setRetcode(retcode);
			cancelpauseack.setCallLetter(callLetter);
			cancelpauseack.setAlarmsn(alarmsn);
			cancelpauseack.setUsername(username);
			return Serialize(MessageType.CancelPauseAlarm_ACK, cancelpauseack.build().toByteString());
		}
		return null;
	}

}
