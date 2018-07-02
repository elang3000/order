package com.youyd.order.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.youyd.order.dto.CartDTO;
import com.youyd.order.po.ProductInfo;

@FeignClient(name="product")
public interface ProductClient {
	
	@GetMapping("/msg")
	String productMsg();
	
	@PostMapping("/product/listForOrder")
	public List<ProductInfo> listForOrder(List<String> productIdList);
	
	@PostMapping("/product/decreaseStock")
	public void decreaseStock(@RequestBody List<CartDTO> cartDTOList);
}
