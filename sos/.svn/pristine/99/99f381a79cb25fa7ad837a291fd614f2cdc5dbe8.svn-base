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
 * @ClassName:Setup
 * @Description:库存设置实体类
 * @author:zfy
 * @date:2013-8-29 上午10:16:53
 */
@Entity
@Table(name = "t_whs_setup")
public class Setup extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//关联ID
	private Long whsId;//仓库ID
	private String whsName;//仓库名称
	private Long productId;//商品ID
	private Integer minStock;//最小库存
	private Integer overstockTime;//积压时长
	private Long userId;//操作员ID
	private Date stamp;//操作时间

	public Setup() {
	}

	public Setup(Long whsId) {
		this.whsId = whsId;
	}

	public Setup(Long whsId, Long id, Long productId,
			String productName, Integer minStock, Integer overstockTime,
			Long userId, Date stamp) {
		this.whsId = whsId;
		this.id = id;
		this.productId = productId;
		this.minStock = minStock;
		this.overstockTime = overstockTime;
		this.userId = userId;
		this.stamp = stamp;
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
	@Column(name = "product_id")
	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	@Column(name = "min_stock")
	public Integer getMinStock() {
		return this.minStock;
	}

	public void setMinStock(Integer minStock) {
		this.minStock = minStock;
	}
	@Column(name = "overstock_time")
	public Integer getOverstockTime() {
		return this.overstockTime;
	}

	public void setOverstockTime(Integer overstockTime) {
		this.overstockTime = overstockTime;
	}
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return this.stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	@Column(name = "whs_name")
	public String getWhsName() {
		return whsName;
	}

	public void setWhsName(String whsName) {
		this.whsName = whsName;
	}

}
