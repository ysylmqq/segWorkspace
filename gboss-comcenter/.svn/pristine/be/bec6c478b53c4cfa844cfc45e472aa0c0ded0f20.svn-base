/*
********************************************************************************************
Discription:  坐席请求转警
			  
			  
Written By:   ZXZ
Date:         2014-08-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.websocket;

import cc.chinagps.gboss.alarmarray.AlarmInfo;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.TransferAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.TransferAlarm;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class TransferAlarmHandler extends ClientBaseHandler {
	public String srcusername;    //转出坐席登录名
	public String dstusername;    //目的坐席登录名
	public String callLetter;     //终端呼号
	public String alarmsn;        //警情唯一序列号
	public String transfermsg;    //转警简单说明
    public String slaver = "";	  //应用程序或通信中心(Slaver)名称, 分布式通信中心用

    public TransferAlarmHandler(ComCenterBaseMessage basemsg, SeatClientInfo info) {
		super(basemsg, info);
	}
    
    public void setRetMsg(String msg) {
    	retmsg = msg;
    }

	@Override
	public int decode() {
		try {
			TransferAlarm transferalarm = TransferAlarm.parseFrom(msgcontent);
			srcusername = transferalarm.getSrcusername().trim();
			assert srcusername.equals(((SeatClientInfo)clientinfo).username);
			dstusername = transferalarm.getDstusername().trim();
			callLetter = transferalarm.getCallLetter().trim();
			alarmsn = transferalarm.getAlarmsn().trim();
			transfermsg = transferalarm.getTransfermsg().trim();
			if (transferalarm.hasSlaver()) {
				slaver = transferalarm.getSlaver().trim();
			}
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		System.out.println("TransferAlarm: " + alarmsn + ", " + callLetter + ", " + srcusername + " to " + dstusername);
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
		if (dstseat.username.equals(clientinfo.username)) {
			retcode = ResultCode.SeatExist_Error;
			retmsg = "转警不能转给自己";
			return;
		}
		if (dstseat.isbusy()) {
			retcode = ResultCode.SeatBusy_Error;
			retmsg = "目的坐席忙碌，不能处理警情";
			return;
		}
		AlarmManager.alarmmanager.transferAlarm(this, alarminfo, dstseat);
	}

	@Override
	public byte[] response() {
		//请求转警不能马上返回，必须等到目的坐席应答返回, 
		if (retcode == ResultCode.OK) {
			return null;
		}
		//如果失败，必须返回
		TransferAlarm_ACK.Builder ack = TransferAlarm_ACK.newBuilder();
		ack.setAlarmsn(alarmsn);
		ack.setCallLetter(callLetter);
		ack.setDstusername(dstusername);
		ack.setRetcode(retcode);
		ack.setRetmsg(retmsg);
		ack.setSrcusername(srcusername);
		return Serialize(MessageType.TransferAlarm_ACK, ack.build().toByteString()); 
	}

}
