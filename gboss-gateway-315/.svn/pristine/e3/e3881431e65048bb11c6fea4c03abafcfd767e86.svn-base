package cc.chinagps.gateway.memcache;

import java.util.Date;
import java.util.Iterator;

import com.meetup.memcached.MemcachedClient;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.SystemConfig;

public class MemcacheUpdator {
	private UnitServer unitServer;
	private String serverId;
	private MemcachedClient client;
	private long routingRunInterval;
	private long routingUpdateInterval;
	
	//status
	private String statusKeyHead;
	private long statusRunInterval;
	private long statusExpired;
	
	public MemcacheUpdator(){
		serverId = Constants.SYSTEM_ID;
		client = MemcacheManager.getMemcachedClient();
		
		routingRunInterval = Long.valueOf(SystemConfig.getMemcacheProperty("routing_run_interval"));
		routingUpdateInterval = Long.valueOf(SystemConfig.getMemcacheProperty("routing_update_interval"));
		
		statusKeyHead = SystemConfig.getMemcacheProperty("gateway_status_key_head");
		statusRunInterval = Long.valueOf(SystemConfig.getMemcacheProperty("gateway_status_run_interval"));
		statusExpired = Long.valueOf(SystemConfig.getMemcacheProperty("gateway_status_expired"));
	}
	
	public void startWork(){
		doUpdateGatewayStatus();
		
		//升级服务器不更新routing
		if(!Constants.IS_UPDATE_SERVER){
			UpdateRoutingThread updateRoutingThread = new UpdateRoutingThread();
			updateRoutingThread.start();
		}
		
		UpdateGatewayStatusThread updateGatewayStatusThread = new UpdateGatewayStatusThread();
		updateGatewayStatusThread.start();
	}
	
	public UnitServer getUnitServer() {
		return unitServer;
	}

	public void setUnitServer(UnitServer unitServer) {
		this.unitServer = unitServer;
	}
	
	private boolean runFlag = true;
	
	private class UpdateRoutingThread extends Thread{
		@Override
		public void run() {
			while(runFlag){
				try {
					sleep(routingRunInterval);
					updateRouting();
				} catch (Throwable e) {
					unitServer.exceptionCaught(e, "[M]");
				}
			}
		}
	}
	
	private void updateRouting(){
		long now = System.currentTimeMillis();
		Date expDate = new Date(now + MemcacheManager.ROUTING_EXPIRED);
		
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			if(session.getUnitInfo() != null){
				//client.set(MemcacheManager.ROUTING_KEY_HEAD + session.getUnitInfo().getCallLetter(), 
				//		serverId, expDate);
				if(now - session.getUnitInfo().getLastUpdateRouteTime() > routingUpdateInterval){
					boolean success = client.set(MemcacheManager.ROUTING_KEY_HEAD + session.getUnitInfo().getCallLetter(), 
							serverId, expDate);
					if(success){
						session.getUnitInfo().setLastUpdateRouteTime(now);
					}
				}
			}
		}
	}
	
	/**
	 * 刷新全部路由(memcached重启)
	 * */
	private void reloadRoutingAll(){
		long now = System.currentTimeMillis();
		Date expDate = new Date(now + MemcacheManager.ROUTING_EXPIRED);
		
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			if(session.getUnitInfo() != null){
				boolean success = client.set(MemcacheManager.ROUTING_KEY_HEAD + session.getUnitInfo().getCallLetter(), 
						serverId, expDate);
				if(success){
					session.getUnitInfo().setLastUpdateRouteTime(now);
				}else{
					session.getUnitInfo().setLastUpdateRouteTime(0);
				}
			}
		}
	}
	
	private class UpdateGatewayStatusThread extends Thread{
		@Override
		public void run() {
			while(runFlag){
				try {
					sleep(statusRunInterval);
					updateGatewayStatus();
				} catch (Throwable e) {
					unitServer.exceptionCaught(e, "[M]");
				}
			}
		}
	}
	
	private void updateGatewayStatus(){
		//Long now = System.currentTimeMillis();
		//Date expDate = new Date(now + statusExpired);
		//client.set(statusKeyHead + serverId, now.toString(), expDate);
		//判断是否已连接
		if(client.stats().size() == 0){
			//未连接
			return;
		}
		
		String key = statusKeyHead + serverId;
		//取最后更新时间
		//如果最后更新时间为空，判断为memcached重启(或第一次运行更新任务)
		Object obj = client.get(key);
		if(obj == null){
			//升级服务器不更新routing
			if(!Constants.IS_UPDATE_SERVER){
				reloadRoutingAll();
			}
		}
		
		//更新状态
		Long now = System.currentTimeMillis();
		Date expDate = new Date(now + statusExpired);
		client.set(key, now.toString(), expDate);
	}
	
	private void doUpdateGatewayStatus(){
		String key = statusKeyHead + serverId;
		Long now = System.currentTimeMillis();
		Date expDate = new Date(now + statusExpired);
		client.set(key, now.toString(), expDate);
	}
}