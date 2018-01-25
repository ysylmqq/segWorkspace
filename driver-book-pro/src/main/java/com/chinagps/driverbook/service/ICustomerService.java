package com.chinagps.driverbook.service;

import com.chinagps.driverbook.pojo.AppBind;
import com.chinagps.driverbook.pojo.Customer;
import com.chinagps.driverbook.pojo.Driver;
import com.chinagps.driverbook.pojo.Linkman;
import com.chinagps.driverbook.pojo.ReturnValue;

public interface ICustomerService extends BaseService<Customer> {
	
	public ReturnValue bind(AppBind appBind, ReturnValue rv) throws Exception;
	
	public ReturnValue showBind(String callLetter, ReturnValue rv) throws Exception;
	
	public ReturnValue checkServicePassword(Customer customer, ReturnValue rv) throws Exception;
	
	public ReturnValue editServicePassword(Customer customer, ReturnValue rv) throws Exception;
	
	public ReturnValue findVehiclesByCustomerId(String customerId, Long subcoNo, ReturnValue rv) throws Exception;
	
	public void saveLinkmanAndDriver(Linkman linkman, Driver driver, String customerName) throws Exception;
	
}
