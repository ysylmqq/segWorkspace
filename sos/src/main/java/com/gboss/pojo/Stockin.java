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
 * @ClassName:Stockin
 * @Description:仓库入库实体类
 * @author:zfy
 * @date:2013-8-8 上午11:26:14
 */
@Entity
@Table(name = "t_whs_stockin")
public class Stockin extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//入库ID
	private Long whsId;//仓库ID
	private String whsName;//仓库Name
	private Long companyId;//公司ID
	private String companyName;//公司名称
	private String code;//入库单号
	private Date stamp;//入库时间
	private Integer type;//类型
	private Integer isWaste;//是否是废品 1：是，0：否
	private Long orderId;//订单ID
	private Long outWhsId;//调拨出仓库ID
	private String outWhsName;//调拨出仓库Name
	private Long stockoutId;//调拨单ID=出库ID
	private Long channelId;//渠道ID
	private Long managersId;//经办人ID
	private String managersName;//经办人name
	private Long userId;//操作员ID
	private String userName;//操作员name
	private String remark;
	
	private List<StockinDetails> stockinDetails;//入库详细信息
	
	public Stockin() {
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
	@Column(name = "stamp")
	public Date getStamp() {
		return this.stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "code")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public List<StockinDetails> getStockinDetails() {
		return stockinDetails;
	}

	public void setStockinDetails(List<StockinDetails> stockinDetails) {
		this.stockinDetails = stockinDetails;
	}
	@Column(name = "is_waste")
	public Integer getIsWaste() {
		return isWaste;
	}

	public void setIsWaste(Integer isWaste) {
		this.isWaste = isWaste;
	}
	@Column(name = "order_id")
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Column(name = "out_whs_id")
	public Long getOutWhsId() {
		return outWhsId;
	}

	public void setOutWhsId(Long outWhsId) {
		this.outWhsId = outWhsId;
	}
	@Column(name = "whs_name")
	public String getWhsName() {
		return whsName;
	}

	public void setWhsName(String whsName) {
		this.whsName = whsName;
	}
	@Column(name = "out_whs_name")
	public String getOutWhsName() {
		return outWhsName;
	}

	public void setOutWhsName(String outWhsName) {
		this.outWhsName = outWhsName;
	}
	@Column(name = "managers_name")
	public String getManagersName() {
		return managersName;
	}

	public void setManagersName(String managersName) {
		this.managersName = managersName;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	@Column(name = "stockout_id")
	public Long getStockoutId() {
		return stockoutId;
	}

	public void setStockoutId(Long stockoutId) {
		this.stockoutId = stockoutId;
	}
}
