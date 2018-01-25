package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.CustVehicleDao;
import com.gboss.pojo.CustVehicle;
import com.gboss.service.CustVehicleService;

@Service("custVehicleService")
public class CustVehicleServiceImpl extends BaseServiceImpl implements CustVehicleService  {
	
	@Autowired
	private CustVehicleDao custVehicleDao;

	 
	public CustVehicle getCustVehicleByCVID(String c_id,String v_id) throws SystemException {
		return custVehicleDao.getCustVehicleByCVID(c_id, v_id);
	}

}
