package com.hm.bigdata.entity.po.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hm.bigdata.entity.po.BaseEntity;




@Entity
@Table(name = "t_sys_operatelog")
public class Operatelog extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Long id;//日志id
	private Long user_id;//操作员id
	private String user_name;//操作员名称
	private Long subco_no;//分公司ID
	private Integer model_id;//操作类型
	private Integer op_type;//操作类型  1=查, 2=增, 3=改, 4=删
	private String remark;//操作说明
	private String stamp;//操作时间
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "user_id")
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "stamp")
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	@Column(name = "model_id")
	public Integer getModel_id() {
		return model_id;
	}
	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}
	@Column(name = "op_type")
	public Integer getOp_type() {
		return op_type;
	}
	public void setOp_type(Integer op_type) {
		this.op_type = op_type;
	}
	@Column(name = "user_name")
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
