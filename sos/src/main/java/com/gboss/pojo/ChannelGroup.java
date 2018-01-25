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
 * @ClassName:ChannelGroup
 * @Description:代理商所属集团类
 * @author:zfy
 * @date:2013-12-23 上午8:47:24
 */
@Entity
@Table(name = "t_sel_channel_group")
public class ChannelGroup extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String enName;//集团英文名称
	private String cnName;//集团中文名称
	private Long orgId;//机构ID
	private String orgName;//所属机构名称
	private Long companyId;//分公司ID
	private String companyName;//公司名称
	private Long userId;//操作员ID
	private Date stamp;
	private String remark;//备注

	public ChannelGroup() {
	}

	public ChannelGroup(Long id) {
		this.id = id;
	}

	public ChannelGroup(Long id, String enName, String cnName,
			Long orgId, Long companyId, Long userId, Date stamp,
			String remark) {
		this.id = id;
		this.enName = enName;
		this.cnName = cnName;
		this.orgId = orgId;
		this.companyId = companyId;
		this.userId = userId;
		this.stamp = stamp;
		this.remark = remark;
	}
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "en_name")
	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
	@Column(name = "cn_name")
	public String getCnName() {
		return this.cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	@Column(name = "org_id")
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Column(name = "company_id")
	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return this.stamp;
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

}
