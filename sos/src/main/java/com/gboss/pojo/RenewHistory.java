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

@Entity
@Table(name = "t_fee_hm_renew_history")
public class RenewHistory extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Integer history_id;//'序号',
	private String customer_name;//'客户名称',
	private String telphone;//'联系电话',
	private String vin;//'车架号',
	private String engine_no;//'发动机号',
	private String plate_no;//'车牌号 ',
	private String barCode;//'Tbox条码',
	private String call_letter;//'tbox呼号',
	private String equip_code;//'配置简码',
	private Date service_start_date;//'服务起始时间',
	private Date service_end_date;//'服务期满时间',
	private Date combo_change_date;//'套餐变更时间',
	private String combo_type;//'套餐类型',
	private Date service_end_newdate;//'新有效期时间',
	
	@Id
	@Column(name = "history_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getHistory_id() {
		return history_id;
	}
	public void setHistory_id(Integer history_id) {
		this.history_id = history_id;
	}
	@Column(name = "telphone")
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	@Column(name = "vin")
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	@Column(name = "engine_no")
	public String getEngine_no() {
		return engine_no;
	}
	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}
	@Column(name = "plate_no")
	public String getPlate_no() {
		return plate_no;
	}
	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}
	@Column(name = "barCode")
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	@Column(name = "call_letter")
	public String getCall_letter() {
		return call_letter;
	}
	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}
	@Column(name = "equip_code")
	public String getEquip_code() {
		return equip_code;
	}
	public void setEquip_code(String equip_code) {
		this.equip_code = equip_code;
	}
	@Column(name = "service_start_date")
	public Date getService_start_date() {
		return service_start_date;
	}
	public void setService_start_date(Date service_start_date) {
		this.service_start_date = service_start_date;
	}
	@Column(name = "service_end_date")
	public Date getService_end_date() {
		return service_end_date;
	}
	public void setService_end_date(Date service_end_date) {
		this.service_end_date = service_end_date;
	}
	@Column(name = "combo_change_date")
	public Date getCombo_change_date() {
		return combo_change_date;
	}
	public void setCombo_change_date(Date combo_change_date) {
		this.combo_change_date = combo_change_date;
	}
	@Column(name = "combo_type")
	public String getCombo_type() {
		return combo_type;
	}
	public void setCombo_type(String combo_type) {
		this.combo_type = combo_type;
	}
	@Column(name = "service_end_newdate")
	public Date getService_end_newdate() {
		return service_end_newdate;
	}
	public void setService_end_newdate(Date service_end_newdate) {
		this.service_end_newdate = service_end_newdate;
	}
	@Column(name = "customer_name")
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	
}
