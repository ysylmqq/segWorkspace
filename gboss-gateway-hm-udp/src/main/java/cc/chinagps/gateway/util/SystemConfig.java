package cc.chinagps.gateway.util;

import java.io.IOException;
import java.util.Properties;

public class SystemConfig {
	private static final String SYSTEM_CONFIG_FILE_PATH = "config/system.properties";
	private static final String APLAN_CONFIG_FILE_PATH = "config/aplan.properties";
	private static final String MQ_CONFIG_FILE_PATH = "config/mq.properties";
	private static final String MEMCACHE_CONFIG_FILE_PATH = "config/memcache.properties";
	private static final String HBASE_CONFIG_FILE_PATH = "config/hbase.properties";
	private static final String WEB_CONFIG_FILE_PATH = "config/web.properties";
	
	private static Properties system_properties;
	private static Properties aplan_properties;
	private static Properties mq_properties;
	private static Properties memcache_properties;
	private static Properties hbase_properties;
	private static Properties web_properties;
	
	static {
		try {
			system_properties = Util.loadProperties(SYSTEM_CONFIG_FILE_PATH);
			aplan_properties = Util.loadProperties(APLAN_CONFIG_FILE_PATH);
			mq_properties = Util.loadProperties(MQ_CONFIG_FILE_PATH);
			memcache_properties = Util.loadProperties(MEMCACHE_CONFIG_FILE_PATH);
			hbase_properties = Util.loadProperties(HBASE_CONFIG_FILE_PATH);
			web_properties = Util.loadProperties(WEB_CONFIG_FILE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getSystemProperty(String key){
		return system_properties.getProperty(key);
	}
	
	public static String getAPlanProperty(String key){
		return aplan_properties.getProperty(key);
	}
	
	public static String getMQProperty(String key){
		return mq_properties.getProperty(key);
	}
	
	public static String getMemcacheProperty(String key){
		return memcache_properties.getProperty(key);
	}
	
	public static String getHBaseProperty(String key){
		return hbase_properties.getProperty(key);
	}
	
	public static String getWebProperty(String key){
		return web_properties.getProperty(key);
	}
}