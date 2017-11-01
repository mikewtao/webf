package com.github.mikewtao.webf.jdbc.handler;

import java.sql.ResultSet;

public interface ResultSetHandler {
  public Object handler(ResultSet rs);
}
