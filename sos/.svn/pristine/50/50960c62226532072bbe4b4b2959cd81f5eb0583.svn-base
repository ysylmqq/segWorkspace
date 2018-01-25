package com.gboss.comm;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gboss.util.LogOperation;
import com.gboss.util.UUIDCreater;

public class GbossHandlerInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = Logger.getLogger("sysLog");

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (handler.getClass().toString()
				.endsWith("org.springframework.web.method.HandlerMethod")) {
			boolean isLogged = false;
			HandlerMethod method = (HandlerMethod) handler;
			HttpSession session = request.getSession(false);
			if (session != null) {
				String loginname = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_USERNAME);
				String account_id = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);

				// 记录对象
				int type = 0;
				String message = null;

				if (StringUtils.isNotBlank(loginname)) {
					LogOperation lo = null;
					if (method != null
							&& (lo = method.getMethod().getAnnotation(
									LogOperation.class)) != null) {
						type = lo.type();
						if(request.getAttribute(SystemConst.OPLOG_PARAMS_KEY)!=null){
							String obj = (String) request.getAttribute(SystemConst.OPLOG_PARAMS_KEY);
							// 记录参数
							if(StringUtils.isNotBlank(obj) && obj.length()>512){//数据库设置长度的1024，截取时，只截取512，考虑到有中文
								obj=obj.substring(0,512);
							}
							message = loginname + lo.description() + ",参数列表:" + obj;
							isLogged = true;
							
							if (isLogged) {
								MDC.put("user_id", account_id);
								MDC.put("remark", message);
								MDC.put("op_type", type);
								MDC.put("subco_no", companyId);
								logger.debug(message);
							}
						}
					}

				}
			}
		}
	}

}
