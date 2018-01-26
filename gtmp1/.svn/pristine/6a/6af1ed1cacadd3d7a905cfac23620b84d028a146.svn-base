package com.chinaGPS.gtmp.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class HttpURLConnectionUtil {	
	/**
	 * 初始化连接
	 * @param path 访问地址
	 * @return 初始化过的连接
	 * @throws Exception
	 */
	public static HttpURLConnection initConnection(String path) throws Exception {
		URL url = new URL(path);//property.getProperty(path));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");// 提交模式
		//conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
        conn.setConnectTimeout(100000);// 连接超时 单位毫秒
		conn.setReadTimeout(100000);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.connect();	
		return conn; 
	}
	public static Map<String, Object> requestResult(Map<String, Object> map,String path){
		HttpURLConnection conn = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		try {
			conn =initConnection(path);
			String r = JSON.toJSONString(map);
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
			osw.write(r);
			osw.flush();
			osw.close();

			String result = "";
			if (conn.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "utf-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					result += inputLine;
				}
				in.close();
			}

			if (result != null && !"".equals(result) ) {
				if(!result.startsWith("no")){
				    resultMap = JSON.parseObject(result, HashMap.class);
				}else{
					resultMap.put("error", result);
				}
			}
			System.out.println("接收到服务器返回数据："+JSON.toJSONString(resultMap));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return resultMap;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	  ip = request.getHeader("http_client_ip");  
    	}  
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	  ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
    	}  
    	if (ip != null && ip.indexOf(",") != -1) {  
		  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
		} 
        if("0:0:0:0:0:0:0:1".equals(ip))
        {
        	ip="127.0.0.1";
        }
        return ip;
	}
	
	public static Object baiduConvert(String gps) {
		HttpURLConnection conn = null;
		Object returnStr = null;
		Properties property = PropertyUtil.getProperty("activemq.properties");
		String url = property.getProperty("baiduURL") + "&coords=" + gps;
		try {
			conn = initConnection(url);
			String result = "";
			if (conn.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "utf-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					result += inputLine;
				}
				in.close();
			}

			if (result != null && !"".equals(result)) {
				com.alibaba.fastjson.JSONObject re = JSON.parseObject(result);
				returnStr= re.get("result");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}
	public static String getAddress(String location){
		if(location.equals("0,0")||location.equals("")||location.equals("0.0,0.0")){
			return "";
		}
		HttpURLConnection conn = null;
		String returnStr = null;
		Properties property = PropertyUtil.getProperty("activemq.properties");
		String baiduAddressURL = property.getProperty("baiduAddressURL");
		String ak = property.getProperty("ak");
		String url = baiduAddressURL+"?ak="+ak + "&location="+location+"&output=json";
		try {
			conn = initConnection(url);
			String result = "";
			if (conn.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "utf-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					result += inputLine;
				}
				in.close();
			}

			if (result != null && !"".equals(result)) {
				com.alibaba.fastjson.JSONObject re = JSON.parseObject(result);
				Map<String, Object> resultMap = re.getJSONObject("result");
				returnStr= ""+resultMap.get("formatted_address")+resultMap.get("sematic_description");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}
	public static String getBaiduLonlat(String coords){
		if(coords.equals("0,0")||coords.equals("0.0,0.0")){
			return "";
		}
		HttpURLConnection conn = null;
		String returnStr = null;
		Properties property = PropertyUtil.getProperty("activemq.properties");
		String baiduLonlat = property.getProperty("baiduLonlat");
		String ak = property.getProperty("ak");
		String url = baiduLonlat+"?ak="+ak + "&coords="+coords+"&output=json";
		try {
			conn = initConnection(url);
			String result = "";
			if (conn.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "utf-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					result += inputLine;
				}
				in.close();
			}

			if (result != null && !"".equals(result)) {
				com.alibaba.fastjson.JSONObject re = JSON.parseObject(result);
				JSONArray j = re.getJSONArray("result");
				Map<String, Object> resultMap = (Map<String, Object> )j.get(0);
				//Map<String, Object> resultMap = re.getJSONArray("result");
				returnStr=resultMap.get("y")+","+resultMap.get("x");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}
	public static void main(String[] args) {
		Map map = new HashMap<String, Object>();
        map.put("coords", "113.73213,25.36623");
        System.out.println(getAddress(getBaiduLonlat("113.73213,25.36623")));
	}
}
