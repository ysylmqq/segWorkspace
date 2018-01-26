package cc.chinagps.gateway.unit;

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
import cc.chinagps.gateway.unit.beans.Loginout;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.common.UploadUtil;
import cc.chinagps.gateway.util.Constants;

public class UnitSessionManager {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG); 
	private UnitServer server;
	
	protected UnitSessionManager(UnitServer server, long runInterval){
		this.server = server;
		startCheckAndClearThread(runInterval);
	}
	
	private Map<Channel, UnitSocketSession> socketSessionMap = new ConcurrentHashMap<Channel, UnitSocketSession>();
	
	//呼号-session(冗余map,发指令使用)
	private Map<String, UnitSocketSession> callSocketSessionMap = new ConcurrentHashMap<String, UnitSocketSession>();
	
	public void addCallSession(String callLetter, UnitSocketSession session){
		callSocketSessionMap.put(callLetter, session);
		//logger_debug.debug("addCallSession callLetter:"+callLetter+";session:"+session);
		//logger_debug.debug("[addCallSession]socketSessionMap size:" + socketSessionMap.size());
		//logger_debug.debug("[addCallSession]callSocketSessionMap size:" + callSocketSessionMap.size());
	}
	
	public UnitSocketSession removeCallSession(String callLetter){
		UnitSocketSession unitSocketSession = callSocketSessionMap.remove(callLetter);
		logger_debug.debug("removeCallSession callLetter:"+callLetter+";session:"+unitSocketSession);
		return unitSocketSession;
	}
	
	public UnitSocketSession getSessionByCallLetter(String callLetter){
		return callSocketSessionMap.get(callLetter);
	}
	
	public Map<String, UnitSocketSession> getCallSocketSessionMap() {
		return callSocketSessionMap;
	}
	
	
	
	protected void addSession(Channel ch, UnitSocketSession session){
		//synchronized (socketSessionMap) {
		//	socketSessionMap.put(ch, session);
		//}
		socketSessionMap.put(ch, session);
		//logger_debug.debug("[addSession]socketSessionMap size:" + socketSessionMap.size());
		//logger_debug.debug("[addSession]callSocketSessionMap size:" + callSocketSessionMap.size());
	}
	
	private void beforeRemoveSession(UnitSocketSession session){	
		if(session != null){
			session.clearResource();
		}
		
		if(session != null && "useat".equals(session.getProtocolType())){
			server.removeTrace(session);
		}
		
		if(session != null && session.getUnitInfo() != null){
			String callLetter = session.getUnitInfo().getCallLetter();
			callSocketSessionMap.remove(callLetter);
		}
	}
	
	protected void removeSession(Channel ch){
		UnitSocketSession session = socketSessionMap.get(ch);
		beforeRemoveSession(session);
		
		socketSessionMap.remove(ch);
		ch.close();
		
		afterRemoveSession(session);
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
	private void checkAndClearSession(){
		long now = System.currentTimeMillis();
		Iterator<Entry<Channel, UnitSocketSession>> it = socketSessionMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<Channel, UnitSocketSession> entry = it.next();
			UnitSocketSession session = entry.getValue();
			Channel channel = entry.getKey();
			if(now - session.getLastActiveTime() > session.getMaxKeepAliveTime()){
				removeSession(channel);
				
				if(logger_debug.isDebugEnabled()){
					StringBuilder logInfo = new StringBuilder();
					logInfo.append("clear session, session info:").append(session);
					logger_debug.debug(logInfo.toString());
				}
			}
		}
	}
	
	private void notifyLoginout(UnitSocketSession session){
		if(session != null && session.getUnitInfo() != null){
			String callLetter = session.getUnitInfo().getCallLetter();
			//notify memcache
			//升级服务器不更新routing
			if(!Constants.IS_UPDATE_SERVER){
				MemcacheManager.getMemcachedClient().delete(MemcacheManager.ROUTING_KEY_HEAD + callLetter);
			}
			
			//notify seat
			byte[] bodyData = InnerDataBuff.Unit.newBuilder().setCallLetter(callLetter).build().toByteArray();
			server.getSeatServer().getTCPServer().broadcastPackage(false, false, false, 
					Constants.INNER_CMD_ID_VEHICLE_OFFLINE_NOTIFY, SegPackageUtil.getSerialNumber(), 
					bodyData, UnitCommon.unitLoginChangeFilter);
			
			//notify trace
			UnitCommon.sendOffLineEventTrace(server, session);
			
			//save data
			//System.out.println("notifyLoginout[" + Thread.currentThread().getId() + "](" + session.getRemoteAddress() + ")" + System.currentTimeMillis());			
			Loginout loginout = new Loginout();
			loginout.setIsLogin(Loginout.LOGOUT);
			//loginout.setLoginTime(session.getCreateTime());
			loginout.setLoginTime(session.getLoginTime());
			
			loginout.setLogoutTime(System.currentTimeMillis());
			loginout.setCallLetter(callLetter);
			
			UploadUtil.handleLoginout(server, session, loginout);
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
					server.exceptionCaught(e);
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