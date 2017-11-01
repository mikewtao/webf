package com.github.framework.webf;

import com.alibaba.fastjson.JSONArray;

public class JsonViewResolver extends ViewResolve {

	@Override
	public Object renderView(Object obj, Object param) {
		String data = JSONArray.toJSONStringWithDateFormat(obj,param.toString());
		return data;
	}

}
