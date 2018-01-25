package com.chinagps.driverbook.dao;

import com.chinagps.driverbook.pojo.Maintain;
import com.chinagps.driverbook.pojo.MaintainItems;
import com.chinagps.driverbook.pojo.MaintainRule;

public interface MaintainMapper extends BaseSqlMapper<Maintain> {
	
	public Maintain findByVehicleId(String vehicleId);
	
	public MaintainRule findRuleByVehicleId(String vehicleId);
	
	public int nextPeriod(String vehicleId);
	
	public MaintainItems findItemsByModel(String model);
	
}
