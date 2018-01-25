package com.hm.bigdata.cas;
/**
 * @Package:com.gboss.cas
 * @ClassName:Cas20ProxyTicketValidator
 * @Description:TODO
 * @author:肖克
 * @date:Apr 2, 2013 9:31:43 AM
 */
import java.util.List;
import org.jasig.cas.client.util.XmlUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.InvalidProxyChainTicketValidationException;
import org.jasig.cas.client.validation.ProxyList;
import org.jasig.cas.client.validation.TicketValidationException;

public class Cas20ProxyTicketValidator extends Cas20ServiceTicketValidator
{
  private boolean acceptAnyProxy;
  private ProxyList allowedProxyChains = new ProxyList();

  public Cas20ProxyTicketValidator(String casServerUrlPrefix) {
    super(casServerUrlPrefix);
  }

  public ProxyList getAllowedProxyChains() {
    return this.allowedProxyChains;
  }

  protected String getUrlSuffix() {
    return "proxyValidate";
  }

  protected void customParseResponse(String response, Assertion assertion) throws TicketValidationException {
    List proxies = XmlUtils.getTextForElements(response, "proxy");
    String[] proxiedList = (String[])(String[])proxies.toArray(new String[proxies.size()]);

    if ((proxies == null) || (proxies.isEmpty()) || (this.acceptAnyProxy)) {
      return;
    }

    if (this.allowedProxyChains.contains(proxiedList)) {
      return;
    }

    throw new InvalidProxyChainTicketValidationException("Invalid proxy chain: " + proxies.toString());
  }

  public void setAcceptAnyProxy(boolean acceptAnyProxy) {
    this.acceptAnyProxy = acceptAnyProxy;
  }

  public void setAllowedProxyChains(ProxyList allowedProxyChains) {
    this.allowedProxyChains = allowedProxyChains;
  }
}
