package com.hm.bigdata.cas;
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
import org.jasig.cas.client.proxy.Cas20ProxyRetriever;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.AbstractTicketValidationFilter;

import org.jasig.cas.client.validation.ProxyList;
import org.jasig.cas.client.validation.ProxyListEditor;
import org.jasig.cas.client.validation.TicketValidator;

public class Cas20ProxyReceivingTicketValidationFilter extends AbstractTicketValidationFilter
{
  private static final String[] RESERVED_INIT_PARAMS = { "proxyReceptorUrl", "acceptAnyProxy", "allowedProxyChains", "casServerUrlPrefix", "proxyCallbackUrl", "renew", "exceptionOnValidationFailure", "redirectAfterValidation", "useSession", "serverName", "service", "artifactParameterName", "serviceParameterName", "encodeServiceUrl" };
  private String proxyReceptorUrl;
  private ProxyGrantingTicketStorage proxyGrantingTicketStorage = new ProxyGrantingTicketStorageImpl();

  protected void initInternal(FilterConfig filterConfig) throws ServletException {
    super.initInternal(filterConfig);
    setProxyReceptorUrl(getPropertyFromInitParams(filterConfig, "proxyReceptorUrl", null));
    this.log.trace("Setting proxyReceptorUrl parameter: " + this.proxyReceptorUrl);
  }

  public void init() {
    super.init();
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
