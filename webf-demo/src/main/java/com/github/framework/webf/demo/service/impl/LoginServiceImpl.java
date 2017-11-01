package com.github.framework.webf.demo.service.impl;

import java.util.List;
import java.util.Map;

import com.github.framework.webf.annotation.AutoFind;
import com.github.framework.webf.demo.dao.LoginDao;
import com.github.framework.webf.demo.service.LoginService;

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
