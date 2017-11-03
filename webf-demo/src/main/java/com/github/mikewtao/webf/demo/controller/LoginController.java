package com.github.mikewtao.webf.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.mikewtao.webf.annotation.AutoFind;
import com.github.mikewtao.webf.annotation.Handler;
import com.github.mikewtao.webf.annotation.JSON;
import com.github.mikewtao.webf.annotation.RequestMethod;
import com.github.mikewtao.webf.annotation.Controller;
import com.github.mikewtao.webf.demo.pojo.ExpressDetail;
import com.github.mikewtao.webf.demo.service.LoginService;


@Controller("user")
public class LoginController {
	
	@AutoFind
	private LoginService loginService;
	

	@Handler(method=RequestMethod.GET,path="/getTestView")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		return "WEB-INF/jsp/test.html";
	}

	@JSON
	@Handler("/getStudent")
	public List<Map<String,String>> getTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return loginService.getUserPay();
	}

	@JSON
	@Handler("getExpressDetail")
	public ExpressDetail getExpressDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExpressDetail detail = new ExpressDetail();
		detail.setExpress_id("1001");
		detail.setGoods_name("scott");
		return detail;
	}
	
}
