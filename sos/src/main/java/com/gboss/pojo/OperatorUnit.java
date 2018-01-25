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
 * @ClassName:OperatorUnit
 * @Description:TODO
 * @author: XIAOKE
 * @date:2015-1-22 上午11:53:23
 */

@Entity
@Table(name = "t_ba_op_unit")
public class OperatorUnit extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long uo_id;
	private Long op_id;//'登录ID',ldap中commonOperator对象opid
	private Long vehicle_id;//车辆id
	private Long unit_id;//车台id
	private Date stamp;//'时间戳',
	
	@Id
	@Column(name = "uo_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getUo_id() {
		return uo_id;
	}
	public void setUo_id(Long uo_id) {
		this.uo_id = uo_id;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
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

