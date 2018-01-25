package com.chinagps.driverbook.service;

import com.chinagps.driverbook.pojo.Driver;

public interface IDriverService extends BaseService<Driver> {
	
	public Driver findByCustomerId(Long customerId) throws Exception;
	
}
