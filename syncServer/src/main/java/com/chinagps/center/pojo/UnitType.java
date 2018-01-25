package com.chinagps.center.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chinagps.center.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Package:com.gboss.pojo
 * @ClassName:UnitType
 * @Description:TODO
 * @date:2015-11-17 下午5:30:13
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_ba_unittype")
public class UnitType extends BaseEntity{
	
	private Long unittype_id;//'车台类型ID',
	private String unittype;//'车台类型',
	private String typeclass;//'用途分类',
	private Integer protocol_id;//'协议ID',
	private String memo;//'说明',
	private Date stamp;//'时间戳',
	
	@Id
	@Column(name = "unittype_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getUnittype_id() {
		return unittype_id;
	}
	public void setUnittype_id(Long unittype_id) {
		this.unittype_id = unittype_id;
	}
	
	@Column(name = "unittype")
	public String getUnittype() {
		return unittype;
	}
	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}
	
	@Column(name = "typeclass")
	public String getTypeclass() {
		return typeclass;
	}
	public void setTypeclass(String typeclass) {
		this.typeclass = typeclass;
	}
	
	@Column(name = "protocol_id")
	public Integer getProtocol_id() {
		return protocol_id;
	}
	public void setProtocol_id(Integer protocol_id) {
		this.protocol_id = protocol_id;
	}
	
	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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

