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
import javax.persistence.Transient;
/**
 * @Package:com.gboss.pojo
 * @ClassName:Stock
 * @Description:库存实体类
 * @author:zfy
 * @date:2013-8-29 上午10:14:03
 */
@Entity
@Table(name = "t_whs_stock")
public class Stock extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//库存ID
	private Long whsId;//仓库ID
	private String whsName;//仓库名称
	private Long companyId;//公司ID
	private String companyName;//公司名称
	private Long productId;//商品ID
	private String unit;//单位
	private Integer num;//数量
	private Long userId;//操作员ID
	private String userName;//盘点人名称
	private Date stamp;//操作时间
	private String remark;//备注

	private String productName;//商品name
	public Stock() {
	}

	public Stock(Long id) {
		this.id = id;
	}

	public Stock(Long id, Long whsId, Long productId,
			String productName, String unit, Integer num, Long userId,
			Date stamp, String remark) {
		this.id = id;
		this.whsId = whsId;
		this.productId = productId;
		this.unit = unit;
		this.num = num;
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
	@Column(name = "unit")
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
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
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "whs_name")
	public String getWhsName() {
		return whsName;
	}

	public void setWhsName(String whsName) {
		this.whsName = whsName;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Transient
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
	

}
