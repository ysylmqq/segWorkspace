package com.chinagps.driverbook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.InsuranceMapper;
import com.chinagps.driverbook.pojo.Insurance;
import com.chinagps.driverbook.service.IInsuranceService;

@Service
@Scope("prototype")
public class InsuranceServiceImpl extends BaseServiceImpl<Insurance> implements IInsuranceService {

	@Autowired
	private InsuranceMapper insuranceMapper;
	
	@Override
	public List<Insurance> findList() throws Exception {
		return insuranceMapper.findList();
	}

}
