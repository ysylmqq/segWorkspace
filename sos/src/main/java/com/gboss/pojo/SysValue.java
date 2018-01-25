package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.chinagps.fee.entity.po.sys
 * @ClassName:SysValuePOJO
 * @Description:系统参数值 实体类
 * @author:zfy
 * @date:2014-6-11 上午9:29:25
 */
@Entity
@Table(name = "t_sys_value")
public class SysValue extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Long valueId;//系统定义类型数据ID
	private Integer stype;//类型, 参见t_sys_type
	private String svalue;//类型值
	private String sname;//名称(说明)
	private Integer isDel;//是否删除, 0=否, 1=是
	private Integer sno;//排序号
	private Date stamp;//时间戳
	
	@Id
	@Column(name = "value_id")
	public Long getValueId() {
		return valueId;
	}
	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}
	@Column(name = "stype")
	public Integer getStype() {
		return stype;
	}
	public void setStype(Integer stype) {
		this.stype = stype;
	}
	@Column(name = "svalue")
	public String getSvalue() {
		return svalue;
	}
	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}
	@Column(name = "sname")
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	@Column(name = "is_del")
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	@Column(name = "sno")
	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}

