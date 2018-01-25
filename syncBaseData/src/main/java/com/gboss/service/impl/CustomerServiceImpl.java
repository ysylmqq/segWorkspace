package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemException;
import com.gboss.dao.CustomerDao;
import com.gboss.pojo.Customer;
import com.gboss.service.CustomerService;

@Service("CustomerService")
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {

	@Autowired
	@Qualifier("CustomerDao")
	private CustomerDao customerDao;
	
	public boolean is_repeat(Customer customer) {
		return customerDao.is_repeat(customer);
	}
	 
	public Long add(Customer customer) {
		try {
			save(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer.getCustomer_id();
	}
	 
	public String getCustomerPhone(Long id) throws SystemException {
		return customerDao.getCustomerPhones(id);
	}
	 
	public void delete(Long id) {
		customerDao.delete(Customer.class, id);
	}

	public Customer getCustomer(Long id) {
		return customerDao.getCustomer(id);
	}

	public Customer getCustomer(String customer_name) {
		return customerDao.getCustomer(customer_name);
	}

}
