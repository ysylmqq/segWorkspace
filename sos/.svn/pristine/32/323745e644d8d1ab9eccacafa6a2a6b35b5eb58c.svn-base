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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

/**
 * @Package:com.gboss.pojo
 * @ClassName:PaymentSim
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-29 上午10:26:06
 */
@Entity
@Table(name = "t_fee_payment_sim")
public class PaymentSim extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long payment_id;//缴费ID
	private Long subco_no;//分公司ID
	private Long sim_id;//SIM卡id
	private Long combo_id;//套餐id
	private Long unit_id = 0L;//车台ID
	private Integer pay_model;//缴费方式, 系统值3050, 0=托收, 1=现金, 2=转账, 3=刷卡
	private Date s_date;//开始日期
	private Date e_date;//结束日期
	private Integer s_months;//服务时长(月)
	private Integer s_days;//服务时长(日)
	private float ac_amount;//应收费用总额
	private float real_amount;//实收费用总额
	private Date pay_time;//实际缴费时间
	private	Integer flag;//标记, 0=成功, 1=失败, 2=作废
	private String remark;//备注
	private Integer is_audited =0;//是否审核, 1=是, 0=否
	private String auditor;//审核人
	private Date audited_time;//审核时间
	private Date stamp;
	private Long op_id;
	
	@Id
	@Column(name = "payment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	public Long getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(Long payment_id) {
		this.payment_id = payment_id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}
	
	@Column(name = "sim_id")
	public Long getSim_id() {
		return sim_id;
	}
	public void setSim_id(Long sim_id) {
		this.sim_id = sim_id;
	}
	
	@Column(name = "combo_id")
	public Long getCombo_id() {
		return combo_id;
	}
	public void setCombo_id(Long combo_id) {
		this.combo_id = combo_id;
	}
	@Column(name = "pay_model")
	public Integer getPay_model() {
		return pay_model;
	}
	public void setPay_model(Integer pay_model) {
		this.pay_model = pay_model;
	}
	
	@Column(name = "s_date")
	public Date getS_date() {
		return s_date;
	}
	public void setS_date(Date s_date) {
		this.s_date = s_date;
	}
	
	@Column(name = "e_date")
	public Date getE_date() {
		return e_date;
	}
	public void setE_date(Date e_date) {
		this.e_date = e_date;
	}
	
	@Column(name = "s_months")
	public Integer getS_months() {
		return s_months;
	}
	public void setS_months(Integer s_months) {
		this.s_months = s_months;
	}
	
	@Column(name = "s_days")
	public Integer getS_days() {
		return s_days;
	}
	public void setS_days(Integer s_days) {
		this.s_days = s_days;
	}
	
	@Column(name = "ac_amount")
	public float getAc_amount() {
		return ac_amount;
	}
	public void setAc_amount(float ac_amount) {
		this.ac_amount = ac_amount;
	}
	
	@Column(name = "real_amount")
	public float getReal_amount() {
		return real_amount;
	}
	public void setReal_amount(float real_amount) {
		this.real_amount = real_amount;
	}
	
	@Column(name = "pay_time")
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}
	
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "is_audited")
	public Integer getIs_audited() {
		return is_audited;
	}
	public void setIs_audited(Integer is_audited) {
		this.is_audited = is_audited;
	}
	
	@Column(name = "auditor")
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@Column(name = "audited_time")
	public Date getAudited_time() {
		return audited_time;
	}
	public void setAudited_time(Date audited_time) {
		this.audited_time = audited_time;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}

	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	
}

