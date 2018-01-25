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
 * @ClassName:Stockout
 * @Description:仓库出库实体类
 * @author:zfy
 * @date:2013-8-8 上午11:32:26
 */
@Entity
@Table(name = "t_whs_stockout")
public class Stockout extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//出库ID
	private Long whsId;//仓库ID
	private String whsName;//仓库Name
	private Long companyId;//公司ID
	private String companyName;//公司名称
	private String code;//出库单号
	private Date stamp;//出库日期
	private Integer type;//出库类型 0:代理销售；1：直销；2：升级；3：借用；4：调拨；5：其他
	private Integer isWaste;//是否是废品 1：是，0：否
	private Long inWhsId;//调拨入仓库ID
	private String inWhsName;//调拨入仓库Name
	private Integer isCompleted;//是否调拨完成 0:否；1:是
	private Long channelId;//渠道ID
	private Long customerId;//最终客户ID
	private String receiptNo;//收据编号
	private Float money;//费用
	private Long managersId;//经办人ID
	private String managersName;//经办人name
	private Long userId;//操作员ID
	private String userName;//操作员name
	private String remark;
	
	private List<StockoutDetails> stockoutDetails;//出库详细信息
	
	private Boolean isWriteoffNow;//是否立即核销
	private Long orgId;//机构ID
	
	public Stockout() {
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
	@Column(name = "whs_id")
	public Long getWhsId() {
		return this.whsId;
	}

	public void setWhsId(Long whsId) {
		this.whsId = whsId;
	}
	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "channel_id")
	public Long getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
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
	public List<StockoutDetails> getStockoutDetails() {
		return stockoutDetails;
	}
	public void setStockoutDetails(List<StockoutDetails> stockoutDetails) {
		this.stockoutDetails = stockoutDetails;
	}
	@Column(name="code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="is_waste")
	public Integer getIsWaste() {
		return isWaste;
	}
	public void setIsWaste(Integer isWaste) {
		this.isWaste = isWaste;
	}
	@Column(name="in_whs_id")
	public Long getInWhsId() {
		return inWhsId;
	}
	public void setInWhsId(Long inWhsId) {
		this.inWhsId = inWhsId;
	}
	@Column(name="whs_name")
	public String getWhsName() {
		return whsName;
	}
	public void setWhsName(String whsName) {
		this.whsName = whsName;
	}
	@Column(name="in_whs_name")
	public String getInWhsName() {
		return inWhsName;
	}
	public void setInWhsName(String inWhsName) {
		this.inWhsName = inWhsName;
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
	@Column(name="customer_id")
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	@Column(name = "company_id")
	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(name = "is_completed")
	public Integer getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(Integer isCompleted) {
		this.isCompleted = isCompleted;
	}
	@Transient
	public Boolean getIsWriteoffNow() {
		return isWriteoffNow;
	}
	public void setIsWriteoffNow(Boolean isWriteoffNow) {
		this.isWriteoffNow = isWriteoffNow;
	}
	@Transient
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Column(name = "receipt_no")
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	@Column(name = "money")
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
}
