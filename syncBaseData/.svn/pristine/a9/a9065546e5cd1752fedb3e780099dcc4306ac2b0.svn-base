package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_fee_info_bk")
public class Feeinfobk extends BaseEntity {
	
	private static final long serialVersionUID = 51258284656898064L;
	
	private long bkf_id;//bk计费信息ID
	private long fee_id;//计费信息ID
	private long subco_no;//分公司ID
	private long customer_id;//客户ID, 对应是需要付账的客户
	private long vehicle_id;//车辆ID
	private long unit_id;//车台ID
	private int feetype_id;//计费类型, 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费
	private int pay_model;//付费方式, 集团客户可能每车不同, 私家车每项缴费方式不同, 系统值3050, 0=托收, 1=现金, 2=转账, 3=刷卡
	private long item_id;//套餐ID, pack_id, 预留
	private String items_id;//服务项串, 逗号分隔, 界面显示用
	private double month_fee;//每月费用(元)
	private double ac_amount;//应收费用总额, 系统计算
	private long collection_id;//托收资料ID, 非托收方式值为0
	private String pay_ct_no;//托收合同号, 预留
	private double real_amount;//实收金额, 以实收为准
	private Date fee_date;//收费开始时间
	private Date fee_sedate;//服务截止时间
	private int fee_cycle;//收费周期(月数)
	private long op_id;//操作员id
	private Date stamp;//时间戳
	private int flag;//标志, 预留, 0=正常, 1=删除
	private String remark;//备注
	
	@Id
	@Column(name="bkf_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getBkf_id() {
		return bkf_id;
	}
	public void setBkf_id(long bkf_id) {
		this.bkf_id = bkf_id;
	}
	
	@Column(name="fee_id")
	public long getFee_id() {
		return fee_id;
	}
	public void setFee_id(long fee_id) {
		this.fee_id = fee_id;
	}
	@Column(name="subco_no")
	public long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name="customer_id")
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name="vehicle_id")
	public long getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	@Column(name="unit_id")
	public long getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(long unit_id) {
		this.unit_id = unit_id;
	}
	
	@Column(name="feetype_id")
	public int getFeetype_id() {
		return feetype_id;
	}
	public void setFeetype_id(int feetype_id) {
		this.feetype_id = feetype_id;
	}
	
	@Column(name="pay_model")
	public int getPay_model() {
		return pay_model;
	}
	public void setPay_model(int pay_model) {
		this.pay_model = pay_model;
	}
	
	@Column(name="item_id")
	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	
	@Column(name="items_id")
	public String getItems_id() {
		return items_id;
	}
	public void setItems_id(String items_id) {
		this.items_id = items_id;
	}
	
	@Column(name="month_fee")
	public double getMonth_fee() {
		return month_fee;
	}
	public void setMonth_fee(double month_fee) {
		this.month_fee = month_fee;
	}
	
	@Column(name="ac_amount")
	public double getAc_amount() {
		return ac_amount;
	}
	public void setAc_amount(double ac_amount) {
		this.ac_amount = ac_amount;
	}

	@Column(name="collection_id")
	public long getCollection_id() {
		return collection_id;
	}
	public void setCollection_id(long collection_id) {
		this.collection_id = collection_id;
	}
	
	@Column(name="pay_ct_no")
	public String getPay_ct_no() {
		return pay_ct_no;
	}
	public void setPay_ct_no(String pay_ct_no) {
		this.pay_ct_no = pay_ct_no;
	}
	
	@Column(name="real_amount")
	public double getReal_amount() {
		return real_amount;
	}
	public void setReal_amount(double real_amount) {
		this.real_amount = real_amount;
	}
	
	@Column(name="fee_date")
	public Date getFee_date() {
		return fee_date;
	}
	public void setFee_date(Date fee_date) {
		this.fee_date = fee_date;
	}
	
	@Column(name="fee_sedate")
	public Date getFee_sedate() {
		return fee_sedate;
	}
	public void setFee_sedate(Date fee_sedate) {
		this.fee_sedate = fee_sedate;
	}
	
	@Column(name="fee_cycle")
	public int getFee_cycle() {
		return fee_cycle;
	}
	public void setFee_cycle(int fee_cycle) {
		this.fee_cycle = fee_cycle;
	}
	
	@Column(name="op_id")
	public long getOp_id() {
		return op_id;
	}
	public void setOp_id(long op_id) {
		this.op_id = op_id;
	}
	
	@Column(name="stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name="flag")
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
