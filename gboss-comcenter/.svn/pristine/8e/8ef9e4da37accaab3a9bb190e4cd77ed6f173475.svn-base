/*
********************************************************************************************
Discription:  服务器广播坐席的转警应答
			  
			  
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
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.alarmarray.websocket.AllotTransferAlarmACKHandler;

public class UDPAllotTransferAlarmACKHandler extends AllotTransferAlarmACKHandler {

	public UDPAllotTransferAlarmACKHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);	//没有坐席，是通信中心广播
	}

	@Override
	public void run() {
		AlarmManager.alarmmanager.allotTransferAlarmACK(this);
	}

	@Override
	public byte[] response() {
		return null;
	}

}
