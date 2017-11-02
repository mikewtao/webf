package com.github.mikewtao.webf.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mikewtao.webf.InterceptorAdapter;

public class URI {
	private String key;// 根据请求url生成的key
	private String url;// uri
	private List<InterceptorAdapter> interceptors = new ArrayList<>();// url拦截器

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<InterceptorAdapter> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(List<InterceptorAdapter> interceptors) {
		this.interceptors = interceptors;
	}

}
