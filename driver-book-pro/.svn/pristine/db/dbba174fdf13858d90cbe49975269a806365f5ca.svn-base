package com.chinagps.driverbook.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 读取配置文件
 * 
 * @author Ben
 * 
 */
public class PropertyUtil {
	private PropertyUtil() {
		
	}

	/**
	 * 读取properties文件中的一个属性值
	 * 
	 * @param fileName properties文件名
	 * @param key 键
	 * @return key对应的值
	 * @throws ConfigurationException
	 */
	public static String getPropertyValue(String fileName, String key) {
		Properties properties =new Properties();
		String value = "";
		try {
			InputStream in=PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
			properties.load(in); 
			value = properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 获取properties文件的对象
	 * 
	 * @param fileName 文件名
	 * @return PropertiesConfiguration对象
	 */
	public static Properties getProperty(String fileName) {
		Properties properties =new Properties();
		try {
			InputStream in=PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
			properties.load(in);
		} catch (Exception e) {
			System.out.println("读取" + fileName + "失败！");
			e.printStackTrace();
		}
		return properties;
	}

}