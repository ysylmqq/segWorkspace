package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:CustSales
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-11 上午10:58:20
 */
@Entity
@Table(name = "t_ba_cust_sales")
public class CustSales extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Long cs_id;//关联ID
	private Long customer_id;//客户ID
	private Long manager_id;//销售经理ID
	private String manager_name;//销售经理ID
	private Integer isdel;//是否过时
	private Date stamp;//时间
	private String remark;//备注
	
	@Id
	@Column(name = "cs_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getCs_id() {
		return cs_id;
	}
	public void setCs_id(Long cs_id) {
		this.cs_id = cs_id;
	}
	
	@Column(name = "customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name = "manager_id")
	public Long getManager_id() {
		return manager_id;
	}
	public void setManager_id(Long manager_id) {
		this.manager_id = manager_id;
	}
	
	@Column(name = "manager_name")
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	
	@Column(name = "isdel")
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}

