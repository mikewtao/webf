package org.eclipse.framework.webf.demo.dao.impl;

import java.util.List;
import java.util.Map;

import org.eclipse.framework.webf.core.jdbc.DBUtil;
import org.eclipse.framework.webf.core.jdbc.handler.MapperHandler;
import org.eclipse.framework.webf.core.pojo.Page;
import org.eclipse.framework.webf.core.pojo.PageInfo;
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

	@Override
	public Page getAllResultByPage(PageInfo pageInfo) {
		String sql="select t.object_id,t.username,t.transmissionno,t.reportstatus,t.line_num,"
				+ "t.template_name,t.file_name from hdi_ack_result t where t.object_id is not null";
		Page page=DBUtil.PageQuery(sql, new Object[]{}, new MapperHandler(),pageInfo);
		return page;
	}
}
