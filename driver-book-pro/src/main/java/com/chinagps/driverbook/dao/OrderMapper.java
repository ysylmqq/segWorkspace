package com.chinagps.driverbook.dao;

import java.util.List;

import com.chinagps.driverbook.pojo.Order;

public interface OrderMapper extends BaseSqlMapper<Order> {

	public Order findById(Long id);
	
	public List<Order> findByCustomerId(Long customerId);
	
}
