package com.gboss.cas;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;

// Referenced classes of package org.jasig.cas.client.util:
//			AssertionHolder

public final class AssertionThreadLocalFilter
	implements Filter
{

	public AssertionThreadLocalFilter()
	{
	}

	@Override
	public void init(FilterConfig filterconfig)
		throws ServletException
	{
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException
	{
		Assertion assertion;
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpSession session = request.getSession(false);
		assertion = (Assertion)(session == null ? null : session.getAttribute("_const_cas_assertion_"));
		AssertionHolder.setAssertion(assertion);
		filterChain.doFilter(servletRequest, servletResponse);
		//AssertionHolder.clear();
	}

	@Override
	public void destroy()
	{
	}
}
