package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;

/**
 * @Package:com.gboss.dao
 * @ClassName:ObdFaultDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-25 上午11:14:10
 */
public interface ObdFaultDao extends BaseDao{
	
	public List<HashMap<String, Object>> findobdFaultList(Long companyId, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countObdFault(Long companyId, Map<String, Object> conditionMap) throws SystemException;

}

