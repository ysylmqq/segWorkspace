package com.gboss.service;

import java.util.HashMap;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Customer;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

public interface CustomerService extends BaseService {
	
	public boolean is_repeat(Customer customer);
	
	public void delete(Long id);
	
	public Long add(Customer customer);
	
	public Customer getCustomer(Long id);
	
	public Customer getCustomer(String customer_name);
	
	public Page<Customer> search(PageSelect<Customer> pageSelect, Long subco_no);
	
	public String getCustomerPhone(Long id)throws SystemException;
	
	public HashMap<String,Object> getDetailMsg(Long id)throws SystemException;
	
	public HashMap<String, Object> getDetailMsgBycl(String call_letter)throws SystemException;
	

}
