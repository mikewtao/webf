package org.eclipse.framework.webf.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.framework.webf.core.annotation.Handler;
import org.eclipse.framework.webf.core.annotation.JSON;
import org.eclipse.framework.webf.core.annotation.Module;
import org.eclipse.framework.webf.core.utils.ClassScan;
import org.eclipse.framework.webf.core.utils.JavaassitUtil;

import com.alibaba.fastjson.JSONArray;




public class DispatherFilter implements Filter {
	private static final Map<String, RequestParam> map = new HashMap<String, RequestParam>();
	public void destroy() {
		//释放资源
		map.clear();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest res = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = res.getRequestURI();
		if (uri.endsWith(".jsp") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg")
				|| uri.endsWith(".png")) {
			chain.doFilter(request, response);
			return;
		}
		String contextPath = res.getContextPath();
		RequestParam params = map.get(uri.replaceAll(contextPath, ""));
		if (params != null) {
			try {
				Object obj = params.getClazz();
				Method m = params.getMethod();
				Class<?>[] paramClzz = m.getParameterTypes();
				String[] names = JavaassitUtil.getParams(obj.getClass(), m.getName());
				List<Object> methodParams = new ArrayList<Object>();
				for (int i = 0; i < paramClzz.length; i++) {// HttpServletRequest
															// HttpServletResponse
					String pName = paramClzz[i].getSimpleName();
					String reqVal = res.getParameter(names[i]);
					// 注入request
					if (pName.equals("HttpServletRequest")) {
						methodParams.add(res);
						continue;
						// 注入response
					} else if (pName.equals("HttpServletResponse")) {
						methodParams.add(resp);
						continue;
						// 注入string
					} else if (pName.equals("String")) {
						methodParams.add(reqVal);
						continue;
						// 注入基本类型 int double float boolean byte char
					} else if (pName.equals("int")) {
						methodParams.add(Integer.valueOf(reqVal));
						continue;
					} else if (pName.equals("double")) {
						methodParams.add(Double.valueOf(reqVal));
						continue;
					} else if (pName.equals("float")) {
						methodParams.add(Float.valueOf(reqVal));
						continue;
					} else {
						Field[] fields = paramClzz[i].getDeclaredFields();
						Object ojb = paramClzz[i].newInstance();
						for (Field f : fields) {
							f.setAccessible(true);
							String pjName = f.getType().getSimpleName();
							String pval = res.getParameter(f.getName());
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
						continue;
					}

				}
				// 没有返回值
				if (m.getReturnType() == void.class) {
					m.invoke(obj, methodParams.toArray());
					// 返回string 试图
				} else if (m.getReturnType() == String.class) {
					String path = (String) m.invoke(obj, methodParams.toArray());
					path = "../" + path;
					request.getRequestDispatcher(path).forward(res, resp);
				} else {
					// pojo 转换成json
					Object jobj = m.invoke(obj, methodParams.toArray());
					JSON json = m.getAnnotation(JSON.class);
					if (json != null) {
						String dateType = json.DateType();
						resp.setContentType("text/html;charset=UTF-8");
						resp.setCharacterEncoding("utf-8");
						resp.setHeader("Pragma", "no-cache");
						resp.setHeader("Cache-Control", "no-cache, must-revalidate");
						resp.setHeader("Pragma", "no-cache");
						try {
							response.getWriter().write(JSONArray.toJSONStringWithDateFormat(jobj, dateType));
							response.getWriter().flush();
							response.getWriter().close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			res.getRequestDispatcher("error.html").forward(res, resp);
		}
	}

	//只在应用启动的时候进行注册
	public void init(FilterConfig fConfig) throws ServletException {
		String scanPackage = fConfig.getInitParameter("scanPackage");
		ClassScan classScan=new ClassScan();
		List<Class<?>> clzzList=new ArrayList<Class<?>>();
		try {
			classScan.ScanAllClasses(scanPackage, clzzList);
			for(Class<?> clzz:clzzList){
				Object obj=null;
				try{
				   obj = clzz.newInstance();
				}catch(Exception e){
					continue;
				}
				//扫描module
				Module moduleAnotation = obj.getClass().getAnnotation(Module.class);
				//Named NamedAnotation = obj.getClass().getAnnotation(Named.class);
				Field[] fields=obj.getClass().getDeclaredFields();
				if (moduleAnotation != null) {
					Method[] methods = obj.getClass().getMethods();
					//方法
					for (Method m : methods) {
						RequestParam param = new RequestParam();
						StringBuilder sb = new StringBuilder("/");
						String moduleName = moduleAnotation.name();
						sb.append(moduleName);
						sb.append("/");
						param.setClazz(obj);
						Handler p = m.getAnnotation(Handler.class);
						if (p != null) {
							sb.append(p.value());
							param.setMethod(m);
							map.put(sb.toString(), param);
						}
					}
					for(Field field:fields){
						Inject inject=field.getAnnotation(Inject.class);
						if(inject!=null){
							field.setAccessible(true);
							List<Class<?>> namedClzzlist=classScan.getClassByInterface(scanPackage, field.getType());
			            	//此处根据接口查找实现类  ClassScan
							System.out.println(field.getType().getName());
			            	Class<?> fieldCls=namedClzzlist.get(0);
			            	//初始化service
			            	Object serviceObj=fieldCls.newInstance();
			            	Field[] serviceFields=serviceObj.getClass().getDeclaredFields();
			            	for(Field serviceField:serviceFields){
			            		Inject serviceInject=serviceField.getAnnotation(Inject.class);
			            		if(serviceInject!=null){
			            			List<Class<?>> serviceClzzlist=classScan.getClassByInterface(scanPackage, serviceField.getType());
			            			Class<?> servicefieldCls=serviceClzzlist.get(0);
			            			//初始dao
			            			Object daoObj=servicefieldCls.newInstance();
			            			serviceField.setAccessible(true);
			            			serviceField.set(serviceObj, daoObj);
			            		}
			            	}
			            	//给字段设值
			            	field.set(obj,serviceObj);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
