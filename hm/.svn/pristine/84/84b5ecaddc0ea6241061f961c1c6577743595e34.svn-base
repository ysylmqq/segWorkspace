package com.gboss.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.CustomerDao;
import com.gboss.pojo.Customer;
import com.gboss.service.CustomerService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

@Service("CustomerService")
@Transactional
public class CustomerServiceImpl extends BaseServiceImpl implements
		CustomerService {

	@Autowired
	@Qualifier("CustomerDao")
	private CustomerDao customerDao;

	@Override
	public boolean is_repeat(Customer customer) {
		return customerDao.is_repeat(customer);
	}

	@Override
	public Long add(Customer customer) {
		save(customer);
		return customer.getCustomer_id();
	}

	@Override
	public Page<Customer> search(PageSelect<Customer> pageSelect, Long subco_no) {
		return customerDao.search(pageSelect, subco_no);
	}
	
	@Override
	public String getCustomerPhone(Long id) throws SystemException {
		
		return customerDao.getCustomerPhones(id);

	}


	@Override
	public void delete(Long id) {
		delete(Customer.class, id);
	}


	@Override
	public HashMap<String, Object> getDetailMsg(Long id) throws SystemException {
		HashMap<String, Object> result = customerDao.getDetailMsg(id);
		return result;
	}

	@Override
	public Customer getCustomer(Long id) {
		return customerDao.getCustomer(id);
	}

	@Override
	public HashMap<String, Object> getDetailMsgBycl(String call_letter)
			throws SystemException {
		return customerDao.getDetailMsgBycl(call_letter);
	}

	@Override
	public Customer getCustomer(String customer_name) {
		return customerDao.getCustomer(customer_name);
	}

}
