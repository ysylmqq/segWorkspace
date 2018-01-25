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
 * @ClassName:Plan
 * @Description:销售计划实体类
 * @author:zfy
 * @date:2013-8-6 上午8:31:56
 */
@Entity
@Table(name = "t_sel_plan")
public class Plan extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//销售计划ID
	private String year;//年份
	private Integer level;//计划级别(0:个人；1：部门；2：公司）
	private Long orgUserId;//用户或组织ID
	private String monthQuota;//月份或指标
	private Integer sno;//序号
	private Integer sellNum;//销售台数
	private Integer netQuota;//入网指标
	private Float moneyTotal;//回款总数
	private Long userId;//操作员ID
	private Date stamp;//操作时间
	private String remark;//备注

	public Plan() {
	}

	public Plan(Long id) {
		this.id = id;
	}

	public Plan(Long id, String year, Integer level, Long orgUserId,
			String monthQuota, Integer sno, Integer sellNum, Integer netQuota,
			Float moneyTotal, Long userId, Date stamp, String remark) {
		this.id = id;
		this.year = year;
		this.level = level;
		this.orgUserId = orgUserId;
		this.monthQuota = monthQuota;
		this.sno = sno;
		this.sellNum = sellNum;
		this.netQuota = netQuota;
		this.moneyTotal = moneyTotal;
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
	
	@Column(name = "year")
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "org_user_id")
	public Long getOrgUserId() {
		return this.orgUserId;
	}

	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
	}

	@Column(name = "month_quota")
	public String getMonthQuota() {
		return this.monthQuota;
	}

	public void setMonthQuota(String monthQuota) {
		this.monthQuota = monthQuota;
	}

	@Column(name = "sno")
	public Integer getSno() {
		return this.sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	@Column(name = "sell_num")
	public Integer getSellNum() {
		return this.sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	@Column(name = "net_quota")
	public Integer getNetQuota() {
		return this.netQuota;
	}

	public void setNetQuota(Integer netQuota) {
		this.netQuota = netQuota;
	}

	@Column(name = "money_total")
	public Float getMoneyTotal() {
		return this.moneyTotal;
	}

	public void setMoneyTotal(Float moneyTotal) {
		this.moneyTotal = moneyTotal;
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
		return stamp;
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

}
