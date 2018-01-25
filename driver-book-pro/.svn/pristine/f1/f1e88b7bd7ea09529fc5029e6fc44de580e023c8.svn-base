package com.chinagps.driverbook.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinagps.driverbook.pojo.ReturnValue;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ResponseUtil {
	private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
	private static ObjectMapper mapper = new ObjectMapper();
	
//	APP适配问题屏蔽去除NULL值
//	static {
//		mapper.setSerializationInclusion(Include.NON_NULL);
//	}
	
	public static String encrypt(ReturnValue returnValue) {
		try {
			String valueStr = mapper.writeValueAsString(returnValue);
			System.out.println(returnValue);
			String result = CipherTool.getCipherString(valueStr);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static String getGzipEncryptString(ReturnValue returnValue) {
		ByteArrayOutputStream out = null;
		GZIPOutputStream gzip = null;
		try {
			String valueStr = mapper.writeValueAsString(returnValue);
			out = new ByteArrayOutputStream();
			gzip = new GZIPOutputStream(out);
			gzip.write(valueStr.getBytes("UTF-8"));
			gzip.finish();
			return CipherTool.getCipherString(new String(new Base64().encode(out.toByteArray())));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				if (gzip != null) {
					gzip.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
