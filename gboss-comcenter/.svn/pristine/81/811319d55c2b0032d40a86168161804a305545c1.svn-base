/*
********************************************************************************************
Discription:  从ActiveMQ接收终端版本信息，并处理(转发到客户端)
			  
2.6 模块故障信息
队列名称：unitVersion
队列类型：topic
消息类型：BytesMessage
消息内容：DeliverUnitVersion(protobuf结构，参见comcenter.proto的定义)的二进制数据

Written By:   ZXZ
Date:         2015-01-30
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
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQUnitVersion;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitVersion;

public class UnitVersionReader extends InfoReader {

	public UnitVersionReader(Connection connection) {
		super("unitVersion", Type.Topic, connection);
	}

	/*
	 * 处理故障信息 , 每次只有一个DeliverFault结构
	 */
	@Override
	protected void processByteMessage(byte[] data) {
		try {
			String callletter = null;
			int gatewayid = 0;
			int gatewaytype = 0;
			MQUnitVersion mqunitversion = MQUnitVersion.parseFrom(data);
			for(DeliverUnitVersion deliverunitversion : mqunitversion.getDeliverunitversionList()) {
				//DeliverUnitVersion deliverunitversion = DeliverUnitVersion.parseFrom(data);
				callletter = deliverunitversion.getUnitVersion().getCallLetter();
				if (deliverunitversion.hasGatewayid() && deliverunitversion.hasGatewaytype()) {
					gatewayid = deliverunitversion.getGatewayid();
					gatewaytype = deliverunitversion.getGatewaytype();
					//先改变缓存区终端信息
					UnitInfoManager.unitinfomanager.setGatewayId(callletter, gatewayid, (gatewaytype==0));
				}
				//判断是否要转发到那些客户端
				CenterClientManager.clientManager.deliverClient(callletter, MessageType.DeliverUnitVersion, deliverunitversion.toByteArray(), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
