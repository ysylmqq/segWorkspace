package com.chinagps.driverbook.service;

import com.chinagps.driverbook.pojo.OpUnit;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.pojo.Vehicle;

public interface IVehicleService extends BaseService<Vehicle> {
	
	public ReturnValue editVehicleAndCustOilPrice(Vehicle vehicle, Long subcoNo, String customerName, ReturnValue rv) throws Exception;
	
	public ReturnValue bind(Long opId, String callLetter, String servPwd, ReturnValue rv) throws Exception;
	
	public int unbind(OpUnit opUnit) throws Exception;
	
	public ReturnValue listByOpIdAndOrigin(Long opId, Integer origin, ReturnValue rv) throws Exception;
	
	public ReturnValue verifyServicePassword(Vehicle vehicle, ReturnValue rv) throws Exception;
	
}
