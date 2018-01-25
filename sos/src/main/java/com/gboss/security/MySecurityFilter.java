package com.gboss.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.gboss.dao.UsersDao;

public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {
	//与applicationContext-security.xml里的myFilter的属性securityMetadataSource对应，
	//其他的两个组件，已经在AbstractSecurityInterceptor定义
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	private UsersDao usersDao;
	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
	
	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}
	
	private void invoke(FilterInvocation fi) throws IOException, ServletException {
		System.out.println("用户发送请求！ ");
		InterceptorStatusToken token = null;
		token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			super.afterInvocation(token, null);
		}
	}
	
	private Authentication authenticateIfRequired() {  
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  
	  
	    if (authentication.isAuthenticated()) {  
            if (logger.isDebugEnabled()) {  
                logger.debug("Previously Authenticated: " + authentication);  
            }  
	    }
        return authentication;  
	}

	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<? extends Object> getSecureObjectClass() {
		//下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
		return FilterInvocation.class;
	}
}
