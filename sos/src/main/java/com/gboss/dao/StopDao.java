package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Stop;

/**
 * @Package:com.gboss.dao
 * @ClassName:StopDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-16 下午5:23:20
 */
public interface StopDao extends BaseDao {
	
	public List<Stop> getListByid(Long vehicle_id);
	
	public void deleteStop(Long vehicle_id, Integer type);
	
	public boolean isExist(Long vehicle_id, Integer type);
	
	public List<HashMap<String, Object>> findStops(Map<String, Object> params) throws SystemException;

	public int countStops(Map<String, Object> params)throws SystemException;

	public List<HashMap<String, Object>> findStopsPage(Map<String, Object> filter, String order, boolean is_desc, int pageNo, int pageSize)throws SystemException;

}

