package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 联系人信息
 * @author wx
 */
@Entity
@Table(name = "t_ba_linkman")
public class LinkMan extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	private Long linkman_id;
	private Long customer_id;
	private Long vehicle_id	;
	private Integer linkman_type;
	private String linkman	;
	private String phone	;
	private String title	;
	private Integer appsign	;
	private Date stamp		;

	@Id
	@Column(name="linkman_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getLinkman_id() {
		return linkman_id;
	}
	public void setLinkman_id(Long linkman_id) {
		this.linkman_id = linkman_id;
	}
	
	@Column(name="customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	
	
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name="vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	@Column(name="linkman_type")
	public Integer getLinkman_type() {
		return linkman_type;
	}
	public void setLinkman_type(Integer linkman_type) {
		this.linkman_type = linkman_type;
	}
	
	@Column(name="linkman")
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name="phone")
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="appsign")
	public Integer getAppsign() {
		return appsign;
	}
	public void setAppsign(Integer appsign) {
		this.appsign = appsign;
	}
	
	@Column(name="stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}


}
