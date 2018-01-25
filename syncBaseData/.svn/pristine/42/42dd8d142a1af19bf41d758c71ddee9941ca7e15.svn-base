package com.gboss.comm;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gboss.util.StringUtils;

public class IpsInterceptor implements HandlerInterceptor {

	@Autowired
	protected SystemConfig systemconfig;
	
	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String ipstr = systemconfig.getIps();
		
		if(StringUtils.isNullOrEmpty(ipstr)){
			System.out.println(SDF.format(new Date()) +  "系统未配置允许访问的IP列表!");
			return false;
		}
		
		String ipsArr[] = ipstr.split(",");
		String clientIp = getIpAddr(request);
		for(String allowIP : ipsArr){
			if(StringUtils.isNotNullOrEmpty(allowIP)){
				allowIP = allowIP.trim();
				if(allowIP.equals(clientIp)){
					System.out.println(SDF.format(new Date()) +  "来自ip[" + clientIp + "]的请求，"+request.getRequestURL()+"通过!");
					return true;
				}
			}
		}
		System.out.println(SDF.format(new Date()) +  "来自ip[" + clientIp + "]的请求，"+request.getRequestURL()+"被拒绝!");
		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getIp2(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotNullOrEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotNullOrEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

}
