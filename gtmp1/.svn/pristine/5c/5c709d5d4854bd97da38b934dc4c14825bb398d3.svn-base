package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
@Component
public interface VehicleMapper<T extends VehiclePOJO> extends BaseSqlMapper<T> {
	
	public VehiclePOJO getVehicleByVehicleName(Map<String, Serializable> map) throws Exception;
	
	public int addBatchByProc(Map<String,Object> map) throws Exception;
	
	public int addVU(VehiclePOJO pojo) throws Exception;
	
	public int removeById(String vehicleId) throws Exception;
	
	public List<String> getUnitIdList() throws Exception;
	
	public UnitPOJO searchByUnitId(String unitId) throws Exception;
	
	public int addTest(T entity) throws Exception;
	
	public int getTestById(Long vehicleId) throws Exception;
	
	public int editTest(T entity) throws Exception;
	
	public int alterVehicle(T entity) throws Exception;
	
	public int updateUnitStatus(T entity) throws Exception;
	
	public int updateUnitStatus1(T entity) throws Exception;
	
	public int deleteVehicleUnit(T entity) throws Exception;
	
	public TLastPositionPOJO selectLastPosition(T entity) throws Exception;
	
	public TLastConditionsPOJO selectLastCondition(T entity) throws Exception;
	
	public List<TestCommandPOJO> selectExcel(Map<String, Serializable> map) throws Exception;

	public List<TestCommandPOJO> getCommandType()  throws Exception;
	
	public int deleteVehicles(List<String> vehicleIds) throws Exception;
	
	public int updateVehiclesIsValid(List<String> vehicleIds) throws Exception;
	
	//public int addVehicleUnit(Map<String,Object> map) throws DataAccessException;
	
	public int editStatus(VehiclePOJO vehiclePOJO) throws Exception;
	
	public List<UnitPOJO> getUsefulUnitInfoList() throws Exception;
	
	/**
	 * 根据vehicleId查询
	 * @param vehicleId
	 * @return
	 * @throws Exception
	 */
	public VehiclePOJO selectVehicleByVehicleId(String vehicleId)throws Exception;
	
	/**
	 * 更新销售日期
	 * @param vehiclePOJO
	 */
	public void updateVehicleSaleDate(VehiclePOJO vehiclePOJO);
	
}