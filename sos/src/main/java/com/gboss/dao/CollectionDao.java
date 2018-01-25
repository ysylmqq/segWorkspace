package com.gboss.dao;

import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Collection;

/**
 * @Package:com.gboss.dao
 * @ClassName:CollectionDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-9 下午3:22:03
 */
public interface CollectionDao extends BaseDao {
	
	public List<Collection> getCollections(Long cust_id)throws SystemException;
	
	public Collection getCollectionByCustId(Long cust_id)throws SystemException;
	
	public Collection getCollectionByctno(Collection collection)throws SystemException;
	
	public Long addCollection(Collection collection)throws SystemException;

	public Collection getCollectionByCondition(Map<String, Object> param)throws SystemException;

	public Map<String, Object> getCollectionByUnit(Long unitId, Long customerId)throws SystemException;

}

