package com.github.mikewtao.webf.demo.service.impl;

import java.util.List;
import java.util.Map;

import com.github.mikewtao.webf.annotation.AutoFind;
import com.github.mikewtao.webf.demo.dao.LoginDao;
import com.github.mikewtao.webf.demo.service.LoginService;

/**
 * 
 */
public class LoginServiceImpl implements LoginService {

	@AutoFind
	private LoginDao loginDao;
	
	@Override
	public List<Map<String,String>> getUserPay() {
		return loginDao.getUserPay();
	}

	
}
