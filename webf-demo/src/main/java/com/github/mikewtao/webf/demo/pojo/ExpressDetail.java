package com.github.mikewtao.webf.demo.pojo;

public class ExpressDetail {
	private String express_id;
	private String goods_name;

	public String getExpress_id() {
		return express_id;
	}

	public void setExpress_id(String express_id) {
		this.express_id = express_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	@Override
	public String toString() {
		return "ExpressDetail [express_id=" + express_id + ", goods_name=" + goods_name + "]";
	}
	

}
