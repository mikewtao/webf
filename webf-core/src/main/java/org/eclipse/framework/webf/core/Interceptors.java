package org.eclipse.framework.webf.core;


import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptors {
	
	public static  Map<String, List<Interceptor>> interceptorMap = new ConcurrentHashMap<String,List<Interceptor>>();
	
	public void addInterceptor(String url,List<Interceptor> e) {
		interceptorMap.put(url,e);
	}
	
	public boolean excuteInterceptor(String url,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Interceptor> interceptors=interceptorMap.get(url);
		for(Interceptor in:interceptors){
			Method method=in.getClass().getMethod("doAction",HttpServletRequest.class,HttpServletResponse.class);
			boolean b=(boolean) method.invoke(in,request,response);
			if(!b){
				return false;
			}
		}
		return true;
	}
	
	
}
