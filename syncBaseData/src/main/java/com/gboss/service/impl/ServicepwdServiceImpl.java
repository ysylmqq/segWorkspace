package com.gboss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemException;
import com.gboss.dao.ServicepwdDao;
import com.gboss.service.ServicepwdService;

@Service("servicepwdService")
public class ServicepwdServiceImpl extends BaseServiceImpl implements ServicepwdService {

	@Autowired
	private ServicepwdDao ervicepwdDao;
	
	public List<Map<String, Object>> getServicePwdByBV(String barcode,String vin) throws SystemException {
		return ervicepwdDao.getServicePwdByBV(barcode,vin);
	}

	 
	public List<Map<String, Object>> getServicePwdByTimes(String begintime, String endtime) throws SystemException {
		return ervicepwdDao.getServicePwdByTimes(begintime,endtime);
	}

	 
	public List<Map<String, Object>> getServicePwdByTimes(Map<String, String> params) throws SystemException {
		return ervicepwdDao.getServicePwdByTimes(params);
	}

}
