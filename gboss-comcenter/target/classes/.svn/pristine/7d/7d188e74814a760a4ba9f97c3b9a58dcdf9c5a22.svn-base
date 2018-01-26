/*
********************************************************************************************
Discription:  从ActiveMQ接收终指令应答信息，并处理
			  
2.9 指令回应信息
队列名称：cmdResponse
队列类型：topic
消息类型：BytesMessage
消息内容：SendCommand_ACK(protobuf结构，参见comcenter.proto的定义)的二进制数据

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

import cc.chinagps.gboss.comcenter.command.CommandManager;
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQSendCommand_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand_ACK;

public class CmdResponseReader extends InfoReader {

	public CmdResponseReader(Connection connection) {
		super("cmdResponse", Type.Topic, connection);
	}

	/*
	 * 处理指令应答 , 每次只有一个SendCommand_ACK结构
	 */
	@Override
	protected void processByteMessage(byte[] data) {
		try {
			MQSendCommand_ACK mqcmdack = MQSendCommand_ACK.parseFrom(data);
			for(SendCommand_ACK cmdack : mqcmdack.getCommandacksList()) {
				//SendCommand_ACK cmdack = SendCommand_ACK.parseFrom(data);
				CommandManager.responsethread.appendResponse(cmdack);
			}
			mqcmdack = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
