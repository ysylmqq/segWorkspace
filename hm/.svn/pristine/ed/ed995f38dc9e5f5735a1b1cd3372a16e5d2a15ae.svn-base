package com.gboss.comm.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
import ldap.objectClasses.CommonRole;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.jasig.cas.client.authentication.AttributePrincipal;



import com.gboss.comm.SystemConst;
import com.gboss.pojo.Operatelog;
import com.gboss.service.OperatelogService;
import com.gboss.util.SpringContext;
import com.gboss.util.UrlUtils;

public class SessionFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Object oper = httpRequest.getSession().getAttribute(SystemConst.ACCOUNT_USER);
		String url = httpRequest.getRequestURL().toString();
		if(url.indexOf(";jsessionid")!=-1 || oper==null){
			String cleanUrl = UrlUtils.cleanupUrl(url);
			httpResponse.sendRedirect(cleanUrl);
		}
		AttributePrincipal principal = (AttributePrincipal)httpRequest.getUserPrincipal();
		String username =""; 
		if(principal!=null){
			username = principal.getName();
		}else{
			chain.doFilter(request, response);
			return;
		}
		if(oper != null){
			chain.doFilter(request, response);
			return;
		}
		OpenLdap ldap = OpenLdapManager.getInstance();
		CommonOperator operator = ldap.getOperator(username);
		/**
		//写LDAP日志
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String opertime = sdf.format(new Date());
		CommonLog commonlog = new CommonLog();
		commonlog.setLogloginname(operator.getLoginname());
		commonlog.setOpname(operator.getOpname());
		commonlog.setOperobject("4");
		commonlog.setOpertime(opertime);
		commonlog.setOpertype("登录");
		commonlog.setMessage("从sos门店系统登录,"+operator.toString());
		ldap.add(commonlog);
		**/
		String companyId = ldap.getCompanyByOrgId(operator.getCompanynos().get(0)).getCompanyno();
	    //特殊处理掉属于公司总部或下属企业这个虚拟机构的
		if(companyId.equals("2")||companyId.equals("3")){
	    	companyId = "101";
	    	//有地方要特殊处理用到，还是需要判断是否是总部
			httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ISHQ, "true");
	    }else{
	    	httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ISHQ, "false");
	    }
		CommonCompany company = ldap.getCompanyById(companyId);
		//写数据库日志,因为要做判断所以不通过注释方式写了
		Operatelog log = new Operatelog();
		if(operator.getOpid()!=null){
			log.setUser_id(Long.valueOf(operator.getOpid()));
		}
		log.setUser_name(operator.getOpname());
		log.setModel_id(20000);
		log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_LOGIN));
		log.setSubco_no(Long.valueOf(companyId));
		log.setRemark(operator.getOpname()+"用户登录空中升级系统");
		OperatelogService operatelogService = (OperatelogService) SpringContext.getBean("OperatelogService");
		operatelogService.add(log);
		//写Session
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_USER, operator);
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ID, operator.getOpid());
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_USERNAME, operator.getOpname());
		//如果一个用户属于多个机构就不好办了，所以暂时屏蔽掉一个用户可能属于多个机构的情况
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ORGID, operator.getCompanynos().get(0));
	    httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYID, companyId);
	    httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYCODE, company.getCompanycode());
	    httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYNAME, company.getCompanyname());
	    List<CommonRole> roles = ldap.getRole(operator.getOpid(), SystemConst.SYSTEMID);
	    if(roles!=null){
	    	httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ROLEIDS, operator.getRoleid());
	    }
	    chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
