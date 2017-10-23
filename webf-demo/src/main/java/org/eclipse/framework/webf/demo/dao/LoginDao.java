package org.eclipse.framework.webf.demo.dao;

import java.util.List;
import java.util.Map;

import org.eclipse.framework.webf.core.pojo.Page;
import org.eclipse.framework.webf.core.pojo.PageInfo;

public interface LoginDao {

	List<Map<String,String>> getAllStudentFromDB();
	
	Page getAllResultByPage(PageInfo pageInfo);

}