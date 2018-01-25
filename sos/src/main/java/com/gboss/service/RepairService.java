package com.gboss.service;

import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Repair;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:RepairService
 * @Description:TODO
 * @author:xiaoke
 * @date:2015-2-11 上午11:33:55
 */
public interface RepairService extends BaseService {
	
	public Page<Map<String,Object>> findRepairPage(PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	public Repair getRepairByVehicleId(Map<String, Object> param)throws SystemException;

	public Map<String, Object> saveVehicleReserve(Map<String, Object> param)throws SystemException;

	public Map<String, Object> sendReserve(Map<String, Object> param)throws SystemException;

	public Page<Repair> search(PageSelect<Repair> pageSelect, Long subcoNo)throws SystemException;

	public Map<String, Object> saveRepairReserve(Map<String, Object> param)throws SystemException;

	public Map<String, Object> saveRepairPaiGong(Map<String, Object> param)throws SystemException;

	public Map<String, Object> saveRepairDoing(Map<String, Object> param)throws SystemException;

	public Map<String, Object> saveRepairSuccess(Map<String, Object> param)throws SystemException;

	public Map<String, Object> saveRepairUndo(Map<String, Object> param)throws SystemException;

	public List<Map<String, Object>> findRepairDt(Map<String, Object> map)throws SystemException;

	public Page<Map<String, Object>> findRepairOrderPage(PageSelect<Map<String, Object>> pageSelect)throws SystemException;

}

