package com.chinagps.driverbook.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinagps.driverbook.pojo.Vehicle;

public interface VehicleMapper extends BaseSqlMapper<Vehicle> {
	
	public Vehicle findByCallLetter(String callLetter);
	
	public List<Vehicle> findListByCustomerId(Map<String, Object> params);
	
	public List<Vehicle> findByOpId(@Param("origin") Integer origin, @Param("opId") Long opId);
	
}
