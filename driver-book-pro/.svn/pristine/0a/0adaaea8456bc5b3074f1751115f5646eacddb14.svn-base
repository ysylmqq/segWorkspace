package com.chinagps.driverbook.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class RequestUtil {
	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
	}

	public static Integer getIntegerValue(String encryptStr, String fieldName)
			throws Exception {
		Integer parameterValue = null;
		if (encryptStr != null && !"".equals(encryptStr)) {
			String decryptStr = CipherTool.getOriginString(encryptStr);
			JsonNode jsonNode = mapper.readTree(decryptStr).get(fieldName);
			if (jsonNode != null && jsonNode.getNodeType() != JsonNodeType.NULL
					&& jsonNode.getNodeType() == JsonNodeType.NUMBER) {
				parameterValue = jsonNode.intValue();
			}
		}
		return parameterValue;
	}

	public static Long getLongValue(String encryptStr, String fieldName)
			throws Exception {
		Long parameterValue = null;
		if (encryptStr != null && !"".equals(encryptStr)) {
			String decryptStr = CipherTool.getOriginString(encryptStr);
			JsonNode jsonNode = mapper.readTree(decryptStr).get(fieldName);
			if (jsonNode != null && jsonNode.getNodeType() != JsonNodeType.NULL
					&& jsonNode.getNodeType() == JsonNodeType.NUMBER) {
				parameterValue = jsonNode.longValue();
			}
		}
		return parameterValue;
	}

	public static String getStringValue(String encryptStr, String fieldName)
			throws Exception {
		String parameterValue = null;
		if (encryptStr != null && !"".equals(encryptStr)) {
			String decryptStr = CipherTool.getOriginString(encryptStr);
			JsonNode jsonNode = mapper.readTree(decryptStr).get(fieldName);
			if (jsonNode != null && jsonNode.getNodeType() != JsonNodeType.NULL
					&& jsonNode.getNodeType() == JsonNodeType.STRING) {
				parameterValue = jsonNode.textValue();
			}
		}
		return parameterValue;
	}

	/**
	 * 解码密文并转换成参数对象
	 * 
	 * @param <T>
	 * @param encryptStr
	 *            原始密文
	 * @param clazz
	 *            可被实例化的Class对象
	 * @return
	 */
	public static <T> T getParameters(String encryptStr, Class<T> clazz)
			throws Exception {
		T params = null;
		if (encryptStr != null && !"".equals(encryptStr)) {
			String decryptStr = CipherTool.getOriginString(encryptStr);
			params = mapper.readValue(decryptStr, clazz);
		}
		return params;
	}

	/**
	 * 解码密文并转换成参数对象
	 * 
	 * @param <T>
	 * @param encryptStr
	 *            原始密文
	 * @param typeRef
	 *            泛型引用
	 * @return
	 */
	public static <T> T getParameters(String encryptStr,
			TypeReference<T> typeRef) throws Exception {
		T params = null;
		if (encryptStr != null && !"".equals(encryptStr)) {
			String decryptStr = CipherTool.getOriginString(encryptStr);
			params = mapper.readValue(decryptStr, typeRef);
		}
		return params;
	}

	public static String getGzipParameterValue(String encryptStr,
			String fieldName) throws Exception {
		String parameterValue = null;
		if (encryptStr != null && !"".equals(encryptStr)) {
			String decryptStr = CipherTool.getOriginString(encryptStr);

			ByteArrayInputStream in = null;
			GZIPInputStream ginzip = null;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			String decompressed = null;
			try {
				in = new ByteArrayInputStream(new Base64().decode(decryptStr));
				ginzip = new GZIPInputStream(in);

				byte[] buffer = new byte[1024];
				int offset = -1;
				while ((offset = ginzip.read(buffer)) != -1) {
					out.write(buffer, 0, offset);
				}
				decompressed = new String(out.toByteArray(), "UTF-8");
				parameterValue = mapper.readTree(decompressed).get(fieldName)
						.textValue();
			} finally {
				if (out != null) {
					out.close();
				}
				if (ginzip != null) {
					ginzip.close();
				}
				if (in != null) {
					in.close();
				}
			}
		}
		return parameterValue;
	}

	public static <T> T getGzipParameters(String encryptStr, Class<T> clazz)
			throws Exception {
		T params = null;
		if (encryptStr != null && !"".equals(encryptStr)) {
			String decryptStr = CipherTool.getOriginString(encryptStr);

			ByteArrayInputStream in = null;
			GZIPInputStream ginzip = null;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			String decompressed = null;
			try {
				in = new ByteArrayInputStream(new Base64().decode(decryptStr));
				ginzip = new GZIPInputStream(in);

				byte[] buffer = new byte[1024];
				int offset = -1;
				while ((offset = ginzip.read(buffer)) != -1) {
					out.write(buffer, 0, offset);
				}
				decompressed = new String(out.toByteArray(), "UTF-8");
				params = mapper.readValue(decompressed, clazz);
			} finally {
				if (out != null) {
					out.close();
				}
				if (ginzip != null) {
					ginzip.close();
				}
				if (in != null) {
					in.close();
				}
			}
		}
		return params;
	}

	public static <T> T getGzipParameters(String encryptStr,
			TypeReference<T> typeRef) throws Exception {
		T params = null;
		if (encryptStr != null && !"".equals(encryptStr)) {
			String decryptStr = CipherTool.getOriginString(encryptStr);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in = null;
			GZIPInputStream ginzip = null;
			String decompressed = null;
			try {
				in = new ByteArrayInputStream(new Base64().decode(decryptStr));
				ginzip = new GZIPInputStream(in);

				byte[] buffer = new byte[1024];
				int offset = -1;
				while ((offset = ginzip.read(buffer)) != -1) {
					out.write(buffer, 0, offset);
				}
				decompressed = out.toString("UTF-8");
				params = mapper.readValue(decompressed, typeRef);
			} finally {
				try {
					if (ginzip != null) {
						ginzip.close();
					}
					if (in != null) {
						in.close();
					}
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
				}
			}
		}
		return params;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param url
	 * @param param
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		String server = PropertyUtil.getPropertyValue("comm.properties", "push.server");
		String sr=RequestUtil.sendPost(server, "vin=121212&title=tset&body=ddddddddddddddddddddddddd");
        System.out.println(sr);
	}

}