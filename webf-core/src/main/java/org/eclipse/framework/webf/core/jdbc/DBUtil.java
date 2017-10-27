package org.eclipse.framework.webf.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.eclipse.framework.webf.core.jdbc.handler.ResultSetHandler;
import org.eclipse.framework.webf.core.pojo.Page;
import org.eclipse.framework.webf.core.pojo.PageInfo;


public class DBUtil {
	/**
	 * 执行增删改
	 * @param sql
	 * @param param
	 */
	public static void update(String sql, Object[] param) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.release(conn, ps, rs);
		}
	}
   /**
    * 普通查询
    * @param sql
    * @param param
    * @param rsh
    * @return
    */
	public static <T> T query(String sql, Object[] param, ResultSetHandler rsh,Class<T> clzz) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			rs = ps.executeQuery();
			return (T)rsh.handler(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.release(conn, ps, rs);
		}
	}
	/**
	 * 支持分页查询
	 * @param sql sql
	 * @param param
	 * @param rsh 
	 * @param page 
	 * @return 
	 */
	@SuppressWarnings("resource")
	public static Page PageQuery(String sql, Object[] param, ResultSetHandler rsh, PageInfo pageinf) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String dbname = PageInfo.dbName;
		Page page=new Page();
		int pageSize=pageinf.getPageSize();
		int pageNum=pageinf.getPageNum();
		try {
			conn = JdbcUtil.getConnection();
			String totalsql="select count(1) as total from ( "+sql+" )";
			ps = conn.prepareStatement(totalsql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()){
				int totalcnt=rs.getInt("TOTAL");
				page.setTotal(totalcnt%pageSize==0?totalcnt/pageSize:(totalcnt/pageSize)+1);
			}
			if (page != null && dbname != null) {
				if ("MYSQL".equals(dbname.toUpperCase())) {
					sql = sql+" limit "+(pageNum-1)*pageSize+","+pageSize;
				} else if ("ORACLE".equals(dbname.toUpperCase())) {
					sql = "SELECT * FROM ("+"SELECT A.*, ROWNUM RN FROM ("+sql+") A WHERE ROWNUM <="+pageNum*pageSize+") where RN>="+(pageNum-1)*pageSize;
				}
			}
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			System.out.println(sql);
			rs = ps.executeQuery();
			List list=(List) rsh.handler(rs);
		    System.out.println(list);
			page.setData(list);
			return page;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.release(conn, ps, rs);
		}
	}
}
