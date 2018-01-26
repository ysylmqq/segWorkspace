/*
********************************************************************************************
Discription:  通信中心广播转警请求
			  
			  
Written By:   ZXZ
Date:         2014-08-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.UdpHandler;

import cc.chinagps.gboss.alarmarray.AlarmInfo;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.alarmarray.websocket.TransferAlarmHandler;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.TransferAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class UDPTransferAlarmHandler extends TransferAlarmHandler {
    public UDPTransferAlarmHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public void run() {
		AlarmInfo alarminfo = AlarmManager.alarmmanager.findAlarmInfo(callLetter); 
		if (alarminfo == null) {
			retcode = ResultCode.AlarmNoExist_Error;
			retmsg = "警单不存在或已经被处理";
			return;
		}
		SeatClientInfo dstseat = AlarmManager.alarmmanager.findSeat(dstusername);
		if (dstseat == null) {
			retcode = ResultCode.SeatNoLogin_Error;
			retmsg = "目的坐席未登录";
			return;
		}
		if (dstseat.isbusy()) {
			retcode = ResultCode.SeatBusy_Error;
			retmsg = "目的坐席忙碌，不能处理警情";
			return;
		}
		if (dstseat.isLoginSelf()) {
			AlarmManager.alarmmanager.transferAlarm(this, alarminfo, dstseat);
		}
	}

	@Override
	public byte[] response() {
		if (retcode != ResultCode.OK && ZooKeeperAlarm.zookeeperalarm.isMasterd) {
			//如果Master发现坐席未登录, 或者警情不存在, 返回失败 
			TransferAlarm_ACK.Builder ack = TransferAlarm_ACK.newBuilder();
			ack.setRetcode(retcode);
			ack.setRetmsg(retmsg);
			ack.setSrcusername(srcusername);
			ack.setDstusername(dstusername);
			ack.setAlarmsn(alarmsn);
			ack.setCallLetter(callLetter);
			return Serialize(MessageType.TransferAlarm_ACK, ack.build().toByteString());
		}
		return null;
	}

}
