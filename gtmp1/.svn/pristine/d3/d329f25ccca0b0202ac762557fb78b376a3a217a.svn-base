package com.chinaGPS.gtmp.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.util.Properties;

import com.sun.org.apache.bcel.internal.generic.NEW;

import oracle.jdbc.OracleConnection;

/**
 * @Package:com.chinaGPS.gtmp.util
 * @ClassName:DBTools
 * @Description:
 * @author:zfy
 * @date:2013-4-10 下午01:04:55
 */
public class DBTools {
	 static private DBTools instance;   
	    
	    static synchronized public DBTools getInstance() {   
	        if (instance == null) {
	            instance = new DBTools();
	        }
	        return instance;
	    }
	private static OracleConnection conn = null;
	private static String url;
	private static String user;
	private static String password;
	private static String driver;
	static private Properties properties = null;
	
	public  OracleConnection getConnection() {
		try {			
			setProperties();
			driver = properties.getProperty("driver").trim();
			url = properties.getProperty("url").trim();
			user = properties.getProperty("username").trim();
			password = properties.getProperty("password").trim();
			// 1：注册驱动类
			Class.forName(getDriver());
			// 2：创建数据库连接
			conn = (OracleConnection) DriverManager.getConnection(getUrl(),
					getUser(), getPassword());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (OracleConnection) conn;
	}
	
	public void setProperties() {
		InputStream is = null;
		try {
			is = getClass().getResourceAsStream("/database.properties");
			properties = new Properties();
			properties.load(is);
		} catch (IOException ex) {
			System.err.println("不能读取属性文件.请确保database.properties在CLASSPATH指定的路径中");
		}finally{
        	try {
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	

	public static OracleConnection getConn() {
		return conn;
	}

	public static String getUrl() {
		return url;
	}

	public static String getUser() {
		return user;
	}

	public static String getPassword() {
		return password;
	}

	public static String getDriver() {
		return driver;
	}

	public static void main(String[] args) {
		System.out.println(new DBTools().getConnection());
	}

}
