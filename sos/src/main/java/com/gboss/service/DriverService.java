package com.gboss.service;

import java.util.List;

import com.gboss.pojo.Driver;

/**
 * @Package:com.gboss.service
 * @ClassName:DriverService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-21 上午10:32:32
 */
public interface DriverService extends BaseService {
	
	/**
	 * 添加司机信息
	 * @param driver
	 */
	public void add(Driver driver);
	
	/**
	 * 获取车辆司机列表
	 * @param vehicle_id
	 * @return
	 */
	public List<Long> getDriverids(Long vehicle_id);
	
	/**
	 * 获取车辆对应的司机列表
	 * @param vehicle_id
	 * @return
	 */
	public List<Driver> getDrivers(Long vehicle_id);
	
	/**
	 * 删除车辆对应的司机
	 * @param vehicle_id
	 */
	public void deleteByVehicleid(Long vehicle_id);
	
	/**
	 * 删除客户关联的司机
	 * @param customer_id
	 */
	public void deleteByCustomerid(Long customer_id);

}

