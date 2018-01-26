/*
********************************************************************************************
Discription:  UDP广播挂警
			  
			  
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
import cc.chinagps.gboss.alarmarray.websocket.PauseAlarmHandler;
import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.PauseAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class UDPPauseAlarmHandler extends PauseAlarmHandler {
    public UDPPauseAlarmHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public void run() {
		//如果是发送广播消息的通信中心，不执行
		if (this.slaver.equals(ComCenter.systemname)) {
			return;
		}
		AlarmManager.alarmmanager.pauseAlarm(this);
	}

	@Override
	public byte[] response() {
		if (ZooKeeperAlarm.zookeeperalarm.isMasterd) {
			PauseAlarm_ACK.Builder pauseack = PauseAlarm_ACK.newBuilder();
			pauseack.setRetcode(retcode);
			pauseack.setCallLetter(callLetter);
			pauseack.setAlarmsn(alarmsn);
			pauseack.setUsername(username);
			return Serialize(MessageType.PauseAlarm_ACK, pauseack.build().toByteString());
		}
		return null;	//Slaver收到不用再返回
	}

}
