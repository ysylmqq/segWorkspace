package com.chinaGPS.gtmp.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class CityUtil{
	
	/*
	 * @param latitude
	 *            纬度
	 * @param longitude
	 *            经度
	 * @return
	 */
	public static String GetAddr(String latitude, String longitude) {
		String addr = "";

		if(latitude == null  || longitude == null )
		{
			return addr; 
		}
		// 也可以是http://maps.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s，不过解析出来的是英文地址
		// 密钥可以随便写一个key=abc
		// output=csv,也可以是xml或json，不过使用csv返回的数据最简洁方便解析
		String url = ""; 
		try{
			url = String.format("http://ditu.google.cn/maps/geo?output=json&key=abcdef&q=%s,%s",formatLat(latitude), formatLon(longitude));
		} catch(Exception e){
			return "";
		}
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		try {
			httpsConn = (URLConnection) myURL.openConnection();
			if (httpsConn != null) {
				InputStream is = httpsConn.getInputStream();
	
				DataInputStream in = new DataInputStream(is);
				int length = in.available();
				byte sContent[] = new byte[length];
				BufferedInputStream buf = new BufferedInputStream(httpsConn.getInputStream());
	
				buf.read(sContent, 0, length);
				String sContent2 = new String(sContent, 0, length, "UTF-8");
				JSONObject joInput = JSONObject.fromObject(sContent2);
				String placemark = joInput.getString("Placemark");
	
				JSONObject placeJSON = JSONObject.fromObject(placemark.substring(1, placemark.length()-1));
				String addressDetails = placeJSON.getString("AddressDetails");
				JSONObject addressJSON = JSONObject.fromObject(addressDetails);
				String country = addressJSON.getString("Country");
				JSONObject countryJSON = JSONObject.fromObject(country);
				String administrativeArea = countryJSON.getString("AdministrativeArea");
				JSONObject areaJSON = JSONObject.fromObject(administrativeArea);
				String locality = areaJSON.getString("Locality");
				JSONObject localityJSON = JSONObject.fromObject(locality);
				addr = localityJSON.getString("LocalityName");
//				System.out.print(addr);
			}   
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		catch(JSONException e){
			e.printStackTrace();
			return "";
		}
		return addr.replace("市", "");
	}
	
	//GPS坐标转换为google坐标
	private static String formatLat(String latitude) throws Exception{
		String formatLat = "";
		if(latitude.endsWith("S")){
			formatLat = "-" + formatLat;
		}
		try{
			formatLat += latitude.substring(0, 2);
			Double branch = Double.valueOf(latitude.substring(2, 9));
			branch = branch/60;
			DecimalFormat df = new DecimalFormat("0.0000"); //设定小数后统一保留四位的数值格式
			formatLat += df.format(branch).substring(1);
		}catch (Exception e){
			throw e;
		}
		return formatLat;
	}
	
	//GPS坐标转换为google坐标
	private static String formatLon(String longitude) throws Exception{
		String formatLon = "";
		if(longitude.endsWith("W")){
			formatLon = "-" + formatLon;
		}
		try{
			formatLon += longitude.substring(0, 3);
			Double branch = Double.valueOf(longitude.substring(3, 10));
			branch = branch/60;
			DecimalFormat df = new DecimalFormat("0.0000"); //设定小数后统一保留四位的数值格式
			formatLon += df.format(branch).substring(1);
		}catch (Exception e){
			throw e;
		}
		return formatLon;
	}
	
	public static void main(String[] args){
		String test = "11232.1115W";
		try {
			System.out.println(formatLon(test));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

