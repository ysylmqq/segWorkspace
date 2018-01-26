/*
********************************************************************************************
Discription:  通信中心单元测试工具用
			  
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.serverHandler;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.testtools.WebSocketClientHandler;

public class serverHandlerFactory {
	
	public static serverBaseHandler CreateServerHandler(ComCenterBaseMessage responsemsg, WebSocketClientHandler handler) {
		
		serverBaseHandler ret = null;
		switch(responsemsg.getId()) {
		case MessageType.Login_ACK:  //登录返回
			ret = new LoginACKHandler(responsemsg, handler);
			break;
		case MessageType.GetLastInfo_ACK:
			ret = new GetLastInfoACKHandler(responsemsg, handler);
			break;
		case MessageType.GetHistoryInfo_ACK:
			ret = new GetHistoryInfoACKHandler(responsemsg, handler);
			break;
		case MessageType.DeliverGPS:
			ret = new DeliverGPSHandler(responsemsg, handler);
			break;
		case MessageType.DeliverAlarm:
			ret = new DeliverAlarmHandler(responsemsg, handler);
			break;
		case MessageType.DeliverFault:
			ret = new DeliverFaultHandler(responsemsg, handler);
			break;
		case MessageType.DeliverOperateData:
			ret = new DeliverOperateDataHandler(responsemsg, handler);
			break;
		case MessageType.DeliverTravel:
			ret = new DeliverTravelHandler(responsemsg, handler);
			break;
		case MessageType.DeliverSMS:
			ret = new DeliverSMSHandler(responsemsg, handler);
			break;
		case MessageType.DeliverUnitLoginOut:
			ret = new DeliverUnitLoginOutHandler(responsemsg, handler);
			break;
		}
		return ret;
	}

}
