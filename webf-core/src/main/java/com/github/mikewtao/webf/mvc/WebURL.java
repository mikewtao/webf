package com.github.mikewtao.webf.mvc;

import java.util.HashMap;
import java.util.Map;

public class WebURL{
	private String key;
	private Map<String, String> reqParams = new HashMap<>();// 请求参数
	private String methdodType;// 请求方式
	private String requrl;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, String> getReqParams() {
		return reqParams;
	}

	public void setReqParams(Map<String, String> reqParams) {
		this.reqParams = reqParams;
	}

	public String getMethdodType() {
		return methdodType;
	}

	public void setMethdodType(String methdodType) {
		this.methdodType = methdodType;
	}

	public String getRequrl() {
		return requrl;
	}

	public void setRequrl(String requrl) {
		this.requrl = requrl;
	}
	
	

}
