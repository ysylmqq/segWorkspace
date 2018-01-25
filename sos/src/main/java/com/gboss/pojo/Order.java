package com.gboss.pojo;

import java.util.Date;
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
 * @Package:com.gboss.pojo
 * @ClassName:Order
 * @Description:订单实体类
 * @author:zfy
 * @date:2013-10-29 下午3:19:39
 */
@Entity
@Table(name = "t_whs_order")
public class Order extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Long id;//订单ID
	private String orderNo;//订单号
	private Float totalPrice;//总金额
	private Long companyId;//分公司ID
	private String companyName;//公司名称
	private Long orgId;//部门ID
	private String orgName;//部门name
	private Long whsId;//仓库ID
	private String whsName;//仓库Name
	private Integer status;//审核状态:1: 已生效,0:未生效
	private Integer isCompleted;//是否采购完成 0:否；1:是
	private String receiptId;//订单接收人Id
	private String receiptName;//订单接收人
	private Long userId;//操作员ID
	private String userName;//盘点人名称
	private Long checkUserId;//审核人ID
	private Date checkStamp;//审核时间
	private Date stamp;//时间
	private String remark;//备注

	private List<OrderAddress> orderAddresses;//订单送货地址
	private List<OrderDetails> orderDetails;//订单明细
	public Order() {
	}

	public Order(Long id) {
		this.id = id;
	}

	public Order(Long id, String orderNo, Long companyId,
			Long userId, Date stamp, String remark) {
		this.id = id;
		this.orderNo = orderNo;
		this.companyId = companyId;
		this.userId = userId;
		this.stamp = stamp;
		this.remark = remark;
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
	@Column(name = "order_no")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	@Temporal(TemporalType.DATE)
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
	@Transient
	public List<OrderAddress> getOrderAddresses() {
		return orderAddresses;
	}

	public void setOrderAddresses(List<OrderAddress> orderAddresses) {
		this.orderAddresses = orderAddresses;
	}

	@Transient
	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Column(name = "total_price")
	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "receipt_name")
	public String getReceiptName() {
		return receiptName;
	}

	public void setReceiptName(String receiptName) {
		this.receiptName = receiptName;
	}
	@Column(name = "is_completed")
	public Integer getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Integer isCompleted) {
		this.isCompleted = isCompleted;
	}
	@Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "receipt_id")
	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
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
	@Column(name = "org_id")
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Column(name = "org_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name = "whs_id")
	public Long getWhsId() {
		return whsId;
	}

	public void setWhsId(Long whsId) {
		this.whsId = whsId;
	}
	@Column(name = "whs_name")
	public String getWhsName() {
		return whsName;
	}

	public void setWhsName(String whsName) {
		this.whsName = whsName;
	}
}
