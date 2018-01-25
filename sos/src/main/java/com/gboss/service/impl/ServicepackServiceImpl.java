package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.ServicepackDao;
import com.gboss.pojo.Servicepack;
import com.gboss.service.ServicepackService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ServicepackServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-6 下午4:27:07
 */
@Service("ServicepackService")
@Transactional
public class ServicepackServiceImpl extends BaseServiceImpl implements ServicepackService {

	@Autowired
	@Qualifier("ServicepackDao")
	private ServicepackDao servicepackDao;
	
	@Override
	public List<Servicepack> getServicepacks(Long subco_no) {
		return servicepackDao.getServicepacks(subco_no);
	}

}

