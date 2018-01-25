package com.gboss.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.ConfigurationException;


/**
 * 读取配置文件
 * 
 * @author Ben
 * 
 */
public class PropertyUtil {
	private static final String SYSTEM_CONFIG_FILE_PATH = "system.properties";
	
	private static Properties system_properties;
	
	static {
		try {
			system_properties = loadProperties(SYSTEM_CONFIG_FILE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getSystemProperty(String key){
		return system_properties.getProperty(key);
	}
	
	private PropertyUtil() {
		
	}
	
	public static Properties loadProperties(String fileName) throws IOException{
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
			properties.load(is);
			return properties;
		}finally{
			if(is!=null){
				is.close();
			}
		}
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