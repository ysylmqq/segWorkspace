package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.DispatchElectricianDao;
import com.gboss.service.DispatchElectricianService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:DispatchElectricianServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-3-27 下午6:40:12
 */
@Service("dispatchElectricianService")
@Transactional
public class DispatchElectricianServiceImpl extends BaseServiceImpl implements
		DispatchElectricianService {
	
	@Autowired  
	@Qualifier("dispatchElectricianDao")
    private DispatchElectricianDao dispatchElectricianDao;

	@Override
	public HashMap<String, Object> getElectriciansDetail(Long userId,
			String time) {
		return dispatchElectricianDao.getElectriciansDetail(userId, time);
	}

	@Override
	public List<HashMap<String, Object>> getElectriciansBytaskId(Long taks_id) {
		return dispatchElectricianDao.getElectriciansBytaskId(taks_id);
	}

	@Override
	public List<HashMap<String, Object>> getElectriciansByDispatchId(
			Long dispatch_id) {
		return dispatchElectricianDao.getElectriciansByDispatchId(dispatch_id);
	}



}

