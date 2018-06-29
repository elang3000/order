package com.youyd.order.converter;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youyd.order.dto.OrderDTO;
import com.youyd.order.enums.ResultEnum;
import com.youyd.order.exception.OrderException;
import com.youyd.order.form.OrderForm;
import com.youyd.order.po.OrderDetail;

public class OrderFrom2OrderDTOConverter {
	
	public static OrderDTO convert(OrderForm orderForm) {
		OrderDTO orderDTO=new OrderDTO();
		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		orderDTO.setBuyerAddress(orderForm.getAddress());
		orderDTO.setBuyerOpenid(orderForm.getOpenid());
		List<OrderDetail> orderDetailList=new ArrayList<>();
		Gson gson=new Gson();
		try {
			orderDetailList=gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {}.getType());
		} catch (Exception e) {
			throw new OrderException(ResultEnum.PARAM_ERROR);
		}
		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;
	}

}
