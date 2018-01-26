package cc.chinagps.gateway.test.hashmap;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ClearThread extends Thread{
	private Map<String, String> map;
	
	public ClearThread(Map<String, String> map){
		this.map = map;
	}
	
	@Override
	public void run() {
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			if("abc".equals(entry.getKey())){
				System.out.println("clearThread clear abc");
				it.remove();
			}
		}
	}
}
