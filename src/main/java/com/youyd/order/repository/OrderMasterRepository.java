package com.youyd.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youyd.order.po.OrderMaster;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String>{
	
}
