package org.eclipse.framework.webf.core.mvc;

import java.lang.reflect.Method;
/**
 * url映射处理器
 *
 */
public class UrlHandler {
	private String url;
	private Object clazz;
	private Method method;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

}
