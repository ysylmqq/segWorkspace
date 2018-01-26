/*
********************************************************************************************
Discription:  从ActiveMQ接收终端登退录信息，并处理
			  
2.7 车台登退录信息
队列名称：loginout
队列类型：topic
消息类型：BytesMessage
消息内容：DeliverUnitLoginOut(protobuf结构，参见comcenter.proto的定义)的二进制数据

Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.activemq;

import org.apache.log4j.Logger;
import javax.jms.Connection;

import cc.chinagps.gboss.Log.LogManager;
import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.UnitInfoManager;
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQUnitLoginOut;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitLoginOut;
import cc.chinagps.gboss.comcenter.buff.MessageType;

public class LoginoutReader extends InfoReader {

    protected Logger logger = null;
    
	public LoginoutReader(Connection connection) {
		super("loginout", Type.Topic, connection);
		LogManager.init();
		logger = Logger.getLogger("nameCmd");
	}

	/*
	 * 处理终端登奶录信息 , 每次只有一个DeliverUnitLoginOut结构
	 */
	@Override
	protected void processByteMessage(byte[] data) {
		try {
			String callletter = null;
			int gatewayid = 0;
			int inorout = 0;
			boolean bupgrade = false;
			long ltime = 0;
			MQUnitLoginOut mqunitloginout = MQUnitLoginOut.parseFrom(data);
			for(DeliverUnitLoginOut deliverloginout : mqunitloginout.getDeliverunitloginoutsList()) {
				//DeliverUnitLoginOut deliverloginout = DeliverUnitLoginOut.parseFrom(data);
				callletter = deliverloginout.getCallLetter().trim();
				if (deliverloginout.hasGatewayid() && deliverloginout.hasInorout()) {
					bupgrade = false;
					if (deliverloginout.hasUpgradegateway()) {
						bupgrade = deliverloginout.getUpgradegateway(); 
					}
					//只有数据网关才执行下面操作，升级网关不执行
					if (!bupgrade) {
						gatewayid = deliverloginout.getGatewayid();
						inorout = deliverloginout.getInorout();
						//下面是查询登退录延迟原因
						ltime = 0;
						if (deliverloginout.hasLogoutTime()) {
							ltime = deliverloginout.getLogoutTime();
						} else if (deliverloginout.hasLoginTime()) {
							ltime = deliverloginout.getLoginTime();
						}
						if (callletter.equals("18964059013") || callletter.equals("13912345001")) {
							logger.info(callletter + ((inorout!=0) ? " 登录" : " 退录") + gatewayid);
						}
						//先改变缓存区终端信息
						UnitInfoManager.unitinfomanager.setGatewayId(callletter, ltime, gatewayid, inorout, true);
						if (inorout == 0) {
							//脱网提醒
							CenterClientManager.clientManager.addAppNoticeInfo(deliverloginout, callletter);
						}
					}
				}
				//判断是否要转发到那些客户端
				CenterClientManager.clientManager.deliverClient(callletter, MessageType.DeliverUnitLoginOut, deliverloginout.toByteArray(), false);
			}
			callletter = null;
			mqunitloginout = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
