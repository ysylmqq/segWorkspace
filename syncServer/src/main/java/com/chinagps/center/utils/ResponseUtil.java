package com.chinagps.center.utils;

import java.util.Map;

import com.chinagps.center.pojo.ReturnValue;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Package:com.chinagps.center.utils
 * @ClassName:ResponseUtil
 * @Description:
 * @author:xiaoke
 * @date:2014-5-21 下午4:39:07
 */
public class ResponseUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 不加密
	 * @param returnValue
	 * @return
	 */
	public static String getEncryptReturnValueString(ReturnValue returnValue) {
		try {
			String valueStr = mapper.writeValueAsString(returnValue);
			//return CipherTool.getCipherString(valueStr);
			return valueStr;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 加密
	 * @param returnValue
	 * @return
	 */
	public static String getReturnValueString(ReturnValue returnValue) {
		try {
			String valueStr = mapper.writeValueAsString(returnValue);
			return CipherTool.getCipherString(valueStr);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String writeJsonAsString(Map<String, Object> resultMap){
		try {
			String valueStr = mapper.writeValueAsString(resultMap);
			//return CipherTool.getCipherString(valueStr);
			return valueStr;
		} catch (Exception e) {
			return null;
		}
	}
}