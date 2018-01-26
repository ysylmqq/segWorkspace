/*
********************************************************************************************
Discription:  从ActiveMQ接收OBD信息，并处理(转发到客户端)
			  
2.2警情信息
队列名称：obd
队列类型：topic
消息类型：BytesMessage
消息内容：
			  
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
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQOBD;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverOBD;
import cc.chinagps.gboss.comcenter.buff.MessageType;

public class OBDReader extends InfoReader {

	public OBDReader(Connection connection) {
		super("obd", Type.Topic, connection);
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
			boolean bhistory = false;
			MQOBD mqobd = MQOBD.parseFrom(data);
			for(DeliverOBD deliverobd : mqobd.getDeliverobdsList()) {
				//DeliverOBD deliverobd = DeliverOBD.parseFrom(data);
				callletter = deliverobd.getObdinfo().getCallLetter().trim();
				if (deliverobd.hasGatewayid() && deliverobd.hasGatewaytype()) {
					gatewayid = deliverobd.getGatewayid();
					gatewaytype = deliverobd.getGatewaytype();
					//先改变缓存区终端信息
					UnitInfoManager.unitinfomanager.setGatewayId(callletter, gatewayid, (gatewaytype==0));
				}
				bhistory = false;
				if (deliverobd.getObdinfo().hasHistory()) {
					bhistory = (deliverobd.getObdinfo().getHistory() != 0);
					if (bhistory &&  deliverobd.getObdinfo().hasGpsTime()) {
						long expiredtime = System.currentTimeMillis() - deliverobd.getObdinfo().getGpsTime();
						bhistory = (expiredtime > 120000);	//超过120秒才是真的补报 
					}
				}
				//判断是否要转发到那些客户端
				CenterClientManager.clientManager.deliverClient(callletter, MessageType.DeliverOBD, deliverobd.toByteArray(), bhistory);
			}
			callletter = null;
			mqobd = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
