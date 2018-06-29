package com.youyd.order.service;

import com.youyd.order.dto.OrderDTO;

public interface OrderService {
	/*
	 * 创建订单
	 */
	public OrderDTO create(OrderDTO orderDAO);
}
