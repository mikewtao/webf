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
import com.github.mikewtao.webf.annotation.RequestMethod;
import com.github.mikewtao.webf.annotation.Controller;
import com.github.mikewtao.webf.utils.ClazzScanner;
import com.github.mikewtao.webf.utils.webfUtil;

public final class WebStarter {
	private static Logger logger = LoggerFactory.getLogger(WebStarter.class);

	public static WebStarter getWebfStarter() {
		return new WebStarter();
	}

	private static ClazzScanner classScan = ClazzScanner.getClassScan();

	public void initWebf() throws Exception {
		Set<String> clzzSet = classScan.getClazzset();
		for (String clstr : clzzSet) {
			Object obj = webfUtil.loadClass(clstr);// 加载类
			if (obj == null) {
				continue;
			}
			// URI uri=new URI();
			Interceptor interceptor = obj.getClass().getAnnotation(Interceptor.class);// 拦截器
			if (interceptor != null) {
				InterceptorManager.handleInterceptor(interceptor, clstr);
				continue;
			}
			Controller controllerAnotation = obj.getClass().getAnnotation(Controller.class);// 扫描module
			if (controllerAnotation != null) {
				// URI=
				uriMapping(obj, controllerAnotation);// 映射处理器
				Field[] fields = obj.getClass().getDeclaredFields();
				for (Field field : fields) {
					injectClass(obj, field);// 注入依赖
				}
			}

		}
	}

	private void uriMapping(Object obj, Controller controllerAnotation) {
		Method[] methods = obj.getClass().getMethods();
		for (Method m : methods) {
			URI uri = new URI();
			WebController controller = new WebController();
			StringBuilder sb = new StringBuilder();
			String controllerPath = controllerAnotation.value();
			if (controllerPath.indexOf("/") == -1) {
				sb = new StringBuilder("/");
			}
			sb.append(controllerPath);
			sb.append("/");
			controller.setClazz(obj);// controller clzz
			WebHandler handler = new WebHandler();
			handler.setMethod(m);
			Handler p = m.getAnnotation(Handler.class);
			if (p != null) {
				RequestMethod[] reqmethod = p.method();
				handler.setReqMethod(reqmethod);
				controller.setHandler(handler);
				sb.append(p.path());
				uri.setKey(sb.toString());
				uri.setUrl(sb.toString());
				uri.setController(controller);
				logger.info("url:{} class:{} -> method:{}",
						new Object[] { sb.toString(), obj.getClass().getName(), m.getName() });
				WebfConf.urilist.add(uri);
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
