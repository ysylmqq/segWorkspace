package com.gboss.cas;

import java.io.IOException;
import java.security.Principal;
import javax.servlet.*;
import javax.servlet.http.*;

import org.jasig.cas.client.validation.Assertion;

public final class HttpServletRequestWrapperFilter
	implements Filter
{
	final class CasHttpServletRequestWrapper extends HttpServletRequestWrapper
	{

		private final Principal principal;

		@Override
		public Principal getUserPrincipal()
		{
			return principal;
		}

		@Override
		public String getRemoteUser()
		{
			return principal == null ? null : principal.getName();
		}

		CasHttpServletRequestWrapper(HttpServletRequest request, Principal principal)
		{
			super(request);
			this.principal = principal;
		}
	}


	public HttpServletRequestWrapperFilter()
	{
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException
	{
		Principal principal = retrievePrincipalFromSessionOrRequest(servletRequest);
		filterChain.doFilter(new CasHttpServletRequestWrapper((HttpServletRequest)servletRequest, principal), servletResponse);
	}

	protected Principal retrievePrincipalFromSessionOrRequest(ServletRequest servletRequest)
	{
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpSession session = request.getSession(false);
		Assertion assertion = (Assertion)(session != null ? session.getAttribute("_const_cas_assertion_") : request.getAttribute("_const_cas_assertion_"));
		return assertion != null ? assertion.getPrincipal() : null;
	}

	@Override
	public void init(FilterConfig filterconfig)
		throws ServletException
	{
	}
}
