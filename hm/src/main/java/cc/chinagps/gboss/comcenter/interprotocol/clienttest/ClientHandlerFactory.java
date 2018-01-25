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
		//车辆监控
		case MessageType.AddMonitor_ACK:
			//ret = new AddMonitorACKHandler(responsemsg, centerClientHandler);
			break;
		//终端升级成功上报版本号，终端相应类
		case MessageType.DeliverUnitVersion:
			//ret = new DeliverUnitVersionHandler(responsemsg, centerClientHandler);
			break;
		//终端回应
		case MessageType.SendCommand_ACK: 
			//ret = new SendUpgradeCommand_ACK(responsemsg, centerClientHandler);
			ret = new SendFlowCtrlCommand_ACK(responsemsg, centerClientHandler);
			break;
		//网关回应 
		case MessageType.SendCommandSend_ACK: //终端不回应，网关发送成功
			//ret = new SendUpgradeCommandSend_ACK(responsemsg, centerClientHandler);
			ret = new SendFlowCtrlCommandSend_ACK(responsemsg, centerClientHandler);
			break;	
			
		case MessageType.DeliverGPS:
			//ret = new DeliverGPSHandler(responsemsg, centerClientHandler);
			ret = new FCDeliverGPSHandler(responsemsg, centerClientHandler);
			break;
		case MessageType.DeliverUnitLoginOut:
			//ret = new DeliverUnitLoginOutHandler(responsemsg, centerClientHandler);
			ret = new FCDeliverUnitLoginOutHandler(responsemsg, centerClientHandler);
			
			break;
		//查询配置、
		case MessageType.DeliverECUConfig:
			//ret = new DeliverECUConfigHandler(responsemsg, centerClientHandler);
			break;
		}
		
		if (ret != null) {
//			System.out.println(ret.toString());
		}
		return ret;
	}

}
