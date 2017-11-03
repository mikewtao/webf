
package com.github.mikewtao.webf.mvc;


public class WebController {
	private Object clazz;//处理类
    private WebHandler handler;
    
    
	public WebHandler getHandler() {
		return handler;
	}

	public void setHandler(WebHandler handler) {
		this.handler = handler;
	}

	public Object getClazz() {
		return clazz;
	}

	public void setClazz(Object clazz) {
		this.clazz = clazz;
	}


}
