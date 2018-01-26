package cc.chinagps.gateway.unit.udp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.seg.lib.util.SegPackageUtil;

import cc.chinagps.gateway.buff.InnerDataBuff;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.memcache.MemcacheManager;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.Loginout;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.common.UploadUtil;
import cc.chinagps.gateway.util.Constants;

public class UdpSessionManager {
	private UdpServer udpServer;
	
	public UdpSessionManager(UdpServer udpServer, long runInterval){
		this.udpServer = udpServer;
		//udp去掉清除链接功能
		startCheckAndClearThread(runInterval);
	}
	
	private Map<Channel, UnitSocketSession> socketSessionMap = new ConcurrentHashMap<Channel, UnitSocketSession>();
	
	//呼号-session(冗余map,发指令使用)
	private Map<String, UnitSocketSession> callSocketSessionMap = new ConcurrentHashMap<String, UnitSocketSession>();
	
	public void addCallSession(String callLetter, UnitSocketSession session){
		callSocketSessionMap.put(callLetter, session);
	}
	
	public UnitSocketSession removeCallSession(String callLetter){
		return callSocketSessionMap.remove(callLetter);
	}
	
	public UnitSocketSession getSessionByCallLetter(String callLetter){
		return callSocketSessionMap.get(callLetter);
	}
	
	public Map<String, UnitSocketSession> getCallSocketSessionMap() {
		return callSocketSessionMap;
	}
	
	
	
	public void addSession(Channel ch, UnitSocketSession session){
		//synchronized (socketSessionMap) {
		//	socketSessionMap.put(ch, session);
		//}
		socketSessionMap.put(ch, session);
		
	}
	
	private void beforeRemoveSession(UnitSocketSession session){	
		if(session != null){
			session.clearResource();
		}
		
		if(session != null && "useat".equals(session.getProtocolType())){
			udpServer.removeTrace(session);
		}
		
		if(session != null && session.getUnitInfo() != null){
			String callLetter = session.getUnitInfo().getCallLetter();
			callSocketSessionMap.remove(callLetter);
		}
	}
	
	private void beforeRemoveSession(UnitSocketSession session,String callLetter){	
		if(session != null){
			session.clearResource();
		}
		if(session != null && "useat".equals(session.getProtocolType())){
			udpServer.removeTrace(session);
		}
		callSocketSessionMap.remove(callLetter);
	}
	
	protected void removeSession(Channel ch){
		/*try {
			UnitSocketSession session = socketSessionMap.get(ch);
			beforeRemoveSession(session);
			
			socketSessionMap.remove(ch);
			ch.close();
			
			afterRemoveSession(session);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger_debug.debug(e.getMessage());
		}*/
		
	}
	
	protected void removeSession(String callLetter){
		try {
			UnitSocketSession session = callSocketSessionMap.get(callLetter);
			beforeRemoveSession(session,callLetter);
			
			afterRemoveSession(session);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger_debug.debug(e.getMessage());
		}
		
	}
	
	private void afterRemoveSession(UnitSocketSession session){
		notifyLoginout(session);
	}
	
	public UnitSocketSession getSession(Channel sc){
		return socketSessionMap.get(sc);
	}
	
	/**
	 * 检查关闭长时间未活动的session
	 */
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	private void checkAndClearSession(){
		long now = System.currentTimeMillis();
		Iterator<Entry<String, UnitSocketSession>> it = callSocketSessionMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, UnitSocketSession> entry = it.next();
			UnitSocketSession session = entry.getValue();
			String callLetter = entry.getKey();
			if(now - session.getLastActiveTime() > session.getMaxKeepAliveTime()){
				removeSession(callLetter);
				
				if(logger_debug.isDebugEnabled()){
					StringBuilder logInfo = new StringBuilder();
					logInfo.append("clear session, session info:").append(session);
					logger_debug.debug(logInfo.toString());
				}
			}
		}
	}
	
	private void notifyLoginout(UnitSocketSession session){
		try {
			if(session != null && session.getUnitInfo() != null){
				String callLetter = session.getUnitInfo().getCallLetter();
				//notify memcache
				//升级服务器不更新routing
				if(!Constants.IS_UPDATE_SERVER){
					MemcacheManager.getMemcachedClient().delete(MemcacheManager.ROUTING_KEY_HEAD + callLetter);
				}
				
				//notify seat
				byte[] bodyData = InnerDataBuff.Unit.newBuilder().setCallLetter(callLetter).build().toByteArray();
				udpServer.getSeatServer().getTCPServer().broadcastPackage(false, false, false, 
						Constants.INNER_CMD_ID_VEHICLE_OFFLINE_NOTIFY, SegPackageUtil.getSerialNumber(), 
						bodyData, UnitCommon.unitLoginChangeFilter);
				
				//notify trace
				//udp去掉
				UnitCommon.sendOffLineEventTraceFromUdpServer(udpServer, session);
				
				//save data
				//System.out.println("notifyLoginout[" + Thread.currentThread().getId() + "](" + session.getRemoteAddress() + ")" + System.currentTimeMillis());			
				Loginout loginout = new Loginout();
				loginout.setIsLogin(Loginout.LOGOUT);
				//loginout.setLoginTime(session.getCreateTime());
				loginout.setLoginTime(session.getLoginTime());
				
				loginout.setLogoutTime(System.currentTimeMillis());
				loginout.setCallLetter(callLetter);
				//udp去掉
				UploadUtil.handleLoginout(udpServer, session, loginout);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger_debug.debug(e.getMessage());
		}
		
	}
	
	private boolean checkFlag = true;
	
	public boolean isCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(boolean checkFlag) {
		this.checkFlag = checkFlag;
	}

	private class CheckAndClearThread extends Thread{
		private long runInterval = 60000L;
		
		public CheckAndClearThread(long runInterval){
			this.runInterval = runInterval;
		}
		
		@Override
		public void run() {
			while(true){
				try{
					sleep(runInterval);
					
					if(checkFlag){
						checkAndClearSession();
					}
				}catch (Throwable e) {
					udpServer.exceptionCaught(e);
				}
			}
		}
	}
	
	private void startCheckAndClearThread(long runInterval){
		CheckAndClearThread thread = new CheckAndClearThread(runInterval);
		thread.start();
	}

	public Map<Channel, UnitSocketSession> getSocketSessionMap() {
		return socketSessionMap;
	}
}