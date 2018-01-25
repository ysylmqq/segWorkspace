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

import com.gboss.comm.SystemConst;
import com.gboss.util.CustomDateSerializer;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Preload
 * @Description:预装
 * @author:bzhang
 * @date:2014-9-12 上午8:37:15
 */
@Entity
@Table(name = "t_ba_sim_bk")
public class PreloadBk extends BaseEntity {

	/** 
	 * @Fields serialVersionUID : TODO 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private Long bks_id;
	
	private Long sim_id;
	
	private Long subco_no;//分公司

	private Date stamp;//时间
	
	private Integer flag = SystemConst.SIM_NORM;//状态
	
	private Integer combo_status = SystemConst.SIM_COMBO_NORMAL;
	
	private String iccid;//识别码
	
	private String imsi;//移动识别码
	
	private String akey;//鉴权码
	
	private Integer telco;//运营商

	private String esn;//ESN
	
	private String w_user;//EVDO账号
	
	private String w_password;//EVDO密码
	
	private String call_letter;//MDN
	
	private String imei;//MEID
	
	private Long op_id;
	
	private String barcode;//条形码
	
	private Long unittype_id;//终端类型

	private String vin;//车架号
	
	private String engine_no;//发动机号
	
	private String color;//车辆颜色
	
	private Date production_date;//生产日期
	
	private Date s_date;//缴费日期
	
	private Date e_date;//缴费日期
	
	private Long  unit_id =0L;//入网后终端id
	
	private Long combo_id;//套餐id
	
	private Long new_combo_id = 0L;//新套餐ID,更改成功改为0
	
	private Long pack_id =0L;//服务包id
	
	private String remark;
	
	@Id
	@Column(name = "bks_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getBks_id() {
		return bks_id;
	}

	public void setBks_id(Long bks_id) {
		this.bks_id = bks_id;
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

	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}

	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "combo_status")
	public Integer getCombo_status() {
		return combo_status;
	}

	public void setCombo_status(Integer combo_status) {
		this.combo_status = combo_status;
	}

	@Column(name = "iccid")
	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	@Column(name = "imsi")
	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	@Column(name = "akey")
	public String getAkey() {
		return akey;
	}

	public void setAkey(String akey) {
		this.akey = akey;
	}

	@Column(name = "esn")
	public String getEsn() {
		return esn;
	}

	public void setEsn(String esn) {
		this.esn = esn;
	}

	
	@Column(name = "call_letter")
	public String getCall_letter() {
		return call_letter;
	}

	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}

	@Column(name = "imei")
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}

	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}


	@Column(name = "unittype_id")
	public Long getUnittype_id() {
		return unittype_id;
	}

	public void setUnittype_id(Long unittype_id) {
		this.unittype_id = unittype_id;
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

	@Column(name = "color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	@Column(name = "production_date")
	public Date getProduction_date() {
		return production_date;
	}

	public void setProduction_date(Date production_date) {
		this.production_date = production_date;
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

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}

	@Column(name = "telco")
	public Integer getTelco() {
		return telco;
	}

	public void setTelco(Integer telco) {
		this.telco = telco;
	}

	@Column(name = "w_user")
	public String getW_user() {
		return w_user;
	}

	public void setW_user(String w_user) {
		this.w_user = w_user;
	}

	@Column(name = "w_password")
	public String getW_password() {
		return w_password;
	}

	public void setW_password(String w_password) {
		this.w_password = w_password;
	}

	@Column(name = "barcode")
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	@Column(name = "new_combo_id")
	public Long getNew_combo_id() {
		return new_combo_id;
	}

	public void setNew_combo_id(Long new_combo_id) {
		this.new_combo_id = new_combo_id;
	}

	@Column(name = "pack_id")
	public Long getPack_id() {
		return pack_id;
	}

	public void setPack_id(Long pack_id) {
		this.pack_id = pack_id;
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

