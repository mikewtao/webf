package com.github.mikewtao.webf.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.github.mikewtao.webf.utils.ClazzScanner;

public class JdbcUtil {
	private static DataSource ds;
	static {
		try {
		    Class<?> dsclzz=ClazzScanner.getClassScan().getClassByInterface(InitDataSource.class).get(0);
		    InitDataSource init=(InitDataSource) dsclzz.newInstance();
		    ds= init.initDataSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws Exception {
		return ds.getConnection();
	}

	public static void release(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				rs = null;
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				ps = null;
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				conn = null;
			}
		}
	}
}
