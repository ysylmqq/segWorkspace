package com.gboss.dao;

import java.util.HashMap;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Customer;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

public interface CustomerDao extends BaseDao {

	public Long add(Customer customer);

	public boolean is_repeat(Customer customer);

	public Page<Customer> search(PageSelect<Customer> pageSelect, Long subco_no);

	public Customer getCustomer(Long id);

	public Customer getCustomer(String customer_name);

	public String getCustomerPhones(Long customerId) throws SystemException;

	public HashMap<String, Object> getDetailMsg(Long id) throws SystemException;

	public HashMap<String, Object> getDetailMsgBycl(String call_letter)
			throws SystemException;

	public void updateCustSales(Long customerId);

}
