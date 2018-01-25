package com.gboss.dao;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Customer;

public interface CustomerDao extends BaseDao {

	public boolean is_repeat(Customer customer);

	public Customer getCustomer(Long id);

	public Customer getCustomer(String customer_name);

	public String getCustomerPhones(Long customerId) throws SystemException;

}
