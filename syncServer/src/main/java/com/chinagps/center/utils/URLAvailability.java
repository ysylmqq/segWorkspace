package com.chinagps.center.utils;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * 文件名称为：URLAvailability.java 文件功能简述： 描述一个URL地址是否有效
 * 
 * @author Jason
 * @time 2010-9-14
 * 
 */
public class URLAvailability {
	private static Logger logger = Logger.getLogger(URLAvailability.class);
	private static URL url;
	private static HttpURLConnection con;
	private static int state = -1;

	/**
	 * 功能：检测当前URL是否可连接或是否有效, 描述：最多连接网络 5 次, 如果 5 次都不成功，视为该地址不可用
	 * 
	 * @param urlStr
	 *            指定URL网络地址
	 * @return URL
	 */
	public synchronized URL isConnect(String urlStr) {
		int counts = 0;
		if (urlStr == null || urlStr.length() <= 0) {
			return null;
		}
		while (counts < 3) {
			try {
				url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(6000);  
				con.setReadTimeout(6000);  
				state = con.getResponseCode();
				logger.info(urlStr+" "+counts + "= " + state);
				if (state == 200) {
					logger.info(urlStr+"可用！");
				}
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex, ex);
				counts++;
				logger.error(urlStr+"不可用，连接第 " + counts + " 次");
				url = null;
				continue;
			}
		}
		return url;
	}
	
	public URL getUrl(){
		return url;
	}

	public static void main(String[] args) {
		URLAvailability u = new URLAvailability();
		u.isConnect("http://localhost:8080/synchClient");
		u.isConnect("http://www.163.com/synchClient");
		System.out.println(u.url);
	}
}
