/*
********************************************************************************************
Discription:  Master收到UDP广播分配结果，再广播应答消息，发布者收到应答消息后，删除对应的广播消息
			  
			  
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
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class UDPAllotAlarmACK_ACKHandler extends UDPAllotAlarmACKHandler {
	public UDPAllotAlarmACK_ACKHandler(ComCenterBaseMessage basemsg) {
		super(basemsg);
	}

	@Override
	public void run() {
		AlarmManager.alarmmanager.removeMulticast(alarmsn, MessageType.AllotAlarm_ACK);
	}

	@Override
	public byte[] response() {
		return null;
	}
}
