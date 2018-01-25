package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.BrandDao;
import com.gboss.dao.MaintainRuleDao;
import com.gboss.pojo.MaintainItems;
import com.gboss.pojo.MaintainRule;
import com.gboss.service.MaintainRuleService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:MaintainRuleServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-27 上午11:00:49
 */
@Service("maintainRuleService")
@Transactional
public class MaintainRuleServiceImpl extends BaseServiceImpl implements
		MaintainRuleService {
	
	@Autowired
	@Qualifier("maintainRuleDao")
	private MaintainRuleDao maintainRuleDao;

	@Override
	public MaintainRule getMaintainRuleByModelId(Long model_id) throws SystemException{
		return maintainRuleDao.getMaintainRuleByModelId(model_id);
	}

	@Override
	public MaintainItems getMaintainItemsByModelId(Long model_id) throws SystemException{
		return maintainRuleDao.getMaintainItemsByModelId(model_id);
	}

}