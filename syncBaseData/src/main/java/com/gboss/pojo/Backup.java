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
 * @ClassName:Backup
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-10 上午11:43:42
 */
@Entity
@Table(name = "t_ba_bk")
public class Backup extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private Long bk_id;//'历史版本id',
	private Long unit_id;//车台id
	private Long vehicle_id;//车辆id
	private String bk_type;//'业务类型，有可能多个type,逗号隔开，0=维修, 1=换装, 2= 过户, 3=换号, 4=升级, 5=更改资料, 6=新业务办理',
	private Long bkc_id;//'历史客户ID',
	private Long bkv_id;//'历史车辆ID',
	private Long bku_id;//'历史车台ID',
	private Long bkfc_id;//'历史托收资料ID',
	private String bkf_ids;//'历史计费信息ID,有可能多个ID，逗号隔开',	  
	private String bkd_ids;//'历史司机资料ID,有可能多个ID，逗号隔开,预留',          	
	private Long op_id;//'经办人id',
	private String op_name;//'经办人姓名',
	private String remark;//'称呼/备注',
	private Date stamp;//'时间戳',
	
	@Id
	@Column(name = "bk_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getBk_id() {
		return bk_id;
	}
	public void setBk_id(Long bk_id) {
		this.bk_id = bk_id;
	}
	
	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}
	
	@Column(name = "vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	@Column(name = "bk_type")
	public String getBk_type() {
		return bk_type;
	}
	public void setBk_type(String bk_type) {
		this.bk_type = bk_type;
	}
	
	@Column(name = "bkc_id")
	public Long getBkc_id() {
		return bkc_id;
	}
	public void setBkc_id(Long bkc_id) {
		this.bkc_id = bkc_id;
	}
	
	@Column(name = "bkv_id")
	public Long getBkv_id() {
		return bkv_id;
	}
	public void setBkv_id(Long bkv_id) {
		this.bkv_id = bkv_id;
	}
	
	@Column(name = "bku_id")
	public Long getBku_id() {
		return bku_id;
	}
	public void setBku_id(Long bku_id) {
		this.bku_id = bku_id;
	}
	
	@Column(name = "bkf_ids")
	public String getBkf_ids() {
		return bkf_ids;
	}
	public void setBkf_ids(String bkf_ids) {
		this.bkf_ids = bkf_ids;
	}
	
	@Column(name = "bkd_ids")
	public String getBkd_ids() {
		return bkd_ids;
	}
	public void setBkd_ids(String bkd_ids) {
		this.bkd_ids = bkd_ids;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	@Column(name = "bkfc_id")
	public Long getBkfc_id() {
		return bkfc_id;
	}
	public void setBkfc_id(Long bkfc_id) {
		this.bkfc_id = bkfc_id;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}
	
	@Column(name = "op_name")
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}

}

