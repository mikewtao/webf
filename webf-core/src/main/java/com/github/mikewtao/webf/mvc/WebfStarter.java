package com.github.mikewtao.webf.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mikewtao.webf.InterceptorManager;
import com.github.mikewtao.webf.WebfConf;
import com.github.mikewtao.webf.annotation.AutoFind;
import com.github.mikewtao.webf.annotation.Handler;
import com.github.mikewtao.webf.annotation.Interceptor;
import com.github.mikewtao.webf.annotation.Module;
import com.github.mikewtao.webf.utils.ClazzScanner;
import com.github.mikewtao.webf.utils.webfUtil;

public final class WebfStarter {
	private static Logger logger = LoggerFactory.getLogger(WebfStarter.class);

	public static WebfStarter getWebfStarter() {
		return new WebfStarter();
	}

	private static ClazzScanner classScan = ClazzScanner.getClassScan();

	public void initWebf() throws Exception {
		Set<String> clzzSet = classScan.getClazzset();
		for (String clstr : clzzSet) {
			Object obj = webfUtil.loadClass(clstr);// 加载类
			if (obj == null) {
				continue;
			}
			Module moduleAnotation = obj.getClass().getAnnotation(Module.class);// 扫描module
			Interceptor interceptor=obj.getClass().getAnnotation(Interceptor.class);//拦截器
			if(interceptor!=null){
				InterceptorManager.handleInterceptor(interceptor,clstr);
			}
			Field[] fields = obj.getClass().getDeclaredFields();
			if(moduleAnotation!=null){
				MappingHandler(obj, moduleAnotation);// 映射处理器
				for (Field field : fields) {
					injectClass(obj, field);
				}
			}
			
		}
	}
	
	private void MappingHandler(Object obj, Module moduleAnotation) {
		Method[] methods = obj.getClass().getMethods();
		for (Method m : methods) {
			Controller param = new Controller();
			StringBuilder sb = new StringBuilder("/");
			String moduleName = moduleAnotation.name();
			sb.append(moduleName);
			sb.append("/");
			param.setClazz(obj);
			Handler p = m.getAnnotation(Handler.class);
			if (p != null) {
				sb.append(p.value());
				param.setMethod(m);
				logger.info("url:{} class:{} -> method:{}", new Object[] { sb.toString(),obj.getClass().getName(),m.getName()});
				WebfConf.urlHandlerMap.put(sb.toString(), param);
			}
		}
	}

	private void injectClass(Object obj, Field field) throws Exception {
		AutoFind inject = field.getAnnotation(AutoFind.class);// 查找module类字段是否有需要注入字段，若有，递归注入
		if (inject != null) {
			field.setAccessible(true);
			List<Class<?>> namedClzzlist = classScan.getClassByInterface(field.getType());
			Class<?> fieldCls = namedClzzlist.get(0);
			Object serviceObj = fieldCls.newInstance();// 初始化service
			Field[] serviceFields = serviceObj.getClass().getDeclaredFields();
			for (Field serviceField : serviceFields) {
				AutoFind serviceInject = serviceField.getAnnotation(AutoFind.class);// 再次检查service类是否有需要注入的字段
				if (serviceInject != null) {
					List<Class<?>> daoClzzlist = classScan.getClassByInterface(serviceField.getType());
					Class<?> servicefieldCls = daoClzzlist.get(0);
					Object daoObj = servicefieldCls.newInstance();// 初始dao
					serviceField.setAccessible(true);
					serviceField.set(serviceObj, daoObj);// service注入dao
				}
			}
			field.set(obj, serviceObj);// 再将service注入module
		}
	}
}
