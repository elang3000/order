package com.youyd.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youyd.order.po.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String>{

}
