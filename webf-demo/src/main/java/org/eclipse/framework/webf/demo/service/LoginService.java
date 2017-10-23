package org.eclipse.framework.webf.demo.service;

import java.util.List;
import java.util.Map;

import org.eclipse.framework.webf.core.pojo.Page;
import org.eclipse.framework.webf.core.pojo.PageInfo;

public interface LoginService {

	List<Map<String,String>> findAllStudent();
	Page getAllResult(PageInfo pageInfo);
}