package org.eclipse.framework.webf.core.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Page implements Serializable {

	private long total;
	private Object data;

	public Page() {
		this(0, new ArrayList());
	}

	public Page(long total, Object data) {
		this.total = total;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}