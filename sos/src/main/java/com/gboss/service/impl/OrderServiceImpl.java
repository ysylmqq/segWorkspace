package com.gboss.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.OrderDao;
import com.gboss.pojo.CustomerAddress;
import com.gboss.pojo.Order;
import com.gboss.pojo.OrderAddress;
import com.gboss.pojo.OrderDetails;
import com.gboss.pojo.Supplycontracts;
import com.gboss.service.OrderService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.UUIDCreater;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:OrderServiceImpl
 * @Description:订单管理业务层实现类
 * @author:zfy
 * @date:2013-11-4 上午8:56:29
 */
@Service("orderService")
@Transactional
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired  
	@Qualifier("orderDao")
	private OrderDao orderDao;
	
	@Override
	public int addCustomerAddress(CustomerAddress customerAddress)
			throws SystemException {
		if (customerAddress == null) {
			return -1;
		}else{
			orderDao.save(customerAddress);
			return 1;
		}
	}

	@Override
	public int updateCustomerAddress(CustomerAddress customerAddress)
			throws SystemException {
		if (customerAddress == null || customerAddress.getId()==null) {
			return -1;
		}else{
			if(orderDao.get(CustomerAddress.class, customerAddress.getId()) != null) {
				orderDao.merge(customerAddress);
				//如果是设为默认地址，则把之前的默认地址改回来
				if(customerAddress.getIsDefault()!=null && customerAddress.getIsDefault()==1){
					orderDao.updateCustomAddressIsDefault(customerAddress.getId(), customerAddress.getCompanyId());
				}
				return 1;
			} else {
				return 0;
			}
		}
	}

	@Override
	public int deleteCustomerAddresss(List<Long> ids) throws SystemException {
		int result=1;
		if (ids ==null || ids.isEmpty()) {
			result= -1;
		} else {
			orderDao.deleteCustomerAddresss(ids);
		}
		return result;
	}

	@Override
	public List<HashMap<String, Object>> findCustomerAddress(Map<String, Object> map)
			throws SystemException {
		return orderDao.findCustomerAddress(map);
	}

	@Override
	public Page<HashMap<String, Object>> findOrdersByPage(
			PageSelect<Map<String, Object>> pageSelect)
			throws SystemException {
		int total=orderDao.countOrders(pageSelect.getFilter());
		List<HashMap<String, Object>> list=orderDao.findOrders(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public HashMap<String, Object> addOrder(Order order) throws SystemException {
		HashMap<String, Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if(order==null){
			flag=false;
			msg="参数不正确!";
		}else{
			//判断单号是否存在
			if(orderDao.checkOrderNo(order)){
				//自动生成单号
				flag=false;
				msg = "单号为[" + order.getOrderNo() + "]的订单已存在,将自动生成新的订单单号!";
				result.put("code", getOrderNo(order.getCompanyId(),order.getUserId()));
			}else{
				//增加订单主体信息
				order.setIsCompleted(0);
				order.setStatus(0);
				orderDao.save(order);
				Long id=order.getId();
				
				//增加订单明细
				List<OrderDetails> orderDetails = order.getOrderDetails();
				if(orderDetails!=null && !orderDetails.isEmpty()) {
					for (OrderDetails orderDetails2 : orderDetails) {
						orderDetails2.setOrderId(id);
						orderDetails2.setIsCompleted(0);//未完成
						orderDetails2.setInNum(0);
						orderDao.save(orderDetails2);
					}
				}
				
				//增加订单收货地址
				List<OrderAddress> orderAddresses= order.getOrderAddresses();
				if(orderAddresses!=null && !orderAddresses.isEmpty()) {
					for (OrderAddress orderAddresses2 : orderAddresses) {
						orderAddresses2.setOrderId(id);
						orderDao.save(orderAddresses2);
					}
				}
			
			}
		
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}

	@Override
	public List<HashMap<String, Object>> findOrderDetailsByOrderId(
			Long orderId) throws SystemException {
		return orderDao.findOrderDetailsByOrderId(orderId);
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public HashMap<String, Object> deleteOrders(List<Long> ids) throws SystemException {
		HashMap<String, Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if(ids==null || ids.isEmpty()){
			flag=false;
			msg="参数不正确!";
		}else{
			Order order=null;
			for (Long id : ids) {
				//先判断是否存在
				order=orderDao.get(Order.class, id);
				if(order!=null){
					//存在就删除
					//删除前判断是否在使用
					if(orderDao.checkOrderIsUsing(id)){
						flag=false;
						msg="订单号为["+order.getOrderNo()+"]的订单正在使用,删除失败!";
						throw new RuntimeException(msg);
					}else{
						//删除订单收货地址
						orderDao.deleteAddressByOrderId(id);
						//删除订单明细
						orderDao.deleteDetailsByOrderId(id);
						//删除订单
						orderDao.delete(Order.class, id);
					}
				}
			}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}

	@Override
	public int updateCustomAddressIsDefault(Long id, Long companyId)
			throws SystemException {
		int result=1;
		if(id==null){
			result=-1;
		}else{
			CustomerAddress customerAddress=orderDao.get(CustomerAddress.class, id);
			if(customerAddress!=null){
				//设置本地址为默认地址
				customerAddress.setIsDefault(1);
				orderDao.merge(customerAddress);
				
				//把其他地址置为普通收货地址
				orderDao.updateCustomAddressIsDefault(id, companyId);
			}else{
				result=0;
			}
		}
		return result;
	}

	@Override
	public String getOrderNo(Long companyId,Long userId) throws SystemException {
		OpenLdap openLdap=OpenLdapManager.getInstance();
		CommonCompany commonCompany=null;
		String companyNo=null;
		
		if(companyId!=null){
			commonCompany=openLdap.getCompanyById(companyId.toString());
			companyNo=commonCompany.getCompanycode();
			int length=companyNo.length();
			if(length>=2){
			   companyNo=companyNo.substring(length-2, length);
			}
		}
		String userIdStr=null;
		if(userId==null){
			userIdStr="";
		}else{
			if(userId>100000){//大于百万，只取模
				userIdStr=String.valueOf(userId%100000);
			}else{
				userIdStr=String.valueOf(userId);
			}
		}
		//流水号加1，前面不足4位，用0补充
		String serialNoStr=Utils.formatSerial(orderDao.getMaxOrderNo(companyId, DateUtil.formatToday()));
		return SystemConst.ORDER_NO_PREFIX+companyNo+userIdStr+DateUtil.format(new Date(), DateUtil.YMD)+serialNoStr;
	}

	@Override
	public Float getPriceByProductId(Long companyId, Long productId,String stamp,Integer status)
			throws SystemException {
		return orderDao.getPriceByProductId(companyId, productId, stamp, status);
	}

	@Override
	public int updateStatus(Order order)
			throws SystemException {
		int result=1;
		if(order==null || StringUtils.isNullOrEmpty(order.getId())){
			result=-1;
		}else{
			Order order2=orderDao.get(Order.class, order.getId());
			if(order2!=null){
				order2.setStatus(order.getStatus());
				order.setCheckUserId(order.getCheckUserId());
				order2.setCheckStamp(new Date());
				orderDao.merge(order2);
			}else{
				result=0;
			}
		}
		return result;
	}

	@Override
	public List<HashMap<String, Object>> findAllOrders(Map<String, Object> map)
			throws SystemException {
		return orderDao.findOrders(map,null,false,0,0);

	}

	@Override
	public Float getOrderPriceByProductId(Long companyId, Long productId)
			throws SystemException {
		return orderDao.getOrderPriceByProductId(companyId, productId);
	}

	@Override
	public HashMap<String, Object> updateOrder(Order order)
			throws SystemException {
		
			HashMap<String, Object> result=new HashMap<String, Object>();
			boolean flag=true;
			String msg=SystemConst.OP_SUCCESS;
			if(order==null || StringUtils.isNullOrEmpty(order.getId())){
				flag=false;
				msg="参数不正确!";
			}else{
				//修改订单
				Long orderId=order.getId();
				//操作之前，判断存在不存在
				if(orderDao.get(Order.class, orderId)!=null){
					//判断单号是否存在
					if(orderDao.checkOrderNo(order)){
						//自动生成单号
						flag=false;
						msg = "单号为[" + order.getOrderNo() + "]的订单已存在,将自动生成新的订单单号!";
						result.put("code", getOrderNo(order.getCompanyId(),order.getUserId()));
					}else{
						//修改订单主体信息
						order.setStatus(0);
						order.setIsCompleted(0);
						orderDao.merge(order);
						
						//先删除订单与订单明细的关系
						orderDao.deleteDetailsByOrderId(orderId);
						//再增加订单明细
						List<OrderDetails> orderDetails = order.getOrderDetails();
						if(orderDetails!=null && !orderDetails.isEmpty()) {
							for (OrderDetails orderDetails2 : orderDetails) {
								orderDetails2.setOrderId(orderId);
								orderDetails2.setIsCompleted(0);//未完成
								orderDetails2.setInNum(0);
								orderDao.save(orderDetails2);
							}
						}
						//先删除订单与订单收货地址的关系
						orderDao.deleteAddressByOrderId(orderId);
						//再增加订单收货地址
						List<OrderAddress> orderAddresses= order.getOrderAddresses();
						if(orderAddresses!=null && !orderAddresses.isEmpty()) {
							for (OrderAddress orderAddresses2 : orderAddresses) {
								orderAddresses2.setOrderId(orderId);
								orderDao.save(orderAddresses2);
							}
						}
					
					}
				}
			}
			result.put(SystemConst.SUCCESS, flag);
			result.put(SystemConst.MSG, msg);
			return result;
	}

}

