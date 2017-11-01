package com.github.mikewtao.webf.demo.pojo;

public class People {
    private ExpressDetail detail;
    
	public ExpressDetail getDetail() {
		return detail;
	}

	public void setDetail(ExpressDetail detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "People [detail=" + detail + "]";
	}
   
}
