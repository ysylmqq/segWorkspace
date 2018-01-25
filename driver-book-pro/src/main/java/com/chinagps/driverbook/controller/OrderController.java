package com.chinagps.driverbook.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.Order;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IOrderService;
import com.chinagps.driverbook.util.DateUtil;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;

/**
 * 订单Controller
 * @author Ben
 *
 */
@RestController("orderController")
@RequestMapping(value="/order")
public class OrderController {
	private static Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	@Qualifier("orderServiceImpl")
	private IOrderService orderService;
	
	/**
	 * 创建订单接口
	 * @param encryptStr
	 * @param request
	 * @param response
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/new", method = RequestMethod.POST)
	public String _new(@RequestBody String encryptStr, HttpServletRequest request, HttpServletResponse response, ReturnValue rv) {
		try {
			Order order = RequestUtil.getParameters(encryptStr, Order.class);
			order.setId(DateUtil.orderSerial());
			Map<String, Object> resultMap = orderService.orderAndSign(order, request, response);
			rv.setDatas(resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 订单列表接口
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping
	public String index(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Long customerId = RequestUtil.getLongValue(encryptStr, "customerId");
			List<Order> orderList = orderService.findByCustomerId(customerId);
			rv.setDatas(orderList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 订单详情接口
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/show")
	public String show(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			String orderId = RequestUtil.getStringValue(encryptStr, "orderId");
			Order order = orderService.findById(orderId);
			order.getVehicles();
			rv.setDatas(order);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 取消订单接口
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/delete", method = RequestMethod.DELETE)
	public String destroy(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			String orderId = RequestUtil.getStringValue(encryptStr, "orderId");
			Order order = new Order();
			order.setId(orderId);
			order.setStatus(4);
			orderService.edit(order);
			rv.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 订单支付参数接口
	 * @param encryptStr
	 * @param request
	 * @param response
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/prepare")
	public String prepare(@RequestBody String encryptStr, HttpServletRequest request, HttpServletResponse response, ReturnValue rv) {
		try {
			String orderId = RequestUtil.getStringValue(encryptStr, "orderId");
			Order order = orderService.findById(orderId);
			rv.setDatas(orderService.resignOrder(order, request, response));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
}
