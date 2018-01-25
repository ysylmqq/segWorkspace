package bhz.com.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class PageFilter<E>  implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        
        PageSelect<Object> pageSelect = new PageSelect<Object>();
        String pageNo =  request.getParameter("pageNo");
		String pageSize =  request.getParameter("pageSize");
		if(StringUtils.isNotBlank(pageNo)){
			pageSelect.setPageNo(Integer.valueOf(pageNo));
		}
		if(StringUtils.isNotBlank(pageSize)){
			pageSelect.setPageSize(Integer.valueOf(pageSize));
		}
		
		Map<String,String> filter = new HashMap<String,String>();

		Enumeration<String> enumeration = request.getParameterNames();
		for(Enumeration<String> e = enumeration; e.hasMoreElements();){
			String  key=e.nextElement().toString();
			String	value=request.getParameter(key);
			if( !"".equals(key) ){
				filter.put(key, value);
			}
		}
		pageSelect.setFilter(filter);
        request.setAttribute("pageSelect",pageSelect);
        chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
