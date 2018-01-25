package ldap.mysql;

import java.sql.*;
import java.util.*;
import java.io.*;

import ldap.util.Config;
import ldap.util.IOUtil;

public class SimpleConnetionManager {
	
	private static final String DB_CONFIG_FILE_PATH = "classes/jdbc2.properties";
	
	private static Properties properties = new Properties();
	
	private static String driverClassName = "com.mysql.jdbc.Driver";
    
    private static String url = "jdbc:mysql://192.168.3.196:3306/myseq?useUnicode=true&amp;characterEncoding=UTF-8";
    
    private static String username = "gboss"; 
    
    private static String password = "123456";
	
    static{
		String configPath = Config.getConfigPath();
		String filePath = configPath + DB_CONFIG_FILE_PATH;
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			properties.load(is);
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.closeIS(is);
		}
		driverClassName = properties.getProperty("driverClassName");
		url = properties.getProperty("url");
		username = properties.getProperty("username");
		password = properties.getProperty("password");
	}
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
}
