package com.chinagps.driverbook.dao;

import com.chinagps.driverbook.pojo.Customer;

public interface CustomerMapper extends BaseSqlMapper<Customer> {
	
	public int editServicePwd(Customer customer);
	
	public Customer findByCallLetter(String callLetter);
	
}
