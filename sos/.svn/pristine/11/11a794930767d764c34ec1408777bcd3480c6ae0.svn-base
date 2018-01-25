package com.gboss.pojo;

import java.util.Date;
import java.util.List;

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
 * @ClassName:Documents
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-23 上午11:03:31
 */
@Entity
@Table(name = "t_ba_documents")
public class Documents extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Long doc_id;//'原始单据ID',
	private Long customer_id;//'客户ID, 冗余, 有可能单独查, 集客是每单每档还是同一个',
	private Long vehicle_id;//'车辆ID, 冗余, 有可能单独查',
	private Long unit_id;//'车台ID',
	private String doc_name;//'单据名称',
	private Integer doc_type;//'单据类型, 系统值2070, 1=身份证, 2=行驶证, 3=入网证, 4=托收协议, 5=银行卡/存折, 6=保单, 7=保卡, 8=回单',
	private String doc_path;//'单据附件路径',
	private Date stamp;//'上传时间',
	private Integer flag;//'审核标志, 0=未审核, 1=审核通过',
	private String remark;//'备注',
	
	@Id
	@Column(name = "doc_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(Long doc_id) {
		this.doc_id = doc_id;
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
	
	@Column(name = "doc_name")
	public String getDoc_name() {
		return doc_name;
	}
	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}
	
	@Column(name = "doc_type")
	public Integer getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(Integer doc_type) {
		this.doc_type = doc_type;
	}
	
	@Column(name = "doc_path")
	public String getDoc_path() {
		return doc_path;
	}
	public void setDoc_path(String doc_path) {
		this.doc_path = doc_path;
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

}
