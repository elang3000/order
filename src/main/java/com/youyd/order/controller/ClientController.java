package com.youyd.order.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youyd.order.client.ProductClient;
import com.youyd.order.dto.CartDTO;
import com.youyd.order.po.ProductInfo;

@RestController
public class ClientController {
	
	@Autowired
	private ProductClient productClient;
	
//	@Autowired
//	private LoadBalancerClient loadBalancerClient;
//	
//	
//	@Autowired
//	private RestTemplate restTemplate;

	@GetMapping("/getProductMsg")
	public String getProductMsg() {
		//1.第一种方式  url写死
//		RestTemplate restTemplate=new RestTemplate();
//		String obj = restTemplate.getForObject("http://10.6.10.244:28080/aip_wj/mdp/welcome/resourceCatalog/queryAssetCategory.json?mode=mode", String.class);
//		System.out.println(obj);
//		return obj;
		
		//第二种方式  利用loadBalancerClient 通过应用名获取url,然后再使用resttemplate
//		RestTemplate restTemplate=new RestTemplate();
//		ServiceInstance serviceInstance=loadBalancerClient.choose("PRODUCT");
//		String url=String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort()+"/msg");
//		String response=restTemplate.getForObject(url, String.class);
		
		
		//第三种方式(利用@LoadBalanced,可在resttemplate里使用应用的名字
//		String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
		
		String response=productClient.productMsg();
		
		return response;
	}
	
	
	@GetMapping("/getProductList")
	public String getProductList() {
		List<ProductInfo> productInfoList = productClient.listForOrder(Arrays.asList("164103465734242707"));
		return "ok";
	}
	
	
	@GetMapping("/productDecreaseStock")
	public String productDecreaseStock() {
		productClient.decreaseStock(Arrays.asList(new CartDTO("164103465734242707", 3)));
		return "ok";
	}
	
}
