package org.eclipse.framework.webf.core.mvc;

import org.eclipse.framework.webf.core.DBconfig;
import org.eclipse.framework.webf.core.Interceptors;

public interface WebfInitConfig {
   public void addInterceptor(Interceptors interceptors);
   
   public void initDBconfig(DBconfig config);
}
