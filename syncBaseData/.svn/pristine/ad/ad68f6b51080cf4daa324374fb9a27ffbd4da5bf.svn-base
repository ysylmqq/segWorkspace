package com.gboss.service.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.Operatelog;
import com.gboss.service.OperatelogService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.StringUtils;

@Aspect
@Service("OperatelogService")
@Transactional(value="txManager")
public class OperatelogServiceImpl extends BaseServiceImpl implements OperatelogService {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(OperatelogServiceImpl.class);
	
	   //@Before("execution(* org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter.handle(..))")
	   //@Before("execution(* com.*.*.controller..*.*(..))")	
	   public void logAll(JoinPoint point)throws Throwable
		{
	        //System.out.println("打印========================");
	    }
		
	    //@After("execution(* com.*.*.controller..*.*(..))")
		public void after()
		{
	        //System.out.println("after");
	    }
		
		//方法执行的前后调用 
	    @Around("execution(* com.gboss.controller.*.*(..))")
	    public Object around(ProceedingJoinPoint point) throws Throwable
	    {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getRequest();
			//String ip=getIpAddress(request);
			//ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			Object object = point.proceed();
			Operatelog  operatelog=getMthodRemark(point);
			if(operatelog!=null){
				Object userNameObj = request.getSession().getAttribute(SystemConst.ACCOUNT_USERNAME);
				Object userIdObj = request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
				//分公司ID
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				
				String userName="test";
				Long userId=1l;
				if(userNameObj!=null)
				{
					userName = userNameObj.toString();
					userId=Long.valueOf(userIdObj.toString());
				}
				else
				{
					userName="匿名用户";
				}
				//String monthName = point.getSignature().getName();
				String packages = point.getThis().getClass().getName();
				if (packages.indexOf("$$EnhancerByCGLIB$$") > -1) 
				{  //如果是CGLIB动态生成的类
			         try {
			        	 packages = packages.substring(0,packages.indexOf("$$"));
			         } catch (Exception ex) {
			        	 ex.printStackTrace();
			         }
			    }
				StringBuffer params = new StringBuffer();
				Object[] method_param = null;

				try {
					method_param = point.getArgs();	//获取方法参数 
					if(method_param!=null && method_param.length>0){
						for (int i = 0; i < method_param.length; i++) {
							if(method_param[i] instanceof HttpServletRequest ||method_param[i] instanceof HttpServletResponse){
								
							}else{
								if(method_param!=null){
									String jsonStr=JsonUtil.oToJ(method_param[i]);
									if(StringUtils.isNotBlank(jsonStr) && !"null".equals(jsonStr)){
										params.append(jsonStr);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(),e);
					throw e;
				}
		         
		        operatelog.setRemark(operatelog.getRemark()+",参数:"+params.toString());
		        operatelog.setUser_id(userId);
		        operatelog.setUser_name(userName);
		        operatelog.setSubco_no(Long.valueOf(companyId));
		        save(operatelog);
			}
	        return object;
	    }
	      
	    //方法运行出现异常时调用  
	    @AfterThrowing(pointcut = "execution(* com.gboss.controller.*.*(..))",throwing = "ex")
	    public void afterThrowing(Exception ex)
	    {
	        System.out.println("afterThrowing");
	        System.out.println(ex);
	    }
	    
	    // 获取方法的中文备注____用于记录用户的操作日志描述 
	    public static Operatelog getMthodRemark(ProceedingJoinPoint joinPoint)  
	            throws Exception 
	    {
	        String targetName = joinPoint.getTarget().getClass().getName();
	        String methodName = joinPoint.getSignature().getName();
	        Object[] arguments = joinPoint.getArgs();
	  
	        Class targetClass = Class.forName(targetName);
	        Method[] method = targetClass.getMethods();
	        String methode = "";
	        Operatelog operatelog=new Operatelog();
	        for (Method m : method)
	        {
	            if (m.getName().equals(methodName)) 
	            {
	                Class[] tmpCs = m.getParameterTypes();
	                if (tmpCs.length == arguments.length)
	                {
	                    LogOperation methodCache = m.getAnnotation(LogOperation.class);
	                    if(methodCache!=null)
	                    {
	                    	operatelog.setRemark( methodCache.description());
	                    	operatelog.setModel_id(methodCache.model_id());
	                    	operatelog.setOp_type(methodCache.type());
	                    }else{
	                    	return null;
	                    }
	                    break;
	                }
	            }
	        }
	        return operatelog;  
	    }  
	    
    public String getIpAddress(HttpServletRequest request)
    {
    	String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	  ip = request.getHeader("http_client_ip");  
    	}  
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	  ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
    	}  
    	if (ip != null && ip.indexOf(",") != -1) {  
		  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
		} 
        if("0:0:0:0:0:0:0:1".equals(ip))
        {
        	ip="127.0.0.1";
        }
        return ip;
    }
	
	 
	public void add(Operatelog operatelog) {
		try {
			save(operatelog);
		} catch (Exception e) {
			SystemConst.E_LOG.error("保存Operatelog出错",e);
		}
	}

}
