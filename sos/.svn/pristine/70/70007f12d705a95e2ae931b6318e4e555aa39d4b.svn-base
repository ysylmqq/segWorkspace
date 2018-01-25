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
 * @ClassName:WriteoffsDetails
 * @Description:电工核销明细实体类
 * @author:zfy
 * @date:2013-8-30 上午10:53:25
 */
@Entity
@Table(name = "t_whs_writeoffs_details")
public class WriteoffsDetails extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//核销明细ID
	private Long writeoffsId;//核销ID
	private Long borrowId;//借用挂账ID
	private Long productId;//商品ID
	private Integer num;///数量
	private Float price;//单价
	private Integer isExpired;///数量
	private Float money;//费用

	private String productName;//商品名称
	
	public WriteoffsDetails() {
	}

	public WriteoffsDetails(Long id) {
		this.id = id;
	}

	public WriteoffsDetails(Long id, Long writeoffsId,
			Long productId, String productName, Integer num) {
		this.id = id;
		this.writeoffsId = writeoffsId;
		this.productId = productId;
		this.productName = productName;
		this.num = num;
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
	@Column(name = "writeoffs_id")
	public Long getWriteoffsId() {
		return this.writeoffsId;
	}

	public void setWriteoffsId(Long writeoffsId) {
		this.writeoffsId = writeoffsId;
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
	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	@Column(name = "money")
	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}
	@Column(name = "borrow_id")
	public Long getBorrowId() {
		return borrowId;
	}
	
	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}
	@Column(name = "price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Column(name = "is_expired")
	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}
	
}
