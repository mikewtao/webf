package com.github.mikewtao.webf.demo.pojo;

public class User {
	private String name;
	private int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void sayhello(){
		System.out.println("hello user");
	}

}
