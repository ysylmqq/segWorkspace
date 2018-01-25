package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.CustVehicle;

/**
 * @Package:com.gboss.dao
 * @ClassName:CustVehicleDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-29 下午1:51:36
 */
public interface CustVehicleDao extends BaseDao {
	
	public List<CustVehicle> getByVehicleid(Long vehicle_id);
	
	public List<CustVehicle> getByCustomerid(Long cust_id);
	
	public List<HashMap<String, Object>> findLargeVehicles(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;
	
	public int countLargeVehicles(Map<String, Object> conditionMap) throws SystemException;
	
	public List<HashMap<String, Object>> findLargeVehicleList(Long cust_id, String lockTime) throws SystemException;

	public Long add(CustVehicle cv)throws SystemException;


}

