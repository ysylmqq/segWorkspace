package com.gboss.dao;

import java.util.List;

import com.gboss.pojo.Servicefee;

/**
 * @Package:com.gboss.dao
 * @ClassName:ServicefeeDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-8 下午2:18:12
 */
public interface ServicefeeDao extends BaseDao{
	
	public List<Servicefee> getByVehicle_id(Long vehicle_id);

}

