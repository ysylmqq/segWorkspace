package com.chinaGPS.gtmp.action.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinaGPS.gtmp.util.Constants;

public class CheckLoginFilter implements Filter {   
	public void destroy() {    
	}    
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {    
		HttpServletRequest request = (HttpServletRequest) req; 
		HttpServletResponse response = (HttpServletResponse) res; 
		 String uri=request.getRequestURI();
		 
		HttpSession session = request.getSession();
		/*if(session.getAttribute(Constants.USER_INFO)==null){
			response.sendRedirect(request.getContextPath()+"/login.action");
		}*/ 
		if(session.getAttribute(Constants.USER_INFO)==null&&!uri.contains("login.action")&&!uri.contains("login.jsp")){
		    request.getRequestDispatcher("/jsp/redirect_login.jsp").forward(request, response);
		}else{
			//判断是否有权限
			//String uri=request.getRequestURI();
			//String path=uri.substring(uri.lastIndexOf("/"),uri.lastIndexOf("."));
			//判断session是否超时
			/*if(request.getSession().isNew()){//如果session实效
				request.getSession().invalidate();
				response.sendRedirect("login.action");
			}else{
				chain.doFilter(request, response);  
			}*/
			chain.doFilter(request, response);  
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}  
 
}
