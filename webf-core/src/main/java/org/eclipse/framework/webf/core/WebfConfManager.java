package org.eclipse.framework.webf.core;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.framework.webf.core.mvc.RequestParam;
import org.eclipse.framework.webf.core.utils.ClassScan;

public class WebfConfManager {
   public static  Map<String, RequestParam> urlHandlerMap = new ConcurrentHashMap<String, RequestParam>();
   
   public static  Map<String, List<Interceptor>> interceptorMap = Interceptors.interceptorMap;
   
   private static Set<String> classSet = ClassScan.getClassScan().getClazzset();
   
   public static void clearAllConfig(){
	   urlHandlerMap.clear();
	   classSet.clear();
	   interceptorMap.clear();
   }
   
}
