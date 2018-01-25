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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

/**
 * @Package:com.gboss.pojo
 * @ClassName:FeeInfo
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-27 下午7:22:45
 */
@Entity
@Table(name = "t_fee_info")
public class FeeInfo extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long fee_id;//'计费信息ID',
	private Long subco_no;//'分公司ID',
	private Long customer_id;//'客户ID, 对应是需要付账的客户',
	private Long vehicle_id;//'车辆ID',
	private Long unit_id;//'车台ID',
	private Integer feetype_id;//'计费类型, 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费
	private Integer pay_model;
	private Long item_id;//'套餐ID',
	private String items_id;//服务项串, 逗号分隔, 界面显示用
	private Float month_fee;//每月费用(元)
	private Float ac_amount;//'应收费用总额, 系统计算',
	private String pay_ct_no;//'托收合同号, 预留',
	private Long collection_id;//'托收资料id',
	private Float real_amount;//'实收金额, 以实收为准',
	private Date fee_date;//'收费开始时间',
	private Date fee_sedate;//服务截止时间
	private Integer fee_cycle;//'收费周期(月数)',
	private Long op_id;//'操作员id',
	private Date stamp;//'时间戳',
	private String remark;
	
	//欠费信息（后台计算，方便前台页面显示，不存入数据库的）
	private Integer arrearage_fee = 0;//欠费金额
	private Integer a_months = 0;//欠费月数
	private Integer a_days = 0;//欠费天数
	
	
	
	@Id
	@Column(name = "fee_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getFee_id() {
		return fee_id;
	}
	public void setFee_id(Long fee_id) {
		this.fee_id = fee_id;
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
	
	@Column(name = "item_id")
	public Long getItem_id() {
		return item_id;
	}
	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}
	
	@Column(name = "ac_amount")
	public Float getAc_amount() {
		return ac_amount;
	}
	public void setAc_amount(Float ac_amount) {
		this.ac_amount = ac_amount;
	}
	
	@Column(name = "pay_ct_no")
	public String getPay_ct_no() {
		return pay_ct_no;
	}
	public void setPay_ct_no(String pay_ct_no) {
		this.pay_ct_no = pay_ct_no;
	}
	
	@Column(name = "collection_id")
	public Long getCollection_id() {
		return collection_id;
	}
	public void setCollection_id(Long collection_id) {
		this.collection_id = collection_id;
	}
	
	@Column(name = "real_amount")
	public Float getReal_amount() {
		return real_amount;
	}
	public void setReal_amount(Float real_amount) {
		this.real_amount = real_amount;
	}
	
	@Column(name = "fee_date")
	public Date getFee_date() {
		return fee_date;
	}
	public void setFee_date(Date fee_date) {
		this.fee_date = fee_date;
	}
	
	@Column(name = "fee_cycle")
	public Integer getFee_cycle() {
		return fee_cycle;
	}
	public void setFee_cycle(Integer fee_cycle) {
		this.fee_cycle = fee_cycle;
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
	
	@Column(name = "pay_model")
	public Integer getPay_model() {
		return pay_model;
	}
	public void setPay_model(Integer pay_model) {
		this.pay_model = pay_model;
	}
	
	@Column(name = "items_id")
	public String getItems_id() {
		return items_id;
	}
	public void setItems_id(String items_id) {
		this.items_id = items_id;
	}
	
	@Column(name = "month_fee")
	public Float getMonth_fee() {
		return month_fee;
	}
	public void setMonth_fee(Float month_fee) {
		this.month_fee = month_fee;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "fee_sedate")
	public Date getFee_sedate() {
		return fee_sedate;
	}
	public void setFee_sedate(Date fee_sedate) {
		this.fee_sedate = fee_sedate;
	}
	
	@Transient
	public Integer getArrearage_fee() {
		return arrearage_fee;
	}
	public void setArrearage_fee(Integer arrearage_fee) {
		this.arrearage_fee = arrearage_fee;
	}
	
	@Transient
	public Integer getA_months() {
		return a_months;
	}
	public void setA_months(Integer a_months) {
		this.a_months = a_months;
	}
	
	@Transient
	public Integer getA_days() {
		return a_days;
	}
	public void setA_days(Integer a_days) {
		this.a_days = a_days;
	}
	
	
	
	

}

