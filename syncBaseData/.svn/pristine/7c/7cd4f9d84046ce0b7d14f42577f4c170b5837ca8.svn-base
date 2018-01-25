package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_ba_cust_vehicle")
public class CustVehicle extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long cv_id;//'客户ID',
	private Long customer_id;//'客户ID',
	private Long vehicle_id;//'车辆ID',
	private Date stamp;//'操作时间'
	
	@Id
	@Column(name = "cv_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCv_id() {
		return cv_id;
	}
	
	public void setCv_id(Long cv_id) {
		this.cv_id = cv_id;
	}
	
	@Column(name = "customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name = "vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	
	
}
