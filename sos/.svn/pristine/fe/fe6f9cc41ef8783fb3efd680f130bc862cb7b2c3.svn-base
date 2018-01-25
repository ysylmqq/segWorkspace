package com.gboss.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
 * @ClassName:Supplycontracts
 * @Description:总部供货合同实体类
 * @author:zfy
 * @date:2013-8-20 下午3:54:16
 */
@Entity
@Table(name = "t_sel_supplycontracts")
public class Supplycontracts extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//供货合同ID
	private String code;//供货合同编号
	private String name;//供货合同名称
	private Integer type;//0:所有，1:2005年前成立的公司使用，2：2005年后成立的公司使用
	private String validDate;//生效日期
	private String matureDate;//到期日期
	private Integer status;//供货合同状态:1: 已生效,0:未生效
	private Long userId;//操作员ID
	private String userName;//操作员name
	private Date stamp;//操作时间
	private Long checkUserId;//审核人ID
	private Date checkStamp;//审核时间
	private String remark;//备注
	
	private List<SupplyBranch> supplyBranchs;//总部供货合同子公司关系
	private List<SupplyDetails> supplyDetails;//供货合同产品明细
	
	private List<HashMap<String, Object>> suppplyDetailsMap;//供货合同产品明细 
	public Supplycontracts() {
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
	@Column(name = "code",length=32)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "name",length=256)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	@Column(name = "remark",length=512)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Transient
	public List<SupplyBranch> getSupplyBranchs() {
		return supplyBranchs;
	}
	public void setSupplyBranchs(List<SupplyBranch> supplyBranchs) {
		this.supplyBranchs = supplyBranchs;
	}
	@Transient
	public List<SupplyDetails> getSupplyDetails() {
		return supplyDetails;
	}
	public void setSupplyDetails(List<SupplyDetails> supplyDetails) {
		this.supplyDetails = supplyDetails;
	}
	@Transient
	public List<HashMap<String, Object>> getSuppplyDetailsMap() {
		return suppplyDetailsMap;
	}
	public void setSuppplyDetailsMap(List<HashMap<String, Object>> suppplyDetailsMap) {
		this.suppplyDetailsMap = suppplyDetailsMap;
	}
	@Column(name = "type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "check_user_id")
	public Long getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(Long checkUserId) {
		this.checkUserId = checkUserId;
	}
	@Column(name = "check_stamp")
	public Date getCheckStamp() {
		return checkStamp;
	}
	public void setCheckStamp(Date checkStamp) {
		this.checkStamp = checkStamp;
	}
}
