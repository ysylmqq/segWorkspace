/*
********************************************************************************************
Discription:  通信中心调度协调警情的请求及应答基类生产工厂，简单工厂模式，工厂类
			  
			  
Written By:   ZXZ
Date:         2014-09-05
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.UdpHandler;

import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class UdpHandlerFactory {
	
	public static ClientBaseHandler CreateSeatHandler(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage requestmsg) {
		ClientBaseHandler ret = null;
		switch(requestmsg.getId()) {
		case MessageType.SeatStatus:	//坐席状态(登录、退录、忙碌、空闲、挂警、处警结果、申请转警、接收转警(Slaver通知Master)
			ret = new SeatStatusHandler(requestmsg);	//坐席列表，要求每个通信中心都保持所有坐席信息
			break;
		case MessageType.SeatStatus_ACK:	//Master应答
			ret = new SeatStatusACKHandler(requestmsg);
			break;
		case MessageType.NewAlarm:	//有新的警情生成, 自己发的自己也会收到
			ret = new UDPNewAlarmHandler(requestmsg);
			break;
		case MessageType.NewAlarm_ACK:
			ret = new UDPNewAlarmACKHandler(requestmsg);
			break;
		case MessageType.AllotAlarm:	//Master通知Slaver，要给某坐席分配警情
			ret = new UDPAllotAlarmHandler(requestmsg);
			break;
		case MessageType.AllotAlarm_ACK:
			ret = new UDPAllotAlarmACKHandler(requestmsg);
			break;
		case MessageType.UDPAllotAlarmACK_ACK:
			ret = new UDPAllotAlarmACK_ACKHandler(requestmsg);
			break;
		case MessageType.HandleAlarm:
			ret = new UDPHandleAlarmHandler(requestmsg);
			break;
		case MessageType.HandleAlarm_ACK:
			ret = new UDPHandleAlarmACKHandler(requestmsg);
			break;
		case MessageType.PauseAlarm:
			ret = new UDPPauseAlarmHandler(requestmsg);
			break;
		case MessageType.PauseAlarm_ACK:
			ret = new UDPPauseAlarmACKHandler(requestmsg);
			break;
		case MessageType.CancelPauseAlarm:
			ret = new UDPCancelPauseAlarmHandler(requestmsg);
			break;
		case MessageType.CancelPauseAlarm_ACK:
			ret = new UDPCancelPauseAlarmACKHandler(requestmsg);
			break;
		case MessageType.TransferAlarm:     //通信中心收到坐席转警申请，广播通知目的坐席连接的通信中心处理转警
			ret = new UDPTransferAlarmHandler(requestmsg);
			break;
		case MessageType.TransferAlarm_ACK: //
			ret = new UDPTransferAlarmACKHandler(requestmsg);
			break;
		case MessageType.AllotTransferAlarm_ACK: //通信中心收到坐席转警应答，广播通知其他通信中心
			ret = new UDPAllotTransferAlarmACKHandler(requestmsg);
			break;
		case MessageType.AskSyncAlarmList: //Slaver请求同步警情列表
			if (ZooKeeperAlarm.zookeeperalarm.isMasterd) {
				ret = new UDPAskSyncAlarmListHandler(requestmsg);
			}
			break;
		case MessageType.AskSyncAlarmList_ACK: //Master应答同步警情列表
			if (!ZooKeeperAlarm.zookeeperalarm.isMasterd) {
				ret = new UDPAskSyncAlarmListACKHandler(requestmsg);
			}
			break;
		}
		if (ret != null) {
			System.out.println("UDPHandler: " + ret.toString());
		}
		return ret;
	}
}
