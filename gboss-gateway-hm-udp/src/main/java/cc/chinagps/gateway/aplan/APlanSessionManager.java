package cc.chinagps.gateway.aplan;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.Channel;

import cc.chinagps.gateway.aplan.pkg.APlanHead;
import cc.chinagps.gateway.aplan.pkg.APlanPackage;
import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.aplan.pkg.RouteTable;
import cc.chinagps.gateway.util.Constants;

public class APlanSessionManager {
	private APlanServer server;
	
	protected APlanSessionManager(APlanServer server, long checkInterval, long sendHearBeatInterval){
		this.server = server;
		startThreads(checkInterval, sendHearBeatInterval);
	}
	
	private Map<Channel, APlanSocketSession> socketSessionMap = new ConcurrentHashMap<Channel, APlanSocketSession>();
	
	protected void addSession(Channel ch, APlanSocketSession session){
		//synchronized (socketSessionMap) {
		//	socketSessionMap.put(ch, session);
		//}
		socketSessionMap.put(ch, session);
	}
	
	protected void removeSession(Channel ch){
		//synchronized (socketSessionMap) {
		//	ch.close();
		//	socketSessionMap.remove(ch);
		//}
		ch.close();
		socketSessionMap.remove(ch);
	}
	
	public APlanSocketSession getSession(Channel sc){
		return socketSessionMap.get(sc);
	}
	
	/**
	 * 检查关闭长时间未活动的session
	 */
	private void checkAndClearSession(){
//		synchronized (socketSessionMap) {
//			long now = System.currentTimeMillis();
//			Iterator<Entry<Channel, APlanSocketSession>> it = socketSessionMap.entrySet().iterator();
//			while(it.hasNext()){
//				Entry<Channel, APlanSocketSession> entry = it.next();
//				APlanSocketSession session = entry.getValue();
//				Channel channel = entry.getKey();
//				if(now - session.getLastActiveTime() > session.getMaxKeepAliveTime()){
//					channel.close();
//					it.remove();
//				}
//			}
//		}
		
		long now = System.currentTimeMillis();
		Iterator<Entry<Channel, APlanSocketSession>> it = socketSessionMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<Channel, APlanSocketSession> entry = it.next();
			APlanSocketSession session = entry.getValue();
			Channel channel = entry.getKey();
			if(now - session.getLastActiveTime() > session.getMaxKeepAliveTime()){
				channel.close();
				it.remove();
			}
		}
	}
	
	private class CheckAndClearThread extends Thread{
		private boolean runFlag = true;
		
		private long runInterval = 60000L;
		
		public CheckAndClearThread(long runInterval){
			this.runInterval = runInterval;
		}
		
		@Override
		public void run() {
			while(runFlag){
				try{
					sleep(runInterval);
					checkAndClearSession();
				}catch (Throwable e) {
					server.exceptionCaught(e);
				}
			}
		}
	}
	
	private void startThreads(long checkInterval, long sendHearBeatInterval){
		CheckAndClearThread thread1 = new CheckAndClearThread(checkInterval);
		thread1.start();
		
		SendHearBeatThread thread2 = new SendHearBeatThread(sendHearBeatInterval);
		thread2.start();
	}
	
	private class SendHearBeatThread extends Thread{
		private long sendInterval = 10000;
		
		public SendHearBeatThread(long sendInterval){
			this.sendInterval = sendInterval;
			
			head = new APlanHead();
			head.setCommandId(APlanHead.CMD_ID_LINK_TEST);
			
			rt = new RouteTable();
			rt.getNodeList().add(server.getServerNode());
		}
		
		private boolean runFlag = true;
		
		private APlanHead head;
		
		private RouteTable rt;
		
		@Override
		public void run() {
			while(runFlag){
				try{
//					synchronized (socketSessionMap) {
//						if(socketSessionMap.size() > 0){
//							head.setSequenceNo(APlanUtil.getSequenceNo());
//							byte[] data = APlanPackage.encode(head, rt, bodyData);
//							
//							Iterator<APlanSocketSession> it = socketSessionMap.values().iterator();
//							while(it.hasNext()){
//								it.next().sendData(data);
//							}
//						}
//					}
					if(socketSessionMap.size() > 0){
						head.setSequenceNo(APlanUtil.getSequenceNo());
						byte[] data = APlanPackage.encode(head, rt, Constants.ZERO_BYTES_DATA);
						
						Iterator<APlanSocketSession> it = socketSessionMap.values().iterator();
						while(it.hasNext()){
							it.next().sendData(data);
						}
					}
					
					sleep(sendInterval);
				}catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Map<Channel, APlanSocketSession> getSocketSessionMap() {
		return socketSessionMap;
	}
	
	//群发消息
	public void broadcastData(byte[] data){
//		synchronized (socketSessionMap) {
//			if(socketSessionMap.size() == 0){
//				return;
//			}
//			
//			Iterator<APlanSocketSession> it = socketSessionMap.values().iterator();
//			while(it.hasNext()){
//				APlanSocketSession session = it.next();
//				session.sendData(data);
//			}
//		}
		Iterator<APlanSocketSession> it = socketSessionMap.values().iterator();
		while(it.hasNext()){
			APlanSocketSession session = it.next();
			session.sendData(data);
		}
	}
}