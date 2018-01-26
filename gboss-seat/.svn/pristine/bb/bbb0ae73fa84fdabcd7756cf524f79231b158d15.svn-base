package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.googlecode.jsonplugin.annotations.JSON;


/**
 * The persistent class for the t_ba_unittype database table.
 * 
 */
@Entity
@Table(name="t_ba_unittype")
public class UnitTypeTable implements Serializable {

	private static final long serialVersionUID = 9062155839139137675L;

	@Id
	@Column(name="unittype_id")
	private BigDecimal typeId;

	@Column(name="unittype")
	private String type;
	
	@Column(name="typeclass")
	private String typeclass;
	
	@JSON(serialize=false)
	public String getTypeclass() {
		return typeclass;
	}

	public void setTypeclass(String typeclass) {
		this.typeclass = typeclass;
	}

	public BigDecimal getTypeId() {
		return this.typeId;
	}

	public void setTypeId(BigDecimal typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}