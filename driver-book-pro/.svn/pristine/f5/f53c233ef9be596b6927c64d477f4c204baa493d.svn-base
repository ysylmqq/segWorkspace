package com.chinagps.driverbook.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinagps.driverbook.pojo.OpUnit;

public interface OpUnitMapper extends BaseSqlMapper<OpUnit> {
	
	public Map<String, Long> validateByOpidAndUnitId(@Param("opId") Long opId, @Param("unitId") Long unitId);  
	
}
