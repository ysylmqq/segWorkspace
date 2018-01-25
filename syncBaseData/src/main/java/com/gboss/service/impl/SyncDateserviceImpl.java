package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;

import com.gboss.dao.SyncDateDao;
import com.gboss.pojo.SyncDate;
import com.gboss.service.SyncDateService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SyncDateserviceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2015-3-18 上午11:15:26
 */
@Service("syncDateService")
 
public class SyncDateserviceImpl extends BaseServiceImpl implements
		SyncDateService {
	
	@Autowired
	private SyncDateDao syncDateDao;

	 
	public SyncDate getSyncDateByName(String name) throws RecoverableDataAccessException {
		return syncDateDao.getSyncDateByName(name);
	}

	 
	public List<SyncDate> getSyncDates() {
		return syncDateDao.getSyncDates();
	}
	
	public static void main(String[] args) {
		ApplicationContext beanfactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		SyncDateService syncHelper = (SyncDateService)beanfactory.getBean("syncDateService");
		System.out.println(syncHelper.getSyncDates().size());
	}

	public void updateSybcDate(SyncDate sd) {
		syncDateDao.updateSybcDate(sd);
	}


	public void saveSybcDate(SyncDate sd) {
		syncDateDao.saveSybcDate(sd);
	}

}

