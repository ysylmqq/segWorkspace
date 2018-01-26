package cc.chinagps.seat.bean;

import java.math.BigInteger;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 车牌车载信息
 * @author Administrator
 *
 */
public class VehicleUnitInfo {
	private BigInteger vehicleId;
	private String plateNo;
	private String callLetter;
	
	public VehicleUnitInfo(BigInteger vehicleId, String plateNo, 
			String callLetter) {
		this.vehicleId = vehicleId;
		this.plateNo = plateNo;
		this.callLetter = callLetter;
	}

	@JSON (name = "vehicle_id")
	public BigInteger getVehicleId() {
		return vehicleId;
	}
	
	@JSON (name = "plate_no")
	public String getPlateNo() {
		return plateNo;
	}
	
	@JSON (name = "call_letter")
	public String getCallLetter() {
		return callLetter;
	}
	public void setVehicleId(BigInteger vehicleId) {
		this.vehicleId = vehicleId;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}
	
	
}
