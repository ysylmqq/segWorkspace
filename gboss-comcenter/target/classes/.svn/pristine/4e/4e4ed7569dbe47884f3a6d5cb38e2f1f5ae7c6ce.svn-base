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
package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.interprotocol.clienttest.CenterClientHandler;

public class ClientHandlerFactory {
	
	public static ClientBaseHandler CreateClientHandler(ComCenterBaseMessage responsemsg, CenterClientHandler centerClientHandler) {
		
		ClientBaseHandler ret = null;
		switch(responsemsg.getId()) {
		case MessageType.AddMonitor_ACK:
			ret = new AddMonitorACKHandler(responsemsg, centerClientHandler);
			break;
		//case MessageType.GetLastInfo_ACK:
		//	ret = new GetLastInfoACKHandler(responsemsg, centerClientHandler);
		//	break;
		//case MessageType.GetHistoryInfo_ACK:
		//	ret = new GetHistoryInfoACKHandler(responsemsg, centerClientHandler);
		//	break;
		case MessageType.DeliverGPS:
			ret = new DeliverGPSHandler(responsemsg, centerClientHandler);
			break;
		//case MessageType.DeliverAlarm:
		//	ret = new DeliverAlarmHandler(responsemsg, centerClientHandler);
		//	break;
		//case MessageType.DeliverFault:
		//	ret = new DeliverFaultHandler(responsemsg, centerClientHandler);
		//	break;
		//case MessageType.DeliverOperateData:
		//	ret = new DeliverOperateDataHandler(responsemsg, centerClientHandler);
		//	break;
		//case MessageType.DeliverTravel:
		//	ret = new DeliverTravelHandler(responsemsg, centerClientHandler);
		//	break;
		//case MessageType.DeliverSMS:
		//	ret = new DeliverSMSHandler(responsemsg, centerClientHandler);
		//	break;
		case MessageType.DeliverUnitLoginOut:
			ret = new DeliverUnitLoginOutHandler(responsemsg, centerClientHandler);
			break;
		case MessageType.DeliverUnitVersion:
			ret = new DeliverUnitVersionHandler(responsemsg, centerClientHandler);
			break;
		case MessageType.SendCommandSend_ACK: //网关发送回应(这个有可能没有)
			ret = new SendCommandSendACKHandler(responsemsg, centerClientHandler);
			break;
		case MessageType.SendCommand_ACK: //终端回应，或者终端不回应，网关发送成功也是这个应答
			ret = new SendCommandACKHandler(responsemsg, centerClientHandler);
			break;
		}
		//if (ret != null) {
			//System.out.println(ret.toString());
		//}
		return ret;
	}

}
