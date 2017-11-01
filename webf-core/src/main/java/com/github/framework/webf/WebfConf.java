package com.github.framework.webf;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.github.framework.webf.mvc.UrlHandler;
import com.github.framework.webf.utils.ClassScan;

public class WebfConf {
   public static  Map<String, UrlHandler> urlHandlerMap = new ConcurrentHashMap<String, UrlHandler>();
   
   public static  Map<String, List<InterceptorAdapter>> interceptorMap = InterceptorManager.interceptorMap;
   
   private static Set<String> classSet = ClassScan.getClassScan().getClazzset();
   
   public static String contextPath;
   
   public static void clearAllConfig(){
	   urlHandlerMap.clear();
	   classSet.clear();
	   interceptorMap.clear();
   }
   
   public static void initPath(String path){
	   contextPath=path;
   }
   
}
