package cc.chinagps.lib.util;

import java.io.IOException;
import java.util.Properties;

public class SystemConfig {
	private static final String ACTIVEMQ_CONFIG_FILE_PATH = "./config/activemq.properties";
	private static final String SYSTEM_CONFIG_FILE_PATH = "./config/system.properties";
	private static final String HBASE_CONFIG_FILE_PATH = "./config/hbase.properties";
	private static final String MEMCACHE_CONFIG_FILE_PATH = "./config/memcache.properties";
	private static final String LOG_CONFIG_FILE_PATH = "./config/log4j.properties";
	private static final String ALARM_CONFIG_FILE_PATH = "./config/alarm.properties";
	private static final String DB_CONFIG_FILE_PATH = "./config/db.properties";
	//private static final String AUTHORITY_CONFIG_FILE_PATH = "./config/authority.properties"; //LDAP权限
	private static final String CUSTOMER_CONFIG_FILE_PATH = "./config/customer.properties";   //集团用户外部接口登录名、密码、固定IP
	
	
	private static Properties activemq_properties;
	private static Properties system_properties;
	private static Properties hbase_properties;
	private static Properties memcache_properties;
	private static Properties log_properties;
	private static Properties alarm_properties;
	private static Properties db_properties;
	//private static Properties authority_properties;
	private static Properties customer_properties;
	
	public static void init(){
		try {
			activemq_properties = Util.loadProperties(ACTIVEMQ_CONFIG_FILE_PATH);
			system_properties = Util.loadProperties(SYSTEM_CONFIG_FILE_PATH);
			hbase_properties = Util.loadProperties(HBASE_CONFIG_FILE_PATH);
			memcache_properties = Util.loadProperties(MEMCACHE_CONFIG_FILE_PATH);
			log_properties = Util.loadProperties(LOG_CONFIG_FILE_PATH);
			alarm_properties = Util.loadProperties(ALARM_CONFIG_FILE_PATH);
			db_properties = Util.loadProperties(DB_CONFIG_FILE_PATH);
			//authority_properties = Util.loadProperties(AUTHORITY_CONFIG_FILE_PATH);
			customer_properties = Util.loadProperties(CUSTOMER_CONFIG_FILE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getActiveMQProperty(String key){
		init();
		return activemq_properties.getProperty(key);
	}
	
	public static String getSystemProperties(String key){
		init();
		return system_properties.getProperty(key);
	}
	
	public static String getHbaseProperties(String key){
		init();
		return hbase_properties.getProperty(key);
	}

	public static String getMemcacheProperties(String key){
		init();
		return memcache_properties.getProperty(key);
	}

	public static String getLogProperties(String key){
		init();
		return log_properties.getProperty(key);
	}
	
	public static String getAlarmProperties(String key){
		init();
		return alarm_properties.getProperty(key);
	}

	public static String getDBProperties(String key){
		init();
		return db_properties.getProperty(key);
	}

	public static Properties getDBproperties() {
		init();
		return db_properties;
	}
	
	//public static String getAuthorityproperties(String key) {
	//	init();
	//	return authority_properties.getProperty(key);
	//}

	public static String getCustomerproperties(String key) {
		init();
		return customer_properties.getProperty(key);
	}
}