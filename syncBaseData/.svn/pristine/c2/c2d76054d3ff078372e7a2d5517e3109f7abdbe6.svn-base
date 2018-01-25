package com.gboss.dao;

import java.util.List;

import org.springframework.dao.RecoverableDataAccessException;

import com.gboss.pojo.SyncDate;

/**
 * @Package:com.gboss.dao
 * @ClassName:SyncDateDao
 * @Description:TODO
 * @author:bzhang
 * @date:2015-3-18 上午11:13:22
 */
public interface SyncDateDao extends BaseDao {

	public SyncDate getSyncDateByName(String name) throws RecoverableDataAccessException;

	public List<SyncDate> getSyncDates();

	public void updateSybcDate(SyncDate sd);

	public void saveSybcDate(SyncDate sd);

}
