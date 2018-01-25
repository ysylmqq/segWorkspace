package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Vehicle;

/**
 * @Package:com.gboss.dao
 * @ClassName:VehicleDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午2:56:45
 */
public interface VehicleDao extends BaseDao {
	
	public Long add(Vehicle vehicle);
	
	public Vehicle getVehicleByid(Long id);
	
	public Vehicle getVehicleByCallLetter(String call_letter);
	
	public Long getIdByPlate(String plate_no);
	
	public Long getIdByVin(String vin);
	
	public boolean is_repeat(Vehicle vehicle);
	
	public List<Vehicle> getVehiclesByCustid(Long cust_id);
	
	public boolean is_exist(Vehicle vehicle);
	
}

