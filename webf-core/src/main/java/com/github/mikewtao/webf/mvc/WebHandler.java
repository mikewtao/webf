package com.github.mikewtao.webf.mvc;

import java.lang.reflect.Method;

import com.github.mikewtao.webf.annotation.RequestMethod;

public class WebHandler {
	private Method method;// 处理方法
	private RequestMethod[] reqMethod;// 请求方式

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public RequestMethod[] getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(RequestMethod[] reqMethod) {
		this.reqMethod = reqMethod;
	}

	
   
	
}
