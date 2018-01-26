package com.chinaGPS.gtmp.action.interceptor;

import java.util.Map;

import com.chinaGPS.gtmp.util.Constants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 登录检验拦截器。执行所有action时，执行此拦截器，如果session无数据，返回登录界面
 * @author xk
 */
public class CheckLoginInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -8765821994533966677L;

	public String intercept(ActionInvocation ai) throws Exception {
		Map<String, Object> session = ai.getInvocationContext().getSession();
		Object obj=session.get(Constants.USER_INFO);
		if(obj==null){
		    return Action.LOGIN;
		}else{
        	    return ai.invoke();
		}
		
	}
}