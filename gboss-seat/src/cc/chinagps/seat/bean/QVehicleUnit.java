package cc.chinagps.seat.bean;

import java.math.BigInteger;

public class QVehicleUnit {
	private String call_letter;
	
	private String plate_no;
	
	private BigInteger unit_id;
	
	private BigInteger vehicle_id;

	public String getCall_letter() {
		return call_letter;
	}

	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}

	public String getPlate_no() {
		return plate_no;
	}

	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}

	public BigInteger getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(BigInteger unit_id) {
		this.unit_id = unit_id;
	}

	public BigInteger getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(BigInteger vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
}