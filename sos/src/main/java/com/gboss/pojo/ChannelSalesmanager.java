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
/**
 * @Package:com.gboss.pojo
 * @ClassName:Channelcontacts
 * @Description:销售经理与代理商关系表
 * @author:zfy
 * @date:2013-8-26 上午11:56:42
 */
@Entity
@Table(name = "t_sel_channel_salesmanager")
public class ChannelSalesmanager extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//关联ID
	private Long channelId;//渠道ID
	private Long managerId;//销售经理ID
	private String managerName;//销售经理ID
	private Integer isdel;//是否过时
	private Date stamp;//时间
	private String remark;//备注

	public ChannelSalesmanager() {
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
	@Column(name = "channel_id")
	public Long getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	@Column(name = "manager_id")
	public Long getManagerId() {
		return this.managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	@Column(name = "isdel")
	public Integer getIsdel() {
		return this.isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
    @Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "manager_name")
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	

}
