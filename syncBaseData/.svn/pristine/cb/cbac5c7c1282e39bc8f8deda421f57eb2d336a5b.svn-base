package com.gboss.service;

import java.util.Map;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;

/**
 * 车载电话与服务密码
 *
 */
public interface ServicepwdService extends BaseService{
	
	List<Map<String, Object>> getServicePwdByBV(String barcode,String vin) throws SystemException ;
	
	List<Map<String, Object>> getServicePwdByTimes(String begintime,String endtime) throws SystemException ;
	
	List<Map<String, Object>> getServicePwdByTimes(Map<String,String> params) throws SystemException ;
	
}
