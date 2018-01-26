/*
********************************************************************************************
Discription:  读ActiveMQ的信息
			  
			  
Written By:   ZXZ
Date:         2014-04-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.activemq;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

public abstract class InfoReader {
	//消息类型
	public enum Type {
		Queue, Topic
	}

	private Connection connection = null;	//所有读信息共享一个连接，用不同session
	private String name = "";		//消息队列名称
	private Type type=Type.Queue;	//消息队列类型
	private Session session = null;
	private Destination destination = null;
	private MessageConsumer consumer = null;
	private int count = 0;	//读的次数
	
	public InfoReader(String name, Type type, Connection connection){
		this.name = name;
		this.type = type;
		this.connection = connection;
	}
	
	protected abstract void processByteMessage(byte[] data); 
	
	public void startRead() {
		try {
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

			if (type == Type.Queue) {
				destination = session.createQueue(name);
			} else {
				destination = session.createTopic(name);
			}
			consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message msg) {					
			        if (msg instanceof BytesMessage) {//if (BytesMessage.class.isInstance(msg)) {
						BytesMessage byteMsg = (BytesMessage) msg;
						try {
							int len = (int) byteMsg.getBodyLength();
							byte[] data = new byte[len];
							byteMsg.readBytes(data);
							count++;
							if (count >= ActiveMQManager.activemq.BATCH_SIZE_READ) {
								session.commit();
								count = 0;
							}
							//处理接收数据
							processByteMessage(data);
							data = null;
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						try {
							//msg.clearBody();
							count++;
							if (count >= ActiveMQManager.activemq.BATCH_SIZE_READ) {
								session.commit();
								count = 0;
							}
						}catch (JMSException e) {
							e.printStackTrace();
						}
					}					
				}
			});
		} catch (Throwable e) {
			/*try {
				session.close();
				connection.stop();
			} catch (Exception e1) {
			}*/
			e.printStackTrace();
		}
	}
}