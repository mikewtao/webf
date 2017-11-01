package com.github.mikewtao.webf.demo.dao.impl;

import java.util.List;
import java.util.Map;

import com.github.mikewtao.webf.demo.dao.LoginDao;
import com.github.mikewtao.webf.jdbc.DBUtil;
import com.github.mikewtao.webf.jdbc.handler.MapperHandler;

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
