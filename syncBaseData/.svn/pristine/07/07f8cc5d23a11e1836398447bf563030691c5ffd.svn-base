package ldap.util;

import java.util.UUID;

public class Util {
	/**
	 * 获得UUID主键ID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static boolean isBlank(String str) {
		if (str == null || str.trim().equals("") || str.trim().equals("null")) {
			return true;
		}
		return false;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

}
