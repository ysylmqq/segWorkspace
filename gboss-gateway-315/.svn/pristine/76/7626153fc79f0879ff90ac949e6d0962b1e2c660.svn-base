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

import cc.chinagps.gateway.buff.DeliverBuff.DeliverGPS;
import cc.chinagps.gateway.buff.DeliverBuff.MultDeliverGPS;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.mq.MQManager;

import com.google.protobuf.InvalidProtocolBufferException;

public class Receiver {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	public Receiver(){
		
	}
	//mq
	private Session session_gps;
	private Destination queue_gps;
	private MessageConsumer consumer_gps;
		
	public void init() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]init gps reader start");
		
		Connection connection = MQManager.openConnection();
		connection.start();
		
		session_gps = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		if(MQManager.QUEUE_TYPE_GPS == 1){
			queue_gps = session_gps.createQueue(MQManager.QUEUE_NAME_GPS);
		}else{
			queue_gps = session_gps.createTopic(MQManager.QUEUE_NAME_GPS);
		}
		
		consumer_gps = session_gps.createConsumer(queue_gps);
		System.out.println("[" + sdf.format(new Date()) + "]init gps reader end");
	}
	
	public void startWorker() throws JMSException{
		
		consumer_gps.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				System.out.println("gps reader message coming....");
				try{
					if(BytesMessage.class.isInstance(msg)){
						BytesMessage byteMsg = (BytesMessage) msg;
						int len = (int) byteMsg.getBodyLength();
						byte[] data = new byte[len];						
						byteMsg.readBytes(data);						
						try{
							//CommandBuff.Command cmd = CommandBuff.Command.parseFrom(data);						
							//unitServer.sendCommand(cmd, null);
							
							MultDeliverGPS multDeliverGPS = MultDeliverGPS.parseFrom(data);
							List<DeliverGPS> list =multDeliverGPS.getMdeliverGPSList();
							for (DeliverGPS deliverGPS : list) {
								System.out.println("GPSReader gateway id: "+ deliverGPS.getGatewayid());
								GpsInfo gpsInfo = deliverGPS.getGpsinfo();
								System.out.println("GPSReader callletter:"+gpsInfo.getCallLetter());
								GpsBaseInfo gpsBaseInfo =gpsInfo.getBaseInfo();
								System.out.println("GPSReader gpsBaseInfo lat:"+gpsBaseInfo.getLat()+";lng:"+gpsBaseInfo.getLng());
							}
							
						}catch (InvalidProtocolBufferException e) {
							if(logger_debug.isDebugEnabled()){
								logger_debug.debug("(mq)gpsreader data error:" + e);
							}
						}
					}else{
						if(logger_debug.isDebugEnabled()){
							logger_debug.debug("(mq)gpsreader type error:" + msg.getClass());
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
			Receiver receiver=new Receiver();
			receiver.init();
			receiver.startWorker();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}