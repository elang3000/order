package com.youyd.order;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youyd.order.enums.OrderStatusEnum;
import com.youyd.order.enums.PayStatusEnum;
import com.youyd.order.po.OrderMaster;
import com.youyd.order.repository.OrderMasterRepository;

@Component
public class OrderMasterRepositoryTest extends OrderApplicationTests{

	@Autowired
	private OrderMasterRepository orderMasterRepository;
	
	@Test
	public void testSave() {
		
		OrderMaster orderMaster=new OrderMaster();
		orderMaster.setOrderId("1234567");
		orderMaster.setBuyerAddress("慕课网总部");
		orderMaster.setBuyerName("师兄");
		orderMaster.setBuyerOpenid("1101110");
		orderMaster.setBuyerPhone("15123123123");
		orderMaster.setOrderAmount(new BigDecimal(2.5));
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		OrderMaster order = orderMasterRepository.save(orderMaster);
		Assert.assertTrue(order!=null);
	}

}
