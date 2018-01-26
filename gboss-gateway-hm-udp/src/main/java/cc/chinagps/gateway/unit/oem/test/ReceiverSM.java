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

import com.google.protobuf.InvalidProtocolBufferException;

import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.MultCommand;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.mq.MQManager;

public class ReceiverSM {
	
	public ReceiverSM(){
		
	}
	private Session session_sm;
	private Destination queue_sm;
	private MessageConsumer consumer_sm;
		
	public void init() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]init gps reader start");
		
		Connection connection = MQManager.openConnection();
		connection.start();
		
		session_sm = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		if(MQManager.QUEUE_TYPE_CMD == 1){
			queue_sm = session_sm.createQueue("cmd_sm");
		}else{
			queue_sm = session_sm.createTopic("cmd_sm");
		}
		
		consumer_sm = session_sm.createConsumer(queue_sm);
		System.out.println("[" + sdf.format(new Date()) + "]init gps reader end");
	}
	
	public void startWorker() throws JMSException{
		
		consumer_sm.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				System.out.println("cmd_sm reader message coming....");
				try{
					if(BytesMessage.class.isInstance(msg)){
						BytesMessage byteMsg = (BytesMessage) msg;
						int len = (int) byteMsg.getBodyLength();
						byte[] data = new byte[len];						
						byteMsg.readBytes(data);						
						try{
							MultCommand multCommand = MultCommand.parseFrom(data);
							List<Command> list =multCommand.getMcommandList();
							for (Command cmd : list) {
								if("13684969490".equals(cmd.getCallLetter(0)))
									System.err.println(cmd);
								else
									System.out.println(cmd);
							}
							
						}catch (InvalidProtocolBufferException e) {
							e.printStackTrace();
						}
					}else{
						
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
			ReceiverSM receiver=new ReceiverSM();
			receiver.init();
			receiver.startWorker();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}