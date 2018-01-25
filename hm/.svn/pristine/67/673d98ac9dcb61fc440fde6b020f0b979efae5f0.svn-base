package cc.chinagps.lib.util;

import java.io.IOException;
import java.util.Properties;

public class Config {
	public static String getConfigPath() {
		String path = Config.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		return path.substring(0, path.indexOf("classes"));
	}

	public static String getWebRootPath() {
		String path = Config.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		return path.substring(0, path.indexOf("WEB-INF"));
	}

	private static Properties systemProperties;
	
	private static Properties cmdProperties;
	
	private static Properties keyProperties;
	
	private static Properties messageProperties;
	
	private static Properties jdbcProperties;

	public static Properties getSystemProperties() {
		return systemProperties;
	}
	
	public static Properties getCmdProperties() {
		return cmdProperties;
	}
	
	public static Properties getKeyProperties() {
		return keyProperties;
	}
	
	public static Properties getMessageProperties() {
		return messageProperties;
	}
	
	public static Properties getJdbcProperties(){
		return jdbcProperties;
	}

	static {
		//String path_system = getConfigPath() + "config/system.properties";
		String path_cmd = getConfigPath() + "classes/cmd.properties";
		//String path_key = getConfigPath() + "config/key.properties";
		//String path_message = getConfigPath() + "config/message.properties";
		String path_jdbc = getConfigPath()+"classes/jdbc.properties";
		
		try {
			//systemProperties = Util.loadProperties(path_system);
			cmdProperties = Util.loadProperties(path_cmd);
			//keyProperties = Util.loadProperties(path_key);
			//messageProperties = Util.loadProperties(path_message);
			jdbcProperties = Util.loadProperties(path_jdbc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}