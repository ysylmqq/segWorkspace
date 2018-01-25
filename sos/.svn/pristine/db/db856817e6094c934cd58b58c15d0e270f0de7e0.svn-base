package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;

/**
 * @Package:com.gboss.dao
 * @ClassName:FaultcodesDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 上午10:13:09
 */
public interface FaultcodesDao extends BaseDao {
	
	public List<HashMap<String, Object>> findFaultcodesPage(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countFaultcodes(Map<String, Object> conditionMap) throws SystemException;
}

