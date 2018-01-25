package com.hm.bigdata.comm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonLog;
import ldap.objectClasses.CommonOperator;
import ldap.objectClasses.CommonRole;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hm.bigdata.entity.po.sys.Operatelog;
import com.hm.bigdata.service.OperatelogService;
import com.hm.bigdata.util.SpringContext;
import com.hm.bigdata.util.StringUtils;
import com.hm.bigdata.util.UrlUtils;

/**
 * @Package:com.chinagps.smh.comm
 * @ClassName:SessionInterceptor
 * @Description:session登录的拦截器 等效于SessionInterceptor,但是SessionFilter暂未用到,在web.xml中已注释
 * @author:zfy
 * @date:2014-4-15 上午9:50:21
 */
public class SessionInterceptor implements HandlerInterceptor{
	private Logger logger = Logger.getLogger(SessionInterceptor.class.getName()); 
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		/*String url = httpRequest.getRequestURL().toString();
		if(url.indexOf(";jsessionid")!=-1){
			String cleanUrl = UrlUtils.cleanupUrl(url);
			httpResponse.sendRedirect(cleanUrl);
		}*/
		
		AttributePrincipal principal = (AttributePrincipal)httpRequest.getUserPrincipal();
		String username =""; 
		if(principal!=null){
			username = principal.getName();
		}else{
			return true;
		}
		Object oper = httpRequest.getSession().getAttribute(SystemConst.ACCOUNT_USER);
		if(oper != null){
			return true;
		}
		OpenLdap ldap = OpenLdapManager.getInstance();
		CommonOperator operator = ldap.getOperator(username);
		//写LDAP日志
/*		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String opertime = sdf.format(new Date());
		CommonLog commonlog = new CommonLog();
		commonlog.setLogloginname(operator.getLoginname());
		commonlog.setOpname(operator.getOpname());
		commonlog.setOperobject("4");
		commonlog.setOpertime(opertime);
		commonlog.setOpertype("登录");
		commonlog.setMessage("从fee计费系统登录,"+operator.toString());
		ldap.add(commonlog);*/
		
		//写Session
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_USER, operator);
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ID, operator.getOpid());
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_USERNAME, operator.getOpname());
		//如果一个用户属于多个机构就不好办了，所以暂时屏蔽掉一个用户可能属于多个机构的情况
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ORGID, operator.getCompanynos().get(0));
	    String companyId = ldap.getCompanyByOrgId(operator.getCompanynos().get(0)).getCompanyno();
	    httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYID, companyId);
	    List<CommonRole> roles = ldap.getRole(operator.getOpid(), SystemConst.SYSTEMID);
	    if(roles!=null){
	    	httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ROLEIDS, operator.getRoleid());
	    }
	    
	  //写数据库日志,因为要做判断所以不通过注释方式写了
  		Operatelog log = new Operatelog();
  		if(operator.getOpid()!=null){
  			log.setUser_id(Long.valueOf(operator.getOpid()));
  			log.setUser_name(operator.getOpname());
  		}
  		if(StringUtils.isNotBlank(companyId)){
  			log.setSubco_no(Long.valueOf(companyId));
  		}
  		log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_LOGIN));
  		log.setModel_id(Integer.valueOf(SystemConst.OPERATELOG_LOGIN_VALUE));
  		log.setRemark(operator.getOpname()+"用户登录");
  		try {
  			OperatelogService operatelogService = (OperatelogService) SpringContext.getBean("operatelogService");
  			operatelogService.add(log);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
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
	}
}

