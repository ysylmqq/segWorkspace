package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ServicefeeDao;
import com.gboss.pojo.Servicefee;
import com.gboss.pojo.Serviceitem;
import com.gboss.service.ServicefeeService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ServicefeeServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-8 下午2:29:29
 */

@Service("ServicefeeService")
@Transactional
public class ServicefeeServiceImpl extends BaseServiceImpl implements ServicefeeService{

	@Autowired
	@Qualifier("ServicefeeDao")
	private ServicefeeDao servicefeeDao;
	
	@Override
	public void add(Servicefee servicefee) {
		save(servicefee);
	}

	@Override
	public List<Servicefee> getByVehicle_id(Long vehicle_id) {
		List<Servicefee> fees = servicefeeDao.getByVehicle_id(vehicle_id);
		List<Servicefee> result = new ArrayList<Servicefee>();
		List<Serviceitem> items = null;
		return result;
	}
	

}

