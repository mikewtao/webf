package org.eclipse.framework.webf.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticResourceInteceptor extends Interceptor {

	@Override
	public boolean doAction(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		if (uri.endsWith(".jsp") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg")
				|| uri.endsWith(".png")) {// 过滤静态资源
			return true;
		}
		return false;

	}

}
