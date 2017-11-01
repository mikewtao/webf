package com.github.framework.webf;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.framework.webf.annotation.Interceptor;
import com.github.framework.webf.utils.webfUtil;

public class InterceptorManager {

	public static Map<String, List<InterceptorAdapter>> interceptorMap = new ConcurrentHashMap<String, List<InterceptorAdapter>>();

	public void addInterceptor(String url, List<InterceptorAdapter> e) {
		interceptorMap.put(url, e);
	}

	public static boolean excuteInterceptor(String url, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<InterceptorAdapter> interceptors = interceptorMap.get(url);
		for (InterceptorAdapter in : interceptors) {
			Method method = in.getClass().getMethod("preHandle", HttpServletRequest.class, HttpServletResponse.class);
			boolean b = (boolean) method.invoke(in, request, response);
			if (!b) {
				return false;
			}
		}
		return true;
	}

	public static void excuteExceptionInterceptor(String url, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<InterceptorAdapter> interceptors = interceptorMap.get(url);
		for (InterceptorAdapter in : interceptors) {
			Method method = in.getClass().getMethod("handleException", HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(in, request, response);
		}
	}
	public static void handleInterceptor(Interceptor interceptor, String clstr) {
		String uri=interceptor.url();
		InterceptorAdapter interapt=(InterceptorAdapter) webfUtil.initClass(clstr);
		List<InterceptorAdapter> list=InterceptorManager.interceptorMap.get(uri);
		if(list==null){
			list=new ArrayList<>();
		}
		list.add(interapt);
		InterceptorManager.interceptorMap.put(uri, list);
	}

}
