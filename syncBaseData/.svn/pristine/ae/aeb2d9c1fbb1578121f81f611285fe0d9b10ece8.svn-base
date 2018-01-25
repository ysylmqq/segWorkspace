package com.gboss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemException;
import com.gboss.dao.CallletterDao;
import com.gboss.pojo.Preload;
import com.gboss.service.CallletterService;

@Service("callletterService")
public class CallletterServiceImpl extends BaseServiceImpl implements CallletterService {
	
	@Autowired
	private CallletterDao callletterDao;


	 
	public List<Preload> getCallLettersByTimes(String begintime, String endtime,String barcode) throws SystemException {
		return callletterDao.getCallLettersByTimes(begintime, endtime,barcode);
	}


	 
	public List<Preload> getCallLettersByTimes(Map<String, String> params) throws SystemException {
		return callletterDao.getCallLettersByTimes(params);
	}

}
