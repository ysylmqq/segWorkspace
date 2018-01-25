package com.gboss.dao;

import java.util.List;

import com.gboss.pojo.Driver;

/**
 * @Package:com.gboss.dao
 * @ClassName:DriverDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-21 上午11:22:45
 */
public interface DriverDao extends BaseDao {
	
	public List<Long> getDriverids(Long vehicle_id);
	
	public List<Driver> getDrivers(Long vehicle_id);
	
	public void deleteByVehicleid(Long vehicle_id);
	
	public void deleteByCustomerid(Long cust_id);

}

