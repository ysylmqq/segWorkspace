package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Pack;

/**
 * @Package:com.gboss.dao
 * @ClassName:PackDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-11-5 下午3:53:47
 */
public interface PackDao extends BaseDao {
	
	public List<HashMap<String, Object>> findPackList(Long companyno, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countPack(Long companyno, Map<String, Object> conditionMap) throws SystemException;
	
	public boolean isExist(Pack pack)throws SystemException ;
	
	public int delete(List<Long> ids) throws SystemException;

}

