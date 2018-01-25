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
 * @ClassName:SellPerformance
 * @Description:销售业绩实体类
 * @author:zfy
 * @date:2013-8-6 上午11:01:58
 */
@Entity
@Table(name = "t_stat_sell_performance")
public class SellPerformance extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private String year;//年
	private String month;//月份或者全年
	private Integer type;//业绩类型 1:销售经理业绩，2:部门业绩，3:分公司业绩
	private Long userOrgId;//销售经理、所属部门、分公司ID
	private Integer planSellNum;//计划销售数量
	private Integer realSellNum;//实际销售数量
	private Float sellNumRate;//销售数量完成比例
	private Integer planNet;//计划入网数量
	private Integer realNet;//实际入网数量
	private Float netRate;//入网数量完成比例
	private Float planReturnMoney;//计划回款额度
	private Float realReturnMoney;//实际回款额度
	private Float returnMoneyRate;//回款额度完成比例
	private Date stamp;

	public SellPerformance() {
	}

	public SellPerformance(Long id) {
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
	@Column(name = "year")
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	@Column(name = "month")
	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	@Column(name = "plan_sell_num")
	public Integer getPlanSellNum() {
		return this.planSellNum;
	}

	public void setPlanSellNum(Integer planSellNum) {
		this.planSellNum = planSellNum;
	}
	@Column(name = "real_sell_num")
	public Integer getRealSellNum() {
		return this.realSellNum;
	}

	public void setRealSellNum(Integer realSellNum) {
		this.realSellNum = realSellNum;
	}
	@Column(name = "sell_num_rate")
	public Float getSellNumRate() {
		return this.sellNumRate;
	}

	public void setSellNumRate(Float sellNumRate) {
		this.sellNumRate = sellNumRate;
	}
	@Column(name = "plan_net")
	public Integer getPlanNet() {
		return this.planNet;
	}

	public void setPlanNet(Integer planNet) {
		this.planNet = planNet;
	}
	@Column(name = "real_net")
	public Integer getRealNet() {
		return this.realNet;
	}

	public void setRealNet(Integer realNet) {
		this.realNet = realNet;
	}
	@Column(name = "net_rate")
	public Float getNetRate() {
		return this.netRate;
	}
	
	public void setNetRate(Float netRate) {
		this.netRate = netRate;
	}
	@Column(name = "plan_return_money")
	public Float getPlanReturnMoney() {
		return this.planReturnMoney;
	}
	
	public void setPlanReturnMoney(Float planReturnMoney) {
		this.planReturnMoney = planReturnMoney;
	}
	@Column(name = "real_return_money")
	public Float getRealReturnMoney() {
		return this.realReturnMoney;
	}
	
	public void setRealReturnMoney(Float realReturnMoney) {
		this.realReturnMoney = realReturnMoney;
	}
	@Column(name = "return_money_rate")
	public Float getReturnMoneyRate() {
		return this.returnMoneyRate;
	}
	public void setReturnMoneyRate(Float returnMoneyRate) {
		this.returnMoneyRate = returnMoneyRate;
	}
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
    @Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "user_org_id")
	public Long getUserOrgId() {
		return userOrgId;
	}

	public void setUserOrgId(Long userOrgId) {
		this.userOrgId = userOrgId;
	}
}
