package org.eclipse.framework.webf.core;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * url拦截器 
 *
 */
public abstract class Interceptor {
  
	public abstract boolean doAction(HttpServletRequest request,HttpServletResponse response);
    
}
