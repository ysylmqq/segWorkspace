package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;

public interface ServicepwdDao extends BaseDao  {
	
	/**
	 * 根据call_letter 获取服务密码
	 * @param call_letter
	 * @return
	 * @throws SystemException
	 */
	List<Map<String, Object>> getServicePwdByBV(String barcode,String vin) throws SystemException ;
	
	List<Map<String, Object>> getServicePwdByTimes(String begintime,String endtime) throws SystemException ;
	
	public List<Map<String, Object>> getServicePwdByTimes(Map<String, String> params) throws SystemException;
	
}
