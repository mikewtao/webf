package org.eclipse.framework.webf.core.utils;

public class StringUtil {
  public static boolean isEmpty(String str){
	  return str==null?true:str.equals("")||str.equals(" ")?true:false;
  }
}
