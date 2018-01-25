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
	
	public Long getIdByVin(String vin);
	
	public boolean is_repeat(Vehicle vehicle);
	
	public Long add(Vehicle vehicle);
	
	public void delete(Long id);
	
	public List<Vehicle> getVehiclesByCustid(Long cust_id);

	public boolean is_exist(Vehicle vehicle);
	
	
}

