package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UnitSetUp;

@Component
public interface UnitMapper<T extends UnitPOJO> extends BaseSqlMapper<T> {

	public UnitPOJO getById(String unitId) throws Exception;

	public int removeById(String unitId) throws Exception;

	public List<UnitPOJO> getBySnSID(Map<String, Serializable> map)
			throws Exception;

	public int addBatchByProc(Map<String, Object> map) throws Exception;

	public List<UnitPOJO> getBySimNo(Map<String, Serializable> map)
			throws Exception;

	public int deleteUnits(List<String> unitIds) throws Exception;

	public int updateUnitsIsValid(List<UnitPOJO> unitIds) throws Exception;
	
	public int countUnitSetUp(Map map) throws Exception;
	public List<UnitSetUp> findUnitSetUp(Map map,RowBounds rowBounds)throws Exception;
	public List<UnitSetUp> findUnitSetUp(Map map)throws Exception;
	public void editUtilSetUp(Map map) throws Exception;
	public void addUtilSetUp(Map map) throws Exception;
	public int findVehicleByvehicleDef(String  vehicleDef) throws Exception;
	public int findUtilSetUpByvehicleDef(String  vehicleDef) throws Exception;
	
	
}