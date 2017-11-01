package com.github.framework.webf.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassScan {
	private static ClassScan classScan = new ClassScan();
	private static String basePath = ClassScan.class.getResource("/").getPath();
	private static Set<String> clazzset = new HashSet<>();

	private ClassScan() {
	};

	public static ClassScan getClassScan() {
		ScanAllClasses(basePath.substring(0, basePath.length() - 1), clazzset);
		return classScan;
	}

	// 扫描所有的类
	private static void ScanAllClasses(String basePackage, Set<String> clzzset) {
		File[] files = new File(basePackage).listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				ScanAllClasses(basePackage + "/" + file.getName(), clzzset);
				continue;
			}
			String path1 = file.getAbsolutePath().replaceAll("\\\\", ".");
			String path2 = basePath.replaceAll("\\/", ".");
			String clazz = path1.replaceAll(path2.substring(1, path2.length()), "").replaceAll(".class", "");
			clzzset.add(clazz);
		}
	}

	// 根据接口找到实现类
	public List<Class<?>> getClassByInterface(Class<?> iface) {
		List<Class<?>> ifaceList = new ArrayList<Class<?>>();
		String ifaceName = iface.getName();
		for (String clzzStr : clazzset) {
			try {
				Class<?> clzz = Class.forName(clzzStr);
				if (iface.isAssignableFrom(clzz) && !ifaceName.equals(clzz.getName())) {
					ifaceList.add(clzz);
				}
			} catch (ClassNotFoundException e) {
				continue;
			}

		}
		return ifaceList;
	}

	public Set<String> getClazzset() {
		return clazzset;
	}

	public static String getBasePath() {
		return basePath;
	}
}
