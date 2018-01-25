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
 * @ClassName:BorrowDetails
 * @Description:借用挂账实体类
 * @author:zfy
 * @date:2013-10-29 下午2:58:30
 */
@Entity
@Table(name = "t_whs_borrow_details")
public class BorrowDetails extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//借用挂账ID
	private Long toBorrowId;//转移后的借用挂账ID
	private Long borrowerId;//借用人ID
	private String borrowerName;//借用人name
	private Long orgId;//借用人所属部门ID
	private String orgName;//借用人所属部门name
	private Long companyId;//借用人所属分公司ID
	private String companyName;//借用人所属分公司name
	private Long channelId;//代理商ID
	private Long customerId;//最终客户ID
	private Long whsId;//仓库ID
	private String whsName;//仓库Name
	private Integer type;//挂账(出库)类型
	private Long productId;//商品ID
	private Integer num;//数量
	private Long contractId;//销售合同ID
	private Float price;//单价
	private Integer writeoffsNum;//已核销数量
	private Integer writeoffsNum2;//已销账数量
	private Integer returnNum;//已归还数量
	private Integer status;//0：未销（未还）、1：已销（已还）、2：已转移（如离职）
	private Date stamp;//借用时间
	private Long userId;//操作员ID
	private String userName;//操作员name
	private Long stockoutDetailsId;//出库明细ID
	private String remark;//备注
	
	public BorrowDetails() {
	}

	public BorrowDetails(Long id) {
		this.id = id;
	}

	public BorrowDetails(Long id, Long toBorrowId, Long borrowerId,
			Long orgId, Long companyId, Long productId, Integer num,
			Float price, Integer writeoffsNum, Integer writeoffsStatus,
			Date stamp, String remark) {
		this.id = id;
		this.toBorrowId = toBorrowId;
		this.borrowerId = borrowerId;
		this.orgId = orgId;
		this.companyId = companyId;
		this.productId = productId;
		this.num = num;
		this.price = price;
		this.writeoffsNum = writeoffsNum;
		this.status = writeoffsStatus;
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
	@Column(name = "borrower_id")
	public Long getBorrowerId() {
		return this.borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
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
	@Column(name = "product_id")
	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	@Column(name = "price")
	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	@Column(name = "writeoffs_num")
	public Integer getWriteoffsNum() {
		return this.writeoffsNum;
	}

	public void setWriteoffsNum(Integer writeoffsNum) {
		this.writeoffsNum = writeoffsNum;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	@Column(name = "whs_id")
	public Long getWhsId() {
		return whsId;
	}

	public void setWhsId(Long whsId) {
		this.whsId = whsId;
	}
	@Column(name = "channel_id")
	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	
	@Column(name = "return_num")
	public Integer getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(Integer returnNum) {
		this.returnNum = returnNum;
	}
	@Column(name = "borrower_name")
	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
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
	@Column(name = "whs_name")
	public String getWhsName() {
		return whsName;
	}

	public void setWhsName(String whsName) {
		this.whsName = whsName;
	}
	
	@Column(name = "customer_id")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "contract_id")
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	@Column(name = "to_borrow_id")
	public Long getToBorrowId() {
		return toBorrowId;
	}

	public void setToBorrowId(Long toBorrowId) {
		this.toBorrowId = toBorrowId;
	}
	@Column(name = "writeoffs_num2")
	public Integer getWriteoffsNum2() {
		return writeoffsNum2;
	}

	public void setWriteoffsNum2(Integer writeoffsNum2) {
		this.writeoffsNum2 = writeoffsNum2;
	}
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "stockout_details_id")
	public Long getStockoutDetailsId() {
		return stockoutDetailsId;
	}

	public void setStockoutDetailsId(Long stockoutDetailsId) {
		this.stockoutDetailsId = stockoutDetailsId;
	}
}
