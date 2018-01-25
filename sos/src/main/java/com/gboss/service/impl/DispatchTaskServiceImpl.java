package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.DispatchTaskDao;
import com.gboss.pojo.DispatchTask;
import com.gboss.service.DispatchTaskService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:DispatchTaskServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-3-27 下午6:39:35
 */
@Service("dispatchTaskService")
@Transactional
public class DispatchTaskServiceImpl extends BaseServiceImpl implements
		DispatchTaskService {
	@Autowired  
	@Qualifier("dispatchTaskDao")
    private DispatchTaskDao dispatchTaskDao;

	@Override
	public DispatchTask findDistachByTask(Long id) throws SystemException {
		return dispatchTaskDao.findTaskByDistach(id);
	}

}

