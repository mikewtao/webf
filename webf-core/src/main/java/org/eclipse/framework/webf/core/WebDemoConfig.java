package org.eclipse.framework.webf.core;

import java.util.Arrays;

import org.eclipse.framework.webf.core.mvc.WebfInitConfig;
/**
 * 配置demo
 *
 */
public class WebDemoConfig implements WebfInitConfig {

	@Override
	public void addInterceptor(Interceptors interceptors) {
	   Interceptor interceptor=new StaticResourceInteceptor();
	   interceptors.addInterceptor("/*", Arrays.asList(interceptor));
	}

	@Override
	public void initDBconfig(DBconfig config) {
		config.setDriver("com.mysql.jdbc.driver");
		config.setUsername("root");
		config.setPassword("root");
		config.setUrl("");
	}

}
