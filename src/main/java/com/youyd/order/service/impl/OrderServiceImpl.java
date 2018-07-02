package com.youyd.order.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youyd.order.client.ProductClient;
import com.youyd.order.dto.CartDTO;
import com.youyd.order.dto.OrderDTO;
import com.youyd.order.enums.OrderStatusEnum;
import com.youyd.order.enums.PayStatusEnum;
import com.youyd.order.po.OrderDetail;
import com.youyd.order.po.OrderMaster;
import com.youyd.order.po.ProductInfo;
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
	
	@Autowired
	public ProductClient productClient;

	
	/**
	 * 1.参数校验
	 * 2.查询商品信息(调用商品服务)
	 * 3.计算总价
	 * 4.扣库存(调用商品服务)
	 * 5.订单入库
	 */
	@Override
	public OrderDTO create(OrderDTO orderDTO) {
		String orderId=KeyUtil.genUniqueKey();
		
		//查询商品信息(调用商品服务)
		List<String> productIdList=new ArrayList<>();
		BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);
		for(OrderDetail orderDetail:orderDTO.getOrderDetailList()) {
			productIdList.add(orderDetail.getProductId());
		}
		List<ProductInfo> productList = productClient.listForOrder(productIdList);
		
		//计算总价
		for(OrderDetail orderDetail:orderDTO.getOrderDetailList()) {
			for (ProductInfo productInfo : productList) {
				if(orderDetail.getProductId().equals(productInfo.getProductId())) {
					orderAmount=productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
					BeanUtils.copyProperties(productInfo, orderDetail);
					orderDetail.setOrderId(orderId);
					orderDetail.setDetailId(KeyUtil.genUniqueKey());
					//订单详情入库
					orderDetailRepository.save(orderDetail);
				}
			}
		}
		
		//4.扣库存(调用商品服务)
		List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream()
				.map(e->new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
		productClient.decreaseStock(cartDTOList);
		
		OrderMaster orderMaster=new OrderMaster();
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		
		orderMasterRepository.save(orderMaster);
		
		return orderDTO;
	}

}
