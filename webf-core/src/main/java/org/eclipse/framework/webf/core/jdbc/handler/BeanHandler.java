package org.eclipse.framework.webf.core.jdbc.handler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class BeanHandler<E> implements ResultSetHandler {
    private Class<E> clazz;
	public BeanHandler(Class<E> clazz){
		this.clazz=clazz;
	}
	@Override
	public Object handler(ResultSet rs) {
		 
		 try {
			if(!rs.next()){
				 return null;
			 }
			Object bean=clazz.newInstance();
			ResultSetMetaData metadata=rs.getMetaData();
			int columncount=metadata.getColumnCount();
			for(int i=0;i<columncount;i++){
				String columname=metadata.getColumnName(i);
				Object columdata=rs.getObject(i+1);
				Field f=clazz.getDeclaredField(columname);
				f.setAccessible(true);
				f.set(bean, columdata);
			}
			return bean;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
