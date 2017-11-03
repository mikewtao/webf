package com.github.mikewtao.webf.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class webfUtil {

	// 加载类
	public static Object loadClass(String clazz) {
		try {
			Class<?> clzz = Class.forName(clazz);
			Object obj = clzz.newInstance();
			return obj;
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str) || str.trim().length() == 0;
	}

	public static String base64Encoder(String key) {
		Encoder encoder = Base64.getEncoder();
		try {
			return encoder.encodeToString(key.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
