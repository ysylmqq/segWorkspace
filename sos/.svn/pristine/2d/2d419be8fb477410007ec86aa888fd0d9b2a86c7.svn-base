package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Package:com.gboss.pojo
 * @ClassName:StockoutDetails
 * @Description:仓库出库明细实体类
 * @author:zfy
 * @date:2013-8-8 上午11:35:30
 */
@Entity
@Table(name = "t_whs_stockout_details")
public class StockoutDetails extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//出库明细ID
	private Long stockoutId;//出库ID
	private Long productId;//商品ID
	private Integer curNum;//原库存数量
	private Long contractId;//销售合同ID
	private Float price;//价格
	private Integer outNum;//出库数量
	private Integer allocatedNum;//已调拨数量
	private Integer isCompleted;//是否调拨完成 0:否；1:是
	private Float money;//费用
	private String remark;
	
	private String productName;//商品名称
	private String norm;//商品规格
	private String productCode;//商品编码

	public StockoutDetails() {
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
	@Column(name = "stockout_id")
	public Long getStockoutId() {
		return this.stockoutId;
	}

	public void setStockoutId(Long stockoutId) {
		this.stockoutId = stockoutId;
	}
	@Column(name = "product_id")
	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	@Transient
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Column(name = "cur_num")
	public Integer getCurNum() {
		return this.curNum;
	}

	public void setCurNum(Integer curNum) {
		this.curNum = curNum;
	}
	@Column(name = "out_num")
	public Integer getOutNum() {
		return this.outNum;
	}

	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	@Transient
	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}
	@Transient
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	@Column(name = "contract_id")
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	@Column(name = "is_completed")
	public Integer getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Integer isCompleted) {
		this.isCompleted = isCompleted;
	}
	@Column(name = "allocated_num")
	public Integer getAllocatedNum() {
		return allocatedNum;
	}

	public void setAllocatedNum(Integer allocatedNum) {
		this.allocatedNum = allocatedNum;
	}
	@Column(name = "money")
	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}
}
