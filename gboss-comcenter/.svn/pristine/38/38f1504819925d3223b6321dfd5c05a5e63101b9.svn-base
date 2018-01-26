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
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.HandleAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class UDPHandleAlarmACKHandler extends ClientBaseHandler {
    private String alarmsn = "";    //警情唯一序列号
    //private String slaver = "";		//应用程序或通信中心(Slaver)名称, 分布式通信中心用
    public UDPHandleAlarmACKHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public int decode() {
		try {
			HandleAlarm_ACK ack = HandleAlarm_ACK.parseFrom(msgcontent);
			retcode = ack.getRetcode();
			alarmsn = ack.getAlarmsn();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		//如果解码失败，不能往下执行
		return retcode;
	}
	
	@Override
	public void run() {
		//删除广播消息
		AlarmManager.alarmmanager.removeMulticast(alarmsn, MessageType.HandleAlarm);
	}

	@Override
	public byte[] response() {
		return null;
	}
}
