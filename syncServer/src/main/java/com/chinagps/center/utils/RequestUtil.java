package com.chinagps.center.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Package:com.chinagps.center.utils
 * @ClassName:RequestUtil
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-21 下午4:15:45
 */
public class RequestUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 解码密文并转换成参数对象
	 * @param <T>
	 * @param encryptStr 原始密文
	 * @param clazz 可被实例化的Class对象
	 * @return
	 */
	public static <T> T getParameters(String encryptStr, Class<T> clazz) throws Exception {
		T params = null;
	    if (encryptStr != null && !"".equals(encryptStr)) {
	    	String decryptStr = CipherTool.getOriginString(encryptStr);
	    	params = mapper.readValue(decryptStr, clazz);
	    }
	    return params;
	}
	
	/**
	 * 解码密文并转换成参数对象
	 * @param <T>
	 * @param encryptStr 原始密文
	 * @param typeRef 泛型引用
	 * @return
	 */
	public static <T> T getParameters(String encryptStr, TypeReference<T> typeRef) throws Exception {
		T params = null;
	    if (encryptStr != null && !"".equals(encryptStr)) {
	    	String decryptStr = CipherTool.getOriginString(encryptStr);
	    	params = mapper.readValue(decryptStr, typeRef);
	    }
	    return params;
	}
	
	public static <T> T getParameters1(String encryptStr, TypeReference<T> typeRef) throws Exception {
		T params = null;
		if (encryptStr != null && !"".equals(encryptStr)) {
			params = mapper.readValue(encryptStr, typeRef);
		}
		return params;
	}
	
}

