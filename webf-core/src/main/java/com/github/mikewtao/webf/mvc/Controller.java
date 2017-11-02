
package com.github.mikewtao.webf.mvc;

import java.lang.reflect.Method;

public class Controller {
	private String key;
	private Object clazz;//处理类
	private Method method;//处理方法
    private String reqType;//请求方式
    private String returnType;//返回值
    
    
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getClazz() {
		return clazz;
	}

	public void setClazz(Object clazz) {
		this.clazz = clazz;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	
	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Override
	public String toString() {
		return "RequestParam [clazz=" + clazz + ", method=" + method.getName() + "]";
	}

}
