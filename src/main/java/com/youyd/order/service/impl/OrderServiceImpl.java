package com.youyd.order.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youyd.order.dto.OrderDTO;
import com.youyd.order.enums.OrderStatusEnum;
import com.youyd.order.enums.PayStatusEnum;
import com.youyd.order.po.OrderMaster;
import com.youyd.order.repository.OrderDetailRepository;
import com.youyd.order.repository.OrderMasterRepository;
import com.youyd.order.service.OrderService;
import com.youyd.order.utils.KeyUtil;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Resource
	private OrderMasterRepository orderMasterRepository;

	
	/**
	 * 1.参数校验
	 * 2.查询商品信息(调用商品服务)
	 * 3.计算总价
	 * 4.扣库存(调用商品服务)
	 * 5.订单入库
	 */
	@Override
	public OrderDTO create(OrderDTO orderDTO) {
		// TODO 查询商品信息(调用商品服务)
		// TODO 计算总价
		// TODO 4.扣库存(调用商品服务)
		// TODO 5.订单入库
		OrderMaster orderMaster=new OrderMaster();
		orderDTO.setOrderId(KeyUtil.genUniqueKey());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderAmount(new BigDecimal(5));
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		
		orderMasterRepository.save(orderMaster);
		
		return orderDTO;
	}

}
