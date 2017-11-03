package com.github.mikewtao.webf.mvc;

import com.github.mikewtao.webf.utils.webfUtil;

public class URI {
	private String key;// 根据请求url生成的key
	private String url;// uri
	private WebController controller;//处理器

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = webfUtil.base64Encoder(key);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public WebController getController() {
		return controller;
	}

	public void setController(WebController controller) {
		this.controller = controller;
	}
	
	

}
