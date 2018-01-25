package com.gboss.cas;

import java.io.*;
import java.util.*;
import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.jasig.cas.client.proxy.*;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;
import org.jasig.cas.client.validation.TicketValidationException;

// Referenced classes of package org.jasig.cas.client.validation:
//			AbstractCasProtocolUrlBasedTicketValidator, TicketValidationException, AssertionImpl, Assertion

public class Cas20ServiceTicketValidator extends MyAbstractCasProtocolUrlBasedTicketValidator
{

	private String proxyCallbackUrl;
	private ProxyGrantingTicketStorage proxyGrantingTicketStorage;
	private ProxyRetriever proxyRetriever;

	public Cas20ServiceTicketValidator(String casServerUrlPrefix)
	{
		super(casServerUrlPrefix);
		proxyRetriever = new Cas20ProxyRetriever(casServerUrlPrefix,"UTF-8");
	}

	protected final void populateUrlAttributeMap(Map urlParameters)
	{
		urlParameters.put("pgtUrl", encodeUrl(proxyCallbackUrl));
	}

	protected String getUrlSuffix()
	{
		return "serviceValidate";
	}

	protected final Assertion parseResponseFromServer(String response)
		throws TicketValidationException
	{
		String error = XmlUtils.getTextForElement(response, "authenticationFailure");
		if (CommonUtils.isNotBlank(error))
			throw new TicketValidationException(error);
		String principal = XmlUtils.getTextForElement(response, "user");
		String proxyGrantingTicketIou = XmlUtils.getTextForElement(response, "proxyGrantingTicket");
		String proxyGrantingTicket = proxyGrantingTicketStorage == null ? null : proxyGrantingTicketStorage.retrieve(proxyGrantingTicketIou);
		if (CommonUtils.isEmpty(principal))
			throw new TicketValidationException("No principal was found in the response from the CAS server.");
		Map attributes = extractCustomAttributes(response);
		Assertion assertion;
		if (CommonUtils.isNotBlank(proxyGrantingTicket))
		{
			org.jasig.cas.client.authentication.AttributePrincipal attributePrincipal = new AttributePrincipalImpl(principal, attributes, proxyGrantingTicket, proxyRetriever);
			assertion = new AssertionImpl(attributePrincipal);
		} else
		{
			assertion = new AssertionImpl(new AttributePrincipalImpl(principal, attributes));
		}
		customParseResponse(response, assertion);
		return assertion;
	}

	protected Map extractCustomAttributes(String xml)
	{
		int pos1 = xml.indexOf("<cas:attributes>");
		int pos2 = xml.indexOf("</cas:attributes>");
		if (pos1 == -1)
			return Collections.EMPTY_MAP;
		String attributesText = xml.substring(pos1 + 16, pos2);
		Map attributes = new HashMap();
		BufferedReader br = new BufferedReader(new StringReader(attributesText));
		List attributeNames = new ArrayList();
		try
		{
			do
			{
				String line;
				if ((line = br.readLine()) == null)
					break;
				String trimmedLine = line.trim();
				if (trimmedLine.length() > 0)
				{
					int leftPos = trimmedLine.indexOf(":");
					int rightPos = trimmedLine.indexOf(">");
					attributeNames.add(trimmedLine.substring(leftPos + 1, rightPos));
				}
			} while (true);
			br.close();
		}
		catch (IOException e) { }
		String name;
		for (Iterator iter = attributeNames.iterator(); iter.hasNext(); attributes.put(name, XmlUtils.getTextForElement(xml, name)))
			name = (String)iter.next();

		return attributes;
	}

	protected void customParseResponse(String s, Assertion assertion1)
		throws TicketValidationException
	{
	}

	public final void setProxyCallbackUrl(String proxyCallbackUrl)
	{
		this.proxyCallbackUrl = proxyCallbackUrl;
	}

	public final void setProxyGrantingTicketStorage(ProxyGrantingTicketStorage proxyGrantingTicketStorage)
	{
		this.proxyGrantingTicketStorage = proxyGrantingTicketStorage;
	}

	public final void setProxyRetriever(ProxyRetriever proxyRetriever)
	{
		this.proxyRetriever = proxyRetriever;
	}

	@Override
	protected void setDisableXmlSchemaValidation(boolean disabled) {
		// TODO Auto-generated method stub
		
	}
}
