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
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

@Entity
@Table(name = "t_ba_taskflow")
public class TaskFlow  extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tf_id;
	private Long task_id;//任务单id
	private String charger_id;//调度人员id，用,隔开
	private String charger;//负责人 用,隔开
	private Long op_id;//转单人
	private String operator;//转单人
	private Date stamp;//转单时间
	private String org_id;
	private String org_name;//转单受理机构
	
	
	
	
	@Id
	@Column(name = "tf_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getTf_id() {
		return tf_id;
	}
	public void setTf_id(Long tf_id) {
		this.tf_id = tf_id;
	}

	
	@Column(name = "charger")
	public String getCharger() {
		return charger;
	}
	
	public void setCharger(String charger) {
		this.charger = charger;
	}
	
	
	@Column(name = "charger_id")
	public String getCharger_id() {
		return charger_id;
	}
	public void setCharger_id(String charger_id) {
		this.charger_id = charger_id;
	}
	
	@Column(name = "task_id")
	public Long getTask_id() {
		return task_id;
	}
	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}
	
	@Column(name = "org_id")
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	
	@Column(name = "org_name")
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	
	@Column(name = "operator")
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(name = "stamp", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	
}
