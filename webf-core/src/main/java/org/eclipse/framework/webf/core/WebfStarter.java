package org.eclipse.framework.webf.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.framework.webf.core.annotation.Handler;
import org.eclipse.framework.webf.core.annotation.Module;
import org.eclipse.framework.webf.core.mvc.RequestParam;
import org.eclipse.framework.webf.core.utils.ClassScan;
import org.eclipse.framework.webf.core.utils.webfUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WebfStarter {
	private static Logger logger = LoggerFactory.getLogger(WebfStarter.class);

	public static WebfStarter getWebfStarter() {
		return new WebfStarter();
	}

	private static ClassScan classScan = ClassScan.getClassScan();

	public void initWebf() throws Exception {
		Set<String> clzzSet = classScan.getClazzset();
		for (String clstr : clzzSet) {
			Object obj = webfUtil.initClass(clstr);// 加载类
			if (obj == null) {
				continue;
			}
			Module moduleAnotation = obj.getClass().getAnnotation(Module.class);// 扫描module
			Field[] fields = obj.getClass().getDeclaredFields();
			MappingHandler(obj, moduleAnotation);// 映射处理器
			for (Field field : fields) {
				injectClass(obj, field);
			}

		}
	}

	private void MappingHandler(Object obj, Module moduleAnotation) {
		Method[] methods = obj.getClass().getMethods();
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
				WebfConfManager.urlHandlerMap.put(sb.toString(), param);
			}
		}
	}

	private void injectClass(Object obj, Field field) throws Exception {
		Inject inject = field.getAnnotation(Inject.class);// 查找module类字段是否有需要注入字段，若有，递归注入
		if (inject != null) {
			field.setAccessible(true);
			List<Class<?>> namedClzzlist = classScan.getClassByInterface(field.getType());
			Class<?> fieldCls = namedClzzlist.get(0);
			Object serviceObj = fieldCls.newInstance();// 初始化service
			Field[] serviceFields = serviceObj.getClass().getDeclaredFields();
			for (Field serviceField : serviceFields) {
				Inject serviceInject = serviceField.getAnnotation(Inject.class);// 再次检查service类是否有需要注入的字段
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
