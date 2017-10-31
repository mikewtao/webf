package org.eclipse.framework.webf.core;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public abstract class ViewResolve {
	
	public abstract Object renderView(Object obj, Object param);

	public void sendData(HttpServletResponse resp, String data) {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache, must-revalidate");
		resp.setHeader("Pragma", "no-cache");
		try {
			resp.getWriter().write(data);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
