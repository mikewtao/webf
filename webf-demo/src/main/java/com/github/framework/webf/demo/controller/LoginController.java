package com.github.framework.webf.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.framework.webf.annotation.AutoFind;
import com.github.framework.webf.annotation.Handler;
import com.github.framework.webf.annotation.JSON;
import com.github.framework.webf.annotation.Module;
import com.github.framework.webf.demo.pojo.ExpressDetail;
import com.github.framework.webf.demo.service.LoginService;


@Module(name = "user",desc="用户模块")
public class LoginController {
	
	@AutoFind
	private LoginService loginService;
	

	@Handler(value = "logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		return "WEB-INF/jsp/test.html";
	}

	@JSON
	@Handler(value = "getStudent")
	public List<Map<String,String>> getTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return loginService.getUserPay();
	}

	@JSON
	@Handler(value = "getExpressDetail")
	public ExpressDetail getExpressDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExpressDetail detail = new ExpressDetail();
		detail.setExpress_id("1001");
		detail.setGoods_name("scott");
		return detail;
	}
	
}
