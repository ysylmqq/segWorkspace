/*
********************************************************************************************
Discription:  ActiveMQ统一管理, 主要入口
			  
			  
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
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import cc.chinagps.gboss.comcenter.command.CommandManager;
import cc.chinagps.gboss.comcenter.command.SendCommandInfo;
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQSendCommand;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand;
import cc.chinagps.lib.util.SystemConfig;

public class ActiveMQManager {
	//设置一个全局变量
	public static ActiveMQManager activemq = null;
	private ConnectionFactory factory = null;
	//接收终端递交信息用一个连接
	private Connection gpsconnection = null;
	private Connection readconnection = null;
	private Connection loginoutconnection = null;
	//发指令用一个连接
	private Connection cmdconnection = null;
	//private Session cmdsession = null;

	//多少次提交一次
	public int BATCH_SIZE_READ = 500;
	public int BATCH_SIZE_WRITE = 500;
	
	private GPSReader gpsreader = null;
	private AlarmReader alarmreader = null;
	private OperateDataReader operatedatareader = null;
	private SMReader smsreader = null;
	private CmdResponseReader cmdresponsereader = null;
	private CmdSendResponseReader cmdsendresponsereader = null;
	private LoginoutReader loginoutreader = null;
	private TravelReader travelreader = null;
	private FaultReader faultreader = null;
	private OBDReader obdreader = null;
	private UnitVersionReader unitversionreader = null;
	private ECUConfigReader ecuconfigreader = null;
	
	//手机通知
	public AppNoticeThread appnoticethread = null;
	
	public Connection createConnection() {
		try {
			String user = SystemConfig.getActiveMQProperty("user");
			String password = SystemConfig.getActiveMQProperty("password");
			return factory.createConnection(user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 初始化ActiveMQ:
	 * 1.获取从activeMQ批量读取数量和缓存最大长度
	 * 2.初始化ActiveMQConnectionFactory
	 */
	public boolean start(){
		//url 必须以failover:开始,才能自动连接,并且可以自动分布式连接其他服务器
		//failover:(tcp://localhost:61616,tcp://90.0.12.135:61616)?initialReconnectDelay=100
		String url = SystemConfig.getActiveMQProperty("url");
		System.out.println(url);
		BATCH_SIZE_READ = Integer.valueOf(SystemConfig.getActiveMQProperty("batch_size_read"));
		BATCH_SIZE_WRITE = Integer.valueOf(SystemConfig.getActiveMQProperty("batch_size_write"));
		//factory = new ActiveMQConnectionFactory("system", "seggps123456", url);
		factory = new ActiveMQConnectionFactory(url);
		try {
			gpsconnection = createConnection();
			readconnection = createConnection();
			loginoutconnection = createConnection();
			cmdconnection = createConnection();
			gpsconnection.start();
			readconnection.start();
			loginoutconnection.start();
			cmdconnection.start();
			
			//cmdsession = cmdconnection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			gpsreader = new GPSReader(gpsconnection);
			gpsreader.startRead();
			
			alarmreader = new AlarmReader(readconnection);
			alarmreader.startRead();
			
			operatedatareader = new OperateDataReader(readconnection);
			operatedatareader.startRead();
			
			smsreader = new SMReader(readconnection);
			smsreader.startRead();
			
			loginoutreader = new LoginoutReader(loginoutconnection);
			loginoutreader.startRead();
			
			travelreader = new TravelReader(readconnection);
			travelreader.startRead();
			
			faultreader = new FaultReader(readconnection);
			faultreader.startRead();
			
			obdreader = new OBDReader(readconnection);
			obdreader.startRead();

			unitversionreader = new UnitVersionReader(loginoutconnection);
			unitversionreader.startRead();
			
			ecuconfigreader = new ECUConfigReader(loginoutconnection);
			ecuconfigreader.startRead();
			
			//读终端返回指令结果
			cmdresponsereader = new CmdResponseReader(readconnection);
			cmdresponsereader.startRead();

			//读网关发送指令成功
			cmdsendresponsereader = new CmdSendResponseReader(readconnection);
			cmdsendresponsereader.startRead();
			
			appnoticethread = new AppNoticeThread();
			appnoticethread.start();
			System.out.println("appnotice thread started!");
			
            System.out.println("activeMQ started.");
            return true;
		} catch (Exception e) {
            System.out.println("activeMQ unstart:");
			e.printStackTrace();
		}
		return false;
	}
	
	// [start] 批量写指令到MQ
	//批量写入ActiveMQ
	public boolean writeCommand(Short gatewayid, ArrayList<SendCommandInfo> mqcmdlist) {
		try {
			String cmdname = "cmd" + gatewayid.toString();
			if (CommandManager.smsgatewaymap.containsKey(gatewayid)) {	//短信代理程序发送短信
				cmdname = "cmd_sm";
			}
			//cmdconnection.start();
			//Session session = cmdconnection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Session cmdsession = cmdconnection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Queue queue = cmdsession.createQueue(cmdname);
			MessageProducer producer = cmdsession.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);	//NON_PERSISTENT
			int icount = 0;
			MQSendCommand.Builder mqcmdbuilder = null;
			for(SendCommandInfo mqcmd: mqcmdlist) {
				if (mqcmdbuilder == null) {
					mqcmdbuilder = MQSendCommand.newBuilder();
				}
				SendCommand sendcmd = getSendCommand(mqcmd);
				mqcmdbuilder.addSendcommands(sendcmd);
				if (((++icount) & 0x3F) != 0) {
					continue;
				}
				BytesMessage sendmsg = cmdsession.createBytesMessage();
				sendmsg.writeBytes(mqcmdbuilder.build().toByteArray());
				producer.send(sendmsg);
				mqcmdbuilder = null;
				sendmsg = null;
			}
			if (mqcmdbuilder != null) {
				BytesMessage sendmsg = cmdsession.createBytesMessage();
				sendmsg.writeBytes(mqcmdbuilder.build().toByteArray());
				producer.send(sendmsg);
				mqcmdbuilder = null;
				sendmsg = null;
			}
			cmdsession.commit();
			//System.out.println("write command to mq");
			producer.close();
			cmdsession.close();
			return true;
		} catch (JMSException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	/*
	 * 将命令转换成protobuf格式
	 */
	private SendCommand getSendCommand(SendCommandInfo cmdinfo) {
		SendCommand.Builder sendcmd = SendCommand.newBuilder();
		sendcmd.setCmdId(cmdinfo.cmdId);
		sendcmd.setChannelId(cmdinfo.channelId);
		sendcmd.setSn(cmdinfo.cmdsn + cmdinfo.cmdtime1);
		sendcmd.addCallLetters(cmdinfo.callletter);
		sendcmd.clearParams();
		sendcmd.addAllParams(cmdinfo.sendparams);
		return sendcmd.build();
	}
	// [end] 写指令到MQ
}

/*
 * The Failover Transport
The Failover transport layers reconnect logic on top of any of the other transports. (We used to call this transport the Reliable transport in ActiveMQ 3).
The Failover configuration syntax allows you to specify any number of composite uris. The Failover transport randomly chooses one of the composite URI and attempts to establish a connection to it. If it does not succeed or if it subsequently fails, a new connection is established to one of the other uris in the list.
Configuration Syntax
failover:(uri1,...,uriN)?transportOptions
or
failover:uri1,...,uriN
The failover transport uses random by default which lets you to load balance clients over a number of brokers.
If you would rather connect to a primary first and only connect to a secondary backup broker if the primary is unavailable, turn off randomizing using something like
failover:(tcp://primary:61616,tcp://secondary:61616)?randomize=false
Transport Options
Option Name
Default Value
Description
initialReconnectDelay
10
How long to wait before the first reconnect attempt (in ms)
maxReconnectDelay
30000
The maximum amount of time we ever wait between reconnect attempts (in ms)
useExponentialBackOff
true
Should an exponential backoff be used btween reconnect attempts
reconnectDelayExponent
2.0
The exponent used in the exponential backoff attempts
maxReconnectAttempts
-1 | 0
From version 5.6 onwards: -1 is default and means retry forever, 0 means don't retry (only try connection once but no retry). 
Prior to version 5.6: 0 is default and means retry forever. 
All versions: If set to >0, then this is the maximum number of reconnect attempts before an error is sent back to the client.
startupMaxReconnectAttempts
0
If not 0, then this is the maximum number of reconnect attempts before an error is sent back to the client on the first attempt by the client to start a connection, once connected the maxReconnectAttempts option takes precedence.
randomize
true
use a random algorithm to choose the the URI to use for reconnect from the list provided
backup
false
initialize and hold a second transport connection - to enable fast failover
timeout
-1
Enables timeout on send operations (in miliseconds) without interruption of reconnection process
trackMessages
false
keep a cache of in-flight messages that will flushed to a broker on reconnect
maxCacheSize
131072
size in bytes for the cache, if trackMessages is enabled
updateURIsSupported
true
Determines whether the client should accept updates to its list of known URIs from the connected broker. Added in ActiveMQ 5.4
updateURIsURL
null
A URL (or path to a local file) to a text file containing a comma separated list of URIs to use for reconnect in the case of failure. Added in ActiveMQ 5.4
nested.*
null
Extra options to add to the nested URLs. Added in ActiveMQ 5.9
warnAfterReconnectAttempts.*
10
After every N reconnect attempts log a warning to indicate there is no connection but that we are still trying, set to <= 0 to disable. Added in ActiveMQ 5.10
reconnectSupported	true	Determines whether the client should respond to broker ConnectionControl events with a reconnect (see: rebalanceClusterClients)
Example URI
failover:(tcp://localhost:61616,tcp://remotehost:61616)?initialReconnectDelay=100
If the above gives errors try it this way (this way works in ActiveMQ 4.1.1 the one above does not)
failover://(tcp://localhost:61616,tcp://remotehost:61616)?initialReconnectDelay=100
Notes
If you use failover, and a broker dies at some point, your sends will block by default. Using TransportListener can help with this regard. It is best to set the Listener directly on the ActiveMQConnectionFactory so that it is in place before any request that may require an network hop.
Additionally you can use timeout option which will cause your current send to fail after specified timeout. The following URL, for example
failover:(tcp://primary:61616)?timeout=3000
will cause send to fail after 3 seconds if the connection isn't established. The connection will not be killed, so you can try sending messages later at some point using the same connection (presumably some of your brokers will be available again). Timeouts on the failover transport are available since 5.3 version.
Transactions
The Failover transport tracks transactions by default. The inflight transactions are replayed on reconnection. For simple scenarios this works ok. However there is an assumption for acknowledged (or consumer) transactions, that the previously received messages will get relayed after a reconnect. This is not always true when there are many connections and consumers, as redelivery order is not guaranteed. It is possible to have stale outstanding acknowledgements that can interfere with newly delivered messages, potentially leading to unacknowledged messages.
Starting in version 5.3.1, redelivery order is tracked and a transaction will fail to commit (throw a TransactionRolledBackException) if outstanding messages are not redelivered after failover. In addition, in doubt transaction will now result in a rollback such that they can be replayed by the application. In doubt transactions occur when failover happens with a commit message inflight. It is not possible to know the exact point of failure. Did the transaction commit message get delivered or was it just the commit reply that is lost? In this case, it is necessary to rollback so that the application can get an indication of the failure and deal with any potential problem.
Broker side Options for Failover
This is new in version 5.4:
There are some options that are available on a TransportConnector that is used by the broker that can be used to update clients automatically with information about new brokers to failover to. These are:
Option Name
Default Value
Description
updateClusterClients
false
if true, pass information to connected clients about changes in the topology of the broker cluster
rebalanceClusterClients
false
if true, connected clients will be asked to rebalance across a cluster of brokers when a new broker joins the network of brokers (note: priorityBackup=true can override)
updateClusterClientsOnRemove
false
if true, will update clients when a cluster is removed from the network. Having this as separate option enables clients to be updated when new brokers join, but not when brokers leave.
updateClusterFilter
null
comma separated list of regular expression filters used to match broker names of brokers to designate as being part of the failover cluster for the clients
An example as defined within the broker's XML configuration file:
<broker>
  ...
  <transportConnectors>
    <transportConnector name="openwire" uri="tcp://0.0.0.0:61616" updateClusterClients="true" updateClusterFilter="*A*,*B*" />
  </<transportConnectors>
  ...
</broker>
If updateClusterClients is enabled, then your clients will only need to know about the first broker to connect to in a cluster of brokers - e.g.:
failover://tcp://primary:61616
If new brokers join, the client will automatically be updated with the additional URI of that broker to connect to in the event of a network or broker failure.
More Information
Also check out the following blog entry about using the cluster client updates and rebalancing features titled New Features in ActiveMQ 5.4: Automatic Cluster Update and Rebalance.
Priority Backup
If your setup have brokers in both local and remote networks, you probably want your clients connected to the local ones if those are available. As of version 5.6, ActiveMQ supports priority backup feature, so you can have your clients automatically reconnect to so called priority (or local) urls. Consider the following url
failover:(tcp://local:61616,tcp://remote:61616)?randomize=false&priorityBackup=true
If this url is used for the client, the client will try to connect and stay connected to the local broker. If local broker fails, it will of course fail over to the remote one. But as priorityBackup parameter is used, it will constantly try to reconnect to the local broker. Once it can do so, the client will get back to it without any need for manual intervention.
By default, only the first url in the list is considered prioritized (local). In most cases this will suffice, but in some cases you can have multiple "local" urls. You can configure which urls are considered prioritized, by using priorityURIs parameter, like
failover:(tcp://local1:61616,tcp://local2:61616,tcp://remote:61616)?randomize=false&priorityBackup=true&priorityURIs=tcp://local1:61616,tcp://local2:61616
In this case the client will prioritize either local1 or local2 brokers and (re)connect to them if they are available.
Passing extra options to the nested URLs.
This is new in version 5.9:
You can now add options the nested URLs via options on the failover URL. Previously, if you wanted to detect dead connections faster you had to add the wireFormat.maxInactivityDuration=1000 option to all the nested URLs in the failover list. For example:
failover:(tcp://host01:61616?wireFormat.maxInactivityDuration=1000,tcp://host02:61616?wireFormat.maxInactivityDuration=1000,tcp://host03:61616?wireFormat.maxInactivityDuration=1000)
As of ActiveMQ 5.9, you can now do the same thing using the following URL:
failover:(tcp://host01:61616,tcp://host02:61616,tcp://host03:61616)?nested.wireFormat.maxInactivityDuration=1000

 */

/*
一：jms介绍
jms说白了就是java message service，是J2EE规范的一部分，跟jdbc差不多，sun只提供了接口，由各个厂商（provider）来进行具体的实现，然后使用者使用他们的jar包进行开发使用即可。
另外在jms的API中，jms传递消息有两种方式，一种是点对点的Queue，还有一个是发布订阅的Topic方式。区别在于：
对于Queue模式，一个发布者发布消息，下面的接收者按队列顺序接收，比如发布了10个消息，两个接收者A,B那就是A,B总共会收到10条消息，不重复。
对于Topic模式，一个发布者发布消息，有两个接收者A,B来订阅，那么发布了10条消息，A,B各收到10条消息。
关于api的简单基础可以看下：http://www.javaeye.com/topic/64707，简单的参考！
二：ActiveMQ介绍
activeMQ是apache下的一个开源jms产品，具体参见apache官方网站；
Apache ActiveMQ is fast, supports many Cross Language Clients and Protocols, comes with easy to use Enterprise Integration Patterns and many advanced features while fully supporting JMS 1.1 and J2EE 1.4. Apache ActiveMQ is released under the Apache 2.0 License
三：开始实现代码
1： 使用activeMQ来完成jms的发送，必须要下载activeMQ，然后再本机安装，并且启动activeMQ的服务才行。在官网下载完成之后，运行bin目录下面的activemq.bat，将activeMQ成功启动。
启动成功之后可以运行：http://localhost:8161/admin/index.jsp 查看一下。
2：发送端，sender

imp 
ort javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
private static final int SEND_NUMBER = 5;

public static void main(String[] args) {
// ConnectionFactory ：连接工厂，JMS 用它创建连接
ConnectionFactory connectionFactory;
// Connection ：JMS 客户端到JMS Provider 的连接
Connection connection = null;
// Session： 一个发送或接收消息的线程
Session session;
// Destination ：消息的目的地;消息发送给谁.
Destination destination;
// MessageProducer：消息发送者
MessageProducer producer;
// TextMessage message;
// 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar

connectionFactory = new ActiveMQConnectionFactory(
        ActiveMQConnection.DEFAULT_USER,
        ActiveMQConnection.DEFAULT_PASSWORD,
        "tcp://localhost:61616");
try {
    // 构造从工厂得到连接对象
   connection = connectionFactory.createConnection();
    // 启动
   connection.start();
    // 获取操作连接
   session = connection.createSession(Boolean.TRUE,
            Session.AUTO_ACKNOWLEDGE);
   // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
   destination = session.createQueue("test-queue");
    // 得到消息生成者【发送者】
   producer = session.createProducer(destination);
   // 设置不持久化，可以更改
   producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
   // 构造消息
   sendMessage(session, producer);
    session.commit();

} catch (Exception e) {
    e.printStackTrace();
} finally {
    try {
        if (null != connection)
            connection.close();
    } catch (Throwable ignore) {
    }
}

}
public static void sendMessage(Session session, MessageProducer producer)
    throws Exception {
for (int i = 1; i <=SEND_NUMBER; i++) {
    TextMessage message = session
            .createTextMessage("ActiveMq 发送的消息" + i);
    // 发送消息到目的地方
   System.out.println("发送消息:" + i);
    producer.send(message);
}
}
}

 3：接收端，receive
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {
public static void main(String[] args) {

// ConnectionFactory ：连接工厂，JMS 用它创建连接
ConnectionFactory connectionFactory;
// Connection ：JMS 客户端到JMS Provider 的连接
Connection connection = null;
// Session： 一个发送或接收消息的线程
Session session;
// Destination ：消息的目的地;消息发送给谁.
Destination destination;
// 消费者，消息接收者
MessageConsumer consumer;

connectionFactory = new ActiveMQConnectionFactory(
        ActiveMQConnection.DEFAULT_USER,
        ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
try {
    // 构造从工厂得到连接对象
   connection = connectionFactory.createConnection();
    // 启动
   connection.start();
    // 获取操作连接
   session = connection.createSession(Boolean.FALSE,
            Session.AUTO_ACKNOWLEDGE);
    //test-queue跟sender的保持一致，一个创建一个来接收
   destination = session.createQueue("test-queue");
    consumer = session.createConsumer(destination);
    consumer.setMessageListener(new MessageListener() {
        public void onMessage(Message arg0) {
            System.out.println("==================");
            try {
                System.out.println("RECEIVE1第一个获得者:"
                        + ((TextMessage) arg0).getText());
            } catch (JMSException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    });

    MessageConsumer consumer1 = session.createConsumer(destination);
    consumer1.setMessageListener(new MessageListener() {
        public void onMessage(Message arg0) {
            System.out.println("+++++++++++++++++++");
            try {
                System.out.println("RECEIVE1第二个获得者:"
                        + ((TextMessage) arg0).getText());
            } catch (JMSException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    });
} catch (Exception e) {
    e.printStackTrace();
}
//在eclipse里运行的时候，这里不要关闭，这样就可以一直等待服务器发送了，不然就直接结束了。
// } finally {
// try {
// if (null != connection)
// connection.close();
// } catch (Throwable ignore) {
// }
// }

}
}

 4：发送端，sender
 上面的是用Queue的方式来创建，下面再用topic的方式实现同样的功能。

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class TopicTest {
public static void main(String[] args) throws Exception {
ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
       "tcp://localhost:61616");

Connection connection = factory.createConnection();
connection.start();

// 创建一个Topic
Topic topic = new ActiveMQTopic("testTopic");
Session session = connection.createSession(false,
        Session.AUTO_ACKNOWLEDGE);

// 注册消费者1
MessageConsumer comsumer1 = session.createConsumer(topic);
comsumer1.setMessageListener(new MessageListener() {
    public void onMessage(Message m) {
        try {
            System.out.println("Consumer1 get "
                    + ((TextMessage) m).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
});

// 注册消费者2
MessageConsumer comsumer2 = session.createConsumer(topic);
comsumer2.setMessageListener(new MessageListener() {
    public void onMessage(Message m) {
        try {
            System.out.println("Consumer2 get "
                    + ((TextMessage) m).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

});

// 创建一个生产者，然后发送多个消息。
MessageProducer producer = session.createProducer(topic);
for (int i = 0; i < 10; i++) {
    System.out.println("producer begin produce=======");
    producer.send(session.createTextMessage("Message:" + i));
}
}

}
*/