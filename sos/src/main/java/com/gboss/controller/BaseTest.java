package com.gboss.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;

public class BaseTest {
	protected static ObjectMapper mapper = new ObjectMapper();
	
	
	    public static String simpleMapToJsonStr(Map<String ,String > map){ 
	        if(map==null||map.isEmpty()){ 
	            return "null"; 
	        } 
	        String jsonStr = "{"; 
	        Set<?> keySet = map.keySet(); 
	        for (Object key : keySet) { 
	            jsonStr += "\""+key+"\":\""+map.get(key)+"\",";      
	        } 
	        jsonStr = jsonStr.substring(0,jsonStr.length()-2); 
	        jsonStr += "}"; 
	        return jsonStr; 
	    } 
	     
	    //{"pass":"4355","name":"12342","wang":"fsf"}
	    public static Map  getData(String str){
	        String sb = str.substring(0, str.length()-1);
	         String[] name =  sb.split("\\\",\\\"");
	         String[] nn =null;
	         Map map = new HashMap();
	         for(int i= 0;i<name.length; i++){
	             nn = name[i].split("\\\":\\\"");
	             map.put(nn[0], nn[1]);
	         }
	        return map;
	    }
	    
	    public static Map<String, Object> parseJSON2Map(String jsonStr){
	        Map<String, Object> map = new HashMap<String, Object>();
	        //最外层解析
	        JSONObject json = JSONObject.fromObject(jsonStr);
	        for(Object k : json.keySet()){
	            Object v = json.get(k); 
	            //如果内层还是数组的话，继续解析
	            if(v instanceof JSONArray){
	                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	                Iterator<JSONObject> it = ((JSONArray)v).iterator();
	                while(it.hasNext()){
	                    JSONObject json2 = it.next();
	                    list.add(parseJSON2Map(json2.toString()));
	                }
	                map.put(k.toString(), list);
	            } else {
	                map.put(k.toString(), v);
	            }
	        }
	        return map;
	    }
	     
	    
		public String  getHttpResponse(String path, Map<String, String> m) throws Exception {
			URL url = new URL("http://192.168.3.151:18080/gboss-comcenter" + path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");// 提交模式
			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setConnectTimeout(10000);// 连接超时 单位毫秒
			conn.setReadTimeout(10000);// 读取超时 单位毫秒
			conn.setDoOutput(true);// 是否输入参数
			conn.setDoInput(true);
			conn.connect();
			byte[] bytes = mapper.writeValueAsString(m).getBytes("UTF-8");
			conn.getOutputStream().write(bytes);// 输入参数
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			
			InputStream in = conn.getInputStream();
			String returnValue = "";
			byte[] buffer = new byte[512];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for (int len = 0; (len = in.read(buffer)) > 0;) {
				baos.write(buffer, 0, len);
			}
			returnValue = new String(baos.toByteArray(), "UTF-8");
			baos.flush();
			baos.close();
			in.close();
			System.out.println("服务端返回的内容："+ returnValue);
			conn.disconnect();
			return returnValue;
		}
	     
	
	public static void testAPI(String path, Map<String, String> m) throws Exception {
		URL url = new URL("http://192.110.10.215:8091/obdexp" + path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("POST");// 提交模式
		conn.setRequestProperty("Content-Type", "text/plain");
		conn.setConnectTimeout(10000);// 连接超时 单位毫秒
		conn.setReadTimeout(10000);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.connect();
		
		byte[] bytes = mapper.writeValueAsString(m).getBytes("UTF-8");
		conn.getOutputStream().write(bytes);// 输入参数
		conn.getOutputStream().flush();
		conn.getOutputStream().close();
		
		InputStream in = conn.getInputStream();
		String returnValue = "";
		byte[] buffer = new byte[512];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int len = 0; (len = in.read(buffer)) > 0;) {
			baos.write(buffer, 0, len);
		}
		
	/*	
		//封装成map
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line=reader.readLine())!=null){
            sb.append(line);
        }
        Map<String, Object> map = parseJSON2Map(sb.toString());
		System.out.println(map.toString());
		*/
		
		returnValue = new String(baos.toByteArray(), "UTF-8");
		 Map<String, Object> map = parseJSON2Map(returnValue);
		System.out.println(map.toString());
		
		baos.flush();
		baos.close();
		in.close();
		System.out.println("服务端返回的内容："+ returnValue);
		
		conn.disconnect();
	}
	
	public static void main(String [] args){
		
		/*String str = "{ '_id' : { 'symptom' : '52fdbe36e97fee8601000000','price':'9999'},'data':'abcdefg'}";
		String a = "{'sitename':'K JSON','siteurl':'www.kjson.com','keyword':'JSON在线校验,格式化JSON,json 在线校验','description':'JSON解析,json 在线校验,JSON格式化工具您要是觉得这个工具不错，请推荐给您的好友'}";
		
		 Map<String, Object> map = parseJSON2Map(str);
		
		 System.out.println(map.get("_id").toString()+map.get("data").toString());*/
		 
		
		String path = "/obd/getObdErrorMsgList";
		Map<String,String> m = new HashMap<String,String>();
		 
        String call_letter="13896584526";  
        String start_date="2014-09-26 16:20:16";  
        String end_date="2014-12-26 16:20:16";  
		m.put("call_letter", call_letter);
		m.put("start_date", start_date);
		m.put("end_date", end_date);
		try {
			testAPI(path,m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
