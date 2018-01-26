package cc.chinagps.gateway.mq.export;

import java.io.IOException;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.SystemConfig;
import cc.chinagps.gateway.util.filecacher.DefaultFileCacher;
import cc.chinagps.gateway.util.filecacher.FData;
import cc.chinagps.gateway.util.filecacher.FileCacher;

public class SpecialAlarmHandler {
	private FileCacher cacher;
	
	private int readSize;
	
	private DeliverThread deliverThread;
	
	//mq
	private Session session;
	
	private MessageProducer producer;
	
	public SpecialAlarmHandler(Connection connection, String queueName, int queueType, int queueMode) throws IOException, JMSException{
		String filePath = SystemConfig.getSystemProperty("alarm_file_path");
		long max_size = Long.valueOf(SystemConfig.getSystemProperty("alarm_file_max_size"));
		
		readSize = Integer.valueOf(SystemConfig.getSystemProperty("alarm_file_read_size"));
		cacher = new DefaultFileCacher(max_size, filePath);
		
		initMQ(connection, queueName, queueType, queueMode);
		
		deliverThread = new DeliverThread();
		deliverThread.start();
	}
	
	private void initMQ(Connection connection, String queueName, int queueType, int queueMode) throws JMSException{
		session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		Destination destination;
		if(queueType == 1){
			destination = session.createQueue(queueName);
		}else{
			destination = session.createTopic(queueName);
		}
		
		producer = session.createProducer(destination);
		if(queueMode == 1){
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}else{
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		}
	}
	
	private long successCount = 0;
	
	public long getSuccessCount() {
		return successCount;
	}

	private long failCount = 0;
	
	public long getFailCount() {
		return failCount;
	}
	
	//write file
//	public boolean addAlarm(String callLetter, GPSInfos gps, SegPackage pkg){
//		byte[] data = SegPkgUtil.transformGPSInfo(callLetter, gps, pkg).toByteArray();
//		boolean success = cacher.write(data);
//		
//		if(success){
//			successCount ++;
//		}else{
//			failCount ++;
//		}
//		
//		return success;
//	}
	public boolean addAlarm(cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo specialAlarm){
		//byte[] data = specialAlarm.toByteArray();
		cc.chinagps.gateway.buff.DeliverBuff.DeliverAlarm.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverAlarm.newBuilder();
		builder.setAlarmInfo(specialAlarm);
		builder.setGatewayid(Constants.SYSTEM_ID_INT);
		builder.setGatewaytype(0);
		byte[] data = builder.build().toByteArray();
		
		boolean success = cacher.write(data);
		
		if(success){
			successCount ++;
		}else{
			failCount ++;
		}
		
		return success;
	}
	
	
	
	private int readPosition = 0;
	
	public int getReadPosition() {
		return readPosition;
	}

	//read file
	private class DeliverThread extends Thread{
		private boolean runFlag = true;
		
		@Override
		public void run() {
			Object writeLock = cacher.getWriteLock();
			while(runFlag){
				readPosition = 1;
				if(!cacher.hasReadFile()){
					readPosition = 2;
					synchronized (writeLock) {
						while(!cacher.hasWriteData()){
							try {
								readPosition = 3;
								writeLock.wait();
								readPosition = 4;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				readPosition = 5;
				try {
					List<FData> list = cacher.read(readSize);
					
					for(int i = 0; i < list.size(); i++){
						byte[] data = list.get(i).getData();
						BytesMessage msg = session.createBytesMessage();
						msg.writeBytes(data);
						producer.send(msg);
					}
					
					readPosition = 6;
					
					session.commit();
					
					readPosition = 7;
					
					cacher.confirmRead(list);
					
					readPosition = 8;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public FileCacher getFileCacher(){
		return cacher;
	}
}