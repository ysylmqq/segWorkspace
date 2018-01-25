package com.gboss.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Policy;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:PolicyService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-29 下午4:14:32
 */
public interface PolicyService extends BaseService {
	
	public String getPolicytNo() throws SystemException;
	
	public HashMap<String,Object> getDetailMsgByCarNum(String carNum)throws SystemException;
	
	public List<HashMap<String, Object>> getTodayPolicy(Long companyId)throws SystemException;
	
	public Page<HashMap<String, Object>> findPolicys(Long companyno, PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	public Page<HashMap<String, Object>> findCusInfos(PageSelect<Map<String, Object>> pageSelect, Long companyno) throws SystemException;

	public HashMap<String,Object> getEditMsgByCarNum(Long id)throws SystemException;
	
	public int operatePolicy(List<Long> ids, Integer type) throws SystemException;
	
	public HashMap<String, Object> getPolicyMsgByNum(Long pid)throws SystemException;
	
	public HashMap<String, Object> getCusMsg(Long vehicle_id, Long opid)throws SystemException;
	
	public List<HashMap<String, Object>> getexportExcelPolicy(Long companyno, Map<String, Object> map) throws SystemException;
	
	public Policy getPolicyBynum(String policy_num) throws SystemException;
	
	public Page<HashMap<String, Object>> findPolicyPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	public Boolean isRightTime(Long insurance_id, Long vehicle_id, Date startDate, Date endDate)throws SystemException;
	
	public boolean is_exist(Long insurance_id, String policy_no);
	
	public List<Long> getPolicys(Policy policy);
}

