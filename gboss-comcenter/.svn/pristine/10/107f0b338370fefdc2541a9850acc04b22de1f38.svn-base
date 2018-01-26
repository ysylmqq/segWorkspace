/*
********************************************************************************************
Discription:  从ActiveMQ接收ECUConfig信息，并处理(转发到客户端)
			  
2.2警情信息
队列名称：ecuConfig
队列类型：topic
消息类型：BytesMessage
消息内容：
			  
Written By:   ZXZ
Date:         2015-07-13
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
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQECUConfig;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverECUConfig;
import cc.chinagps.gboss.comcenter.buff.MessageType;

public class ECUConfigReader extends InfoReader {

	public ECUConfigReader(Connection connection) {
		super("ecuConfig", Type.Topic, connection);
	}

	/*
	 * 处理行程信息 , 每次只有一个DeliverTravel结构
	 */
	@Override
	protected void processByteMessage(byte[] data) {
		try {
			String callletter = null;
			int gatewayid = 0;
			int gatewaytype = 0;
			MQECUConfig mqecuconfig = MQECUConfig.parseFrom(data);
			for(DeliverECUConfig deliverecuconfig : mqecuconfig.getDeliverECUConfigList()) {
				callletter = deliverecuconfig.getEcuConfig().getCallLetter().trim();
				if (deliverecuconfig.hasGatewayid() && deliverecuconfig.hasGatewaytype()) {
					gatewayid = deliverecuconfig.getGatewayid();
					gatewaytype = deliverecuconfig.getGatewaytype();
					//先改变缓存区终端信息
					UnitInfoManager.unitinfomanager.setGatewayId(callletter, gatewayid, (gatewaytype==0));
				}
				//判断是否要转发到那些客户端
				CenterClientManager.clientManager.deliverClient(callletter, MessageType.DeliverECUConfig, deliverecuconfig.toByteArray(), false);
			}
			callletter = null;
			mqecuconfig = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
