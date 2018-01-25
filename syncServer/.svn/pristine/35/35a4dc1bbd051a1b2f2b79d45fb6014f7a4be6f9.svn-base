package com.chinagps.center.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_syn_url")
public class SynUrl extends BaseEntity {
	
	private Long area_id;
	private Long subco_no;
	private String subco_name;
	private String syn_url;
	private Date stamp;
	private String base_code;
	
	@Id
	@Column(name = "area_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getArea_id() {
		return area_id;
	}
	public void setArea_id(Long area_id) {
		this.area_id = area_id;
	}

	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}

	@Column(name = "subco_name")
	public String getSubco_name() {
		return subco_name;
	}
	public void setSubco_name(String subco_name) {
		this.subco_name = subco_name;
	}

	@Column(name = "syn_url")
	public String getSyn_url() {
		return syn_url;
	}
	public void setSyn_url(String syn_url) {
		this.syn_url = syn_url;
	}

	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	@Column(name = "base_code")
	public String getBase_code() {
		return base_code;
	}
	public void setBase_code(String base_code) {
		this.base_code = base_code;
	}
}
