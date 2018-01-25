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
	
	public Vehicle getVehicleByid(Long id);
	
	public Long getIdByPlate(String plate_no);
	
	public boolean is_repeat(Vehicle vehicle);
	
	public Long add(Vehicle vehicle);
	
	public void delete(Long id);
	
	public Page<Vehicle> search(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	public Page<Vehicle> searchlargecustVehicle(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	public Page<Vehicle> searchprivateVehicle(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	public List<Vehicle> getVehiclesByCustid(Long cust_id);
	
	public List<HashMap<String, Object>> getVehicleTree(Customer customer);
	
	public Page<HashMap<String, Object>> findVehiclePage(PageSelect<Map<String, Object>> pageSelect, Long companyno) throws SystemException;

	public boolean is_exist(Vehicle vehicle);
	
	public List<HashMap<String, Object>> countServiceFeePage(Long companyno,Map map ) throws SystemException;
	
	public Page<HashMap<String, Object>> findServiceFeePage(Long companyno, PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	public Page<HashMap<String, Object>> findOnlineNoFeePage(Long companyno, PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	public List<HashMap<String, Object>> findOnlineNoFeeList(Long companyno,Map map ) throws SystemException;
	
	public List<HashMap<String, Object>> webgisList(String parameter, Long subco_no);
	
}

