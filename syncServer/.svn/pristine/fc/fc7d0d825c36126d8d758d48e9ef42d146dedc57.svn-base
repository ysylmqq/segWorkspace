package com.chinagps.center.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.chinagps.center.utils.RequestUtil;
import com.chinagps.center.utils.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public abstract class BaseController {

	static final  String message = "message";

	static final String post = "POST";

	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * @RequestMapping("{url ") public String toUrl(@PathVariable String url)
	 *                       throws Exception { return url; }
	 **/
	
	/**
	 * 获取请求参数键值对数组 </p>
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return HashMap<String,String> 分请求参数键值对数组
	 */
	public Map parseReqParam(HttpServletRequest request){
		// 取消对请求串的UTF-8编码，防止不同浏览器中发送请求时进行默认编码处理导致的转码失败
		Map<?, ?> map = null;
		try {
			byte[] buffer = new byte[512];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream in = request.getInputStream();
			for (int len = 0; (len = in.read(buffer)) > 0;) {
				baos.write(buffer, 0, len);
			}
			String content = new String(baos.toByteArray(), DEFAULT_ENCODING);
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(content, Map.class);
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public Map parseReqParam2(HttpServletRequest request){
		// 返回值Map
	    Map map = new HashMap();
	    Iterator entries =request.getParameterMap().entrySet().iterator();
	    Map.Entry entry = null;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        if("null".equals(value)){
	        	value="";
	        }
	        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)){
				map.put(name,value);
	        }
	    }
	    return map;
	}
	
	public String versionManager(String encryptStr){
		String origin = "";
		String appVersion = "";
		String deviceType = "";
		String apiVersion = "";
		try {
			// 获取解密后的参数
			HashMap<String, String> params = RequestUtil.getParameters(encryptStr, new TypeReference<HashMap<String, String>>(){});
			origin = params.get("origin");
			appVersion = params.get("appVersion");
			deviceType = params.get("deviceType");
			apiVersion = params.get("apiVersion");
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}
	
}
