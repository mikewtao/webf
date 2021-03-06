package com.github.mikewtao.webf.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.github.mikewtao.webf.jdbc.handler.ResultSetHandler;

public class DBUtil {
	/**
	 * 执行增删改
	 * 
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
	 * 
	 * @param sql
	 * @param param
	 * @param rsh
	 * @return
	 */
	public static <T> T query(String sql, Object[] param, ResultSetHandler rsh, Class<T> clzz) {
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
			return (T) rsh.handler(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.release(conn, ps, rs);
		}
	}
}
