package com.youyd.order.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youyd.order.converter.OrderFrom2OrderDTOConverter;
import com.youyd.order.dto.OrderDTO;
import com.youyd.order.enums.ResultEnum;
import com.youyd.order.exception.OrderException;
import com.youyd.order.form.OrderForm;
import com.youyd.order.service.OrderService;
import com.youyd.order.utils.ResultVOUtil;
import com.youyd.order.vo.ResultVO;


@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 1.参数校验s
	 * 2.查询商品信息(调用商品服务)
	 * 3.计算总价
	 * 4.扣库存(调用商品服务)
	 * 5.订单入库
	 */
	@PostMapping("/create")
	public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			System.out.println("创建订单不正确,orderfrom={}"+orderForm.toString());
			throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
		}
		
		OrderDTO orderDTO=OrderFrom2OrderDTOConverter.convert(orderForm);
		if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			System.out.println("[创建订单]购物车信息为空");
			throw new OrderException(ResultEnum.CART_EMPTY);
		}
		
		OrderDTO result = orderService.create(orderDTO);
		
		Map<String,String> map=new HashMap<>();
		map.put("orderId", result.getOrderId());
		return ResultVOUtil.success(map);
	}
}
