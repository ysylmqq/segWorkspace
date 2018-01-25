package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.VehicleConfDao;
import com.gboss.pojo.VehicleConf;
import com.gboss.service.VehicleConfService;

@Service("vehicleConfService")
@Transactional
public class VehicleConfServiceImpl extends BaseServiceImpl implements
		VehicleConfService {

	@Autowired
	private VehicleConfDao vehicleConfDao;
	
	public VehicleConf getVehicleConfByCallLetter(String call_letter) {
		return vehicleConfDao.getVehicleConfByCallLetter(call_letter);
	}

}
