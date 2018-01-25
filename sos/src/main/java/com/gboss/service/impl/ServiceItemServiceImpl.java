package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ServiceItemDao;
import com.gboss.pojo.Serviceitem;
import com.gboss.service.ServiceItemService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ServiceItemServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-2 下午4:49:06
 */

@Service("ServiceItemService")
@Transactional
public class ServiceItemServiceImpl extends BaseServiceImpl implements ServiceItemService {

	@Autowired
	@Qualifier("ServiceItemDao")
	private ServiceItemDao serviceItemDao;
	
	@Override
	public List<Serviceitem> findServiceitem(Serviceitem serviceitem) throws SystemException {
		return serviceItemDao.findServiceitem(serviceitem);
	}

}

