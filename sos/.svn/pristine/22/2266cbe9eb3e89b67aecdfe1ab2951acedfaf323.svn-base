package com.gboss.pojo.web;

import java.util.Date;
import java.util.List;

import com.gboss.pojo.FeePayment;
import com.gboss.pojo.PaymentItem;

/**
 * @Package:com.gboss.pojo.web
 * @ClassName:FeePaymentMsg
 * @Description:TODO
 * @author:bzhang
 * @date:2014-8-22 下午4:08:29
 */
/**
 * 用于缴费页面传值到Controller的POJO类
 * 
 * @author bzhang
 * 
 */
public class FeePaymentMsg {
	

	private FeePayment feePayment;
	private List<PaymentItem> paymentItems;// 缴费子项
	private Long[]  vehicleIds;//车辆id
	private Integer cust_type =0;//0为私家车缴费 ，1为集团客户
	
	
	public List<PaymentItem> getPaymentItems() {
		return paymentItems;
	}
	public void setPaymentItems(List<PaymentItem> paymentItems) {
		this.paymentItems = paymentItems;
	}
	public Long[] getVehicleIds() {
		return vehicleIds;
	}
	public void setVehicleIds(Long[] vehicleIds) {
		this.vehicleIds = vehicleIds;
	}
	public FeePayment getFeePayment() {
		return feePayment;
	}
	public void setFeePayment(FeePayment feePayment) {
		this.feePayment = feePayment;
	}
	public Integer getCust_type() {
		return cust_type;
	}
	public void setCust_type(Integer cust_type) {
		this.cust_type = cust_type;
	}
	
	
}

