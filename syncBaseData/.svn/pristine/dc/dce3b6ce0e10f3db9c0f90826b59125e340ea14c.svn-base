package com.gboss.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Test {
	protected static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getUrl(String url, Long start_date, Long end_date) {
		if(start_date != null){
//			start_date  = start_date/1000;
			url = url.concat("&start_date=").concat(start_date.toString());
		}
		if(end_date != null){
//			end_date 	= end_date/1000;	 
			url = url.concat("&end_date=").concat(end_date.toString());
		}
		return url;
	}
	
	public static List<HashMap<String, Object>> getSyncData(Long start_date, Long end_date, String url) throws org.apache.http.ParseException, IOException{
		url = getUrl(url, start_date, end_date);
		System.out.println(SDF.format(new Date()) + " 请求地址,"+url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//===	20150930
		Map<String,Object> ret_map = ParamsHelper.getParams(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if(ret_map != null){
			url = (String) ret_map.get("url");
			nameValuePairs =  (List<NameValuePair>) ret_map.get("nameValuePairs");
		}
		//===	20150930
		System.out.println("##url##" + url);
		HttpPost httppost = new HttpPost(url);
		String strResult = "";
//		httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		CloseableHttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() == 200) {
			String conResult = EntityUtils.toString(response.getEntity());
			JSONObject sobj = new JSONObject();
			sobj = sobj.fromObject(conResult);
			if (sobj.containsKey("data")) {
				String datas = sobj.getString("data");
				if (null != datas && !datas.toString().equals("null")) {
					System.out.println("==============>"+datas);
					return  null;
				} else {
					System.out.println(SDF.format(new Date()) + " 同步车辆绑定信息没有数据！");
				}
			} else {
				System.out.println(SDF.format(new Date()) + " 同步车辆绑定信息没有数据！");
			}

		} else {
			String err = response.getStatusLine().getStatusCode() + "";
			strResult += "发送失败:" + err;
			System.out.println(SDF.format(new Date()) + " 同步车辆绑定信息获取数据" + strResult);
		}
		response.close();
		return null;
	}
	
	//http://101.200.228.44/sync/car/bindInfo?1=1&start_date=1443582000&end_date=1443582095
	//http://101.200.228.44/sync/car/bindInfo?1=1&start_date=1443581700&end_date=1443581795
	//http://101.200.228.44/sync/car/bindInfo?1=1&start_date=1443584400&end_date=14435844953
	//http://101.200.228.44/sync/car/accounts?1=1&start_date=1443584700&end_date=1443584760
	//nameValuePairs=[start_date=1443584700, end_date=1443584760], url=http://101.200.228.44/sync/car/accounts
	//					[start_date=1443584700, end_date=1443584760]
	public static void main(String[] args) {
		//http://101.200.228.44/sync/car/bindInfo?1=1&start_date=1443588120&end_date=1443588362
//		try {
//			//http://101.200.228.44/sync/car/accounts[start_date=1443590101, end_date=1443590161]
//			//http://101.200.228.44/sync/car/info?1=1&start_date=1443591901&end_date=1443591960
//			//http://101.200.228.44/sync/car/bindInfo?1=1&start_date=1443593220&end_date=1443593460
//			//start_date=1443594000&end_date=1443594060
//			//http://101.200.228.44/sync/car/accounts?1=1&start_date=1443594240&end_date=1443594360
//			getSyncData(1443594240L,1443594360L,"http://101.200.228.44/sync/car/accounts?1=1");
//		} catch (ParseException e2) {
//			e2.printStackTrace();
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}
		System.out.println("<================>");
		//http://101.200.228.44/sync/car/info?1=1&start_date=1444380900&end_date=1444380960
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://101.200.228.44/sync/car/info");
//		HttpPost httppost = new HttpPost("http://101.200.228.44/sync/car/accounts");
		//http://101.200.228.44/sync/car/accounts?1=1&start_date=1443588120&end_date=1443588360
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("start_date", "1444380900"));
		nvps.add(new BasicNameValuePair("end_date", "1444380960"));
		
		System.out.println(nvps);

		UrlEncodedFormEntity uefEntity;
		String returnStr = "";
		try {
			uefEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
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
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
