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
 * @ClassName:Salescontract
 * @Description:销售合同实体类
 * @author:zfy
 * @date:2013-8-6 下午3:58:17
 */
@Entity
@Table(name = "t_sel_salescontract")
public class Salescontract extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String code;//销售合同编号
	private String name;//销售合同名称
	private Long channelId;//渠道ID
	private String phone;//联系电话
	private Long contractorId;//签约人ID
	private String contractorName;//签约人Name
	private Date signDate;//签订日期
	private Date validDate;//生效日期
	private Date matureDate;//到期日期
	private Integer status;//供货合同状态:1: 已生效,0:未生效
	private Integer isFiling;//供货合同是否归档：1：是，0：否
	private Long companyId;//公司ID
	private String companyName;//公司名称
	private Long orgId;//所属机构ID
	private String orgName;//所属机构名称
	private Long userId;//操作员ID
	private String userName;//盘点人名称
	private Date stamp;
	private String annex;//附件
	private Long checkUserId;//审核人ID
	private Date checkStamp;//审核时间
	private String remark;
	
	private String channelName;//代理商名称
	
	private List<SalesProduct> salesProducts;//合同商品

	private List<SalesPack> salesPacks;//合同套餐
	
	public Salescontract() {
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
	@Column(name = "code")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "channel_id")
	public Long getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	@Column(name = "phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "contractor_id")
	public Long getContractorId() {
		return this.contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}
	@Column(name = "sign_date")
	public Date getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	@Column(name = "mature_date")
	public Date getMatureDate() {
		return this.matureDate;
	}

	public void setMatureDate(Date matureDate) {
		this.matureDate = matureDate;
	}
	@Column(name = "valid_date")
	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	@Column(name = "company_id")
	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Column(name = "org_id")
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	@Column(name = "annex")
	public String getAnnex() {
		return this.annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}

	@Transient
	public List<SalesProduct> getSalesProducts() {
		return salesProducts;
	}

	public void setSalesProducts(List<SalesProduct> salesProducts) {
		this.salesProducts = salesProducts;
	}
	@Transient
	public List<SalesPack> getSalesPacks() {
		return salesPacks;
	}

	public void setSalesPacks(List<SalesPack> salesPacks) {
		this.salesPacks = salesPacks;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "contractor_name")
	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	@Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(name = "org_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Transient
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@Column(name = "check_user_id")
	public Long getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(Long checkUserId) {
		this.checkUserId = checkUserId;
	}
	@Column(name = "check_stamp")
	public Date getCheckStamp() {
		return checkStamp;
	}
	public void setCheckStamp(Date checkStamp) {
		this.checkStamp = checkStamp;
	}
	@Column(name = "is_filing")
	public Integer getIsFiling() {
		return isFiling;
	}

	public void setIsFiling(Integer isFiling) {
		this.isFiling = isFiling;
	}
}
