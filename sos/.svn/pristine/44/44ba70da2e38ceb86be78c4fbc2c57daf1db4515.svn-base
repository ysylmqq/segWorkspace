package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonOperator;

import com.gboss.comm.SystemException;
import com.gboss.pojo.CustSales;
import com.gboss.pojo.Customer;
import com.gboss.pojo.UbiSales;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

public interface CustomerService extends BaseService {

	/**
	 * 验证客户名称是否重复(true:存在，false:不存在)
	 * 
	 * @param customer
	 * @return
	 */
	public boolean is_repeat(Customer customer) throws SystemException;

	/**
	 * 通过客户id删除客户
	 * 
	 * @param id
	 */
	public void delete(Long id) throws SystemException;

	/**
	 * 添加客户
	 * 
	 * @param customer
	 * @return
	 */
	public Long add(Customer customer) throws SystemException;

	/**
	 * 通过id获取客户
	 * 
	 * @param id
	 * @return
	 */
	public Customer getCustomer(Long id) throws SystemException;

	/**
	 * 通过名称模糊匹配获取客户
	 * 
	 * @param customer_name
	 * @return
	 */
	public Customer getCustomer(String customer_name) throws SystemException;

	/**
	 * 客户分页查询
	 * 
	 * @param pageSelect
	 * @param subco_no
	 * @return
	 */
	public Page<Customer> search(PageSelect<Customer> pageSelect, Long subco_no) throws SystemException;

	/**
	 * 通过练习电话获取客户
	 * 
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	public String getCustomerPhone(Long id) throws SystemException;

	/**
	 * 通过客户id获取车辆明细信息
	 * 
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> getDetailMsg(Long id) throws SystemException;

	/**
	 * 通过车载电话获取车辆、车台、客户等信息
	 * 
	 * @param call_letter
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> getDetailMsgBycl(String call_letter) throws SystemException;

	/**
	 * 通过客户id获取销售人员
	 * 
	 * @param customerId
	 * @return
	 */
	public CustSales getCustSales(Long customerId) throws SystemException;

	/**
	 * 客户分页查询
	 * 
	 * @param pageSelect
	 * @return
	 */
	public Page<HashMap<String, Object>> findUserPage(PageSelect<HashMap<String, Object>> pageSelect) throws SystemException;

	/**
	 * 查询app账号
	 * 
	 * @param map
	 * @return
	 */
	public List<CommonOperator> listAccount(Map<String, Object> map) throws SystemException;

	/**
	 * @param map
	 * @return
	 */
	public Page<UbiSales> listUbiSales(PageSelect<UbiSales> pageSelect) throws SystemException;
	
	public UbiSales getUbiSales(int salesId) throws SystemException;

	/**
	 * 导入客户、车辆信息数据
	 * 
	 * @param dataList
	 * @param compannyId
	 * @param userId
	 * @param companyName
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> importCustomerVehicle(List<String[]> dataList, Long compannyId, Long userId, String companyName, String companyCode)
			throws SystemException;

}
