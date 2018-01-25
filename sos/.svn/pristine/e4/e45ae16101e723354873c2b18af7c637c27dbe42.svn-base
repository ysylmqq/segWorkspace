package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.CustVehicle;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:CustVehicleService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-27 下午4:08:00
 */
public interface CustVehicleService extends BaseService {
	/**
	 * 添加客户车辆关联关系
	 * @param custVehicle
	 * @return
	 */
	public Long add(CustVehicle custVehicle);
	
	/**
	 * 主键id删除关联关系
	 * @param id
	 */
	public void delete(Long id);
	
	/**
	 * 获取车辆用户关系列表
	 * @param vehicle_id
	 * @return
	 */
	public List<CustVehicle> getByVehicleid(Long vehicle_id);
	
	/**
	 * 分页查询集客车辆列表信息
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findLargeVehicles(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	/**
	 * 获取客户车辆计费总计
	 * @param cust_id
	 * @param lockTime
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> getTotalFeeMsg(Long cust_id, String lockTime) throws SystemException;

	/**
	 * 获取集客车辆列表信息
	 * @param cust_id
	 * @param lockTime
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findLargeVehicleList(Long cust_id,String lockTime) throws SystemException;

	public List<CustVehicle> getByCustId(Long cust_id)throws SystemException;
}

