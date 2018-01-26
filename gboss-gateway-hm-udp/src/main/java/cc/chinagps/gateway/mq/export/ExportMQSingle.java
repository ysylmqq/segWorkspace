package cc.chinagps.gateway.mq.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.buff.CommandBuff.SendCommandResult;
import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse;
import cc.chinagps.gateway.buff.GBossDataBuff.ECUConfig;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OperateData;
import cc.chinagps.gateway.buff.GBossDataBuff.ShortMessage.Builder;
import cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.UnitVersion;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.mq.MQManager;
import cc.chinagps.gateway.unit.beans.Loginout;
import cc.chinagps.gateway.util.Constants;

public class ExportMQSingle implements ExportMQInf{
	private boolean enabled = true;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	private List<MQItem> dataList = new ArrayList<MQItem>();
	
	public List<MQItem> getDataList() {
		return dataList;
	}

	private Object dataLock = new Object();
	
	public Object getDataLock() {
		return dataLock;
	}

	//mq
	private Session session;
	
	private Map<String, Destination> queueMap = new HashMap<String, Destination>();
	private Map<String, MessageProducer> producerMap = new HashMap<String, MessageProducer>();
	
	//一类警情
	private SpecialAlarmHandler specialAlarmHandler;
	
	public SpecialAlarmHandler getSpecialAlarmHandler() {
		return specialAlarmHandler;
	}

	public void init() throws JMSException, IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]init mq-export start");
		Connection connection = MQManager.openConnection();
		connection.start();
		
		session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		
		addQueueOrTopic(MQManager.QUEUE_NAME_CMD_RESPONSE, MQManager.QUEUE_TYPE_CMD_RESPONSE, MQManager.QUEUE_MODE_CMD_RESPONSE);
		addQueueOrTopic(MQManager.QUEUE_NAME_SEND_CMD_RESULT, MQManager.QUEUE_TYPE_SEND_CMD_RESULT, MQManager.QUEUE_MODE_SEND_CMD_RESULT);
		addQueueOrTopic(MQManager.QUEUE_NAME_ALARM, MQManager.QUEUE_TYPE_ALARM, MQManager.QUEUE_MODE_ALARM);

		addQueueOrTopic(MQManager.QUEUE_NAME_GPS, MQManager.QUEUE_TYPE_GPS, MQManager.QUEUE_MODE_GPS);
		addQueueOrTopic(MQManager.QUEUE_NAME_SERVICE_DATA, MQManager.QUEUE_TYPE_SERVICE_DATA, MQManager.QUEUE_MODE_SERVICE_DATA);
		addQueueOrTopic(MQManager.QUEUE_NAME_SHORT_MESSAGE, MQManager.QUEUE_TYPE_SHORT_MESSAGE, MQManager.QUEUE_MODE_SHORT_MESSAGE);
		//OBD
		addQueueOrTopic(MQManager.QUEUE_NAME_OBD, MQManager.QUEUE_TYPE_OBD, MQManager.QUEUE_MODE_OBD);		
		addQueueOrTopic(MQManager.QUEUE_NAME_TRAVEL, MQManager.QUEUE_TYPE_TRAVEL, MQManager.QUEUE_MODE_TRAVEL);
		addQueueOrTopic(MQManager.QUEUE_NAME_FAULT, MQManager.QUEUE_TYPE_FAULT, MQManager.QUEUE_MODE_FAULT);
		//登录退录信息
		addQueueOrTopic(MQManager.QUEUE_NAME_LOGINOUT, MQManager.QUEUE_TYPE_LOGINOUT, MQManager.QUEUE_MODE_LOGINOUT);
		//车台软件版本
		addQueueOrTopic(MQManager.QUEUE_NAME_UNIT_VERSION, MQManager.QUEUE_TYPE_UNIT_VERSION, MQManager.QUEUE_MODE_UNIT_VERSION);
		//电控单元配置信息
		addQueueOrTopic(MQManager.QUEUE_NAME_ECU_CONFIG, MQManager.QUEUE_TYPE_ECU_CONFIG, MQManager.QUEUE_MODE_ECU_CONFIG);
		
		//一类警情
		specialAlarmHandler = new SpecialAlarmHandler(connection, MQManager.QUEUE_NAME_ALARM, MQManager.QUEUE_TYPE_ALARM, MQManager.QUEUE_MODE_ALARM);
		
		System.out.println("[" + sdf.format(new Date()) + "]init mq-export end");
	}
	
	private void addQueueOrTopic(String name, int type, int mode) throws JMSException{
		if(type == 1){
			addQueue(name, mode);
		}else{
			addTopic(name, mode);
		}
	}
	
	/**
	 * 指令回应
	 */
	public void addCommandResponse(CommandResponse commandResponse){
		if(!enabled){
			return;
		}
		
		byte[] data = commandResponse.toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_CMD_RESPONSE);
	}
	
	/**
	 * 发送指令成功
	 */
	public void addSendCommandResult(SendCommandResult sendCommandResult){
		if(!enabled){
			return;
		}
		
		byte[] data = sendCommandResult.toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_SEND_CMD_RESULT);
	}
	
	/**
	 * 短信
	 */
	public void addShortMessage(String callLetter, String message){
		if(!enabled){
			return;
		}
		
		Builder sm = GBossDataBuff.ShortMessage.newBuilder().setCallLetter(callLetter).setMsg(message);
		sm.setRecvTime(System.currentTimeMillis());
		cc.chinagps.gateway.buff.DeliverBuff.DeliverSMS.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverSMS.newBuilder();
		builder.setMsg(sm);
		builder.setGatewayid(Constants.SYSTEM_ID_INT);
		builder.setGatewaytype(0);
		
		byte[] data = builder.build().toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_SHORT_MESSAGE);
	}
	
	/**
	 * GPS
	 */
	public void addGPS(cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo gpsInfo){
		if(!enabled){
			return;
		}
		
		//byte[] data = gpsInfo.toByteArray();
		//addMQItem(data, MQManager.QUEUE_NAME_GPS);
		cc.chinagps.gateway.buff.DeliverBuff.DeliverGPS.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverGPS.newBuilder();
		builder.setGpsinfo(gpsInfo);
		builder.setGatewayid(Constants.SYSTEM_ID_INT);
		builder.setGatewaytype(0);
		
		byte[] data = builder.build().toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_GPS);
	}
	
	
	/**
	 * 普通警情
	 */
	public void addCommonAlarm(cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo alarm){
		if(!enabled){
			return;
		}
		
		//byte[] data = alarm.toByteArray();
		//addMQItem(data, MQManager.QUEUE_NAME_ALARM);
		cc.chinagps.gateway.buff.DeliverBuff.DeliverAlarm.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverAlarm.newBuilder();
		builder.setAlarmInfo(alarm);
		builder.setGatewayid(Constants.SYSTEM_ID_INT);
		builder.setGatewaytype(0);
		
		byte[] data = builder.build().toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_ALARM);
	}
	
	/**
	 * 一类警情
	 */
	public boolean addSpecialAlarm(cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo specialAlarm){
		if(!enabled){
			return false;
		}
		
		return specialAlarmHandler.addAlarm(specialAlarm);
	}
	
	/**
	 * 运营数据
	 */
//	public void addServiceData(String callLetter, ServiceData serviceData, ServiceDetail serviceDetail, SegPackage pkg){
//		if(!enabled){
//			return;
//		}
//		
//		//byte[] data = SegProtobufUtil.transformServiceData(callLetter, serviceData, serviceDetail, pkg).toByteArray();
//		//addMQItem(data, MQManager.QUEUE_NAME_SERVICE_DATA);
//		OperateData value = SegProtobufUtil.transformServiceData(callLetter, serviceData, serviceDetail, pkg);
//		cc.chinagps.gateway.buff.DeliverBuff.DeliverOperateData.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverOperateData.newBuilder();
//		builder.setData(value);
//		builder.setGatewayid(Constants.SYSTEM_ID_INT);
//		builder.setGatewaytype(0);
//		
//		byte[] data = builder.build().toByteArray();
//		addMQItem(data, MQManager.QUEUE_NAME_SERVICE_DATA);
//	}
	
	public void addOperateData(OperateData operateData){
		if(!enabled){
			return;
		}
		
		cc.chinagps.gateway.buff.DeliverBuff.DeliverOperateData.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverOperateData.newBuilder();
		builder.setData(operateData);
		builder.setGatewayid(Constants.SYSTEM_ID_INT);
		builder.setGatewaytype(0);
		
		byte[] data = builder.build().toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_SERVICE_DATA);
	}
	
	/**
	 * 车况信息(OBD)
	 */
	public void addOBDInfo(cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo value){
		if(!enabled){
			return;
		}
		
		cc.chinagps.gateway.buff.DeliverBuff.DeliverOBD.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverOBD.newBuilder();
		builder.setObdinfo(value);
		builder.setGatewayid(Constants.SYSTEM_ID_INT);
		builder.setGatewaytype(0);
		
		byte[] data = builder.build().toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_OBD);
	}
	
	/**
	 * 行程信息(OBD)
	 */
//	public void addTravelInfo(String callLetter, SegTravelInfo travelInfo, SegGPSInfo gps){
//		if(!enabled){
//			return;
//		}
//		
//		TravelInfo value = SegProtobufUtil.transformTravelInfo(callLetter, travelInfo, gps);
//		cc.chinagps.gateway.buff.DeliverBuff.DeliverTravel.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverTravel.newBuilder();
//		builder.setTravelinfo(value);
//		builder.setGatewayid(Constants.SYSTEM_ID_INT);
//		builder.setGatewaytype(0);
//		
//		byte[] data = builder.build().toByteArray();
//		addMQItem(data, MQManager.QUEUE_NAME_TRAVEL);
//	}
	public void addTravelInfo(TravelInfo value){
		if(!enabled){
			return;
		}
		
		cc.chinagps.gateway.buff.DeliverBuff.DeliverTravel.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverTravel.newBuilder();
		builder.setTravelinfo(value);
		builder.setGatewayid(Constants.SYSTEM_ID_INT);
		builder.setGatewaytype(0);
		
		byte[] data = builder.build().toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_TRAVEL);
	}
	
	/**
	 * 模块故障信息(OBD)
	 */
//	public void addFaultInfo(String callLetter, SegFaultInfo faultInfo){
//		if(!enabled){
//			return;
//		}
//		
//		FaultInfo value = SegProtobufUtil.transformFaultInfo(callLetter, faultInfo);
//		cc.chinagps.gateway.buff.DeliverBuff.DeliverFault.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverFault.newBuilder();
//		builder.setFaultinfo(value);
//		builder.setGatewayid(Constants.SYSTEM_ID_INT);
//		builder.setGatewaytype(0);
//		
//		byte[] data = builder.build().toByteArray();
//		addMQItem(data, MQManager.QUEUE_NAME_FAULT);
//	}
	public void addFaultInfo(FaultInfo value){
		if(!enabled){
			return;
		}
		
		cc.chinagps.gateway.buff.DeliverBuff.DeliverFault.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverFault.newBuilder();
		builder.setFaultinfo(value);
		builder.setGatewayid(Constants.SYSTEM_ID_INT);
		builder.setGatewaytype(0);
		
		byte[] data = builder.build().toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_FAULT);
	}
	
	/**
	 * 终端软件版本更新信息
	 */
	public void addUnitVersion(UnitVersion unitVersion){
		if(!enabled){
			return;
		}
		
		cc.chinagps.gateway.buff.DeliverBuff.DeliverUnitVersion.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverUnitVersion.newBuilder();
		builder.setUnitVersion(unitVersion);
		builder.setGatewayid(Constants.SYSTEM_ID_INT);
		builder.setGatewaytype(0);
		
		byte[] data = builder.build().toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_UNIT_VERSION);
	}
	
	/**
	 * 登录退录信息
	 */
	public void addUnitLoginOut(Loginout loginout){
		if(!enabled){
			return;
		}
		
		cc.chinagps.gateway.buff.DeliverBuff.DeliverUnitLoginOut.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverUnitLoginOut.newBuilder();
		builder.setCallLetter(loginout.getCallLetter());
		builder.setInorout(loginout.getIsLogin());
		builder.setGatewayId(Constants.SYSTEM_ID_INT);
		builder.setGatewayType(0);
		if(loginout.getLoginTime() > 0){
			builder.setLoginTime(loginout.getLoginTime());
		}
		
		if(loginout.getLogoutTime() > 0){
			builder.setLogoutTime(loginout.getLogoutTime());
		}
		
		if(loginout.getUnitVersion() != null){
			builder.setUnitVersion(loginout.getUnitVersion());
		}
		
		builder.setUpdateGateway(Constants.IS_UPDATE_SERVER);
		
		byte[] data = builder.build().toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_LOGINOUT);
	}
	
	/**
	 * 上报电控单元配置
	 */
	public void addECUConfig(ECUConfig ecuConfig){
		if(!enabled){
			return;
		}
		
		cc.chinagps.gateway.buff.DeliverBuff.DeliverECUConfig.Builder builder = cc.chinagps.gateway.buff.DeliverBuff.DeliverECUConfig.newBuilder();
		builder.setEcuConfig(ecuConfig);
		builder.setGatewayid(Constants.SYSTEM_ID_INT);
		builder.setGatewaytype(0);
		
		byte[] data = builder.build().toByteArray();
		addMQItem(data, MQManager.QUEUE_NAME_ECU_CONFIG);
	}
	
	/**
	 * 封装为MQItem
	 */
	private void addMQItem(byte[] data, String queueName){
		MQItem item = new MQItem();
		item.setQueueName(queueName);
		item.setData(data);
		addData(item);
	}
	
	/**
	 * 添加MQItem
	 */
	private long throwCount = 0;
	
	public long getThrowCount() {
		return throwCount;
	}

	private void addData(MQItem item){
		if(dataList.size() > MQManager.MAX_CACHE_SIZE_WRITE){
			throwCount++;
			return;
		}
		
		synchronized (dataLock) {
			dataList.add(item);
			dataLock.notifyAll();
		}
	}
	
	private void addQueue(String queueName, int mode) throws JMSException{
		Queue queue = session.createQueue(queueName);
		addDestination(queue, queueName, mode);
	}
	
	private void addTopic(String topicName, int mode) throws JMSException{
		Topic topic = session.createTopic(topicName);
		addDestination(topic, topicName, mode);
	}
	
	private void addDestination(Destination destination, String queueName, int mode) throws JMSException{
		MessageProducer producer = session.createProducer(destination);
		if(mode == 1){
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}else{
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		}
		
		queueMap.put(queueName, destination);
		producerMap.put(queueName, producer);
	}
	
	private SendWorker worker;
	
	public SendWorker getWorker() {
		return worker;
	}

	public void startWorker(){
		worker = new SendWorker();
		worker.start();
	}
	
	private boolean runflag = true;
	
	public class SendWorker extends Thread{
		private List<MQItem> temp;
		private int writeIndex;
		private int position;
		
		private long loop;

		public long getLoop() {
			return loop;
		}

		public List<MQItem> getTemp() {
			return temp;
		}

		public int getWriteIndex() {
			return writeIndex;
		}
		
		public int getPosition() {
			return position;
		}

		@Override
		public void run() {
			while(runflag){
				try{
					//获取数据
					position = 1;
					synchronized (dataLock) {
						while(dataList.size() == 0){
							try {
								position = 2;
								dataLock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						loop ++;
						temp = dataList;
						dataList = new ArrayList<MQItem>();
						
						dataLock.notifyAll();
					}
					
					//发送数据
					position = 3;
					int count = 0;
					for(writeIndex = 0; writeIndex < temp.size(); writeIndex++){
						MQItem item = temp.get(writeIndex);
						MessageProducer producer = producerMap.get(item.getQueueName());
						if(producer == null){
							continue;
						}
						
						Message msg = createMessage(item, session);
						if(msg == null){
							continue;
						}
						
						producer.send(msg);
						count ++;
						if(count >= MQManager.BATCH_SIZE_WRITE){
							session.commit();
							count = 0;
						}
					}
					temp.clear();
					position = 4;
					
					if(count > 0){
						session.commit();
					}
					position = 5;
				}catch (Throwable e) {
					//e.printStackTrace();
					exceptionCaught(e);
				}
			}
		}
	}
	
	private Logger logger_others = Logger.getLogger(LogManager.LOGGER_NAME_OTHERS);
	
	private void exceptionCaught(Throwable e){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(bos));
		logger_others.info(bos.toString());
	}
	
	private Message createMessage(MQItem item, Session session) throws JMSException{
		Object obj_data = item.getData();
		//String queueName = item.getQueueName();
		byte[] data = (byte[]) obj_data;
		BytesMessage msg = session.createBytesMessage();
		msg.writeBytes(data);
		return msg;
	}

	@Override
	public int getDataCount() {
		return dataList.size();
	}

	@Override
	public int getPosition() {
		if(worker == null){
			return -1;
		}
		
		return worker.getPosition();
	}

	@Override
	public long getLoop() {
		if(worker == null){
			return -1;
		}
		
		return worker.getLoop();
	}
}