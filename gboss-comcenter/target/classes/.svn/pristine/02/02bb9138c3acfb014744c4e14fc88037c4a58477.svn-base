/*
********************************************************************************************
Discription:  通信中心返回广播转警请求（一般是失败情况，才返回，如果申请成功，则必须等待目的坐席应答）
			  
			  
Written By:   ZXZ
Date:         2014-12-10
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.UdpHandler;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.TransferAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class UDPTransferAlarmACKHandler extends ClientBaseHandler {
	public String srcusername;    //转出坐席登录名
	public String dstusername;    //目的坐席登录名
	public String callLetter;     //终端呼号
	public String alarmsn;        //警情唯一序列号

	public UDPTransferAlarmACKHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public int decode() {
		try {
			TransferAlarm_ACK transferalarmack = TransferAlarm_ACK.parseFrom(msgcontent);
			retcode = transferalarmack.getRetcode();
			retmsg = transferalarmack.getRetmsg();
			srcusername = transferalarmack.getSrcusername();
			dstusername = transferalarmack.getDstusername();
			callLetter = transferalarmack.getCallLetter();
			alarmsn = transferalarmack.getAlarmsn();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return ResultCode.OK;
	}

	@Override
	public void run() {
		AlarmManager.alarmmanager.transferAlarmACK(this);
	}

	@Override
	public byte[] response() {
		return null;
	}

}
