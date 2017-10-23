package org.eclipse.framework.webf.core.jdbc.handler;

import java.sql.ResultSet;

public interface ResultSetHandler {
  public Object handler(ResultSet rs);
}
