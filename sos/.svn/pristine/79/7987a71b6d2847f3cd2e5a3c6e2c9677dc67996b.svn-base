package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:WriteoffDetails
 * @Description:销账明细实体类
 * @author:zfy
 * @date:2013-8-7 下午5:19:44
 */
@Entity
@Table(name = "t_fee_writeoff_details")
public class WriteoffDetails extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//销账明细ID
	private Long writeoffId;//销账ID
	private Long borrowId;//关联借用挂账表
	private Long commodityId;//商品ID
	private Integer quantity;//挂账数量
	private Integer offQuantity;//本次销账数量
	private Integer remainQuantity;//剩余数量
	private Float money;//金额

	public WriteoffDetails() {
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
	@Column(name = "writeoff_id")
	public Long getWriteoffId() {
		return this.writeoffId;
	}

	public void setWriteoffId(Long writeoffId) {
		this.writeoffId = writeoffId;
	}
	@Column(name = "commodity_id")
	public Long getCommodityId() {
		return this.commodityId;
	}

	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}
	@Column(name = "quantity")
	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@Column(name = "off_quantity")
	public Integer getOffQuantity() {
		return this.offQuantity;
	}

	public void setOffQuantity(Integer offQuantity) {
		this.offQuantity = offQuantity;
	}
	@Column(name = "remain_quantity")
	public Integer getRemainQuantity() {
		return this.remainQuantity;
	}

	public void setRemainQuantity(Integer remainQuantity) {
		this.remainQuantity = remainQuantity;
	}
	@Column(name = "money")
	public Float getMoney() {
		return this.money;
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
	
}
