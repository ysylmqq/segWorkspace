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
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Servicefee
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-8 下午2:02:21
 */
@Entity
@Table(name = "t_fee_service")
public class Servicefee extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;// 服务费缴费ID
	private Long servicepack_id;// 服务套餐ID或服务项ID
	private Long vehicle_id;// 车辆id
	private Integer payment;// 缴费方式
	private Date start_date;// 计费开始日期
	private Integer length;// 服务时长（单位月）
	private Date end_date;// 计费截止日期
	private Integer is_check;// 0没有1选中

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "servicepack_id")
	public Long getServicepack_id() {
		return servicepack_id;
	}

	public void setServicepack_id(Long servicepack_id) {
		this.servicepack_id = servicepack_id;
	}

	@Column(name = "vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
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

	@Column(name = "length")
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
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

	@Transient
	public Integer getIs_check() {
		return is_check;
	}

	public void setIs_check(Integer is_check) {
		this.is_check = is_check;
	}

}
