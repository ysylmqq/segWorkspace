package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.MidCustDao;
import com.gboss.dao.ModelDao;
import com.gboss.pojo.MidCust;
import com.gboss.service.MidCustService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:MidCustServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-29 下午4:55:23
 */
@Service("MidCustService")                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
@Transactional
public class MidCustServiceImpl extends BaseServiceImpl implements MidCustService {

	@Autowired
	@Qualifier("MidCustDao")
	private MidCustDao midCustDao;
	
	@Override
	public MidCust getMidCustByUnitid(Long unit_id) {
		return midCustDao.getMidCustByUnitid(unit_id);
	}

	@Override
	public void deleteMidCust(Long unit_id) {
		midCustDao.deleteMidCust(unit_id);
	}
	
	
}

