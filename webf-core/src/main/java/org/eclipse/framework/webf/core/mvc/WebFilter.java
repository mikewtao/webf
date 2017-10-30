package org.eclipse.framework.webf.core.mvc;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.framework.webf.core.WebfConfManager;
import org.eclipse.framework.webf.core.utils.ClassScan;

public abstract class WebFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
	}

	@Override
	public void destroy() {
       WebfConfManager.clearAllConfig();//清理所有配置
	}

}
