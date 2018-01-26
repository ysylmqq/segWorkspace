/*
********************************************************************************************
Discription:  Slaver应答分配坐席警情, Master收到， 并做相应的处理
			  
			  
Written By:   ZXZ
Date:         2014-09-05
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.UdpHandler;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.alarmarray.websocket.AllotAlarmACKHandler;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AllotAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class UDPAllotAlarmACKHandler extends AllotAlarmACKHandler {
	public UDPAllotAlarmACKHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public void run() {
		AlarmManager.alarmmanager.allotAlarmACK(this);
	}

	@Override
	public byte[] response() {
		if (ZooKeeperAlarm.zookeeperalarm.isMasterd) {
			//Master收到UDP广播分配结果，再广播消息，让发布者删除广播消息
			AllotAlarm_ACK.Builder allotack = AllotAlarm_ACK.newBuilder();
			allotack.setRetcode(retcode);
			allotack.setRetmsg(retmsg);
			allotack.setUsername(username);
			allotack.setCallLetter(callLetter);
			allotack.setAlarmsn(alarmsn);
			return Serialize(MessageType.UDPAllotAlarmACK_ACK, allotack.build().toByteString());
		}
		return null;
	}
}
