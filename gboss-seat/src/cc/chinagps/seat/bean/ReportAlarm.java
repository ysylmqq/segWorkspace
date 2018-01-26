package cc.chinagps.seat.bean;

import cc.chinagps.seat.bean.table.AlarmTable;

public class ReportAlarm extends ReportCommon {

	private AlarmTable alarm;
	private String companyName;
	private String customer;
	private Integer mode;
	private String plateNo;
	private String vehicleType;
	private String status;
	public AlarmTable getAlarm() {
		return alarm;
	}

	public void setAlarm(AlarmTable alarm) {
		this.alarm = alarm;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
