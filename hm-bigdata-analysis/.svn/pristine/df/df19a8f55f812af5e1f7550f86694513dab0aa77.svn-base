package com.hm.bigdata.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.xwork.StringUtils;
import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hm.bigdata.dao.UsersDao;
import com.hm.bigdata.entity.ldap.Users;
import com.hm.bigdata.util.SpringContext;

/*
 * MyUsernamePasswordAuthenticationFilter
	attemptAuthentication
		this.getAuthenticationManager()
			ProviderManager.java
				authenticate(UsernamePasswordAuthenticationToken authRequest)
					AbstractUserDetailsAuthenticationProvider.java
						authenticate(Authentication authentication)
							P155 user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
								DaoAuthenticationProvider.java
									P86 loadUserByUsername
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	public static final String VALIDATE_CODE = "validateCode";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	
	private UsersDao usersDao;
	public UsersDao getUsersDao() {
		return usersDao;
	}
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		/**
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}**/
		
		//checkValidateCode(request);
		if(usersDao==null){
			usersDao = (UsersDao) SpringContext.getBean("usersDao");
		}
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		if(username.equals("")){
			username = AssertionHolder.getAssertion().getPrincipal().getName();
		}
		Users users = this.usersDao.findByName(username);
		if(password.equals("")){
			password = users.getPassword();
		}
		//验证用户账号与密码是否对应
		username = username.trim();
		System.out.println("---------------------------");
		System.out.println(username);
		System.out.println(password);
		System.out.println("---------------------------");
		
		
		if(users == null || !users.getPassword().equals(password)) {
	/*
	 
		if (forwardToDestination) {
            request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        } else {
            HttpSession session = request.getSession(false);

            if (session != null || allowSessionCreation) {
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
            }
        }
	 */
			throw new AuthenticationServiceException("password or username is notEquals"); 
		}
		
		//UsernamePasswordAuthenticationToken实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		// Place the last username attempted into HttpSession for views
		
		// 允许子类设置详细属性
        setDetails(request, authRequest);
		
        // 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	protected void checkValidateCode(HttpServletRequest request) { 
		HttpSession session = request.getSession();
		
	    String sessionValidateCode = obtainSessionValidateCode(session); 
	    //让上一次的验证码失效
	    session.setAttribute(VALIDATE_CODE, null);
	    String validateCodeParameter = obtainValidateCodeParameter(request);  
	    if (StringUtils.isEmpty(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {  
	        throw new AuthenticationServiceException("validateCode.notEquals");  
	    }  
	}
	
	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}
	
	
}
