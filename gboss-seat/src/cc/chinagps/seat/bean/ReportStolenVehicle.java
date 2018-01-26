package cc.chinagps.seat.bean;

import cc.chinagps.seat.bean.table.CustomerTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;
import cc.chinagps.seat.bean.table.UnitTable;
import cc.chinagps.seat.bean.table.VehicleTable;

public class ReportStolenVehicle extends ReportCommon {
	private CustomerTable customer;
	private StolenVehicleTable stolenVehicle;
	private UnitTable unit;
	private VehicleTable vehicle;
	public CustomerTable getCustomer() {
		return customer;
	}
	public VehicleTable getVehicle() {
		return vehicle;
	}
	public StolenVehicleTable getStolenVehicle() {
		return stolenVehicle;
	}
	public UnitTable getUnit() {
		return unit;
	}
	public void setStolenVehicle(StolenVehicleTable stolenVehicle) {
		this.stolenVehicle = stolenVehicle;
	}
	public void setUnit(UnitTable unit) {
		this.unit = unit;
		this.customer = unit.getCustomer();
		this.vehicle = unit.getVehicle();
	}
}
