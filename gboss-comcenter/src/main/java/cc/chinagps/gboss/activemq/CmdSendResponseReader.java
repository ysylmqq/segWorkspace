/*
********************************************************************************************
Discription:  从ActiveMQ接收网关发送指令成功的消息，并处理
			  
2.9 指令回应信息
队列名称：cmdSendResponse
队列类型：topic
消息类型：BytesMessage
消息内容：SendCommandSend_ACK(protobuf结构，参见comcenter.proto的定义)的二进制数据

Written By:   ZXZ
Date:         2014-10-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.activemq;

import javax.jms.Connection;

import cc.chinagps.gboss.comcenter.command.CommandManager;
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQSendCommandSend_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommandSend_ACK;

public class CmdSendResponseReader extends InfoReader {

	public CmdSendResponseReader(Connection connection) {
		super("cmdSendResponse", Type.Topic, connection);
	}

	/*
	 * 处理指令应答 , 每次只有一个SendCommandSend_ACK结构
	 */
	@Override
	protected void processByteMessage(byte[] data) {
		try {
			MQSendCommandSend_ACK mqcmdsendack = MQSendCommandSend_ACK.parseFrom(data);
			for(SendCommandSend_ACK cmdsendack : mqcmdsendack.getSendacksList()) {
				CommandManager.responsethread.appendResponse(cmdsendack);
			}
			mqcmdsendack = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
