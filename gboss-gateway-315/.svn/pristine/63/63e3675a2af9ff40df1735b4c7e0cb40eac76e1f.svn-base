package cc.chinagps.gateway.test.hashmap;

import java.util.Map;

public class ReadThread extends Thread{
	private Map<String, String> map;
	
	public ReadThread(Map<String, String> map){
		this.map = map;
	}
	
	@Override
	public void run() {
		try {
			sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String value = map.get("abc");
		if(value != null){
			map.remove("abc");
		}
		System.out.println("read-thread:" + value);
	}
}