/*
********************************************************************************************
Discription:  Master通知分配坐席警情
			  
			  
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
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.alarmarray.websocket.AllotAlarmHandler;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AllotAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class UDPAllotAlarmHandler extends AllotAlarmHandler {
	public SeatClientInfo seat = null;
    public UDPAllotAlarmHandler(ComCenterBaseMessage basemsg) {
		super(basemsg);
	}

	@Override
	public void run() {
		seat = AlarmManager.alarmmanager.findSeat(username);
		if (seat == null) {
			retcode = ResultCode.SeatNoLogin_Error;
			retmsg = "坐席未登录";
			return;
		}
		if (!seat.isLoginSelf()) {
			//如果目的坐席不是登录本通信中心
			return;
		}
		AlarmManager.alarmmanager.sendAlarmToSeat(seat, callLetter,
    			alarmsn, alarmtime,	alarmid, alarmname,	gpsinfo, append);
	}

	@Override
	public byte[] response() {
		//如果解码失败或者坐席没有登录, 则返回失败
		if (retcode != ResultCode.OK && ZooKeeperAlarm.zookeeperalarm.isMasterd) {
			AllotAlarm_ACK.Builder allotack = AllotAlarm_ACK.newBuilder();
			allotack.setRetcode(retcode);
			allotack.setRetmsg(retmsg);
			allotack.setUsername(username);
			allotack.setCallLetter(callLetter);
			allotack.setAlarmsn(alarmsn);
			return Serialize(MessageType.AllotAlarm_ACK, allotack.build().toByteString());
		}
		return null;
	}

}
