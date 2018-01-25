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
 * @ClassName:Feepayment
 * @Description:TODO
 * @author:bzhang
 * @date:2014-8-22 下午4:07:59
 */
@Entity
@Table(name = "t_fee_payment_dt")
public class FeePaymentDetail extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long payment_sub_id;
	private Long payment_id;//'缴费ID',
	private Long subco_no;//'分公司ID',
	private Long customer_id;//'客户ID',
	private Long org_id;//'营业处组织机构ID, 现金缴费, 托收可为0',
	private Long vehicle_id;//'车辆ID',
	private Long unit_id;//'车台ID',
	private Integer feetype_id;//'计费项目类型, 系统值3100, 1=服务费, 2=SIM卡, 3=保险, 4=终端产品, 5=回单 ',
	private Integer pay_model;//'缴费方式, 系统值3050, 0=托收, 1=现金, 2=转账, 3=刷卡',
	private Integer item_id;//'项目ID',
	private String item_name;//'项目名称',
	private Date s_date;//'开始日期, 如采用余额方式, 无效',
	private Date e_date;//'结束日期, 如采用余额方式, 无效',
	private Integer s_months;//'服务时长(月), 如采用余额方式, 无效',
	private Integer s_days = 0;//'服务时长(天), 如采用余额方式, 无效',
	private Float ac_amount;//'应收费用总额, 现金缴费, 促销活动',
	private Float real_amount;//'实收金额',
	private String bw_no;//'收据单号/交易流水号',
	private Long agent_id;//'经办人, 现金',
	private String agent_name;//'经办人姓名,  现金',
	private Date pay_time;//'实际付费时间, 托收方式为托收返回时间',
	private Date stamp;//'时间戳',
	private Long op_id;//'操作员id',
	private Long flag;//交易标记, 0=成功, 1=失败, 2=作废
	private String remark;//'备注',
	private Integer is_invoice;//'是否已开发票, 1=是, 0=否',
	private Integer print_num;//'发票打印次数',
	private Date print_time;//'发票打印时间',
	private Integer is_audited;//'是否审核, 1=是, 0=否',
	private String auditor;//'审核人',
	private Date audited_time;//'审核时间',
	
	
	@Id
	@Column(name = "payment_sub_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getPayment_sub_id() {
		return payment_sub_id;
	}
	public void setPayment_sub_id(Long payment_sub_id) {
		this.payment_sub_id = payment_sub_id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Column(name = "payment_id")
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
	
	@Column(name = "customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name = "org_id")
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	
	@Column(name = "vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}
	
	@Column(name = "feetype_id")
	public Integer getFeetype_id() {
		return feetype_id;
	}
	public void setFeetype_id(Integer feetype_id) {
		this.feetype_id = feetype_id;
	}
	
	@Column(name = "pay_model")
	public Integer getPay_model() {
		return pay_model;
	}
	public void setPay_model(Integer pay_model) {
		this.pay_model = pay_model;
	}
	
	@Column(name = "item_id")
	public Integer getItem_id() {
		return item_id;
	}
	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}
	
	@Column(name = "item_name")
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
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
	
	@Column(name = "ac_amount")
	public Float getAc_amount() {
		return ac_amount;
	}
	public void setAc_amount(Float ac_amount) {
		this.ac_amount = ac_amount;
	}
	
	@Column(name = "real_amount")
	public Float getReal_amount() {
		return real_amount;
	}
	public void setReal_amount(Float real_amount) {
		this.real_amount = real_amount;
	}
	
	@Column(name = "bw_no")
	public String getBw_no() {
		return bw_no;
	}
	public void setBw_no(String bw_no) {
		this.bw_no = bw_no;
	}
	
	@Column(name = "agent_id")
	public Long getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}
	
	@Column(name = "agent_name")
	public String getAgent_name() {
		return agent_name;
	}
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	
	@Column(name = "pay_time")
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
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
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}

	@Column(name = "flag")
	public Long getFlag() {
		return flag;
	}
	public void setFlag(Long flag) {
		this.flag = flag;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "is_invoice")
	public Integer getIs_invoice() {
		return is_invoice;
	}
	public void setIs_invoice(Integer is_invoice) {
		this.is_invoice = is_invoice;
	}
	
	@Column(name = "print_num")
	public Integer getPrint_num() {
		return print_num;
	}
	public void setPrint_num(Integer print_num) {
		this.print_num = print_num;
	}
	
	@Column(name = "print_time")
	public Date getPrint_time() {
		return print_time;
	}
	public void setPrint_time(Date print_time) {
		this.print_time = print_time;
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
	
	@Column(name = "s_days")
	public Integer getS_days() {
		return s_days;
	}
	public void setS_days(Integer s_days) {
		this.s_days = s_days;
	}
	@Column(name = "audited_time",nullable=true,updatable=false,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getAudited_time() {
		return audited_time;
	}
	public void setAudited_time(Date audited_time) {
		this.audited_time = audited_time;
	}

}

