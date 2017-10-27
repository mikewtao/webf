package org.eclipse.framework.webf.demo.dao.impl;

import java.util.List;
import java.util.Map;

import org.eclipse.framework.webf.core.jdbc.DBUtil;
import org.eclipse.framework.webf.core.jdbc.handler.MapperHandler;
import org.eclipse.framework.webf.demo.dao.LoginDao;

/**
 * 
 */
public class LoginDaoImpl implements LoginDao {
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getAllStudentFromDB() {
		String sql="select username,role_id from hdi_user";
		List<Map<String, String>> list=DBUtil.query(sql, new Object[]{}, new MapperHandler(),List.class);
		return list;
	}

}
