package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:MaintainRule
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-26 下午2:11:18
 */
@Entity
@Table(name = "t_app_maintain_rule")
public class MaintainRule extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long model;
	private Integer first_service_time;
	private Integer first_service_mileage;
	private Integer second_service_time;
	private Integer second_service_mileage;
	private Integer interval_time;
	private Integer interval_mileage;
	private String remark;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "model")
	public Long getModel() {
		return model;
	}

	public void setModel(Long model) {
		this.model = model;
	}

	@Column(name = "first_service_time")
	public Integer getFirst_service_time() {
		return first_service_time;
	}

	public void setFirst_service_time(Integer first_service_time) {
		this.first_service_time = first_service_time;
	}

	
	@Column(name = "first_service_mileage")
	public Integer getFirst_service_mileage() {
		return first_service_mileage;
	}

	public void setFirst_service_mileage(Integer first_service_mileage) {
		this.first_service_mileage = first_service_mileage;
	}

	
	@Column(name = "second_service_time")
	public Integer getSecond_service_time() {
		return second_service_time;
	}

	public void setSecond_service_time(Integer second_service_time) {
		this.second_service_time = second_service_time;
	}

	
	@Column(name = "second_service_mileage")
	public Integer getSecond_service_mileage() {
		return second_service_mileage;
	}

	public void setSecond_service_mileage(Integer second_service_mileage) {
		this.second_service_mileage = second_service_mileage;
	}

	
	@Column(name = "interval_time")
	public Integer getInterval_time() {
		return interval_time;
	}

	public void setInterval_time(Integer interval_time) {
		this.interval_time = interval_time;
	}

	
	@Column(name = "interval_mileage")
	public Integer getInterval_mileage() {
		return interval_mileage;
	}

	public void setInterval_mileage(Integer interval_mileage) {
		this.interval_mileage = interval_mileage;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	

}
