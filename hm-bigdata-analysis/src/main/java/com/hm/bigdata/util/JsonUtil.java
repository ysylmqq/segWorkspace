package com.hm.bigdata.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @Package:com.gboss.util
 * @ClassName:JsonUtil
 * @Description:JSON格式转换工具类
 * @author:zfy
 * @date:2013-12-3 下午4:16:59
 */
public class JsonUtil {
	//java对象转换成json字符串  
	public static String oToJ(Object o){  
	        ObjectMapper om = new ObjectMapper();  
	        Writer w = new StringWriter();  
	        String json = null;  
	        try { 
	        	//禁止把POJO中值为null的字段映射到json字符串中
	        	om.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
	        	
	        	//禁止把POJO中值为null的字段映射到json字符串中
	        	//om.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
	            
	        	//禁止把Map中值为null的字段映射到json字符串中
	        	om.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
	        	
	        	om.writeValue(w, o);  
	            json = w.toString();  
	            w.close();  
	        } catch (IOException e) {  
	            // 错误处理  
	        }  
	        return json;  
	    }  
	
	/**
	 * @Title:oToJ
	 * @Description:java对象转换成json字符串  
	 * @param o
	 * @param cannotNull 如果为true，表示排除字段为null的key，value；为false都打印出来
	 * @return
	 * @throws
	 */
		public static String oToJ(Object o,boolean cannotNull){  
		        ObjectMapper om = new ObjectMapper();  
		        Writer w = new StringWriter();  
		        String json = null;  
		        try { 
		        	//排除字段为null的值
		         	if(cannotNull){
			        	//禁止把POJO中值为null的字段映射到json字符串中
			       
			        	om.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
			        	
			        	//禁止把POJO中值为null的字段映射到json字符串中
			        	//om.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
			            
			        	//禁止把Map中值为null的字段映射到json字符串中
			        	om.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);

		        	}
		        	om.writeValue(w, o);  
		            json = w.toString();  
		            w.close();  
		        } catch (IOException e) {  
		            // 错误处理  
		        }  
		        return json;  
		    }  
		
	//json字符串转换成java对象  
	public static <T> T jToO(String json, Class<T> c){  
	    T o = null;  
	    try{  
	        o = new ObjectMapper().readValue(json, c);  
	    } catch (IOException e){  
	        // 处理异常  
	    }  
	    return o;  
	}  
	/**
	 * @Title:main
	 * @Description:TODO
	 * @param args
	 * @throws
	 */
	public static void main(String[] args) {
		  Map<String, Object> map=new HashMap<String, Object>();
	        map.put("id", "1");
	        map.put("name", "\u4e2d\u6587");
	        Map<String, Object> map2=new HashMap<String, Object>();
	        map2.put("num",12);
	        List<Map> childList=new ArrayList<Map>();
	        childList.add(map2);
	        map.put("productRelations", childList);
	        String content =  new JSONObject(map).toString();//JSON.toJSONString(map);;
	        //{"name":"\u4e2d\u6587","id":"1","productRelations":[{num=12}]}
	}

}

