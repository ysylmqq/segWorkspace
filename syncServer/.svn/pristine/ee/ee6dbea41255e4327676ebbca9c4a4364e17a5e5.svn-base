package com.chinagps.center.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chinagps.center.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Package:com.chinagps.center.pojo
 * @ClassName:Linkman
 * @Description:客户联系电话
 * @date:2015-11-17 下午5:30:13
 */
@Entity
@Table(name = "t_ba_linkman")
public class Linkman extends BaseEntity {

	/** 
	 * @Fields serialVersionUID : TODO 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private Long linkman_id;//'联系人id',
	private Long customer_id;//'客户ID',
	private Long vehicle_id;//'车辆ID, 集客不一定对应车辆, 可填0',
	private Integer linkman_type;//'类型, 系统参数2100, 1=第一联系人/常用联系人, 2=第二联系人/报警联系人, 99=其它',
	private String linkman;//'联系人姓名',
	private String phone;//'电话号码',
	private String title;//'称呼/备注',
	private Date stamp;//'时间戳',
	private Integer appsign;//是否app可登陆
	
	@Id
	@Column(name = "linkman_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getLinkman_id() {
		return linkman_id;
	}
	public void setLinkman_id(Long linkman_id) {
		this.linkman_id = linkman_id;
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
	
	@Column(name = "linkman_type")
	public Integer getLinkman_type() {
		return linkman_type;
	}
	public void setLinkman_type(Integer linkman_type) {
		this.linkman_type = linkman_type;
	}
	
	@Column(name = "linkman")
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
	@Column(name = "appsign")
	public Integer getAppsign() {
		return appsign;
	}
	public void setAppsign(Integer appsign) {
		this.appsign = appsign;
	}

}

