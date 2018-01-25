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
 * @ClassName:Unitservice
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-15 下午2:47:32
 */
@Entity
@Table(name = "t_fee_unitservice")
public class Unitservice extends BaseEntity{

	private static final long serialVersionUID = 1L;
	private Long id;// SIM月租费id
	private Long vehicle_id;// 车辆id
	private Float simfee;// SIM卡月租费
	private Integer is_contain;// 是否包含在服务套餐中（0否1是）
	private Date start_date;//计费开始日期
	private Integer payment;// 缴费方式（0年1月）
	private Date end_date;//计费截止日期

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	@Column(name = "simfee")
	public Float getSimfee() {
		return simfee;
	}

	public void setSimfee(Float simfee) {
		this.simfee = simfee;
	}

	@Column(name = "is_contain")
	public Integer getIs_contain() {
		return is_contain;
	}

	public void setIs_contain(Integer is_contain) {
		this.is_contain = is_contain;
	}

	@Column(name = "payment")
	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	@Column(name = "start_date",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	@Column(name = "end_date",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

}
