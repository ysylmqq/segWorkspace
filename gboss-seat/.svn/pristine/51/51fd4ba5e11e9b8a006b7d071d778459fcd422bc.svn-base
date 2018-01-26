package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.annotations.JSON;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Entity implementation class for Entity: LastPositionTable
 *
 */
@Entity
@Table(name="t_ba_lastposition")
public class LastPositionTable implements Serializable {

	private static final long serialVersionUID = -6058654169101760566L;
	@Id
	@Column(name = "call_letter")
	private String callLetter;
	@Column(name = "gps_time")
	@Temporal(TIMESTAMP)
	private Date gpsTime;
	@Column(name = "is_loc")
	private Integer isLoc;
	private Double lon;
	private Double lat;
	private String status;
	@Temporal(TIMESTAMP)
	private Date stamp;
	public String getCallLetter() {
		return callLetter;
	}
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getGpsTime() {
		return gpsTime;
	}
	public Integer getIsLoc() {
		return isLoc;
	}
	public Double getLon() {
		return lon;
	}
	public Double getLat() {
		return lat;
	}
	public String getStatus() {
		return status;
	}
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getStamp() {
		return stamp;
	}
	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}
	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
	}
	public void setIsLoc(Integer isLoc) {
		this.isLoc = isLoc;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
}
