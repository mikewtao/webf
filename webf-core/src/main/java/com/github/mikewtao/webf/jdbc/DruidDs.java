package com.github.mikewtao.webf.jdbc;


import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidDs implements InitDataSource{

	@Override
	public DataSource initDataSource() {
		 Properties properties = new Properties();
         try {
			 properties.load(new FileInputStream("src/druid.properties"));
			 DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
			 return dataSource;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return null;
	}
  
}
