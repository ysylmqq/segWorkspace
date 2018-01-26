/*
********************************************************************************************
Discription:  从ActiveMQ接收行程信息，并处理(转发到客户端)
			  
2.5 行程信息
队列名称：travel
队列类型：topic
消息类型：BytesMessage
消息内容：DeliverTravel(protobuf结构，参见comcenter.proto的定义)的二进制数据

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
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQTravel;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverTravel;

public class TravelReader extends InfoReader {

	public TravelReader(Connection connection) {
		super("travel", Type.Topic, connection);
		// TODO Auto-generated constructor stub
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
			MQTravel mqtravel = MQTravel.parseFrom(data);
			for(DeliverTravel delivertravel : mqtravel.getDelivertravelsList()) {
				//DeliverTravel delivertravel = DeliverTravel.parseFrom(data);
				callletter = delivertravel.getTravelinfo().getCallLetter();
				if (delivertravel.hasGatewayid() && delivertravel.hasGatewaytype()) {
					gatewayid = delivertravel.getGatewayid();
					gatewaytype = delivertravel.getGatewaytype();
					//先改变缓存区终端信息
					UnitInfoManager.unitinfomanager.setGatewayId(callletter, gatewayid, (gatewaytype==0));
				}
				bhistory = false;
				if (delivertravel.getTravelinfo().hasHistory()) {
					bhistory = (delivertravel.getTravelinfo().getHistory() != 0);
				}
				//判断是否要转发到那些客户端
				CenterClientManager.clientManager.deliverClient(callletter, MessageType.DeliverTravel, delivertravel.toByteArray(), bhistory);
			}
			callletter = null;
			mqtravel = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
