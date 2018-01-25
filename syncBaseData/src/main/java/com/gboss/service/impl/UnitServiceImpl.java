package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemException;
import com.gboss.dao.UnitDao;
import com.gboss.pojo.Unit;
import com.gboss.service.UnitService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:UnitServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:45:05
 */

@Service("UnitService")
public class UnitServiceImpl extends BaseServiceImpl implements UnitService {
	
	@Autowired
	@Qualifier("UnitDao")
	private UnitDao unitDao;

	 
	public boolean is_repeat(Unit unit) {
		return unitDao.is_repeat(unit);
	}

	 
	public List<Unit> getByVehicle_id(Long vehicle_id) {
		return unitDao.getByVehicle_id(vehicle_id);
	}

	 

	 
	public Unit getUnitByid(Long id) {
		return unitDao.getUnitByid(id);
	}

	 
	 
	public void delete(Long id) {
		delete(Unit.class, id);
	}

	 
	public Unit getByCustAndVehicle(Long cust_id, Long vehicle_id) {
		return unitDao.getByCustAndVehicle(cust_id, vehicle_id);
	}

	public Unit getUnitByCL(String call_letter) throws SystemException {
		return unitDao.getUnitByCL(call_letter);
	}

}

