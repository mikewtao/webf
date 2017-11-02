package com.github.mikewtao.webf;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.github.mikewtao.webf.mvc.Controller;
import com.github.mikewtao.webf.utils.ClazzScanner;

public class WebfConf {
	
	public static Map<String, Controller> urlHandlerMap = new ConcurrentHashMap<String, Controller>();

	public static Map<String, List<InterceptorAdapter>> interceptorMap = InterceptorManager.interceptorMap;

	private static Set<String> classSet = ClazzScanner.getClassScan().getClazzset();

	public static String contextPath;

	public static void clearAllConfig() {
		urlHandlerMap.clear();
		classSet.clear();
		interceptorMap.clear();
	}

	public static void initPath(String path) {
		contextPath = path;
	}

}
