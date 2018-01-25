package ldap.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Config {
	 public static String getConfigPath(){
		 String path = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		 try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 return path.substring(0, path.indexOf("classes"));
	 }
	 
	 public static String getWebRootPath(){
		 String path = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		 try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 return path.substring(0, path.indexOf("WEB-INF"));
	 }
}
