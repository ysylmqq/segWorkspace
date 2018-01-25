package com.gboss.dao;

import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Repair;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.dao
 * @ClassName:RepairDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2015-2-11 下午3:07:19
 */
public interface RepairDao extends BaseDao{
	
	public List<Map<String,Object>> findRepairs(Map<String, Object> conditionMap, String order,
			boolean isDesc, int pageNo, int pageSize)throws SystemException;
	
	public Integer countRepairs(Map<String, Object> conditionMap)throws SystemException;

	public Repair getRepairByVehicleId(Map<String, Object> param)throws SystemException;

	public Integer addReserve(Map<String, Object> param)throws SystemException;

	public Integer updateReserve(Map<String, Object> param)throws SystemException;

	public Integer addRepairLog(Map<String, Object> param)throws SystemException;

	public Integer assignJob(Map<String, Object> param)throws SystemException;

	public Page<Repair> search(PageSelect<Repair> pageSelect, Long subcoNo)throws SystemException;

	public Integer saveRepairReserve(Map<String, Object> param)throws SystemException;

	public Integer saveRepairPaiGong(Map<String, Object> param)throws SystemException;

	public Integer saveRepairDoing(Map<String, Object> param)throws SystemException;

	public Integer saveRepairSuccess(Map<String, Object> param)throws SystemException;

	public Integer saveRepairUndo(Map<String, Object> param)throws SystemException;

	public int countRepairOrder(Map<String, Object> filter)throws SystemException;

	public List<Map<String, Object>> findRepairOrder(Map<String, Object> filter, String order,
			boolean is_desc, int pageNo, int pageSize)throws SystemException;
	
}

