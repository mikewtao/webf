package com.github.framework.webf;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * handler 拦截器 
 *
 */
public interface InterceptorAdapter {
  
    /**
     * 处理请求之前
     */
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response);
	
	/**
	 *处理请求出现异常
	 */
	public void handleException(HttpServletRequest request,HttpServletResponse response);
    
}
