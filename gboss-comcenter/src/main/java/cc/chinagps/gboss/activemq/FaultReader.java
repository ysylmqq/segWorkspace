/*
********************************************************************************************
Discription:  从ActiveMQ接收故障信息，并处理(转发到客户端)
			  
2.6 模块故障信息
队列名称：fault
队列类型：topic
消息类型：BytesMessage
消息内容：DeliverFault(protobuf结构，参见comcenter.proto的定义)的二进制数据

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
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQFault;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverFault;

public class FaultReader extends InfoReader {

	public FaultReader(Connection connection) {
		super("fault", Type.Topic, connection);
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
			boolean bhistory = false;
			MQFault mqfault = MQFault.parseFrom(data);
			for(DeliverFault deliverfault : mqfault.getDeliverfaultsList()) {
				//DeliverFault deliverfault = DeliverFault.parseFrom(data);
				callletter = deliverfault.getFaultinfo().getCallLetter().trim();
				if (deliverfault.hasGatewayid() && deliverfault.hasGatewaytype()) {
					gatewayid = deliverfault.getGatewayid();
					gatewaytype = deliverfault.getGatewaytype();
					//先改变缓存区终端信息
					UnitInfoManager.unitinfomanager.setGatewayId(callletter, gatewayid, (gatewaytype==0));
				}
				bhistory = false;
				if (deliverfault.getFaultinfo().hasHistory()) {
					bhistory = (deliverfault.getFaultinfo().getHistory() != 0);
					if (bhistory) {
						long expiredtime = System.currentTimeMillis() - deliverfault.getFaultinfo().getFaultTime();
						bhistory = (expiredtime > 120000);	//超过120秒才是真的补报 
					}
				}
				//判断是否要转发到那些客户端
				CenterClientManager.clientManager.deliverClient(callletter, MessageType.DeliverFault, deliverfault.toByteArray(), bhistory);
			}
			callletter = null;
			mqfault = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
