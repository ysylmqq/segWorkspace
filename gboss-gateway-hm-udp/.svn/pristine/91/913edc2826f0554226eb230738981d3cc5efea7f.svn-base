package cc.chinagps.gateway.mq;

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
import org.seg.lib.net.server.tcp.SocketSession;

import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.MultCommand;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.mq.export.ExportMQInf;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.udp.UdpServer;
import cc.chinagps.gateway.util.Constants;

import com.google.protobuf.InvalidProtocolBufferException;

public class CmdReader {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	private ExportMQInf exportMQ;
	
	private UnitServer unitServer;
	
	private UdpServer udpServer;
	
	public CmdReader(ExportMQInf exportMQ, UnitServer unitServer,UdpServer udpServer){
		this.exportMQ = exportMQ;
		this.unitServer = unitServer;
		this.udpServer = udpServer;
	}
	
	//mq
	private Session session_cmd;
	private Destination queue_cmd;
	private MessageConsumer consumer_cmd;
		
	public void init() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]init cmd reader start");
		
		Connection connection = MQManager.openConnection();
		connection.start();
		
		session_cmd = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		if(MQManager.QUEUE_TYPE_CMD == 1){
			queue_cmd = session_cmd.createQueue(MQManager.QUEUE_NAME_CMD);
		}else{
			queue_cmd = session_cmd.createTopic(MQManager.QUEUE_NAME_CMD);
		}
		
		consumer_cmd = session_cmd.createConsumer(queue_cmd);
		System.out.println("[" + sdf.format(new Date()) + "]init cmd reader end");
	}
	
	private int position;
	
	private long loop;
	
	private long lastReadTime;
	
	public long getLoop() {
		return loop;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	private int batch_count = 0;
	
	/**
	 * 定时提交线程
	 */
	private class CommitThread extends Thread{
		private long runInterval;
		
		public CommitThread(long runInterval){
			this.runInterval = runInterval;
		}
		
		private boolean runFlag = true;
		
		private long lastCommitTime;
		
		@Override
		public void run() {
			while(runFlag){
				try {
					sleep(runInterval);
					
					long now = System.currentTimeMillis();
					long temp = lastReadTime;
					if(temp == lastCommitTime ||  (now - lastReadTime) < runInterval){
						//没有新加数据或正快速读取数据时不提交
						continue;
					}
					
					synchronized (session_cmd) {
						lastCommitTime = temp;
						commitSession();
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int commitSession() throws JMSException{
		if(batch_count > 0){
			int temp = batch_count;
			session_cmd.commit();
			batch_count = 0;
			return temp;
		}
		
		return 0;
	}
	
	public void startWorker() throws JMSException{
		CommitThread commitThread = new CommitThread(MQManager.READ_COMMIT_INTERVAL);
		commitThread.start();
		
		consumer_cmd.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				try{
					Object exportMQDataLock = exportMQ.getDataLock();
					position = 1;
					loop ++;
					lastReadTime = System.currentTimeMillis();
					synchronized (exportMQDataLock) {
						//发送缓存过大时不从MQ读取指令，防止缓存继续扩大
						while(exportMQ.getDataCount() > MQManager.MAX_CACHE_SIZE_WRITE){
							try {
								position = 2;
								exportMQDataLock.wait();
								position = 3;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}	
					
					position = 4;
					if(BytesMessage.class.isInstance(msg)){
						BytesMessage byteMsg = (BytesMessage) msg;
						int len = (int) byteMsg.getBodyLength();
						byte[] data = new byte[len];						
						byteMsg.readBytes(data);						
						try{
							//CommandBuff.Command cmd = CommandBuff.Command.parseFrom(data);						
							//unitServer.sendCommand(cmd, null);
							
							MultCommand mcmd = MultCommand.parseFrom(data);
							List<Command> list = mcmd.getMcommandList();
							logger_debug.debug("CmdReader command list size:"+list.size());
							for(int i = 0; i < list.size(); i++){
								Command cmd = list.get(i);
								logger_debug.debug("CmdReader command cmdid:"+cmd.getCmdId());
								for (int j = 0; j < cmd.getCallLetterCount(); j++) {
									logger_debug.debug("CmdReader command callLetter:"+cmd.getCallLetter(j));
								}
								for (int k = 0; k < cmd.getParamsCount(); k++) {
									logger_debug.debug("CmdReader command param:"+cmd.getParams(k));
								}
								//unitServer.sendCommand(cmd, null);
								sendCommand(cmd,unitServer,udpServer);
							}
						}catch (InvalidProtocolBufferException e) {
							if(logger_debug.isDebugEnabled()){
								logger_debug.debug("(mq)cmd data error:" + e);
							}
						}
					}else{
						if(logger_debug.isDebugEnabled()){
							logger_debug.debug("(mq)cmd type error:" + msg.getClass());
						}
					}
					
					position = 5;
					synchronized (session_cmd) {
						batch_count ++;
						if (batch_count >= MQManager.BATCH_SIZE_READ) {
							commitSession();
						}
					}
					
					position = 6;
				}catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void sendCommand(Command command, UnitServer unitServer, UdpServer udpServer){
		List<String> list = command.getCallLetterList();
		for (String callLetter : list) {
			UnitSocketSession unitSocketSession = unitServer.getSessionManager().getSessionByCallLetter(callLetter);
			//在unitServer中没找到
			if(unitSocketSession == null){
				logger_debug.info("cannot find unit from unitServer:" + callLetter);
				//然后在udpServer中查找
				unitSocketSession = udpServer.getSessionManager().getSessionByCallLetter(callLetter);
				if(unitSocketSession == null){
					//没找到
					//未找到车辆
					logger_debug.info("cannot find unit from udpServer:" + callLetter);
					SocketSession userSession = null;
					CmdResponseUtil.simpleCommandResponse(userSession, unitServer, callLetter, command, Constants.RESULT_UNIT_OFFLINE, null);
				}else{
					logger_debug.info("has finded unit from udpServer:" + callLetter);
					
					udpServer.sendCommandByCallLetter(callLetter, command, unitSocketSession, null);
				}
			}else{
				logger_debug.info("has finded unit from unitServer:" + callLetter);
				
				unitServer.sendCommandByCallLetter(callLetter, command, unitSocketSession, null);
			}
		}
		
	}
}