package com.gboss.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.OndutyDao;
import com.gboss.pojo.Onduty;
import com.gboss.service.OndutyService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:OndutyServiceImpl
 * @Description:电工值班业务层实现类
 * @author:bzhang
 * @date:2014-3-26 下午4:00:13
 */
@Service("ondutyService")
@Transactional
public class OndutyServiceImpl extends BaseServiceImpl implements OndutyService {

	@Autowired
	@Qualifier("ondutyDao")
	private OndutyDao ondutyDao;

	@Override
	public Onduty getOndutyByIdAndTime(String userId, Date time) {
		return ondutyDao.getOndutyByIdAndTime(userId, time);
	}

}
