package cc.chinagps.seat.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import cc.chinagps.seat.util.Consts;

public class HistoryTrack {
	@NotNull
	private String callLetter;
	@NotNull
	private String plateNumber;
	@NotNull
	@DateTimeFormat(pattern = Consts.DATE_FORMAT_PATTERN)
	private Date startTime;
	@NotNull
	@DateTimeFormat(pattern = Consts.DATE_FORMAT_PATTERN)
	private Date endTime;
	private boolean exportRefPos;
	public String getCallLetter() {
		return callLetter;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public boolean isExportRefPos() {
		return exportRefPos;
	}
	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setExportRefPos(boolean exportRefPos) {
		this.exportRefPos = exportRefPos;
	}
}
