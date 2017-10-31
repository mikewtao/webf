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
	public List<Map<String,String>> getUserPay() {
		String sql="select * from tb_user_pay where capplyid=?";
		List<Map<String, String>> list=DBUtil.query(sql, new Object[]{"13D674DC07"}, new MapperHandler(),List.class);
		return list;
	}

}
