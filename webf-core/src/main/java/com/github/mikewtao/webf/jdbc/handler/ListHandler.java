package com.github.mikewtao.webf.jdbc.handler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ListHandler implements ResultSetHandler {
    private Class clazz;
	public  ListHandler(Class clazz){
		this.clazz=clazz;
	}
	@Override
	public Object handler(ResultSet rs) {
		List list=new ArrayList();
        try{
        	while(rs.next()){
        		Object bean=clazz.newInstance();
        		ResultSetMetaData metadata=rs.getMetaData();
        		int columncount=metadata.getColumnCount();
        		for(int i=0;i<columncount;i++){
        			String ColumnName=metadata.getColumnName(i+1);
        			Object columndata=rs.getObject(i+1);
        			Field f=clazz.getDeclaredField(ColumnName);
    				f.setAccessible(true);
    				f.set(bean, columndata); 
        		}
        		list.add(bean);
        	}
        	return list;
        }catch(Exception e){
        	
        }
		return null;
	}
   
}
