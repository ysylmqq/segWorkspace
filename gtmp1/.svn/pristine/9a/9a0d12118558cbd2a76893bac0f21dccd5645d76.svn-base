package com.chinaGPS.gtmp.action.interceptor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;

import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.OperationLog;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * @author zfy 操作日志拦截
 */
@SuppressWarnings("serial")
public class OpLogInterceptor extends MethodFilterInterceptor {
	private Logger logger = Logger.getLogger("OPERATION");

	public void init() {
	}
	  public  String getIpAddr(HttpServletRequest request)  {
		            String ip  =  request.getHeader( " x-forwarded-for " );
		             if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
		                ip  =  request.getHeader( " Proxy-Client-IP " );
		            } 
		             if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
		               ip  =  request.getHeader( " WL-Proxy-Client-IP " );
		            } 
		             if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
		               ip  =  request.getRemoteAddr();
		          } 
		            return  ip;
		       }
	@Override
	public String doIntercept(ActionInvocation ai) throws Exception {
		boolean isLogged = true;
		String result = ai.invoke();
		try {
			
			String method = ai.getProxy().getMethod();

			HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session=request.getSession(false);
            if(session!=null){
			String loginname = (String) request.getSession().getAttribute(
				Constants.USER_NAME);
			
			Long userId = request.getSession().getAttribute(Constants.USER_ID)!=null? (Long)request.getSession().getAttribute(Constants.USER_ID):0;
			//记录对象
			String message=null;
			if (StringUtils.isNotBlank(loginname)) {
				Method m = ai.getProxy().getAction().getClass().getMethod(method);
				OperationLog lo = null;
				if (m != null&& (lo = m.getAnnotation(OperationLog.class)) != null) {
					Map<String, Object> parameters = ai.getInvocationContext()
					.getParameters();
			//记录参数
			StringBuffer p = new StringBuffer();
           //请求参数
			for (String mp : parameters.keySet()) {
				Object obj = parameters.get(mp);
				if (obj instanceof String[]) {
					String[] s = (String[]) obj;
					p.append("参数名称" + mp + " ");
					Pattern p1= Pattern.compile("\\w*p\\w*w\\w*d");
					Matcher mathcer=p1.matcher(mp);
					if(!mathcer.find()){
						for (int i = 0; i < s.length; i++) {
							p.append("第" + (i+1) + "个值:" + s[i] + " ");
						}
					}
					else{
						p.append("值:***** ");
					}
					
				}
			}
					message = "参数列表:" + p;
					if(message.length()>998){//数据库最大长度是1000,最好短一点
						message=message.substring(0, 998);
					}
					//isLogged = true;
					/*String syslogMode = ServletActionContext.getServletContext()
					.getInitParameter("OplogMode");*/
			if (isLogged) {
				MDC.put("logId",UUID.randomUUID().toString());
				MDC.put("userId",(Long)userId);
				MDC.put("remoteIp",getIpAddr(request));
				MDC.put("logType", lo.description());
				logger.info(message);
			}
				} 
				
			}
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}