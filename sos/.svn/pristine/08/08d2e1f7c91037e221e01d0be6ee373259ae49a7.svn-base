package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.FeePayment;

/**
 * @Package:com.gboss.dao
 * @ClassName:FeePaymentDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-7-14 下午1:54:04
 */
public interface FeePaymentDao extends BaseDao {
	
	public List<HashMap<String, Object>> getPaymentRecords(Long cust_id,Long vehicle_id,Long unit_id)throws SystemException;
	
	public Boolean getFeePayment(Long unitId, Integer feetype_id);
	
	public FeePayment getLastFeePayment(Long unitId, Integer feetype_id);

	public List<HashMap<String, Object>> findPaymentRecordsPage(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countPaymentRecords(Map<String, Object> conditionMap) throws SystemException;
	
	public List<HashMap<String, Object>> findPaymentRecordsPage2(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countPaymentRecords2(Map<String, Object> conditionMap) throws SystemException;
	
	public List<HashMap<String, Object>> findFeeDetailPage(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countFeeDetail(Map<String, Object> conditionMap) throws SystemException;
}



