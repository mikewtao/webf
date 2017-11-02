package com.github.mikewtao.webf.mvc;

/**
 * one to one '/user/login ---com.example.user.LoginController.login()'
 */
public class URIMapping {
	private URI uri;// url
	private Controller controller;// 处理器

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	

}
