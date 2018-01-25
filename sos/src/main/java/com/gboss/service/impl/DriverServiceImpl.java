package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.DriverDao;
import com.gboss.pojo.Driver;
import com.gboss.service.DriverService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:DriverServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-21 上午10:40:38
 */
@Service("DriverService")
@Transactional
public class DriverServiceImpl extends BaseServiceImpl implements DriverService {

	@Autowired
	@Qualifier("DriverDao")
	private DriverDao driverDao;
	
	@Override
	public List<Long> getDriverids(Long vehicle_id) {
		return driverDao.getDriverids(vehicle_id);
	}

	@Override
	public void add(Driver driver) {
		save(driver);
	}

	@Override
	public List<Driver> getDrivers(Long vehicle_id) {
		return driverDao.getDrivers(vehicle_id);
	}

	@Override
	public void deleteByVehicleid(Long vehicle_id) {
		driverDao.deleteByVehicleid(vehicle_id);
	}

	@Override
	public void deleteByCustomerid(Long customer_id) {
		driverDao.deleteByCustomerid(customer_id);
	}

}

