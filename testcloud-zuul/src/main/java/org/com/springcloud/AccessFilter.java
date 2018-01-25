package org.com.springcloud;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 统一的权限过滤器 
 *
 */
public class AccessFilter  extends ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(AccessFilter.class); 

	/**
	 * 对所有的请求都进行过滤，具体应用的时候根据实际情况进行过滤
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * 过滤器具体的处理逻辑
	 */
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info(" send {} request to {} ",request.getMethod(),request.getRequestURL().toString());
		
		String accessToken = request.getParameter("accessToken");
		if(accessToken == null ||"".equals(accessToken)){
			//没有携带accessToken 直接跳转到登录页面
			ctx.setSendZuulResponse(false); // 为false标示虽然经过了过滤器，但是不经过路由器
			ctx.setResponseBody("access not");
			ctx.setResponseStatusCode(401);
		}
		return null;
	}

	/**
	 * 过滤类型
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * 过滤顺序
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

}
