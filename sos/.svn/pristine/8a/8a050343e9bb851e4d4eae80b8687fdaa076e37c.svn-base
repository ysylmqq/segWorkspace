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
 * @ClassName:Repair
 * @author:lican
 * @date:2015-4-22 下午5:11:08
 */
@Entity
@Table(name = "t_mt_repair_dt")
public class RepairDt extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private Long dt_id;//跟踪明细ID
	private Long rp_id;//维修ID
	private Integer status;//工单状态, 1=已登记, 2=已受理, 3=已预约, 4=已派工, 5=维修中, 6=维修完成, 7=未维修完成
	private Date stamp;//操作时间, 跟踪时间
	private Long op_id;//操作员ID
	private String op_name;//操作员
	private String trace_result;//跟踪情况
	
	@Id
	@Column(name = "dt_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getDt_id() {
		return dt_id;
	}
	public void setDt_id(Long dt_id) {
		this.dt_id = dt_id;
	}
	@Column(name = "rp_id")
	public Long getRp_id() {
		return rp_id;
	}
	public void setRp_id(Long rp_id) {
		this.rp_id = rp_id;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "stamp")
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
	@Column(name = "op_name")
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}
	@Column(name = "trace_result")
	public String getTrace_result() {
		return trace_result;
	}
	public void setTrace_result(String trace_result) {
		this.trace_result = trace_result;
	}
	
}

