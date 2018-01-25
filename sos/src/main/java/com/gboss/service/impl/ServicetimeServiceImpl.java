package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.ServicetimeDao;
import com.gboss.pojo.Servicetime;
import com.gboss.service.ServicetimeService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ServicetimeServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-13 上午10:55:13
 */
@Service("ServicetimeService")
@Transactional
public class ServicetimeServiceImpl extends BaseServiceImpl implements ServicetimeService {

	@Autowired  
	@Qualifier("ServicetimeDao")
	private ServicetimeDao servicetimeDao;
	
	@Override
	public Servicetime getByUnitid(Long unit_id) {
		return servicetimeDao.getByUnitid(unit_id);
	}

}

