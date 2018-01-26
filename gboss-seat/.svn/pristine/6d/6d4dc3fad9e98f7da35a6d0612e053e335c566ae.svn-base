package cc.chinagps.seat.bean;

import cc.chinagps.seat.bean.table.CustomerTable;
import cc.chinagps.seat.bean.table.LastPositionTable;
import cc.chinagps.seat.bean.table.UnitTable;
import cc.chinagps.seat.bean.table.VehicleTable;

public class ReportUnreportStat extends ReportCommon {
	private CustomerTable customer;
	private LastPositionTable lastPosition;
	private UnitTable unit;
	private VehicleTable vehicle;
	/**
	 * 客户电话
	 */
	private String phone;
	public CustomerTable getCustomer() {
		return customer;
	}
	public VehicleTable getVehicle() {
		return vehicle;
	}
	public UnitTable getUnit() {
		return unit;
	}
	public String getPhone() {
		return phone;
	}
	public LastPositionTable getLastPosition() {
		return lastPosition;
	}
	public void setUnit(UnitTable unit) {
		this.unit = unit;
		this.customer = unit.getCustomer();
		this.vehicle = unit.getVehicle();
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setLastPosition(LastPositionTable lastPosition) {
		this.lastPosition = lastPosition;
	}
}
