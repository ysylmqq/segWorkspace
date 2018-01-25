package com.chinagps.driverbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinagps.driverbook.dao.OrderMapper;
import com.chinagps.driverbook.dao.PaymentLogMapper;
import com.chinagps.driverbook.pojo.Order;
import com.chinagps.driverbook.pojo.PaymentLog;
import com.chinagps.driverbook.service.IPaymentLogService;

@Service
@Scope("prototype")
public class PaymentLogServiceImpl extends BaseServiceImpl<PaymentLog> implements IPaymentLogService {

	@Autowired
	private PaymentLogMapper paymentLogMapper;
	@Autowired
	private OrderMapper orderMapper;
	
	@Transactional
	public void finishTrade(PaymentLog paymentLog) throws Exception {
		Order order = new Order();
		order.setId(paymentLog.getOrderId());
		order.setIsPay(2);
		order.setStatus(2);
		orderMapper.edit(order);
		paymentLogMapper.add(paymentLog);
	}
	
}
