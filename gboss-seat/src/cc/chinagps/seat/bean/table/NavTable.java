package cc.chinagps.seat.bean.table;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * Entity implementation class for Entity: NavTable
 *
 */
@Entity
@Table(name="t_seat_navigation")
public class NavTable implements Serializable {

	private static final long serialVersionUID = 9144358682133253589L;
	@Id
	@GeneratedValue
	private long id;
	private Long vehicleId;
	
	@NotNull
	@Size(max = 50)
	@Column(length = 50)
	private String mapType;
	private Long policyType;
	private Double fromLon;
	private Double fromLat;
	
	@NotNull
	@Size(max = 200)
	@Column(length = 200)
	private String fromLabel;
	
	@NotNull
	@Size(max = 200)
	@Column(length = 200)
	private String fromTitle;
	private Double toLon;
	private Double toLat;
	
	@NotNull
	@Size(max = 200)
	@Column(length = 200)
	private String toLabel;
	
	@NotNull
	@Size(max = 200)
	@Column(length = 200)
	private String toTitle;
	@Column(name = "operatorId")
	private Long opId;
	@Column(name = "operatorName")
	private String opName;
	@Temporal(TIMESTAMP)
	private Calendar stamp;
	public NavTable() {
	}
	@JSON(serialize = false)
	public long getId() {
		return id;
	}
	@JSON(serialize = false)
	public Long getVehicleId() {
		return vehicleId;
	}
	public String getMapType() {
		return mapType;
	}
	public Long getPolicyType() {
		return policyType;
	}
	public Double getFromLon() {
		return fromLon;
	}
	public Double getFromLat() {
		return fromLat;
	}
	public String getFromLabel() {
		return fromLabel;
	}
	public String getFromTitle() {
		return fromTitle;
	}
	public Double getToLon() {
		return toLon;
	}
	public Double getToLat() {
		return toLat;
	}
	public String getToLabel() {
		return toLabel;
	}
	public String getToTitle() {
		return toTitle;
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
	public Calendar getStamp() {
		return stamp;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}
	public void setPolicyType(Long policyType) {
		this.policyType = policyType;
	}
	public void setFromLon(Double fromLon) {
		this.fromLon = fromLon;
	}
	public void setFromLat(Double fromLat) {
		this.fromLat = fromLat;
	}
	public void setFromLabel(String fromLabel) {
		this.fromLabel = fromLabel;
	}
	public void setFromTitle(String fromTitle) {
		this.fromTitle = fromTitle;
	}
	public void setToLon(Double toLon) {
		this.toLon = toLon;
	}
	public void setToLat(Double toLat) {
		this.toLat = toLat;
	}
	public void setToLabel(String toLabel) {
		this.toLabel = toLabel;
	}
	public void setToTitle(String toTitle) {
		this.toTitle = toTitle;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
}
