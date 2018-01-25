package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:DispatchElectrician
 * @Description:派工单与电工中间表
 * @author:bzhang
 * @date:2014-3-27 下午5:44:11
 * 已删除
 */
@Entity
@Table(name = "t_ba_dispatch_electrician")
public class DispatchElectrician extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long dispatch_id;//派工单id
	private Long electrician;//电工id
	private String phone;//电工电话
	private String electrician_name;//电工姓名
	private Long task_id;//任务单id
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "dispatch_id")
	public Long getDispatch_id() {
		return dispatch_id;
	}
	public void setDispatch_id(Long dispatch_id) {
		this.dispatch_id = dispatch_id;
	}
	
	@Column(name = "electrician")
	public Long getElectrician() {
		return electrician;
	}
	public void setElectrician(Long electrician) {
		this.electrician = electrician;
	}
	
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "electrician_name")
	public String getElectrician_name() {
		return electrician_name;
	}
	public void setElectrician_name(String electrician_name) {
		this.electrician_name = electrician_name;
	}
	
	@Column(name = "task_id")
	public Long getTask_id() {
		return task_id;
	}
	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}
	

}

