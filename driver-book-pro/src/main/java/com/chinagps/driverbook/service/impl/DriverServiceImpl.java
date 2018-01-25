package com.chinagps.driverbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.DriverMapper;
import com.chinagps.driverbook.pojo.Driver;
import com.chinagps.driverbook.service.IDriverService;

@Service
@Scope("prototype")
public class DriverServiceImpl extends BaseServiceImpl<Driver> implements IDriverService {

	@Autowired
	private DriverMapper driverMapper;
	
	@Override
	public Driver findByCustomerId(Long customerId) throws Exception {
		return driverMapper.findByCustomerId(customerId);
	}
	
}
