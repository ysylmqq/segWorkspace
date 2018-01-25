package com.gboss.cas;
/**
 * @Package:com.gboss.cas
 * @ClassName:Cas20ProxyReceivingTicketValidationFilter
 * @Description:TODO
 * @author:肖克
 * @date:Apr 1, 2013 2:08:58 PM
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.jasig.cas.client.proxy.Cas20ProxyRetriever;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.AbstractTicketValidationFilter;
import com.gboss.cas.Cas20ProxyTicketValidator;
import com.gboss.cas.Cas20ServiceTicketValidator;

import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.ProxyList;
import org.jasig.cas.client.validation.ProxyListEditor;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class Cas20ProxyReceivingTicketValidationFilter extends AbstractTicketValidationFilter
{
  private static final String[] RESERVED_INIT_PARAMS = { "proxyReceptorUrl", "acceptAnyProxy", "allowedProxyChains", "casServerUrlPrefix", "proxyCallbackUrl", "renew", "exceptionOnValidationFailure", "redirectAfterValidation", "useSession", "serverName", "service", "artifactParameterName", "serviceParameterName", "encodeServiceUrl" };
  private String proxyReceptorUrl;
  private ProxyGrantingTicketStorage proxyGrantingTicketStorage = new ProxyGrantingTicketStorageImpl();
 
  private final String ExcludeFile = "ExcludeFile"; // excludeFile 列表 ，排除掉ExcludeFile文件（免登陆）
  private String[] arrExcludeFile = null;
  private String strExcludeFile;
  
  protected void initInternal(FilterConfig filterConfig) throws ServletException {
    super.initInternal(filterConfig);
    setProxyReceptorUrl(getPropertyFromInitParams(filterConfig, "proxyReceptorUrl", null));
    this.log.trace("Setting proxyReceptorUrl parameter: " + this.proxyReceptorUrl);
  }

  public void init() {
    super.init();
    if (strExcludeFile != null && strExcludeFile.trim().length() > 0) {
		arrExcludeFile = strExcludeFile.split(",");
	}
    CommonUtils.assertNotNull(this.proxyGrantingTicketStorage, "proxyGrantingTicketStorage cannot be null.");
  }

  protected final TicketValidator getTicketValidator(FilterConfig filterConfig)
  {
    String allowAnyProxy = getPropertyFromInitParams(filterConfig, "acceptAnyProxy", null);
    String allowedProxyChains = getPropertyFromInitParams(filterConfig, "allowedProxyChains", null);
    String casServerUrlPrefix = getPropertyFromInitParams(filterConfig, "casServerUrlPrefix", null);
    Cas20ServiceTicketValidator validator;
    if ((CommonUtils.isNotBlank(allowAnyProxy)) || (CommonUtils.isNotBlank(allowedProxyChains))) {
      Cas20ProxyTicketValidator v = new Cas20ProxyTicketValidator(casServerUrlPrefix);
      v.setAcceptAnyProxy(Boolean.parseBoolean(allowAnyProxy));
      v.setAllowedProxyChains(new ProxyList(constructListOfProxies(allowedProxyChains)));
      validator = v;
    } else {
      validator = new Cas20ServiceTicketValidator(casServerUrlPrefix);
    }
    validator.setProxyCallbackUrl(getPropertyFromInitParams(filterConfig, "proxyCallbackUrl", null));
    validator.setProxyGrantingTicketStorage(this.proxyGrantingTicketStorage);
    validator.setProxyRetriever(new Cas20ProxyRetriever(casServerUrlPrefix,"UTF-8"));
    validator.setRenew(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));

    Map additionalParameters = new HashMap();
    List params = Arrays.asList(RESERVED_INIT_PARAMS);

    for (Enumeration e = filterConfig.getInitParameterNames(); e.hasMoreElements(); ) {
      String s = (String)e.nextElement();

      if (!params.contains(s)) {
        additionalParameters.put(s, filterConfig.getInitParameter(s));
      }
    }

    validator.setCustomParameters(additionalParameters);

    return validator;
  }

  protected final List constructListOfProxies(String proxies) {
    if (CommonUtils.isBlank(proxies)) {
      return new ArrayList();
    }

    String[] splitProxies = proxies.split("\n");
    List items = Arrays.asList(splitProxies);
    ProxyListEditor editor = new ProxyListEditor();
    editor.setValue(items);
    return (List)editor.getValue();
  }

  protected final boolean preFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    throws IOException, ServletException
  {
    HttpServletRequest request = (HttpServletRequest)servletRequest;
    HttpServletResponse response = (HttpServletResponse)servletResponse;
    String requestUri = request.getRequestURI();

    if ((CommonUtils.isEmpty(this.proxyReceptorUrl)) || (!requestUri.endsWith(this.proxyReceptorUrl))) {
      return true;
    }
    String requestStr = request.getRequestURL().toString();
	this.log.debug("requestStr-->" + requestStr);
	PathMatcher matcher = new AntPathMatcher();
	if (arrExcludeFile != null) {
		for (String excludePath : arrExcludeFile) {
			boolean flag = matcher.match(excludePath, requestStr);
			if (!flag) {
				flag = requestStr.indexOf(excludePath) > 0;
			}
			if (flag) {
				this.log.debug("excludePath " + excludePath
						+ " pass sso authentication");
				filterChain.doFilter(request, response);
				return false;
			}
		}
	}
	
    CommonUtils.readAndRespondToProxyReceptorRequest(request, response, this.proxyGrantingTicketStorage);
    return false;
  }
  
  public final void setProxyReceptorUrl(String proxyReceptorUrl) {
    this.proxyReceptorUrl = proxyReceptorUrl;
  }

  public final void setProxyGrantingTicketStorage(ProxyGrantingTicketStorage proxyGrantingTicketStorage) {
    this.proxyGrantingTicketStorage = proxyGrantingTicketStorage;
  }
}
