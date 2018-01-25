package com.chinagps.center.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @Package:com.chinagps.center.pojo
 * @ClassName:SynCompany
 * @Description:
 * @date:2015-11-17 下午5:30:13
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_syn_company")
public class SynCompany extends BaseEntity {
	private Long com_id;
	private Long subco_no;
	private String subco_name;
	private String company_code;
	private Date stamp;
	
	@Id
	@Column(name = "com_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getCom_id() {
		return com_id;
	}
	public void setCom_id(Long com_id) {
		this.com_id = com_id;
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

	@Column(name = "company_code")
	public String getCompany_code() {
		return company_code;
	}
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
}
