package com.github.mikewtao.webf.utils;

public class webfUtil {

	// 加载类
	public static Object loadClass(String clazz) {
		try {
			Class<?> clzz = Class.forName(clazz);
			Object obj = clzz.newInstance();
			return obj;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isEmpty(String str){
		return str==null||"".equals(str)||str.trim().length()==0;
	}
}
