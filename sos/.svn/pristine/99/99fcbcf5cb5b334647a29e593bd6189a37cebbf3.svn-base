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
 * @ClassName:StockinDetails
 * @Description:仓库入库明细实体类
 * @author:zfy
 * @date:2013-8-8 上午11:30:00
 */
@Entity
@Table(name = "t_whs_stockin_details")
public class StockinDetails extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//入库明细ID
	private Long stockinId;//入库ID
	private Long productId;//商品ID
	private Integer curNum;//原库存数量
	private Integer inNum;//入库数量
	private Float price;//价格
	private String remark;//备注

	private String productName;//商品名称
	private Long borrowId;//借用挂账ID
	private Long orderDetailId;//订单明细ID,用于采购入库修改已采购入库数量
	private Long stockoutDetailId;//出库明细ID,用于调拨入库修改已调拨入库数量
	public StockinDetails() {
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
	@Column(name = "stockin_id")
	public Long getStockinId() {
		return this.stockinId;
	}

	public void setStockinId(Long stockinId) {
		this.stockinId = stockinId;
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
	@Column(name = "in_num")
	public Integer getInNum() {
		return this.inNum;
	}

	public void setInNum(Integer inNum) {
		this.inNum = inNum;
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

	@Column(name = "borrow_id")
	public Long getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}
	@Transient
	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	@Transient
	public Long getStockoutDetailId() {
		return stockoutDetailId;
	}

	public void setStockoutDetailId(Long stockoutDetailId) {
		this.stockoutDetailId = stockoutDetailId;
	}
	
	
}
