package com.github.mikewtao.webf.mvc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.mikewtao.webf.InterceptorAdapter;
import com.github.mikewtao.webf.InterceptorManager;
import com.github.mikewtao.webf.JsonViewResolver;
import com.github.mikewtao.webf.ViewResolve;
import com.github.mikewtao.webf.WebfConf;
import com.github.mikewtao.webf.XmlViewResolver;
import com.github.mikewtao.webf.annotation.JSON;
import com.github.mikewtao.webf.annotation.XML;
import com.github.mikewtao.webf.exception.InitializedException;
import com.github.mikewtao.webf.utils.JavaassitUtil;

public class WebFilter implements Filter {
	private static final String urlpath = "/**";

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String urlpath = getRequestUri(request);
		Controller params = WebfConf.urlHandlerMap.get(urlpath);
		if (urlpath.endsWith(".jsp") || urlpath.endsWith(".css") || urlpath.endsWith(".js") ||urlpath.endsWith(".jpg")
				|| urlpath.endsWith(".png")||urlpath.endsWith(".html")) {// 过滤静态资源
			return ;
		}
		if (!doInterceptor(urlpath, request, response)) {
			return;
		}
		if (params != null) {
			try {
				Object obj = params.getClazz();// module
				Method m = params.getMethod();// handler
				Class<?>[] paramClzz = m.getParameterTypes();
				String[] names = JavaassitUtil.getParams(obj.getClass(), m.getName());// 获取方法参数名称
				ParamInjector paramInjector = new ParamInjector(request, response, paramClzz, names);
				List<Object> methodParams = paramInjector.execInject();// 注入参数
				fowardView(request, response, obj, m, methodParams);
			} catch (Exception e) {
				e.printStackTrace();
				doExceptionInterceptor(urlpath, request, response);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);// 返回404
		}
	}

	private String getRequestUri(HttpServletRequest request) {
		String uri = request.getRequestURI();
		return uri.replaceAll(WebfConf.contextPath, "");
	}

	public boolean doInterceptor(String uri, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<InterceptorAdapter> alllist = InterceptorManager.interceptorMap.get(urlpath);// 是否有全局拦截器
			if (alllist != null) {
				if (alllist.size() != 0) {
					if (!InterceptorManager.excuteInterceptor(urlpath, request, response)) {
						return false;
					}
				}
			}
			List<InterceptorAdapter> list = InterceptorManager.interceptorMap.get(uri);// 当前请求路径是否有拦截器
			if (list == null || list.size() == 0) {
				return true;
			}
			return InterceptorManager.excuteInterceptor(uri, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void doExceptionInterceptor(String uri, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<InterceptorAdapter> alllist = InterceptorManager.interceptorMap.get(urlpath);// 是否有全局拦截器
			if (alllist != null) {
				if (alllist.size() != 0) {
					InterceptorManager.excuteInterceptor(urlpath, request, response);
					return;
				}
			}
			List<InterceptorAdapter> list = InterceptorManager.interceptorMap.get(uri);// 当前请求路径是否有拦截器
			if (list == null || list.size() == 0) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			InterceptorManager.excuteInterceptor(uri, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		WebfConf.clearAllConfig();// 清理所有配置
	}

	private void fowardView(HttpServletRequest request, HttpServletResponse response, Object obj, Method m,
			List<Object> methodParams) throws Exception {
		ViewResolve viewResolve = null;
		if (m.getReturnType() == void.class) {// 没有返回值
			m.invoke(obj, methodParams.toArray());
		} else if (m.getReturnType() == String.class) {// 返回string 试图
			String path = (String) m.invoke(obj, methodParams.toArray());
			path = "../" + path;
			request.getRequestDispatcher(path).forward(request, response);
		} else {
			Object jobj = m.invoke(obj, methodParams.toArray());
			JSON json = m.getAnnotation(JSON.class);
			XML xml = m.getAnnotation(XML.class);
			String data = "";
			if (json != null) {// 需要转换成json格式
				viewResolve = new JsonViewResolver();
				data = (String) viewResolve.renderView(jobj, json.DateType());
			} else if (xml != null) {// 需要转换成xml格式 使用jaxb实现
				try {
					viewResolve = new XmlViewResolver();
					data = (String) viewResolve.renderView(jobj, m.getReturnType());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			viewResolve.sendData(response, data);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			String contextPath = filterConfig.getServletContext().getContextPath();
			WebfConf.initPath(contextPath);
			WebfStarter.getWebfStarter().initWebf();
		} catch (Exception e) {
			throw new InitializedException("webf Initialized Error", e);
		}
	}

}
