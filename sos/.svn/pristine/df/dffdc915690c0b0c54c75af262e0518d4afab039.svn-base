package com.gboss.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * @Package:com.gboss.pojo
 * @ClassName:Writeoffs
 * @Description:电工核销登记实体类
 * @author:zfy
 * @date:2013-8-30 上午10:51:34
 */
@Entity
@Table(name = "t_whs_writeoffs")
public class Writeoffs extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//核销ID
	private Long managersId;//经办人ID
	private String managersName;//经办人名称
	private String writeoffsNo;//核销单号
	private String receiptNo;//收据编号
	private Float peopleMoney;//人工费
	private Long userId;//操作员ID
	private String userName;///操作员名称
	private Date stamp;//操作日期
	private String remark;//备注
	private Long orgId;//所属部门ID
	private Long companyId;//所属分公司ID

	private List<WriteoffsDetails> writeoffsDetails;//核销详细
	
	private Long whsId;//仓库ID
	
	public Writeoffs() {
	}

	public Writeoffs(Long id) {
		this.id = id;
	}

	public Writeoffs(Long id, Long managersId, String receiptNo,
			Long userId, Date stamp) {
		this.id = id;
		this.managersId = managersId;
		this.receiptNo = receiptNo;
		this.userId = userId;
		this.stamp = stamp;
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
	@Column(name = "managers_id")
	public Long getManagersId() {
		return this.managersId;
	}

	public void setManagersId(Long managersId) {
		this.managersId = managersId;
	}
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "stamp")
	public Date getStamp() {
		return this.stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	@Transient
	public List<WriteoffsDetails> getWriteoffsDetails() {
		return writeoffsDetails;
	}

	public void setWriteoffsDetails(List<WriteoffsDetails> writeoffsDetails) {
		this.writeoffsDetails = writeoffsDetails;
	}
	@Transient
	public Long getWhsId() {
		return whsId;
	}

	public void setWhsId(Long whsId) {
		this.whsId = whsId;
	}
	@Column(name = "receipt_no")
	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="writeoffs_no")
	public String getWriteoffsNo() {
		return writeoffsNo;
	}

	public void setWriteoffsNo(String writeoffsNo) {
		this.writeoffsNo = writeoffsNo;
	}

	@Column(name="people_money")
	public Float getPeopleMoney() {
		return peopleMoney;
	}

	public void setPeopleMoney(Float peopleMoney) {
		this.peopleMoney = peopleMoney;
	}
	@Column(name="managers_name")
	public String getManagersName() {
		return managersName;
	}

	public void setManagersName(String managersName) {
		this.managersName = managersName;
	}
	@Column(name="user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name="org_id")
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Column(name="company_id")
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	
}
