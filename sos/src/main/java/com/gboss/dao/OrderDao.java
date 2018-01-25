package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Channelcontacts;
import com.gboss.pojo.CustomerAddress;
import com.gboss.pojo.Order;

/**
 * @Package:com.gboss.dao
 * @ClassName:OrderDao
 * @Description:订单管理数据持久层接口
 * @author:zfy
 * @date:2013-11-4 上午8:53:28
 */
public interface OrderDao extends BaseDao {
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
	 * @Description:查询收货地址
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findCustomerAddress(Map<String, Object> map) throws SystemException;
	
	/**
	 * @Title:findOrders
	 * @Description:查询订单
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findOrders(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	/**
	 * @Title:countOrders
	 * @Description:查询订单记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countOrders(Map<String, Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:findOrderDetailsByOrderId
	 * @Description:根据订单id查商品列表
	 * @param orderId
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findOrderDetailsByOrderId(Long orderId) throws SystemException;
	
	/**
	 * @Title:deleteDetailsByOrderId
	 * @Description:根据订单id删除明细
	 * @param orderId
	 * @return
	 * @throws SystemException
	 */
	public int deleteDetailsByOrderId(Long orderId) throws SystemException;
	/**
	 * @Title:deleteAddressByOrderId
	 * @Description:根据订单id删除明细
	 * @param orderId
	 * @return
	 * @throws SystemException
	 */
	public int deleteAddressByOrderId(Long orderId) throws SystemException;
	
	/**
	 * @Title:checkOrderIsUsing
	 * @Description:判断订单是否在用
	 * @param orderId
	 * @return
	 * @throws SystemException
	 */
	public boolean checkOrderIsUsing(Long orderId) throws SystemException;
	/**
	 * @Title:checkOrderNo
	 * @Description:判断订单是否存在
	 * @param orderId
	 * @return
	 * @throws SystemException
	 */
	public boolean checkOrderNo(Order order) throws SystemException;
	
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
	 * @Title:getMaxOrderNo
	 * @Description:核销、销账单最大条数
	 * @param companyId
	 * @param date
	 * @return
	 * @throws SystemException
	 */
	public int getMaxOrderNo(Long companyId,String date) throws SystemException;
	
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
	 * @Title:getPriceByProductId
	 * @Description:根据product查询订单价格
	 * @param companyId
	 * @param productId
	 * @return
	 * @throws SystemException
	 */
	public Float getOrderPriceByProductId(Long companyId,Long productId) throws SystemException;
	/**
	 * @Title:checkOrderDetailsNotCompleted
	 * @Description:判断是否有未完成的订单明细（针对某一个订单）
	 * @param orderId
	 * @return
	 * @throws SystemException
	 */
	public boolean checkOrderDetailsNotCompleted(Long orderId) throws SystemException;
}

