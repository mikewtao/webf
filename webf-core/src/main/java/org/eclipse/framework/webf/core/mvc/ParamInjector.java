package org.eclipse.framework.webf.core.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParamInjector {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Class<?>[] paramClzz;
	private String[] names;
    
	
	public ParamInjector(HttpServletRequest request, HttpServletResponse response, Class<?>[] paramClzz,
			String[] names) {
		this.request = request;
		this.response = response;
		this.paramClzz = paramClzz;
		this.names = names;
	}

	public List<Object> execInject() throws Exception {
		List<Object> methodParams = new ArrayList<Object>();
		for (int i = 0; i < paramClzz.length; i++) {
             int flag=InjectBasicType(i, methodParams);
             if(flag==1){
            	 InjectPojo(i, methodParams);
             }
		}
		return methodParams;
	}

	public int InjectBasicType(int i, List<Object> methodParams) {
		String pName = paramClzz[i].getSimpleName();
		String reqVal = request.getParameter(names[i]);
		// 注入req resp 基本类型
		if (pName.equals("HttpServletRequest")) {
			methodParams.add(request);
			return 0;
		} else if (pName.equals("HttpServletResponse")) {
			methodParams.add(response);
			return 0;
		} else if (pName.equals("String")) {
			methodParams.add(reqVal);
			return 0;
		} else if (pName.equals("int")) {
			methodParams.add(Integer.valueOf(reqVal));
			return 0;
		} else if (pName.equals("double")) {
			methodParams.add(Double.valueOf(reqVal));
			return 0;
		} else if (pName.equals("float")) {
			methodParams.add(Float.valueOf(reqVal));
			return 0;
		}
		return 1;
	}

	public void InjectPojo(int i, List<Object> methodParams) throws Exception{
		Field[] fields = paramClzz[i].getDeclaredFields();
		Object ojb = paramClzz[i].newInstance();
		for (Field f : fields) {
			f.setAccessible(true);
			String pjName = f.getType().getSimpleName();
			String pval = request.getParameter(f.getName());
			String fieldName = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
			Method pm = paramClzz[i].getMethod("set" + fieldName, f.getType().newInstance().getClass());
			if (pjName.equals("String")) {
				pm.invoke(ojb, pval);
			} else if (pjName.equals("int")) {
				pm.invoke(ojb, Integer.valueOf(pval));
			} else if (pjName.equals("double")) {
				pm.invoke(ojb, Double.valueOf(pval));
				continue;
			} else if (pjName.equals("float")) {
				pm.invoke(ojb, Float.valueOf(pval));
				continue;
			}
		}
		methodParams.add(ojb);
		return;
	}
}
