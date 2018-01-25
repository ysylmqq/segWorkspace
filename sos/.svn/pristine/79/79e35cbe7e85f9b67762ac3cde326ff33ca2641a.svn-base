package com.gboss.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Policy;

/**
 * @Package:com.gboss.dao
 * @ClassName:PolicyDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-29 下午4:11:26
 */
public interface PolicyDao extends BaseDao {

	public int getMaxPolicyNo(String date) throws SystemException;
	
	public HashMap<String,Object> getDetailMsgByCarNum(String carNum)throws SystemException;
	
	public List<HashMap<String, Object>> getTodayPolicy(Long companyId)throws SystemException;
	
	public Policy getPolicy(Long vehicle_id)throws SystemException;

	public int countCustomerInfo(Map<String, Object> conditionMap, Long commpanyNo) throws SystemException;
	
	public List<HashMap<String, Object>> findCustomerInfo(Map<String, Object> conditionMap, Long companyno, String order,boolean isDesc,int pn, int pageSize) throws SystemException;
	
	public List<HashMap<String, Object>> findPolicys(Long companyno, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countPolicys(Long companyno, Map<String, Object> conditionMap) throws SystemException;
	
	public HashMap<String,Object> getEditMsgByCarNum(Long id)throws SystemException;
	
	public int operatePolicy(List<Long> ids, Integer type) throws SystemException;
	
	public HashMap<String, Object> getCusMsg(Long vehicle_id)throws SystemException;
	
	public HashMap<String, Object> getPolicyMsgByNum(Long pid)throws SystemException;
	
	public Policy getPolicyBynum(String policy_num) throws SystemException;
	
	public List<HashMap<String, Object>> findPolicysRecords(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countPolicysRecords(Map<String, Object> conditionMap) throws SystemException;
	
	public Boolean isRightTime(Long insurance_id, Long vehicle_id, Date startDate, Date endDate)throws SystemException;
	
	public boolean is_exist(Long insurance_id, String policy_no);
	
	public List<Long> getPolicys(Policy policy);
	
}


