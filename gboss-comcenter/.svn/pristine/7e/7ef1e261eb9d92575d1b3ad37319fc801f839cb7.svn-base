/*
 ********************************************************************************************
Discription:  写ActiveMQ信息
			  
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
 */
package cc.chinagps.gboss.activemq;

import java.util.ArrayList;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ConnectionFailedException;
import org.apache.activemq.command.ActiveMQTopic;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class InfoWriter {

	private Connection connection; // 所有读信息共享一个连接，用不同session
	private String name;

	public InfoWriter(String name, Connection connection) {
		this.name = name;
		this.connection = connection;
	}

	public boolean writeMessage(ArrayList<byte[]> msges) {
		try {
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			ActiveMQTopic topic = new ActiveMQTopic(name);
			MessageProducer producer = session.createProducer(topic);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			int count = 0;
			for (byte[] msg : msges) {
				BytesMessage sendmsg;
				try {
					sendmsg = session.createBytesMessage();
					sendmsg.writeBytes(msg);
					producer.send(sendmsg);
					if (++count > ActiveMQManager.activemq.BATCH_SIZE_WRITE) {
						session.commit();
					}
				} catch (JMSException e) {
					e.printStackTrace();
					return false;
				}
			}
			session.commit();
			return true;
		} catch (ConnectionFailedException e) {
			System.out.println("ActiveMQ 连接断开");
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean writeMessage(ComCenterBaseMessage[] msges) {
		try {
			connection.start();
			Session session = connection.createSession(true,
					Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(name);
			MessageProducer producer = session.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			int count = 0;
			for (ComCenterBaseMessage basemsg : msges) {
				BytesMessage sendmsg;
				try {
					sendmsg = session.createBytesMessage();
					sendmsg.writeBytes(basemsg.toByteArray());
					producer.send(sendmsg);
					if (++count > ActiveMQManager.activemq.BATCH_SIZE_WRITE) {
						session.commit();
					}
				} catch (JMSException e) {
					e.printStackTrace();
					return false;
				}
			}
			session.commit();
			return true;
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
		return true;
	}
}
