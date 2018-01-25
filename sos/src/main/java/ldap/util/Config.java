package ldap.util;
/**
 * @see获取Web-Inf目录下配置文件
 *
 */
public class Config {
	 public static String getConfigPath(){
		 String path = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		 return path.substring(0, path.indexOf("classes"));
	 }
	 
	 public static String getWebRootPath(){
		 String path = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		 return path.substring(0, path.indexOf("WEB-INF"));
	 }
}
