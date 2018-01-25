package com.hm.bigdata.comm.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.hm.bigdata.comm.SystemConst;
import com.hm.bigdata.comm.SystemException;
import com.hm.bigdata.entity.po.sys.Operatelog;
import com.hm.bigdata.service.OperatelogService;
import com.hm.bigdata.util.SpringContext;
import com.hm.bigdata.util.UrlUtils;

/**
 * @Package:com.chinagps.fee.comm.filter
 * @ClassName:SessionFilter
 * @Description:登录拦截器 等效于SessionFilter,但是SessionFilter暂未用到,在web.xml中已注释
 * @author:zfy
 * @date:2014-6-5 上午9:06:07
 */
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
		if(url.indexOf(";jsessionid")!=-1 || oper==null){//清楚浏览器url尾巴
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
		//写LDAP日志
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String opertime = sdf.format(new Date());
		/*CommonLog commonlog = new CommonLog();
		commonlog.setLogloginname(operator.getLoginname());
		commonlog.setOpname(operator.getOpname());
		commonlog.setOperobject("4");
		commonlog.setOpertime(opertime);
		commonlog.setOpertype("登录");
		commonlog.setMessage("从fee计费系统登录,"+operator.toString());
		ldap.add(commonlog);*/
		//写数据库日志,因为要做判断所以不通过注释方式写了
		Operatelog log = new Operatelog();
		if(operator.getOpid()!=null){
			log.setUser_id(Long.valueOf(operator.getOpid()));
		}
		log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_LOGIN));
		log.setRemark(operator.getOpname()+"用户登录");
		OperatelogService operatelogService = (OperatelogService) SpringContext.getBean("operatelogService");
		try {
			operatelogService.add(log);
		} catch (SystemException e) {
			e.printStackTrace();
		}
			
		//写Session
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_USER, operator);
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ID, operator.getOpid());
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_USERNAME, operator.getOpname());
		//如果一个用户属于多个机构就不好办了，所以暂时屏蔽掉一个用户可能属于多个机构的情况
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ORGID, operator.getCompanynos().get(0));
		String companyId = ldap.getCompanyByOrgId(operator.getCompanynos().get(0)).getCompanyno();
	    //特殊处理掉属于公司总部或下属企业这个虚拟机构的
		if("2".equals(companyId)||"3".equals(companyId)){
	    	companyId = "101";
	    }
	    CommonCompany company = ldap.getCompanyById(companyId);
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
