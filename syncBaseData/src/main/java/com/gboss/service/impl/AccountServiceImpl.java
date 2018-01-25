package com.gboss.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gboss.dao.AccountDao;
import com.gboss.service.AccountService;

@Service("accountService")
public class AccountServiceImpl extends BaseServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao accountDao;
	
	public Map<String, Object> getAccountInfoByVin(String vin) {
		return accountDao.getAccountInfoByVin(vin);
	}

}
