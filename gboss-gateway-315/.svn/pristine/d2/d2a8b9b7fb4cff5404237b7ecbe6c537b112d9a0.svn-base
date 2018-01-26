package cc.chinagps.gateway.test.hashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Test {
	public static void main(String[] args) {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("abc", "123456");
		ClearThread clearThread = new ClearThread(map);
		ReadThread readThread = new ReadThread(map);
		
		clearThread.start();
		readThread.start();
	}
}