package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "t_ba_org")
public class Org extends BaseEntity {

	private Long orgId; //机构ID
	private Integer orgType; //机构类型
	private Long orgNo; //LDAP机构根节点ID
	private String orgCode; //LDAP机构(分公司/集客用户)子机构代码, 根结构填'0', 每级2位字符
	private String orgName; //机构名称
	private Long opId; //操作员ID
	private Date stamp; //时间戳
	private String remark; //备注
	
	@Id
	@Column(name = "org_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Column(name = "org_type")
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	@Column(name = "org_no")
	public Long getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(Long orgNo) {
		this.orgNo = orgNo;
	}
	@Column(name = "org_code")
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	@Column(name = "org_name")
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name = "op_id")
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
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
