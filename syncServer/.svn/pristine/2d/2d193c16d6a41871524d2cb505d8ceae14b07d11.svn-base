package com.chinagps.center.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_syn_his")
public class SynHistory extends BaseEntity {
	
	private Long his_id;
	private Long subco_no;
	private Date syn_time;
	private Date start_time;
	private Date end_time;
	private Integer type;
	private Long unit_id;
	
	@Id
	@Column(name = "his_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getHis_id() {
		return his_id;
	}
	public void setHis_id(Long his_id) {
		this.his_id = his_id;
	}

	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}

	@Column(name = "syn_time")
	public Date getSyn_time() {
		return syn_time;
	}
	public void setSyn_time(Date syn_time) {
		this.syn_time = syn_time;
	}

	@Column(name = "start_time")
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	@Column(name = "end_time")
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}
}
