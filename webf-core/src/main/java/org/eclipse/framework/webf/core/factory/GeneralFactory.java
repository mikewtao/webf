package org.eclipse.framework.webf.core.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author B-0012 dao工厂
 */
public class GeneralFactory {
	private static Map<String, String> map = new HashMap<String, String>();

	private GeneralFactory() {
		Properties properties = new Properties();
		InputStream inputStream = GeneralFactory.class.getClassLoader().getResourceAsStream("factory.properties");
		try {
			properties.load(inputStream);
			@SuppressWarnings("unchecked")
			Enumeration<String> enu = (Enumeration<String>) properties.propertyNames();
			while (enu.hasMoreElements()) {
				String key = enu.nextElement();
				String value = properties.getProperty(key);
				map.put(key, value);
			}
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static final GeneralFactory factory = new GeneralFactory();

	public static GeneralFactory getInstance() {
		return factory;
	}
    
	
	@SuppressWarnings("unchecked")
	public <T> T getImpl(Class<T> clzz) {
		String key = clzz.getSimpleName();
		String value = map.get(key);
		try {
			return (T) Class.forName(value).newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
