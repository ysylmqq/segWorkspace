package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name = "t_seat_map_marker")
public class MarkTable implements Serializable {
	
	private static final long serialVersionUID = -3035744657656143389L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	@Size(max=100)
	@Column(length = 100)
	private String name;
	@Column(name = "lon")
	private Double lon;
	@Column(name = "lat")
	private Double lat;
	@Column(name = "operatorId")
	private Long opId;
	@Column(name = "operatorName", length = 50)
	private String opName;
	@Transient
	private BigInteger[] companyNo;
	public MarkTable() {
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Double getLon() {
		return lon;
	}
	public Double getLat() {
		return lat;
	}
	@JSON(serialize = false)
	public Long getOpId() {
		return opId;
	}
	@JSON(serialize = false)
	public String getOpName() {
		return opName;
	}
	@JSON(serialize = false)
	public BigInteger[] getCompanyNo() {
		return companyNo;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public void setCompanyNo(BigInteger[] companyNo) {
		this.companyNo = companyNo;
	}
}
