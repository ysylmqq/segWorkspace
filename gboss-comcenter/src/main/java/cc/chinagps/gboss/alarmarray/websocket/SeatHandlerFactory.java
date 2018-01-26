/*
********************************************************************************************
Discription:  坐席端请求基类生产工厂，简单工厂模式，工厂类
			  
			  
Written By:   ZXZ
Date:         2014-08-25
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.websocket;

import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class SeatHandlerFactory {
	
	public static ClientBaseHandler CreateSeatHandler(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage requestmsg, SeatClientInfo seatinfo) {
		ClientBaseHandler ret = null;
		switch(requestmsg.getId()) {
		case MessageType.SetAlarmBusy:              //坐席设置处理警情忙闲状态
			ret = new SetAlarmBusyHandler(requestmsg, seatinfo);
			break;
		case MessageType.AllotAlarm_ACK:			//服务器分配警情给某坐席, 坐席应答结果
			ret = new AllotAlarmACKHandler(requestmsg, seatinfo);
			break;
		case MessageType.PauseAlarm:                //坐席请求挂警（暂时不处理这个警情），可以接收其他警情
			ret = new PauseAlarmHandler(requestmsg, seatinfo);
			break;
		case MessageType.CancelPauseAlarm:          //坐席请求取消挂警，继续处理这个警情
			ret = new CancelPauseAlarmHandler(requestmsg, seatinfo);
			break;
		case MessageType.HandleAlarm:               //坐席向服务器报告处理警情结果（已经追加的警情也作相同的处理）
			ret = new HandleAlarmHandler(requestmsg, seatinfo);
			break;
		case MessageType.AskSeatList:               //坐席向服务器请求坐席列表
			ret = new AskSeatListHandler(requestmsg, seatinfo);
			break;
		case MessageType.TransferAlarm:             //坐席向服务器请求转警
			ret = new TransferAlarmHandler(requestmsg, seatinfo);
			break;
		case MessageType.AllotTransferAlarm_ACK:    //目的坐席回复是否收到转警
			ret = new AllotTransferAlarmACKHandler(requestmsg, seatinfo);
			break;
		case MessageType.AskAlarmList:              //坐席向服务器请求警情列表（未处理和正在处理的）
			ret = new AskAlarmListHandler(requestmsg, seatinfo);
			break;
		}
		if (ret != null && !(ret instanceof AskAlarmListHandler) ) {
			System.out.println("SeatHandler: " + ret.toString());
		}
		return ret;
	}
}

