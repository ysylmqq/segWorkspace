package com.chinagps.driverbook.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.chinagps.driverbook.pojo.Vehicle;

public class BeanToMap {
	
	 private BeanToMap() {  
	    }  
	  
	    @SuppressWarnings("unchecked")  
	    public static <K, V> Map<K, V> Bean2Map(Object javaBean) {  
	        Map<K, V> ret = new HashMap<K, V>();  
	        try {  
	            Method[] methods = javaBean.getClass().getDeclaredMethods();  
	            for (Method method : methods) {  
	                if (method.getName().startsWith("get")) {  
	                    String field = method.getName();  
	                    field = field.substring(field.indexOf("get") + 3);  
	                    field = field.toLowerCase().charAt(0) + field.substring(1);  
	                    Object value = method.invoke(javaBean, (Object[]) null);  
	                    ret.put((K) field, (V) (null == value ? null: value));  
	                }  
	            }  
	        } catch (Exception e) {  
	        }  
	        return ret;  
	    }  

	public static void main(String[] args) {
		Vehicle  v  = new Vehicle();
		Map<String,String> map = BeanToMap.Bean2Map(v);
		map.put("callLetter", "111111111111");
		map.put("vin", "333333333333");
		//如何找barcode
		String barcode ="0000000000000000000009";
		map.put("barcode", barcode);
		System.out.println(map);
	}

}
