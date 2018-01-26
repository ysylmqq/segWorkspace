package cc.chinagps.gateway.unit.oem.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.log4j.Logger;

import com.google.protobuf.InvalidProtocolBufferException;

import cc.chinagps.gateway.buff.CommandBuff.CommandResponse;
import cc.chinagps.gateway.buff.CommandBuff.MultCommandResponse;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.mq.MQManager;

public class CmdResponseReceiver {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	public CmdResponseReceiver(){
		
	}
	//mq
	private Session session_cmd_response;
	private Destination queue_cmd_response;
	private MessageConsumer consumer_cmd_response;
		
	public void init() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]init cmd_response reader start");
		
		Connection connection = MQManager.openConnection();
		connection.start();
		
		session_cmd_response = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		if(MQManager.QUEUE_TYPE_CMD_RESPONSE == 1){
			queue_cmd_response = session_cmd_response.createQueue(MQManager.QUEUE_NAME_CMD_RESPONSE);
		}else{
			queue_cmd_response = session_cmd_response.createTopic(MQManager.QUEUE_NAME_CMD_RESPONSE);
		}
		
		consumer_cmd_response = session_cmd_response.createConsumer(queue_cmd_response);
		System.out.println("[" + sdf.format(new Date()) + "]init cmd_response reader end");
	}
	
	public void startWorker() throws JMSException{
		
		consumer_cmd_response.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				System.out.println("cmd_response reader message coming....");
				try{
					if(BytesMessage.class.isInstance(msg)){
						BytesMessage byteMsg = (BytesMessage) msg;
						int len = (int) byteMsg.getBodyLength();
						byte[] data = new byte[len];						
						byteMsg.readBytes(data);						
						try{
							//CommandBuff.Command cmd = CommandBuff.Command.parseFrom(data);						
							//unitServer.sendCommand(cmd, null);
							
							MultCommandResponse multCommandResponse = MultCommandResponse.parseFrom(data);
							List<CommandResponse> list =multCommandResponse.getMcommandResponseList();
							for (CommandResponse commandResponse : list) {
								System.out.println("commandResponse:"+commandResponse);
							}
							
						}catch (InvalidProtocolBufferException e) {
							if(logger_debug.isDebugEnabled()){
								logger_debug.debug("(mq)commandResponse data error:" + e);
							}
						}
					}else{
						if(logger_debug.isDebugEnabled()){
							logger_debug.debug("(mq)commandResponse type error:" + msg.getClass());
						}
					}
				}catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		
		try {
			LogManager.init();
			MQManager.init();
			CmdResponseReceiver receiver=new CmdResponseReceiver();
			receiver.init();
			receiver.startWorker();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}