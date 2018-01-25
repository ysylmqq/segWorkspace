package com.gboss.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.liteframework.core.web.util.WebFileUtils;
/*
 * @author chenzh
 * 上传文件到文件服务器的客户端
 */
public class FileUploadClient {
	/*
	 * urlStr：文件服务器全路径 file：本地文件
	 */
	public static boolean fileUpload(String urlStr, File file) {
		try {
			return fileUpload(urlStr, new FileInputStream(file));
		} catch (FileNotFoundException e) {
			return false;
		}
	}

	public static boolean fileUpload(String urlStr, byte[] bytes) {
		return fileUpload(urlStr, new ByteArrayInputStream(bytes));
	}

	public static boolean fileUpload(String urlStr, InputStream input) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/html");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.connect();
			// 设置过期时间
			conn.setConnectTimeout(10000);
			OutputStream out = conn.getOutputStream();
			DataInputStream in = new DataInputStream(input);
			int bytes = 0;
			byte[] buffer = new byte[1024];
			while ((bytes = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytes);
			}
			in.close();
			out.flush();
			out.close();
			conn.getInputStream();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String urlStr = "http://192.168.3.60:8081/upload.action?filePath=/c&fileName=demo.gif";
		File file = new File("F:\\history.csv");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		WebFileUtils.saveUrlAs("http://www.baidu.com/img/baidu_sylogo1.gif",
				out);
		System.out.println(out.toByteArray().length);
		FileUploadClient.fileUpload(urlStr, out.toByteArray());

		urlStr = "http://192.168.3.60:8081/upload.action?fileName=demo.csv";
		FileUploadClient.fileUpload(urlStr, file);
		
		//urlStr = "/download.action?sysFlag=rds&filePath=/images/2013-6-5/3650378LZ4PWPK1.jpg";
		//FileUploadClient.deleteUpload(urlStr);
		
	}
}