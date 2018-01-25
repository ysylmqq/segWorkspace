package com.gboss.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @Entity
 * @Package:com.gboss.pojo
 * @ClassName:Channel
 * @Description:渠道实体类
 * @author:zfy
 * @date:2013-8-8 下午12:00:31
 */
@Entity
@Table(name = "t_sel_channel")
public class Channel extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//渠道ID
	private String name;//渠道名称
	private String address;//地址
	private Integer monthSell;//月销售量
	private Long dictId;//渠道类型 关联数据字典表(类型为1)
	private Long groupId;//所属集团ID
	private Long orgId;//机构ID
	private String orgName;//所属机构名称
	private Long companyId;//分公司ID
	private String companyName;//公司名称
	private Date stamp;
	private String remark;

	private String groupName;//所属集团
	
	private List<Channelcontacts> channelcontacts;//联系人
	
	private Long salesManagerId;//销售经理ID
	private String salesManagerName;//销售经理名称
	
	public Channel() {
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
	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "month_sell")
	public Integer getMonthSell() {
		return this.monthSell;
	}

	public void setMonthSell(Integer monthSell) {
		this.monthSell = monthSell;
	}
	@Transient
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Transient
	public List<Channelcontacts> getChannelcontacts() {
		return channelcontacts;
	}
	public void setChannelcontacts(List<Channelcontacts> channelcontacts) {
		this.channelcontacts = channelcontacts;
	}
	@Column(name = "company_id")
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Column(name = "dict_id")
	public Long getDictId() {
		return dictId;
	}
	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}
	@Column(name = "org_id")
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Transient
	public Long getSalesManagerId() {
		return salesManagerId;
	}
	public void setSalesManagerId(Long salesManagerId) {
		this.salesManagerId = salesManagerId;
	}
	@Transient
	public String getSalesManagerName() {
		return salesManagerName;
	}
	public void setSalesManagerName(String salesManagerName) {
		this.salesManagerName = salesManagerName;
	}
	@Column(name = "group_id")
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	@Column(name = "org_name")
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
    @Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
}
