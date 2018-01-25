package com.chinagps.driverbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.VehicleConfMapper;
import com.chinagps.driverbook.pojo.VehicleConf;
import com.chinagps.driverbook.service.VehicleConfService;

@Service
@Scope("prototype")
public class VehicleConfServiceImpl extends BaseServiceImpl<VehicleConf> implements VehicleConfService {
	
	@Autowired
	@Qualifier("vehicleConfMapper")
	private VehicleConfMapper vehicleConfMapper;
	
	public VehicleConf findByCL(String call_letter) {
		return vehicleConfMapper.findByCL(call_letter);
	}


}
