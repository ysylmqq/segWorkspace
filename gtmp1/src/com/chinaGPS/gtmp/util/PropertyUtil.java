package com.chinaGPS.gtmp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取src目录下的properties文件
 * @author xk
 *
 */
public class PropertyUtil{
	private PropertyUtil(){
		
	}
	/**
	 * 读取properties文件中的一个属性值
	 * @param fileName properties文件名
	 * @param key 键值
	 * @return key对应的值
	 */
	public static String getPropertyValue(String fileName,String key){
		Properties properties =new Properties(); 
		String value = "";
		try{ 
			//读取.properties文件 
			InputStream in=PropertyUtil.class.getClassLoader().getResourceAsStream(fileName); 
			//将流中读取键值对，放到properties中！ 
			properties.load(in); 
			value = properties.getProperty(key);
		} catch (IOException e) { 
			System.out.println("读取"+fileName+"失败！"); 
			e.printStackTrace(); 
		} 	
		return value;
	}
	
	/**
	 * 获取properties文件的对象
	 * @param fileName 文件名
	 * @return Properties对象
	 */
	public static Properties getProperty(String fileName){
		Properties properties =new Properties(); 
		try{ 
			//读取.properties文件 
			InputStream in=PropertyUtil.class.getClassLoader().getResourceAsStream(fileName); 
			//将流中读取键值对，放到properties中！ 
			properties.load(in); 
		} catch (IOException e) { 
			System.out.println("读取"+fileName+"失败！"); 
			e.printStackTrace(); 
		} 	
		return properties;
	}
	
	public static void main(String[] args){
		String temp = "lbass";
		System.out.println(getPropertyValue("fileSys.properties","fileSys.path"+"."+temp));
	}
}