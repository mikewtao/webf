package org.eclipse.framework.webf.core.pojo;

public class PageInfo {
	private int PageNum;
	private int PageSize;
	public final static String dbName = "oracle";
    
	public PageInfo(int pageNum, int pageSize) {
		PageNum = pageNum;
		PageSize = pageSize;
	}

	public int getPageNum() {
		return PageNum;
	}

	public void setPageNum(int pageNum) {
		PageNum = pageNum;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public static String getDbname() {
		return dbName;
	}
    
}
