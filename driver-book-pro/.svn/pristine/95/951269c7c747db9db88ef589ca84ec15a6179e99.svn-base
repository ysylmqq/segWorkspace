package com.chinagps.driverbook.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Session拦截器，用于判断用户是否登录或登录是否超时
 * @author Ben
 *
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String loginName = (String) request.getSession().getAttribute("loginName");
		if (loginName == null) {
			if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")) || request.getParameter("ajax") != null) {
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				Map<String, String> result = new HashMap<String, String>();
				result.put("statusCode", "301");
				result.put("message", "您尚未登录或登录已超时，请重新登录！");
				out.println(mapper.writeValueAsString(result));
				return false;
			} else {
				response.sendRedirect(request.getContextPath() + "/admin/signin");
				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}
}
