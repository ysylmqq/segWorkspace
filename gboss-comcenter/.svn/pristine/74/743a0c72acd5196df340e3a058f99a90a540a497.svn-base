/*
********************************************************************************************
Discription:  UDP广播挂警应答（Master确认）
			  
			  
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
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.PauseAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class UDPPauseAlarmACKHandler extends ClientBaseHandler {

    private String alarmsn = "";        //警情唯一序列号
    public UDPPauseAlarmACKHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public int decode() {
		try {
			PauseAlarm_ACK ack = PauseAlarm_ACK.parseFrom(msgcontent);
			retcode = ack.getRetcode();
			alarmsn = ack.getAlarmsn();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		//删除广播消息
		AlarmManager.alarmmanager.removeMulticast(alarmsn, MessageType.PauseAlarm);
	}

	@Override
	public byte[] response() {
		return null;
	}

}
