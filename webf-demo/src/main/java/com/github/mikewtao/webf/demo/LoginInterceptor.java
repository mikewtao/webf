package com.github.mikewtao.webf.demo;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.mikewtao.webf.InterceptorAdapter;
import com.github.mikewtao.webf.annotation.Interceptor;
import com.github.mikewtao.webf.utils.webfUtil;


@Interceptor(url="/**")
public class LoginInterceptor implements InterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		if (uri.endsWith(".jsp") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg")
				|| uri.endsWith(".png")||uri.endsWith(".html")) {// 过滤静态资源
			return false;
		}
		String name=(String) request.getSession().getAttribute("username");//用户认证
		if(webfUtil.isEmpty(name)){
			try {
				//response.getWriter().write("must be login");
				request.getRequestDispatcher("../login.jsp").forward(request, response);
				return false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public void handleException(HttpServletRequest request, HttpServletResponse response) {
		
		
	}

}
