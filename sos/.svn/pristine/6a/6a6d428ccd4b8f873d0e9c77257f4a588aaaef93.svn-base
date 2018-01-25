package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

/**
 * @Package:com.gboss.pojo
 * @ClassName:CustVehicle
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-27 上午11:49:42
 */

@Entity
@Table(name = "t_ba_cust_vehicle")
public class CustVehicle extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long cv_id;//'客户与车辆关系id',
	private Long customer_id;//'客户ID',
	private Long vehicle_id;//'车辆ID',
	private Date stamp;//'时间戳',
	
	@Id
	@Column(name = "cv_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
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
	
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}

