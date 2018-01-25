package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.CustVehicleDao;
import com.gboss.dao.DatalockDao;
import com.gboss.service.DatalockService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:DatalockServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-4 下午8:10:53
 */
@Service("DatalockService")
@Transactional
public class DatalockServiceImpl extends BaseServiceImpl implements DatalockService {

	@Autowired
	@Qualifier("DatalockDao")
	private DatalockDao datalockDao;
	
	@Override
	public List<Long> getLockCustomer() {
		return datalockDao.getLockCustomer();
	}

}

