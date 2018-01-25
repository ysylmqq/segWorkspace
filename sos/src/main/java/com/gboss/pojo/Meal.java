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
 * @ClassName:Meal
 * @Description:套餐
 * @author:hansm
 * @date:2016-6-08 下午3:20:13
 */
@Entity
@Table(name = "t_fee_sim_combo")
public class Meal extends BaseEntity {
	
	/** 
	 * @Fields serialVersionUID : TODO 
	 */ 
	private static final long serialVersionUID = 1L;

	private Long combo_id;//套餐ID
	
	private Long subco_no;//分公司
	
	private Integer feetype_id;//计费类型 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费
	
	private Integer sim_type =1;//套餐类型  1=语音卡, 2=流量卡
	
	private String combo_code;//套餐编号
	
	private String combo_name;//套餐名
	
	private Float voice_time;//总共通话时间
	
	private Float data;//总共流量
	
	private Integer telco;//电信运营商, 1=移动, 2=联通, 3=电信
	
	private Float month_fee;//套餐费
	
	private Long op_id;//操作員id
	
	private Date create_date;//创建时间
	
	private Integer flag=1;//状态
	
	private Date stamp;
	
	private String remark;//备注
	
	private Float voice_price;//超出套餐语音价格
	
	private Float data_price;//超出套餐流量价格
	

	@Id
	@Column(name = "combo_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCombo_id() {
		return combo_id;
	}
	public void setCombo_id(Long combo_id) {
		this.combo_id = combo_id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "feetype_id")
	public Integer getFeetype_id() {
		return feetype_id;
	}
	public void setFeetype_id(Integer feetype_id) {
		this.feetype_id = feetype_id;
	}
	
	@Column(name = "sim_type")
	public Integer getSim_type() {
		return sim_type;
	}
	public void setSim_type(Integer sim_type) {
		this.sim_type = sim_type;
	}
	
	@Column(name = "combo_code")
	public String getCombo_code() {
		return combo_code;
	}
	public void setCombo_code(String combo_code) {
		this.combo_code = combo_code;
	}
	
	@Column(name = "combo_name")
	public String getCombo_name() {
		return combo_name;
	}
	public void setCombo_name(String combo_name) {
		this.combo_name = combo_name;
	}
	
	@Column(name = "voice_time")
	public Float getVoice_time() {
		return voice_time;
	}
	public void setVoice_time(Float voice_time) {
		this.voice_time = voice_time;
	}
	
	@Column(name = "data")
	public Float getData() {
		return data;
	}
	public void setData(Float data) {
		this.data = data;
	}
	
	@Column(name = "telco")
	public Integer getTelco() {
		return telco;
	}
	public void setTelco(Integer telco) {
		this.telco = telco;
	}
	
	@Column(name = "month_fee")
	public Float getMonth_fee() {
		return month_fee;
	}
	public void setMonth_fee(Float month_fee) {
		this.month_fee = month_fee;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}
	
	@Column(name = "create_date")
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
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
	
	@Column(name = "voice_price")
	public Float getVoice_price() {
		return voice_price;
	}
	public void setVoice_price(Float voice_price) {
		this.voice_price = voice_price;
	}
	
	@Column(name = "data_price")
	public Float getData_price() {
		return data_price;
	}
	public void setData_price(Float data_price) {
		this.data_price = data_price;
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

