package cc.chinagps.gateway.memcache;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

import cc.chinagps.gateway.util.SystemConfig;

public class MemcacheManager {
	private static MemcachedClient client;
	
	public static String ROUTING_KEY_HEAD;
	
	public static String BASE_INFO_KEY_HEAD;
	
	public static String IMEI_CALLLETTER_KEY_HEAD;
	
	public static String BASE_STATION_INFO_KEY_HEAD;
	
	public static long ROUTING_EXPIRED;
	
	static{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]init memcache start");
		try {
			ROUTING_KEY_HEAD = SystemConfig.getMemcacheProperty("routing_key_head");
			ROUTING_EXPIRED = Long.valueOf(SystemConfig.getMemcacheProperty("routing_expired"));
			BASE_INFO_KEY_HEAD = SystemConfig.getMemcacheProperty("base_info_key_head");
			IMEI_CALLLETTER_KEY_HEAD = SystemConfig.getMemcacheProperty("imei_callletter_key_head");
			BASE_STATION_INFO_KEY_HEAD = SystemConfig.getMemcacheProperty("base_station_info_key_head");
			
			String[] servers = SystemConfig.getMemcacheProperty("servers").split(",");
			SockIOPool pool = SockIOPool.getInstance();
			pool.setServers(servers);
			pool.setFailover(Boolean.valueOf(SystemConfig.getMemcacheProperty("failover")));
			pool.setInitConn(Integer.valueOf(SystemConfig.getMemcacheProperty("initConn")));
			pool.setMinConn(Integer.valueOf(SystemConfig.getMemcacheProperty("minConn")));
			pool.setMaxConn(Integer.valueOf(SystemConfig.getMemcacheProperty("maxConn")));
			pool.setNagle(Boolean.valueOf(SystemConfig.getMemcacheProperty("nagle")));
			pool.setSocketTO(Integer.valueOf(SystemConfig.getMemcacheProperty("socketTO")));
			pool.setAliveCheck(Boolean.valueOf(SystemConfig.getMemcacheProperty("aliveCheck")));
			pool.initialize();
			
			client = new MemcachedClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[" + sdf.format(new Date()) + "]init memcache end");
	}
	
	public static MemcachedClient getMemcachedClient(){
		return client;
	}
	
}