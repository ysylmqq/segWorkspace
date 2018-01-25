package com.hm.bigdata.entity.po;

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
 * @Package:com.chinagps.fee.entity.po
 * @ClassName:subco
 * @Description:公司托收账号信息表 的实体类
 * @author:zfy
 * @date:2014-5-27 下午2:06:59
 */
@Entity
@Table(name = "t_fee_subco")
public class Subco  extends BaseEntity{
	private static final Long serialVersionUID = 1L;
	private Long acId;//账号信息ID
	private Long subcoNo;//分公司ID
	private Integer agency;//托收机构ID, 1=银盛, 2=金融中心(默认值), 3=银联
	private String accountCode;//收付单位代码, 托收机构给公司的商户代码 3位
	private String accountNo;//收付代码/账号 3位
	private String bankCode;//银行代码/代办银行号, 网点, 2位分行代码+3位网点号
	private Integer flag;//标志, 1=有效, 0=无效
	private Integer ctIsfix;//是否固定日期托收
	private Integer ctDay;//固定托收日期
	private Long opId;//操作员id
	private Date stamp;//时间戳
	
	@Id
	@Column(name = "ac_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getAcId() {
		return acId;
	}
	public void setAcId(Long acId) {
		this.acId = acId;
	}
	@Column(name = "subco_no")
	public Long getSubcoNo() {
		return subcoNo;
	}
	public void setSubcoNo(Long subcoNo) {
		this.subcoNo = subcoNo;
	}
	@Column(name = "agency")
	public Integer getAgency() {
		return agency;
	}
	public void setAgency(Integer agency) {
		this.agency = agency;
	}
	@Column(name = "account_code")
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	@Column(name = "account_no")
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	@Column(name = "op_id")
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
    @Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	@Column(name = "bank_code")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@Column(name = "ct_isfix")
	public Integer getCtIsfix() {
		return ctIsfix;
	}
	public void setCtIsfix(Integer ctIsfix) {
		this.ctIsfix = ctIsfix;
	}
	@Column(name = "ct_day")
	public Integer getCtDay() {
		return ctDay;
	}
	public void setCtDay(Integer ctDay) {
		this.ctDay = ctDay;
	}

}

