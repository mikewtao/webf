package com.github.mikewtao.webf.jdbc;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Ds implements InitDataSource {

	@Override
	public DataSource initDataSource() {
		DataSource ds=new ComboPooledDataSource("oracle");
		return ds;
	}

}
