package com.youyd.order.enums;

public enum OrderStatusEnum {
	NEW(0,"新订单"),
	FINISHED(1,"完结"),
	CANCLE(2,"取消"),
	;
	private Integer code;
	
	private String desc;

	OrderStatusEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	
	
}
