package org.eclipse.framework.webf.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.framework.webf.core.annotation.Handler;
import org.eclipse.framework.webf.core.annotation.JSON;
import org.eclipse.framework.webf.core.annotation.Module;
import org.eclipse.framework.webf.demo.pojo.ExpressDetail;
import org.eclipse.framework.webf.demo.service.LoginService;


@Module(name = "user",desc="用户模块")
public class LoginController {
	@Inject
	private LoginService loginService;
	

	@Handler(value = "logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		return "WEB-INF/jsp/test.html";
	}

	@JSON
	@Handler(value = "getStudent")
	public List<Map<String,String>> getTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return loginService.findAllStudent();
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
