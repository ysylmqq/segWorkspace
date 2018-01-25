package com.hm.bigdata.comm;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;

/**
 * @Package:com.chinagps.smh.comm
 * @ClassName:FastJsonArgumentResolver
 * @Description:用于对HttpServletRequest的body进行解析,可解析对象中嵌套的对象、数组
 * @author:zfy
 * @date:2014-4-14 上午8:42:56
 */
public class FastJsonArgumentResolver implements HandlerMethodArgumentResolver {

	 @Override
	    public boolean supportsParameter(MethodParameter parameter) {
	        return parameter.getParameterAnnotation(FastJson.class) != null;
	    }

	    @Override
	    public Object resolveArgument(MethodParameter parameter,
	                                  ModelAndViewContainer mavContainer,
	                                  NativeWebRequest webRequest,
	                                  WebDataBinderFactory binderFactory) throws Exception {

	        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
	        // content-type不是json的不处理
	        if (!request.getContentType().contains("application/json")) {
	            return null;
	        }

	        // 把reqeust的body读取到StringBuilder
	        BufferedReader reader = request.getReader();
	        StringBuilder sb = new StringBuilder();

	        char[] buf = new char[1024];
	        int rd;
	        while((rd = reader.read(buf)) != -1){
	            sb.append(buf, 0, rd);
	        }

	        // 利用fastjson转换为对应的类型
	        if(JSONObjectWrapper.class.isAssignableFrom(parameter.getParameterType())){
	            return new JSONObjectWrapper(JSON.parseObject(sb.toString()));
	        } else {
	        	return JSON.parseObject(sb.toString(), parameter.getParameterType());
	        }
	    }

	/**
	 * @Title:main
	 * @Description:TODO
	 * @param args
	 * @throws
	 */
	public static void main(String[] args) {

	}

}

