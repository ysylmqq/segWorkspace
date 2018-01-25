package com.chinagps.driverbook.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinagps.driverbook.pojo.Order;

public interface IOrderService extends BaseService<Order> {
	
	public Map<String, Object> orderAndSign(Order order, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public Map<String, Object> resignOrder(Order order, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public List<Order> findByCustomerId(Long customerId) throws Exception;
	
	
}
