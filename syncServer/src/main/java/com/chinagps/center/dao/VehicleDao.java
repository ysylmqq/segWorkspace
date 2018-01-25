package com.chinagps.center.dao;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.pojo.Vehicle;

public interface VehicleDao extends BaseDao {

	public Vehicle getByPlateNo(String plate_no)throws SystemException;

	public Long add(Vehicle vehicle)throws SystemException;

}
