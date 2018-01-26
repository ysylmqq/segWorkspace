package cc.chinagps.gateway.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

public class MemcacheTest {
	public static void main(String[] args) {
		String[] servers = {"127.0.0.1:11211"};
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		pool.setFailover(true);
		pool.setInitConn(1);
		pool.setMinConn(1);
		pool.setMaxConn(5);
		pool.setMaintSleep(30);
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setAliveCheck(true);
		pool.initialize();

		MemcachedClient mcc = new MemcachedClient();
		//Object v = mcc.get("abc");
		//System.out.println("v:" + v);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis() + 300000);
		System.out.println("time:" + sdf.format(date));
		//boolean result = mcc.set("abc", "12345");
		boolean result = mcc.set("abc", "abc123", date);
		System.out.println("result:" + result);
		
		while(true){
			Date date1 = new Date(System.currentTimeMillis());
			Object obj = mcc.get("abc");
			System.out.println("abc:" + obj + "[" + sdf.format(date1) + "]");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
 }