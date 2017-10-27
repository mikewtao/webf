package org.eclipse.framework.webf.demo.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.framework.webf.demo.dao.LoginDao;
import org.eclipse.framework.webf.demo.service.LoginService;

/**
 * 
 */
public class LoginServiceImpl implements LoginService {

	@Inject
	private LoginDao loginDao;
	
	@Override
	public List<Map<String,String>> findAllStudent() {
		return loginDao.getAllStudentFromDB();
	}

	
}
