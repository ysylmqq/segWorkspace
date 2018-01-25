package com.gboss.dao;

import java.util.HashMap;
import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.CustSales;
import com.gboss.pojo.Customer;
import com.gboss.pojo.UbiSales;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

public interface CustomerDao extends BaseDao {
	
	public Long add(Customer customer)throws SystemException;
	
	public boolean is_repeat(Customer customer)throws SystemException;
	
	public Page<Customer> search(PageSelect<Customer> pageSelect, Long subco_no)throws SystemException;
	
	public Customer getCustomer(Long id)throws SystemException;
	
	public Customer getCustomer(String customer_name)throws SystemException;
	
	public String getCustomerPhones(Long customerId)throws SystemException;
	
	public HashMap<String,Object> getDetailMsg(Long id)throws SystemException;
	
	public HashMap<String,Object> getDetailMsgBycl(String call_letter)throws SystemException;
	
	public void updateCustSales(Long customerId)throws SystemException; 
	
	public CustSales getCustSales(Long customerId)throws SystemException;
	
	public UbiSales getUbiSaler(int salesId)throws SystemException;
	
	public List<String> getCustids(Long subco_no)throws SystemException;
	
	public Page<UbiSales> getUbiSales(PageSelect<UbiSales> pageSelect) throws SystemException;
	
	public Integer updateCustomerPwd(Long customerId, String servicePwd, String privatePwd, String updateType)throws SystemException;

}
