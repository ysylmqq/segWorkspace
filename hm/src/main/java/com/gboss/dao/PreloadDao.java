package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Preload;

/**
 * @Package:com.gboss.dao
 * @ClassName:SimDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-23 上午11:06:07
 */
public interface PreloadDao extends BaseDao {

	public List<HashMap<String, Object>> findPreloadList(Long companyId, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countPreload(Long companyId, Map<String, Object> conditionMap) throws SystemException;
	
	public int operate(List<Long> ids, Integer type) throws SystemException;
	
	public Boolean isExist_phone(Preload sim)throws SystemException;
	
	public Boolean isExist_vin(Preload sim)throws SystemException;
	
	public Boolean isExist_barcode(Preload sim)throws SystemException;
	
	public Preload getPreloadByTbox(String barcode)throws SystemException;
	
	public Preload getPreloadByCl(String call_letter)throws SystemException;
	
	public Boolean isExist_imei(Preload sim)throws SystemException;
	
	public Boolean isExist_call_letter(Preload sim)throws SystemException;
	
	public int operateCombo(List<Long> ids, Integer type) throws SystemException;
	
	public List<Preload> getSims(Map<String,Object> params)throws SystemException;
	
}

