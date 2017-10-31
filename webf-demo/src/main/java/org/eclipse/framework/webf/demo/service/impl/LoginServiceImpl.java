package org.eclipse.framework.webf.demo.service.impl;

import java.util.List;
import java.util.Map;


import org.eclipse.framework.webf.core.annotation.AutoFind;
import org.eclipse.framework.webf.demo.dao.LoginDao;
import org.eclipse.framework.webf.demo.service.LoginService;

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
