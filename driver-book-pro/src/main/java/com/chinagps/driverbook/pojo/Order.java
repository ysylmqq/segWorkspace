package com.chinagps.driverbook.pojo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单POJO
 * 
 * @author Ben
 *
 */
public class Order extends BaseEntity {
	private static final long serialVersionUID = 4631408323656659906L;

	/** 订单ID */
	private String id;
	/** 订单名称 */
	private String orderName;
	/** 会员ID */
	private Long customerId;
	/** 分公司ID（LDAP分公司根节点ID） */
	private Long subNo;
	/** 订单金额 **/
	private Float amount;
	/** 支付商类型（1：支付宝 2：微信） */
	private Integer payType;
	/** 是否支付（1：未支付 2：已支付） */
	private Integer isPay;
	/** 订单状态（1：待付款 2：已付款 3：已完成 4：已取消 5：已过期） */
	private Integer status;
	/** 订单创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/** 时间戳 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;
	
	/** 订单车辆集 */
	private List<OrderVehicle> vehicles;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getSubNo() {
		return subNo;
	}

	public void setSubNo(Long subNo) {
		this.subNo = subNo;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public List<OrderVehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<OrderVehicle> vehicles) {
		this.vehicles = vehicles;
	}

}
