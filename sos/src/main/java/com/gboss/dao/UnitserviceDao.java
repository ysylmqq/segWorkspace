package com.gboss.dao;

import com.gboss.pojo.Unitservice;

/**
 * @Package:com.gboss.dao
 * @ClassName:UnitserviceDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-15 下午4:15:28
 */
public interface UnitserviceDao extends BaseDao {
	
	public Unitservice getByVehicle_id(Long vehicle_id);

}

