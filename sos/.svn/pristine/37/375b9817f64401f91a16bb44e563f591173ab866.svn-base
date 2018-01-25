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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Product
 * @Description:内部商品实体类
 * @author:zfy
 * @date:2013-7-31 下午3:14:30
 */
@Entity
@Table(name = "t_sel_product")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Product extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//内部商品ID
	private String code;//商品型号
	private String name;//商品名称
	private String norm;//商品规格
	private Integer type;//类别,0:成品，1：配件
	private String unit;//单位
	private Float price;//单价
	private Integer status;//状态，1：已生效，0：未生效
	private Integer issell;//是否在销，1：是，0：否
	private Integer sourceType;//来源类型，0：总部，1：外部采购，2：自定义
	private Long userId;//操作员ID
	private String userName;//操作员name
	private Long companyId;//分公司ID
	private String companyName;//分公司name
	private Date stamp;
	private String remark;

	private String num;//表示配件的用量，不是表的属性
	private String oftenId;//表示常用商品表的主键
	private Integer level;//等级，0：主料，1：替代料
	
	public Product() {
	}

	public Product(Long id) {
		this.id = id;
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
	
	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "code")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "norm")
	public String getNorm() {
		return this.norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}

	@Column(name = "unit")
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "issell")
	public Integer getIssell() {
		return this.issell;
	}

	public void setIssell(Integer issell) {
		this.issell = issell;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	@Column(name = "price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "source_type")
	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "company_id")
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
    @Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Transient
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Transient
	public String getOftenId() {
		return oftenId;
	}

	public void setOftenId(String oftenId) {
		this.oftenId = oftenId;
	}
	
}
