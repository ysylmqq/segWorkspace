package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UnitSetUp;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IUnitService
 * @Description:操作终端信息接口
 * @author:lxj
 * @date:Dec 11, 2012 2:25:45 PM
 *
 */
public interface IUnitService extends BaseService<UnitPOJO> {

	/**
	 * @Title:getUnits
	 * @Description:分页显示终端信息
	 * @param unit
	 * @param pageSelect
	 * @return
	 * @throws
	 */
	public HashMap<String, Object> getUnits(UnitPOJO unit,
			PageSelect pageSelect) throws Exception;

	/**
	 * @Title:getList
	 * @Description:终端信息列表
	 * @param unit
	 * @return
	 * @throws
	 */
	public List<UnitPOJO> getList(UnitPOJO unit) throws Exception;

	/**
	 * @Title:getUnitById
	 * @Description:根据UnitId得到终端信息
	 * @param unitId
	 * @return
	 * @throws
	 */
	public UnitPOJO getUnitById(String unitId) throws Exception;

	/**
	 * @Title:delUnitById
	 * @Description:根据UnitId删除终端信息
	 * @param unitId
	 * @return
	 * @throws
	 */
	public boolean delUnitById(String unitId) throws Exception;

	/**
	 * @Title:saveOrUpdate
	 * @Description:保存或者更新终端信息
	 * @param unit
	 * @return
	 * @throws
	 */
	public boolean saveOrUpdate(UnitPOJO unit) throws Exception;
	
	public boolean saveOrUpdateUtilSetUp(Map map) throws Exception;

	/**
	 * @Title:getUnitBySnSID
	 * @Description:根据终端序列号和供应商Id得到终端信息，终端防重处理
	 * @param unit
	 * @return
	 * @throws
	 */
	public List<UnitPOJO> getUnitBySnSID(UnitPOJO unit) throws Exception;

	/**
	 * @Title:addUnits
	 * @Description:批量插入终端信息
	 * @param units
	 * @return
	 * @throws
	 */
	public HashMap<String, Object> addUnits(List<UnitPOJO> units) throws Exception;
	
	/**
	 * @Title:getUnitBySimNo
	 * @Description:根据Sim防重处理
	 * @param unit
	 * @return
	 * @throws
	 */
	public List<UnitPOJO> getUnitBySimNo(UnitPOJO unit) throws Exception;
	/**
	　　* 函 数 名 :deleteUnits
	　　* 功能描述：批量彻底删除终端
	　　* 输入参数:
	　　* @param 
	　　* @return int
	　　* @throws 无异常处理　　
	　　* 创 建 人:周峰炎
	　　* 日 期:2013-7-4
	　　* 修 改 人:
	　　* 修 改 日 期:
	　　* 修 改 原 因:
	 */
	public int deleteUnits(List<String> unitIds) throws Exception;
	
	/**
	　　* 函 数 名 :updateUnitsIsValid
	　　* 功能描述：批量修改终端的有效性
	　　* 输入参数:
	　　* @param 
	　　* @return int
	　　* @throws 无异常处理　　
	　　* 创 建 人:周峰炎
	　　* 日 期:2013-7-4
	　　* 修 改 人:
	　　* 修 改 日 期:
	　　* 修 改 原 因:
	　　
	 */
	public int updateUnitsIsValid(List<UnitPOJO> unitIds) throws Exception;
	
	public Map findUnitSetUp(Map map,PageSelect pageSelect) throws Exception;
	
	public List<UnitSetUp> findUnitSetUp2(Map map) throws Exception;
	
	public int findVehicleByvehicleDef(String vehicleDef) throws Exception;
	
	public int findUtilSetUpByvehicleDef(String vehicleDef) throws Exception;

	HashMap<String, Object> addUnitsAndSimServer(List<UnitPOJO> units)
			throws Exception;
	
	
}