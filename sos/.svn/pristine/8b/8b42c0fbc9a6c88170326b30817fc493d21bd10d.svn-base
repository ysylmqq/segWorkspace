package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:OrderDetails
 * @Description:订单明细实体类
 * @author:zfy
 * @date:2013-10-29 下午3:29:05
 */
@Entity
@Table(name = "t_whs_order_details")
public class OrderDetails extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Long id;//订单明细ID
	private Long orderId;//订单ID
	private Long supplycontractsId;//采购合同ID,关联总部供货合同表
	private Long productId;//商品ID
	private Integer num;//订购数量
	private Integer inNum;//已采购入库数量
	private Integer isCompleted;//是否采购完成 0:否；1:是
	private Float price;//单价
	private String remark;//备注

	public OrderDetails() {
	}

	public OrderDetails(Long id) {
		this.id = id;
	}

	public OrderDetails(Long id, Long orderId,
			Long supplycontractsId, Long productId, Integer num,
			Float price, String remark) {
		this.id = id;
		this.orderId = orderId;
		this.supplycontractsId = supplycontractsId;
		this.productId = productId;
		this.num = num;
		this.price = price;
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
	@Column(name = "order_id")
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Column(name = "supplycontracts_id")
	public Long getSupplycontractsId() {
		return this.supplycontractsId;
	}

	public void setSupplycontractsId(Long supplycontractsId) {
		this.supplycontractsId = supplycontractsId;
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
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "in_num")
	public Integer getInNum() {
		return inNum;
	}

	public void setInNum(Integer inNum) {
		this.inNum = inNum;
	}
	@Column(name = "is_completed")
	public Integer getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Integer isCompleted) {
		this.isCompleted = isCompleted;
	}
}
