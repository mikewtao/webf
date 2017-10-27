package org.eclipse.framework.webf.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
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
import org.eclipse.framework.webf.core.exception.InitializedException;
import org.eclipse.framework.webf.core.utils.ClassScan;
import org.eclipse.framework.webf.core.utils.JavaassitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;


public class DispatherFilter implements Filter {
 
	private static final Logger logger = LoggerFactory.getLogger(DispatherFilter.class);

	private static final Map<String, RequestParam> urlHandlerMap = new ConcurrentHashMap<String, RequestParam>();//线程安全

	public void destroy() {
		// 释放资源
		urlHandlerMap.clear();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest res = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = res.getRequestURI();
		if (uri.endsWith(".jsp") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg")
				|| uri.endsWith(".png")) {//过滤静态资源
			chain.doFilter(request, response);
			return;
		}
		String contextPath = res.getContextPath();
		RequestParam params = urlHandlerMap.get(uri.replaceAll(contextPath, ""));
		if (params != null) {
			try {
				Object obj = params.getClazz();
				Method m = params.getMethod();
				Class<?>[] paramClzz = m.getParameterTypes();
				String[] names = JavaassitUtil.getParams(obj.getClass(), m.getName());//获取方法参数名称
				List<Object> methodParams = new ArrayList<Object>();
				for (int i = 0; i < paramClzz.length; i++) {
					injectParam(res, resp, paramClzz, names, methodParams, i);//处理方法（handler）参数注入

				}
				fowardView(request, response, res, resp, obj, m, methodParams);//处理结果
				logger.info("url:{} handler:{} has been handled",new Object[]{uri.replaceAll(contextPath, ""),m.getName()});
			} catch (Exception e) {
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//返回500
				e.printStackTrace();
			}
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);//返回404
		}
	}

	private void injectParam(HttpServletRequest res, HttpServletResponse resp, Class<?>[] paramClzz, String[] names,
			List<Object> methodParams, int i)
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		String pName = paramClzz[i].getSimpleName();
		String reqVal = res.getParameter(names[i]);
		// 注入req resp 基本类型
		if (pName.equals("HttpServletRequest")) {
			methodParams.add(res);
			return;
		} else if (pName.equals("HttpServletResponse")) {
			methodParams.add(resp);
			return;
		} else if (pName.equals("String")) {
			methodParams.add(reqVal);
			return;
		} else if (pName.equals("int")) {
			methodParams.add(Integer.valueOf(reqVal));
			return;
		} else if (pName.equals("double")) {
			methodParams.add(Double.valueOf(reqVal));
			return;
		} else if (pName.equals("float")) {
			methodParams.add(Float.valueOf(reqVal));
			return;
		} else {//注入pojo
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
			return;
		}
	}

	private void fowardView(ServletRequest request, ServletResponse response, HttpServletRequest res,
			HttpServletResponse resp, Object obj, Method m, List<Object> methodParams)
			throws IllegalAccessException, InvocationTargetException, ServletException, IOException {
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
	}

	// 只在应用启动的时候进行注册 
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("webf initilize....");
		String scanPackage = fConfig.getInitParameter("scanPackage");
		ClassScan classScan = new ClassScan();
		List<Class<?>> clzzList = new ArrayList<Class<?>>();
		try {
			classScan.ScanAllClasses(scanPackage, clzzList);
			logger.debug("scanning all class file in classpath");
			for (Class<?> clzz : clzzList) {
				Object obj = null;
				try {
					obj = clzz.newInstance();//先初始化controller
				} catch (Exception e) {
					logger.error("class:{} initilize fail,cause:{}",new Object[]{clzz.getName(),e.getMessage()});
					continue;
				}
				// 扫描module
				Module moduleAnotation = obj.getClass().getAnnotation(Module.class);
				Field[] fields = obj.getClass().getDeclaredFields();
				if (moduleAnotation != null) {
					Method[] methods = obj.getClass().getMethods();
					// 方法
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
							logger.info("url:{} handler:{}", new Object[] { sb.toString(), param.toString() });
							urlHandlerMap.put(sb.toString(), param);
						}
					}
					for (Field field : fields) {
						logger.debug("module:{} type:{} field:{} need to be injected",new Object[]{clzz.getName(),field.getType().getSimpleName(),field.getName()});
						injectClass(scanPackage, classScan, obj, field);
						logger.debug("module:{} has been Injected",new Object[]{clzz.getName()});
					}
				}
				logger.info("webf has initilized....");
			}
		} catch (Exception e) {
			throw new InitializedException("webf initilized error",e);
		}
	}

	private void injectClass(String scanPackage, ClassScan classScan, Object obj, Field field)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Inject inject = field.getAnnotation(Inject.class);//查找module类字段是否有需要注入字段，若有，递归注入
		if (inject != null) {
			field.setAccessible(true);
			List<Class<?>> namedClzzlist = classScan.getClassByInterface(scanPackage, field.getType());// 此处根据接口查找实现类 ClassScan
			Class<?> fieldCls = namedClzzlist.get(0);
			Object serviceObj = fieldCls.newInstance();// 初始化service
			Field[] serviceFields = serviceObj.getClass().getDeclaredFields();
			for (Field serviceField : serviceFields) {
				Inject serviceInject = serviceField.getAnnotation(Inject.class);//再次检查service类是否有需要注入的字段
				if (serviceInject != null) {
					logger.debug("service:{} type:{} field:{} need to be injected",new Object[]{fieldCls.getName(),serviceField.getType().getSimpleName(),serviceField.getName()});
					List<Class<?>> daoClzzlist = classScan.getClassByInterface(scanPackage, serviceField.getType());
					Class<?> servicefieldCls = daoClzzlist.get(0);
					Object daoObj = servicefieldCls.newInstance();// 初始dao
					serviceField.setAccessible(true);
					serviceField.set(serviceObj, daoObj);//service注入dao
					logger.debug("service:{} has been Injected",new Object[]{fieldCls.getName()});
				}
			}
			field.set(obj, serviceObj);//再将service注入module
		}
	}

}
