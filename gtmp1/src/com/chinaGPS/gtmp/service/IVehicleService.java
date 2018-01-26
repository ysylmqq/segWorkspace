package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * 
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IVehicleService
 * @Description:机械操作
 * @author:肖克
 * @date:Dec 14, 2012 11:59:59 AM
 *
 */
public interface IVehicleService{
	
	/**
	 * 
	 * @Title:getCommandType
	 * @Description:查找机械测试指令类型
	 * @param TestCommandPOJO
	 * @return
	 * @throws
	 */
	public List<TestCommandPOJO> getCommandType() throws Exception;
	
	/**
	 * 
	 * @Title:getVehicleByVehicleName
	 * @Description:通过机械名得到机械信息
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public VehiclePOJO getVehicleByVehicleName(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * 
	 * @Title:getVehicles
	 * @Description:获得机械信息列表，翻页
	 * @param vehiclePOJO
	 * @param HashMap<String, Object>
	 * @return
	 * @throws
	 */
	public HashMap<String, Object> getVehicles(VehiclePOJO vehiclePOJO,PageSelect pageSelect) throws Exception;
	
	/**
	 * 
	 * @Title:getVehiclesTest
	 * @Description:获得机械测试信息列表，翻页
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public HashMap<String, Object> getVehiclesTest(VehiclePOJO vehiclePOJO,PageSelect pageSelect) throws Exception;
	
	/**
	 * 
	 * @Title:selectExcel
	 * @Description:获得导出excel机械测试信息列表
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public List<TestCommandPOJO> selectExcel(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * 
	 * @Title:getByVihicleList
	 * @Description:获得导出excel机械注册信息列表
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public List<VehiclePOJO> getByVihicleList(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * 
	 * @Title:selectVehicleMod
	 * @Description:获得机械型号
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public VehiclePOJO selectVehicleMod(VehiclePOJO vehiclePOJO) throws Exception;
	
	
	
	
	
	/**
	 * 
	 * @Title:selectLastPosition
	 * @Description:查询测试时初始位置信息
	 * @param vehiclePOJO
	 * @param HashMap<String, Object>
	 * @return
	 * @throws
	 */
	public TLastPositionPOJO selectLastPosition(VehiclePOJO vehiclePOJO) throws Exception;
	
	
	/**
	 * 
	 * @Title:selectLastCondition
	 * @Description:查询测试时初始工况信息
	 * @param vehiclePOJO
	 * @param HashMap<String, Object>
	 * @return
	 * @throws
	 */
	public TLastConditionsPOJO selectLastCondition(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * 
	 * @Title:getList
	 * @Description:获得机械信息列表，ComboBox
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public List<VehiclePOJO> getList(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * 
	 * getTestById
	 * @Description:查询测试记录，根据vehicleId
	 * @param vehicleId
	 * @return
	 * @throws
	 */
	public int getTestById(Long vehicleId) throws Exception;
	
	
	/**
	 * 
	 * searchByUnitId
	 * @Description:查询终端信息，根据unitId
	 * @param unitId
	 * @return
	 * @throws
	 */
	public UnitPOJO searchByUnitId(String unitId) throws Exception;
	
	/**
	 * 
	 * @Title:getList
	 * @Description:获得终端信息列表，ComboBox
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public List<String> getUnitIdList() throws Exception;
	
	/**
	 * 
	 * @Title:getById
	 * @Description:通过机械ID获得机械信息
	 * @param id
	 * @return
	 * @throws
	 */
	public VehiclePOJO getById(Long id) throws Exception;
	
	/**
	 * 
	 * @Title:saveOrUpdate
	 * @Description:新增或修改机械信息
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public boolean saveOrUpdate(VehiclePOJO vehiclePOJO) throws Exception;
	
	public boolean updateStatus(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * 
	 * @Title:alterVehicle
	 * @Description:修改vehicle表
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public boolean alterVehicle(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * 
	 * @Title:updateUnitStatus
	 * @Description:修改unit表
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public boolean updateUnitStatus(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * 
	 * @Title:deleteVehicleUnit
	 * @Description:修改VehicleUnit表
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public boolean deleteVehicleUnit(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * 查询所有待安装的物料条码（机械注册页面扫条码用）
	 * @return
	 * @throws Exception
	 */
	public List<UnitPOJO> getUsefulUnitInfoList() throws Exception;
	
	
	
	/**
	 * 
	 * @Title:saveOrUpdate
	 * @Description:新增或修改机械信息
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public boolean saveExcel(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * 
	 * @Title:saveOrUpdateTest
	 * @Description:确认测试
	 * @param vehiclePOJO
	 * @return
	 * @throws
	 */
	public boolean saveOrUpdateTest(VehiclePOJO vehiclePOJO) throws Exception;
	
	/**
	 * @Title:addVehicles
	 * @Description:批量插入机械信息
	 * @param vehicles
	 * @return
	 * @throws
	 */
	public boolean addVehicles(List<VehiclePOJO> vehicles) throws Exception;
	
	/**
	 * @Title:delVehicleById
	 * @Description:根据vehicleId删除机械信息
	 * @param vehicleId
	 * @return
	 * @throws
	 */
	public boolean delVehicleById(Long vehicleId) throws Exception;
	
	/**
	 * @Title:delVehicles
	 * @Description:根据vehicleIds批量物理删除机械信息
	 * @param vehicleIds
	 * @return
	 * @throws
	 */
	public int delVehicles(List<String> vehicleIds) throws Exception;
	
	/**
	 * @Title:delVehicles
	 * @Description:根据vehicleIds批量还原机械
	 * @param vehicleIds
	 * @return
	 * @throws
	 */
	public int updateVehiclesIsValid(List<String> vehicleIds) throws Exception;
	
	/**
	 * 物理删除机械
	 * @param vehicleIds
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInRecycle(List<String> vehicleIds) throws Exception;
	
	/**
	 * 机械解绑定
	 * @param vehiclePOJO
	 * @return
	 * @throws Exception
	 */
	public boolean unbindVehicleUnit(VehiclePOJO VehiclePOJO) throws Exception;
	
	/**
	 * 根据id查询
	 * @param vehicleId
	 * @return
	 * @throws Exception
	 */
	public VehiclePOJO selectVehicleByVehicleId(String vehicleId )throws Exception;
	
	/**
	 * 更新销售日期
	 * @param VehiclePOJO
	 * @throws Exception
	 */
	public void updateVehicleSaleDate(VehiclePOJO VehiclePOJO )throws Exception;
}