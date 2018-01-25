package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.UnitserviceDao;
import com.gboss.pojo.Unitservice;
import com.gboss.service.UnitserviceService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:UnitserviceServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-15 下午3:04:40
 */

@Service("UnitserviceService")
@Transactional
public class UnitserviceServiceImpl extends BaseServiceImpl implements UnitserviceService {

	@Autowired
	@Qualifier("UnitserviceDao")
	private UnitserviceDao unitserviceDao;
	
	@Override
	public void add(Unitservice unitservice) {
		save(unitservice);
	}

	@Override
	public Unitservice getByVehicle_id(Long vehicle_id) {
		return unitserviceDao.getByVehicle_id(vehicle_id);
	}

}

