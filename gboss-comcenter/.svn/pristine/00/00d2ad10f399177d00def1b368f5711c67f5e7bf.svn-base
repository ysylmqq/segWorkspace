/*
********************************************************************************************
Discription:  从ActiveMQ接收运营数据，并处理(转发到客户端)
			  
2.3 运营数据
队列名称：operateData
队列类型：topic
消息类型：BytesMessage
消息内容：DeliverOperateData(protobuf结构，参见comcenter.proto的定义)的二进制数据
			  
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
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQOperateData;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverOperateData;

public class OperateDataReader extends InfoReader {

	public OperateDataReader(Connection connection) {
		super("operateData", Type.Topic, connection);
	}

	/*
	 * 处理运营数据信息 , 每次只有一个DeliverOperateData结构
	 */
	@Override
	protected void processByteMessage(byte[] data) {
		try {
			String callletter = null;
			int gatewayid = 0;
			int gatewaytype = 0;
			boolean bhistory = false;
			MQOperateData mqoperatedata = MQOperateData.parseFrom(data);
			for(DeliverOperateData deliveroperatedata : mqoperatedata.getDeliveroperatedatasList()) {
				//DeliverOperateData deliveroperatedata = DeliverOperateData.parseFrom(data);
				callletter = deliveroperatedata.getData().getCallLetter().trim();
				if (deliveroperatedata.hasGatewayid() && deliveroperatedata.hasGatewaytype()) {
					gatewayid = deliveroperatedata.getGatewayid();
					gatewaytype = deliveroperatedata.getGatewaytype();
					//先改变缓存区终端信息
					UnitInfoManager.unitinfomanager.setGatewayId(callletter, gatewayid, (gatewaytype==0));
				}
				bhistory = false;
				if (deliveroperatedata.getData().hasHistory()) {
					bhistory = (deliveroperatedata.getData().getHistory() != 0);
				}
				//判断是否要转发到那些客户端
				CenterClientManager.clientManager.deliverClient(callletter, MessageType.DeliverOperateData, deliveroperatedata.toByteArray(), bhistory);
			}
			callletter = null;
			mqoperatedata = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
