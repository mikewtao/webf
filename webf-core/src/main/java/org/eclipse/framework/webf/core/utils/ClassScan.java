package org.eclipse.framework.webf.core.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassScan {
    private static final String basePath=ClassScan.class.getResource("/").getPath();

	// 扫描所有的类
	public void ScanAllClasses(String basePackage, List<Class<?>> clzzList) throws ClassNotFoundException {
		String filePath = basePath + basePackage.replaceAll("\\.", "/");
		File[] files = new File(filePath).listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				ScanAllClasses(basePackage + "." + file.getName(), clzzList);
				continue;
			}
			String path1=file.getAbsolutePath().replaceAll("\\\\", ".");
			String path2=basePath.replaceAll("\\/", ".");
			String clazz = path1.replaceAll(path2.substring(1, path2.length()), "").replaceAll(".class", "");
			try {
				if(!clazz.endsWith("Util")){
					Class<?> clz = Class.forName(clazz);
					if(clz.getGenericSuperclass()!=null){
						clzzList.add(clz);
					}
				}
			} catch (ClassNotFoundException e) {
				throw e;
			}
		}
	}

	//根据接口找到实现类
	public List<Class<?>> getClassByInterface(String basePackage,Class<?> iface) throws ClassNotFoundException{
		System.out.println(" basePackage"+basePackage);
		List<Class<?>> clzzList=new ArrayList<Class<?>>();
		List<Class<?>> ifaceList=new ArrayList<Class<?>>();
		ScanAllClasses(basePackage, clzzList);
		String ifaceName=iface.getName();
		for(Class<?> clzz:clzzList){
			if(iface.isAssignableFrom(clzz)&&!ifaceName.equals(clzz.getName())){
				ifaceList.add(clzz);
			}
		}
		return ifaceList;
	}
	
	
	public static void main(String[] args) throws Exception {
		ClassScan scan=new ClassScan();
		List<Class<?>> clzzList=new ArrayList<Class<?>>();
		Class<?> iface=Class.forName("cn.mike.app.MyFrame.service.LoginService");
		clzzList=scan.getClassByInterface("cn.mike.app",iface);
		for(Class<?> cl:clzzList){
			System.out.println(cl.getName());
		}

	}
}
