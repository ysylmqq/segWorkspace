package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Vehicle;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:VehicleService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:13:03
 */
public interface VehicleService extends BaseService {
	
	/**
	 * 通过id查询车辆
	 * @param id
	 * @return
	 */
	public Vehicle getVehicleByid(Long id);
	
	/**
	 * 通过车牌查询车辆
	 * @param plate_no
	 * @return
	 */
	public Long getIdByPlate(String plate_no);
	
	/**
	 * 查询车牌号码是否重复 true:重复 false：不存在
	 * @param vehicle
	 * @return
	 */
	public boolean is_repeat(Vehicle vehicle);
	
	/**
	 * 添加车辆信息
	 * @param vehicle
	 * @return
	 */
	public Long add(Vehicle vehicle);
	
	/**
	 * 通过车辆id删除车辆
	 * @param id
	 */
	public void delete(Long id);
	
	public Page<Vehicle> search(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	public Page<Vehicle> searchlargecustVehicle(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	public Page<Vehicle> searchprivateVehicle(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	/**
	 * 查询用户下车辆列表
	 * @param cust_id
	 * @return
	 */
	public List<Vehicle> getVehiclesByCustid(Long cust_id);
	
	/**
	 * 获取用户下车辆树
	 * @param customer
	 * @return
	 */
	public List<HashMap<String, Object>> getVehicleTree(Customer customer);
	
	/**
	 * 分页查询车辆
	 * @param pageSelect
	 * @param companyno
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findVehiclePage(PageSelect<Map<String, Object>> pageSelect, Long companyno) throws SystemException;

	/**
	 * 查询车架号是否存在 true:存在 false:不存在
	 * @param vehicle
	 * @return
	 */
	public boolean is_exist(Vehicle vehicle);
	
	/**
	 * 获取车辆、车载、客户信息
	 * @param companyno
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> countServiceFeePage(Long companyno,Map map ) throws SystemException;
	
	/**
	 * 分页获取车辆、车载、客户信息
	 * @param companyno
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findServiceFeePage(Long companyno, PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	/**
	 * 分页查询在网未计费车辆信息
	 * @param companyno
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findOnlineNoFeePage(Long companyno, PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	/**
	 * 查询在网未计费车辆信息列表
	 * @param companyno
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findOnlineNoFeeList(Long companyno,Map map ) throws SystemException;
	
	/**
	 * 通过车牌、车载电话模糊查询车辆列表
	 * @param parameter
	 * @param subco_no
	 * @return
	 */
	public List<HashMap<String, Object>> webgisList(String parameter, Long subco_no);
	
}