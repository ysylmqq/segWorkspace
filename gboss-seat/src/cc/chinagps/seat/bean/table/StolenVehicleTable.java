package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.googlecode.jsonplugin.annotations.JSON;

import cc.chinagps.seat.util.Consts;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Entity implementation class for Entity: StolenVehicleTable
 *
 */
@Entity
@Table(name="t_seat_stolen_vehicle")
public class StolenVehicleTable implements Serializable {
	private static final long serialVersionUID = 5227310855536098637L;

	@Id
	@GeneratedValue
	@NotNull
	private long id;
	private BigInteger vehicleId;
	@Temporal(TIMESTAMP)
	@Column(name = "startTime")
	@DateTimeFormat(pattern = Consts.DATE_FORMAT_PATTERN)
	private Date beginTime;
	@Temporal(TIMESTAMP)
	@DateTimeFormat(pattern = Consts.DATE_FORMAT_PATTERN)
	private Date endTime;
	@Column
	private Integer caseType;
	@Column
	private Integer isCallPolice;
	private Integer isCapture;
	@Column
	private Integer source;// 0车台自动报警，1来电报警
	@Transient
	private BigInteger unitId;
	@Transient
	private String plateNo;
	public long getId() {
		return id;
	}
	public BigInteger getVehicleId() {
		return vehicleId;
	}
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getBeginTime() {
		return beginTime;
	}
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getEndTime() {
		return endTime;
	}
	public Integer getCaseType() {
		return caseType;
	}
	public Integer getIsCallPolice() {
		return isCallPolice;
	}
	public boolean isCallPolice() {
		return isCallPolice != null && isCallPolice == 1;
	}
	public Integer getIsCapture() {
		return isCapture;
	}
	public boolean isCapture() {
		return isCapture != null && isCapture == 1;
	}
	public BigInteger getUnitId() {
		return unitId;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setVehicleId(BigInteger vehicleId) {
		this.vehicleId = vehicleId;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setCaseType(Integer caseType) {
		this.caseType = caseType;
	}
	public void setIsCallPolice(Integer isCallPolice) {
		this.isCallPolice = isCallPolice;
	}
	public void setIsCapture(Integer isCapture) {
		this.isCapture = isCapture;
	}
	public void setUnitId(BigInteger unitId) {
		this.unitId = unitId;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
}
