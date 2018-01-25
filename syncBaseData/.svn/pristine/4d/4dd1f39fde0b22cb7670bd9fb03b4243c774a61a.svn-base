package com.gboss.service;

import java.util.List;

import org.springframework.dao.RecoverableDataAccessException;

import com.gboss.pojo.SyncDate;
import com.mysql.jdbc.CommunicationsException;

/**
 * @Package:com.gboss.service
 * @ClassName:SyncDateService
 * @Description:TODO
 * @author:bzhang
 * @date:2015-3-18 上午11:14:45
 */
public interface SyncDateService extends BaseService {
	
	public SyncDate getSyncDateByName(String name) throws RecoverableDataAccessException;
	
	public List<SyncDate> getSyncDates();
	
	public void updateSybcDate(SyncDate sd);
	
	public void saveSybcDate(SyncDate sd);

}

