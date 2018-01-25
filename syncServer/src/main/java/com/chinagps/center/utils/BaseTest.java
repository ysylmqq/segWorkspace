package com.chinagps.center.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Package:com.chinagps.center.utils
 * @ClassName:BaseTest
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-21 下午4:21:28
 */
public class BaseTest {
	protected static ObjectMapper mapper = new ObjectMapper();
	
	public static void testAPI(String path, HashMap<String, String> params) throws Exception {
		URL url = new URL("http://localhost:8080/xinge" + path);
		//URL url = new URL("http://m.chinagps.cc:1818/xinge" + path);
//		URL url = new URL("http://90.0.29.196:7778/xinge" + path);
		//URL url = new URL("http://192.168.3.108:8080/driver-book-pro" + path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("POST");// 提交模式
		conn.setRequestProperty("Content-Type", "text/plain");
		conn.setConnectTimeout(10000);// 连接超时 单位毫秒
		conn.setReadTimeout(10000);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.connect();
		
		byte[] bytes = CipherTool.getCipherString(mapper.writeValueAsString(params)).getBytes("UTF-8");
//      byte[] bytes = mapper.writeValueAsString(params).getBytes("UTF-8");
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
		System.out.println("服务端返回的内容：" + returnValue);
		System.out.println("服务端返回的内容：" + CipherTool.getOriginString(returnValue));
		
		conn.disconnect();
	}
	
	/**
	 * 参数为json格式测试
	 * @param path
	 * @param params
	 * @throws Exception
	 */
	public static void jtestAPI(String path, HashMap<String, Object> params) throws Exception {
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
//			HttpPost method = new HttpPost("http://90.0.29.196:7778/xinge" + path);
			HttpPost method = new HttpPost("http://localhost:8080/xinge" + path);
//			HttpPost method = new HttpPost("http://90.0.29.196:7778/xinge" + path);
			JSONObject json = new JSONObject();
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(params);
			StringEntity entity = new StringEntity(jsonStr, "utf-8");//解决中文乱码问题    
            entity.setContentEncoding("UTF-8");    
            entity.setContentType("application/json");    
            method.setEntity(entity);
            
            HttpResponse result = httpClient.execute(method);  
            
            // 请求结束，返回结果  
            String resData = EntityUtils.toString(result.getEntity());  
            JSONObject resJson = json.fromObject(resData);
            System.out.println("json请求服务端返回的内容：" + resJson);
		}catch(Exception e){
			
		}
	}
}