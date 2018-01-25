package test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.chinagps.driverbook.util.CipherTool;
import com.chinagps.driverbook.util.RequestUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseUnitTest {
	//
	private static final String RELEASE_URL = "http://m.chinagps.cc:1818/driver-book-pro";
	private static final String HAIMA_URL = "http://hmi.952100.com:8088/driver-book-pro";
	private static final String LOCAL_URL = "http://192.168.3.157:8080/driver-book-pro";
//	private static final String ris = "http://90.0.29.196:7778/ris/";
	private static final String ris = "http://m.chinagps.cc:1818/ris";
	
	protected enum Scope {
		Gboss, Haima, Local,Ris
	}
	
	protected static ObjectMapper mapper = new ObjectMapper();
	
	public static void testAPI(Scope scope, String path, Map<String, Object> params) throws Exception {
		HttpURLConnection conn = null;
		URL url = null;
		InputStream in = null;
		ByteArrayOutputStream baos = null;
		try{
			switch (scope) {
				case Gboss:
					url = new URL(RELEASE_URL + path);
					break;
				case Haima:
					url = new URL(HAIMA_URL + path);
					break;
				case Local:
					url = new URL(LOCAL_URL + path);
					break;
				case Ris:
					url = new URL(ris + path);
					break;
				default:
					url = new URL(LOCAL_URL + path);
					break;
			}
			
			conn = (HttpURLConnection) url.openConnection();
			
			
			conn.setRequestMethod("POST");// 提交模式
			conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			conn.setConnectTimeout(10000);// 连接超时 单位毫秒
			conn.setReadTimeout(10000);// 读取超时 单位毫秒
			conn.setDoOutput(true);// 是否输入参数
			conn.setDoInput(true);
			conn.connect();
			
			System.out.println("提交的参数：" + mapper.writeValueAsString(params));
			byte[] bytes = CipherTool.getCipherString(mapper.writeValueAsString(params)).getBytes("UTF-8");
			conn.getOutputStream().write(bytes);// 输入参数
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			System.out.println("=====>"+url);
			
			in = conn.getInputStream();
			String returnValue = "";
			byte[] buffer = new byte[512];
			baos = new ByteArrayOutputStream();
			for (int len = 0; (len = in.read(buffer)) > 0;) {
				baos.write(buffer, 0, len);
			}
			returnValue = new String(baos.toByteArray(), "UTF-8");
			
			System.out.println("服务端返回的内容：" + CipherTool.getOriginString(returnValue));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(SocketTimeoutException  e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try {
				if(baos!=null)
				{
					baos.flush();
					baos.close();
				}
				if(in   != null )in.close();
				if(conn != null )conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void testGzipAPI(Scope scope, String path, Map<String, Object> params) throws Exception {
		URL url = null;
		switch (scope) {
			case Gboss:
				url = new URL(RELEASE_URL + path);
				break;
			case Haima:
				url = new URL(HAIMA_URL + path);
				break;
			case Local:
				url = new URL(LOCAL_URL + path);
				break;
			default:
				url = new URL(LOCAL_URL + path);
				break;
		}
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("POST");// 提交模式
		conn.setRequestProperty("Content-Type", "text/plain");
		conn.setConnectTimeout(10000);// 连接超时 单位毫秒
		conn.setReadTimeout(10000);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.connect();
		
		System.out.println("提交的参数：" + mapper.writeValueAsString(params));
		byte[] bytes = CipherTool.getCipherString(mapper.writeValueAsString(params)).getBytes("UTF-8");
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
		returnValue = baos.toString("UTF-8");
		baos.flush();
		baos.close();
		in.close();
		System.out.println("服务端返回的内容：" + RequestUtil.getGzipParameters(returnValue, new TypeReference<HashMap<String, Object>>() {}));
		
		conn.disconnect();
	}
	
}
