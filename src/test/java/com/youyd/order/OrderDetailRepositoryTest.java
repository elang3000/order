package com.youyd.order;


import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Component;

import com.youyd.order.po.OrderDetail;
import com.youyd.order.repository.OrderDetailRepository;

@Component
public class OrderDetailRepositoryTest extends OrderApplicationTests{
	@Resource
	private OrderDetailRepository orderDetailRepository;
	
	@Test
	public void testSave() {
		OrderDetail detail=new OrderDetail();
		detail.setDetailId("12367");
		detail.setOrderId("1234567");
		detail.setProductId("123123123123");
		detail.setProductIcon("http://xxx.com");
		detail.setProductName("皮蛋粥");
		detail.setProductPrice(new BigDecimal(0.01));
		detail.setProductQuantity(2);
		OrderDetail orderDetail = orderDetailRepository.save(detail);
		Assert.assertTrue(orderDetail!=null);
	}

}
