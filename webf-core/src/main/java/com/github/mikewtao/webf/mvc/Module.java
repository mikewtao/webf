package com.github.mikewtao.webf.mvc;

import java.util.ArrayList;
import java.util.List;

/**
 * module --- uri---- controller --
 *
 */
public class Module {
	private String key;
	private String name;
	private List<URI> urilist = new ArrayList<URI>();
	private List<Controller> controllerlist = new ArrayList<>();
	private String desc;

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<URI> getUrilist() {
		return urilist;
	}

	public void setUrilist(List<URI> urilist) {
		this.urilist = urilist;
	}

	public List<Controller> getControllerlist() {
		return controllerlist;
	}

	public void setControllerlist(List<Controller> controllerlist) {
		this.controllerlist = controllerlist;
	}

}
