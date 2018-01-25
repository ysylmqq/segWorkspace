package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;

/**
 * @Package:com.gboss.dao
 * @ClassName:PaymentSimDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-29 下午4:29:20
 */
public interface PaymentSimDao extends BaseDao {
	
	public List<HashMap<String, Object>> findRecordsPage(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countRecords(Map<String, Object> conditionMap) throws SystemException;
	
	
	public List<HashMap<String, Object>> findPaymentSimPage(Long companyId,Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countPaymentSimPage(Long companyId,Map<String, Object> conditionMap) throws SystemException;

}

