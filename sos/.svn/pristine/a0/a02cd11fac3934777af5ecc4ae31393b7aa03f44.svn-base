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
 * @ClassName:SupplyDetails
 * @Description:供货合同产品明细实体类
 * @author:zfy
 * @date:2013-8-20 下午3:57:34
 */
@Entity
@Table(name = "t_sel_supply_details")
public class SupplyDetails extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//供货明细ID
	private Long supplyId;//供货合同ID
	private Long productId;//商品ID
	private Float price;//价格
	private String validDate;//生效日期
	private String matureDate;//到期日期
	private Integer status;//供货合同状态:1: 已生效,0:未生效
	private Date stamp;//生效时间
	private String remark;//备注

	public SupplyDetails() {
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
	@Column(name = "supply_id")
	public Long getSupplyId() {
		return this.supplyId;
	}

	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}
	@Column(name = "product_id")
	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	@Column(name = "price")
	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
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
	@Column(name = "valid_date")
	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	@Column(name = "mature_date")
	public String getMatureDate() {
		return matureDate;
	}

	public void setMatureDate(String matureDate) {
		this.matureDate = matureDate;
	}
}
