/*
********************************************************************************************
Discription:  从ActiveMQ接收短消息，并转发到客户端
			  
2.4 车台上传短信
队列名称：sm
队列类型：topic
消息类型：BytesMessage
消息内容：DeliverSMS(protobuf结构，参见comcenter.proto的定义)的二进制数据

			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.activemq;

import javax.jms.Connection;

import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.UnitInfoManager;
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQSMS;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverSMS;

public class SMReader extends InfoReader {

	public SMReader(Connection connection) {
		super("sm", Type.Topic, connection);
	}

	@Override
	protected void processByteMessage(byte[] data) {
		try {
			String callletter = null;
			int gatewayid = 0;
			int gatewaytype = 0;
			MQSMS mqsms = MQSMS.parseFrom(data);
			for(DeliverSMS deliversms : mqsms.getDeliversmsesList()) {
				//DeliverSMS deliversms = DeliverSMS.parseFrom(data);
				callletter = deliversms.getMsg().getCallLetter().trim();
				if (deliversms.hasGatewayid()) {
					gatewayid = deliversms.getGatewayid();
					gatewaytype = deliversms.getGatewaytype();
					//先改变缓存区终端信息
					UnitInfoManager.unitinfomanager.setGatewayId(callletter, gatewayid, (gatewaytype==0));
				}
				//判断是否要转发到那些客户端
				CenterClientManager.clientManager.deliverClient(callletter, MessageType.DeliverSMS, deliversms.toByteArray(), false);
			}
			callletter = null;
			mqsms = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
