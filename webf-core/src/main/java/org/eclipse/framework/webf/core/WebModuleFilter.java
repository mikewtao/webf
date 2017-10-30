package org.eclipse.framework.webf.core;


import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.eclipse.framework.webf.core.exception.InitializedException;
import org.eclipse.framework.webf.core.mvc.WebFilter;



public class WebModuleFilter extends WebFilter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			WebfStarter.getWebfStarter().initWebf();
		} catch (Exception e) {
			throw new InitializedException("webf Initialized Error",e);
		}
	}
}
