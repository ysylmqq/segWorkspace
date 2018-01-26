package cc.chinagps.seat.bean.table;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: VehicleStatusTable
 *
 */
@Entity
@Table(name="t_al_vehiclestatus")
public class VehicleStatusTable implements Serializable {

	private static final long serialVersionUID = 5325846595908458712L;

	@NotNull
	@Id
	@Column(name = "unit_id")
	private BigInteger unitId;
	@Column(name = "call_letter")
	private String callLetter;
	@Column
	private Integer flag;
	@NotNull
	@Column(name = "status_id")
	private Long statusId;
	@Temporal(TIMESTAMP)
	@Column(name = "end_time")
	private Calendar endTime = null;
	@Column(name = "is_del")
	private Integer deleted = 0;
	@Transient
	private Integer caseType;//添加案发车辆传参用 --案件类型(1:被盗 2.被劫 3.被撬 4.玻璃被砸)
	@Transient
	private Integer isCallPolice;//添加案发车辆传参用 --是否报警(1:是 0:否)
	public BigInteger getUnitId() {
		return unitId;
	}
	public String getCallLetter() {
		return callLetter;
	}
	public Integer getFlag() {
		return flag;
	}
	public Long getStatusId() {
		return statusId;
	}
	public Calendar getEndTime() {
		return endTime;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public boolean isDeleted() {
		return getDeleted() != null && getDeleted() == 1;
	}
	
	public void setUnitId(BigInteger unitId) {
		this.unitId = unitId;
	}
	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public void setDeleted(boolean deleted) {
		if (deleted) {
			this.deleted = 1;
		} else {
			this.deleted = 0;
		}
	}	
	public Integer getCaseType() {
		return caseType;
	}
	public void setCaseType(Integer caseType) {
		this.caseType = caseType;
	}
	
	public Integer getIsCallPolice() {
		return isCallPolice;
	}
	public void setIsCallPolice(Integer isCallPolice) {
		this.isCallPolice = isCallPolice;
	}
	/**
	 * 案发车辆是否失效.车辆被删除或者已过期
	 * @return
	 */
	public boolean isInValid() {
		Calendar endTime = getEndTime();
		
		return isDeleted() || 
				(endTime != null && endTime.before(Calendar.getInstance()));
	}
}
