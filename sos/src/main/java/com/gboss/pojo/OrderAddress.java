package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:OrderAddress
 * @Description:订单收货地址实体类
 * @author:zfy
 * @date:2013-10-29 下午3:26:24
 */
@Entity
@Table(name = "t_whs_order_address")
public class OrderAddress extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Long id;//ID
	private Long orderId;//订单ID
	private Long addressId;//关联订单收获地址表
	private Integer transportType;//运输方式
	private String specifyFreight;//指定货运
	private Integer payType;//运费承付方
	private String remark;//备注

	public OrderAddress() {
	}

	public OrderAddress(Long id) {
		this.id = id;
	}

	public OrderAddress(Long id, Long orderId, Long addressId) {
		this.id = id;
		this.orderId = orderId;
		this.addressId = addressId;
	}
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "order_id")
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Column(name = "address_id")
	public Long getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	@Column(name = "transport_type")
	public Integer getTransportType() {
		return this.transportType;
	}

	public void setTransportType(Integer transportType) {
		this.transportType = transportType;
	}
	@Column(name = "specify_freight")
	public String getSpecifyFreight() {
		return this.specifyFreight;
	}

	public void setSpecifyFreight(String specifyFreight) {
		this.specifyFreight = specifyFreight;
	}
	@Column(name = "pay_type")
	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
