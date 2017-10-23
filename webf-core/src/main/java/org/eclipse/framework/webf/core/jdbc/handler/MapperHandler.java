package org.eclipse.framework.webf.core.jdbc.handler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperHandler implements ResultSetHandler {

	@Override
	public Object handler(ResultSet rs) {
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        try{
        	while(rs.next()){
        		ResultSetMetaData metadata=rs.getMetaData();
        		Map<String,String> map=new HashMap<String,String>();
        		int columncount=metadata.getColumnCount();
        		for(int i=0;i<columncount;i++){
        			String ColumnName=metadata.getColumnName(i+1);
        			Object columndata=rs.getObject(i+1);
        			if(!"RN".equals(ColumnName)){
        				map.put(ColumnName, columndata.toString());
        			}
        		}
        		list.add(map);
        	}
        	return (List<Map<String,String>>)list;
        }catch(Exception e){
        	e.printStackTrace();
        }
		return null;
	}
   
}
