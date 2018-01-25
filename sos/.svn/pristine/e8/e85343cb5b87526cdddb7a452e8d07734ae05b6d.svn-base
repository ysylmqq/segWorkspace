package com.gboss.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.JhCityDao;
import com.gboss.service.JhCityService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:CarTypeServiceImpl
 * @Description:
 * @author:yusongya
 * @date:2017-6-9 下午5:22:45
 */
@Service
@Transactional
public class JhCityServiceImpl extends BaseServiceImpl implements
JhCityService {
	
protected static final Logger LOGGER = LoggerFactory.getLogger(JhCityServiceImpl.class);
	
	@Autowired  
	private JhCityDao jhCityDao;

	@Override
	public List<Map<String, Object>> findTodayFlushCity()
			throws SystemException {
		return jhCityDao.findTodayFlushCity();
	}

}

