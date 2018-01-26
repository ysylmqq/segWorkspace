/*
********************************************************************************************
Discription:  通信中心单元测试工具，写ActiveMQ线程基类
			  
			  
Written By:   ZXZ
Date:         2014-08-19
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package testtools;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import cc.chinagps.lib.util.SystemConfig;

public abstract class WriteActiveMQ extends Thread {
	
	private ConnectionFactory factory = null;
	//发指令用一个连接
	private Connection writeconnection = null;
	protected Session session = null;
	protected MessageProducer producer = null; 
	private String topicname;
	
	public WriteActiveMQ(String topicname) {
		this.topicname = topicname;
		super.setName(topicname);
	}
	/**
	 * 初始化ActiveMQ:
	 * 1.获取从activeMQ批量读取数量和缓存最大长度
	 * 2.初始化ActiveMQConnectionFactory
	 */
	public void start(){
		//url 必须以failover:开始,才能自动连接,并且可以自动分布式连接其他服务器
		//failover:(tcp://localhost:61616,tcp://90.0.12.135:61616)?initialReconnectDelay=100
		String url = SystemConfig.getActiveMQProperty("url");
		System.out.println(url);
		factory = new ActiveMQConnectionFactory(url);
		try {
			writeconnection = factory.createConnection();
			writeconnection.start();
			session = writeconnection.createSession(true, Session.AUTO_ACKNOWLEDGE);	//true表示批量，要commit
			ActiveMQTopic mqtopic = new ActiveMQTopic(topicname);
			producer = session.createProducer(mqtopic);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			System.out.println("activeMQ started.");
			super.start();
		} catch (JMSException e) {
            System.out.println("activeMQ unstart:");
			e.printStackTrace();
		}
	}
	
	/*
	public boolean Write(String topicname, byte[] content) {
		try {
			writeconnection.start();
			Session session = writeconnection.createSession(true, Session.AUTO_ACKNOWLEDGE);	//true表示批量，要commit
			ActiveMQTopic mqtopic = new ActiveMQTopic(topicname);
			MessageProducer producer = session.createProducer(mqtopic);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			BytesMessage sendmsg;
			sendmsg = session.createBytesMessage();
			sendmsg.writeBytes(content);
			producer.send(sendmsg);
			session.commit();
			//JOptionPane.showMessageDialog(null, "插入到MQ成功", "提示", JOptionPane.WARNING_MESSAGE );
			return true;
		} catch (JMSException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "提示", JOptionPane.WARNING_MESSAGE );
		}
		return false;
	}*/

}
