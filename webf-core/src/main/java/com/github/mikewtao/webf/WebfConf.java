package com.github.mikewtao.webf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.github.mikewtao.webf.mvc.URI;
import com.github.mikewtao.webf.mvc.WebController;
import com.github.mikewtao.webf.utils.ClazzScanner;
import com.github.mikewtao.webf.utils.webfUtil;
/**
 * 数据存储
 *
 */
public class WebfConf {
	
	public static Map<String, WebController> urlHandlerMap = new ConcurrentHashMap<String, WebController>();

	public static Map<String, List<InterceptorAdapter>> interceptorMap = InterceptorManager.interceptorMap;

	public static List<URI> urilist=new ArrayList<>();
	
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
	
	public static WebController findController(String key){
		if(webfUtil.isEmpty(key)){
			return null;
		}
		key=webfUtil.base64Encoder(key);
		for(URI uri:urilist){
			if(key.equals(uri.getKey())){
				return uri.getController();
			}
		}
		return null;
	}

}
