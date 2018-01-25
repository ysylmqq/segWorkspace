package com.gboss.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * httpclient工具类
 * 
 * @author lic
 * 
 */
public class HttpClientUtils {

	public static String get(String path) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget
			HttpGet httpget = new HttpGet(path);
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求
			CloseableHttpResponse response = httpclient.execute(httpget);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			System.out.println(response.getStatusLine());
			if (entity != null) {
				// 打印响应内容长度
				System.out.println("Response content length: "
						+ entity.getContentLength());
				// 打印响应内容
				System.out.println("Response content: "
						+ EntityUtils.toString(entity));
			}
			response.close();
		} catch (Exception e) {

		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static String get(String path, Map<String, String> params) {

		return "";
	}

	public static String post(String path) {
		return post(path, null);
	}

	public static String post(String path, Map<String, String> params) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(path);
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if(params != null){
			Set<Map.Entry<String, String>> keySet=params.entrySet();
			Iterator<Entry<String, String>> iterator=keySet.iterator();
			String keyStr = ""; 
			String valStr = "";
			while(iterator.hasNext()){
				Entry<String, String> entry = iterator.next();
				keyStr = entry.getKey();
				valStr = entry.getValue();
				formparams.add(new BasicNameValuePair(keyStr, valStr));
			}
		}
		UrlEncodedFormEntity uefEntity;
		String returnStr = "";
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out.println("--------------------------------------");
					returnStr = EntityUtils.toString(entity, "UTF-8");
					System.out.println("Response content: " + returnStr);
					System.out.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnStr;
	}
	
	public static void main(String[] args){
		Map<String, String> params = new HashMap<String, String>();
		params.put("vin", "LMVAFLFC4FA002460");
		params.put("password", "123456");
//		post("http://101.200.228.44/sync/car/resetpassword", params);
		String str = post("http://101.200.228.44/sync/car/resetpassword?vin=eryrtrturrt&password=123456");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
		System.out.println("qq=" + json.get("code"));
	}
}
