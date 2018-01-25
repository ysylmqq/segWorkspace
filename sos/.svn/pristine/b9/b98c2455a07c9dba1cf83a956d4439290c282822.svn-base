package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.CustomerAddress;
import com.gboss.pojo.Order;
import com.gboss.pojo.Serviceitem;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:OrderService
 * @Description:订单管理业务层接口
 * @author:zfy
 * @date:2013-11-4 上午8:55:22
 */
public interface OrderService extends BaseService {
	/**
	 * @Title:addCustomerAddress
	 * @Description:添加客户收货地址
	 * @param customerAddress
	 * @return
	 * @throws SystemException
	 */
	public int addCustomerAddress(CustomerAddress customerAddress) throws SystemException;
	/**
	 * @Title:updateCustomerAddress
	 * @Description:修改客户收货地址
	 * @param customerAddress
	 * @return
	 * @throws SystemException
	 */
	public int updateCustomerAddress(CustomerAddress customerAddress) throws SystemException;
	/**
	 * @Title:deleteCustomerAddresss
	 * @Description:删除客户收货地址
	 * @param ids
	 * @return
	 * @throws SystemException
	 */
	public int deleteCustomerAddresss(List<Long> ids) throws SystemException;
	
	/**
	 * @Title:findCustomerAddress
	 * @Description:查找客户收货地址
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findCustomerAddress(Map<String, Object> map) throws SystemException;
	
	/**
	 * @Title:findOrdersByPage
	 * @Description:分页查找订单
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findOrdersByPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	/**
	 * @Title:findAllOrders
	 * @Description:查询所以订单
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllOrders(Map<String, Object> map) throws SystemException;

	/**
	 * @Title:addOrder
	 * @Description:添加订单信息
	 * @param order
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> addOrder(Order order) throws SystemException;
	
	/**
	 * @Title:updateOrder
	 * @Description:修改订单信息
	 * @param order
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> updateOrder(Order order) throws SystemException;
	/**
	 * @Title:findOrderDetailsByOrderId
	 * @Description:根据订单id查商品列表
	 * @param orderId
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findOrderDetailsByOrderId(Long orderId) throws SystemException;

	/**
	 * @Title:deleteOrders
	 * @Description:删除订单
	 * @param ids
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> deleteOrders(List<Long> ids) throws SystemException;
	
	/**
	 * @Title:updateCustomAddressIsDefault
	 * @Description:修改默认地址，设置除id以外的其他地址非默认
	 * @param id
	 * @param companyId
	 * @return
	 * @throws SystemException
	 */
	public int updateCustomAddressIsDefault(Long id,Long companyId)throws SystemException;
	/**
	 * @Title:getOrderNo
	 * @Description:获得订单号
	 * @param companyId
	 * @param userId
	 * @return
	 * @throws SystemException
	 */
	public String getOrderNo(Long companyId,Long userId) throws SystemException;
	
	/**
	 * @Title:getPriceByProductId
	 * @Description:根据product查询采购合同价格
	 * @param companyId
	 * @param productId
	 * @param stamp
	 * @param status
	 * @return
	 * @throws SystemException
	 */
	public Float getPriceByProductId(Long companyId,Long productId,String stamp,Integer status) throws SystemException;
	/**
	 * @Title:getOrderPriceByProductId
	 * @Description:根据product查询订单价格
	 * @param companyId
	 * @param productId
	 * @return
	 * @throws SystemException
	 */
	public Float getOrderPriceByProductId(Long companyId,Long productId) throws SystemException;

	/**
	 * @Title:updateStatusById
	 * @Description:修改订单状态
	 * @param order
	 * @return
	 * @throws SystemException
	 */
	public int updateStatus(Order order) throws SystemException;
	
}

