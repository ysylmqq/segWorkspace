package com.gboss.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebFileUtils {

	public static boolean isOuterUrl(String url) {

		if (null == url || url.trim().length() <= 0)
			return false;
		String pattern = "^((https|http|ftp|rtsp|mms)?://)?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\\.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\.[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(url.trim());
		return m.matches();
	}

	public static long saveUrlAs(String srcUrl, String fileName) {
		try {
			return saveUrlAs(srcUrl, new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static long saveUrlAs(String srcUrl, OutputStream output) {

		try {
			URL url = new URL(srcUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			DataInputStream in = new DataInputStream(
					connection.getInputStream());
			DataOutputStream out = new DataOutputStream(output);
			byte[] buffer = new byte[4096];
			int len;
			long count = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
				count += len;
			}
			in.close();
			return count;
		} catch (Exception e) {
			return 0;
		}
	}
}
