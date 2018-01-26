package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_sys_value")
public class SysValueTable implements Serializable {
	private static final long serialVersionUID = -4057372613989852716L;
	
	@Id
	@Column(name = "value_id")
	private BigInteger valueId;
	@Column(name = "stype")
	private Integer type;
	@Column(name = "sname")
	private String name;
	@Column(name = "svalue")
	private String value;
	public BigInteger getValueId() {
		return valueId;
	}
	public Integer getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public void setValueId(BigInteger valueId) {
		this.valueId = valueId;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
