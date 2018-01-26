package cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans;

import org.seg.lib.util.Util;

public class YdwInfo {
	private int temprature;
	
	private int drivingTime;
	
	private int totalDistance;
	
	private int unitPower;
	
	private int bluetoothPower;

	public int getTemprature() {
		return temprature;
	}

	public void setTemprature(int temprature) {
		this.temprature = temprature;
	}

	public int getDrivingTime() {
		return drivingTime;
	}

	public void setDrivingTime(int drivingTime) {
		this.drivingTime = drivingTime;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

	public int getUnitPower() {
		return unitPower;
	}

	public void setUnitPower(int unitPower) {
		this.unitPower = unitPower;
	}

	public int getBluetoothPower() {
		return bluetoothPower;
	}

	public void setBluetoothPower(int bluetoothPower) {
		this.bluetoothPower = bluetoothPower;
	}
	
	public static YdwInfo parse(byte[] data, int offset){
		YdwInfo info = new YdwInfo();
		int temprature = data[offset];
		int drivingTime = Util.getInt(data, offset + 1);
		int totalDistance = Util.getInt(data, offset + 5);
		int unitPower = data[offset + 9];
		int bluetoothPower = data[offset + 10];
		
		info.setTemprature(temprature);
		info.setDrivingTime(drivingTime);
		info.setTotalDistance(totalDistance);
		info.setUnitPower(unitPower);
		info.setBluetoothPower(bluetoothPower);

		return info;
	}
	
	public byte[] encode(){
		byte[] data = new byte[11];
		data[0] = (byte) temprature;
		byte[] bs_drivingTime = Util.getIntByte(drivingTime);
		byte[] bs_totalDistance = Util.getIntByte(totalDistance);
		
		System.arraycopy(bs_drivingTime, 0, data, 1, bs_drivingTime.length);
		System.arraycopy(bs_totalDistance, 0, data, 5, bs_totalDistance.length);
		data[9] = (byte) unitPower;
		data[10] = (byte) bluetoothPower;
		
		return data;
	}

	@Override
	public String toString() {
		return "YdwInfo [temprature=" + temprature + ", drivingTime=" + drivingTime + ", totalDistance=" + totalDistance
				+ ", unitPower=" + unitPower + ", bluetoothPower=" + bluetoothPower + "]";
	}
	
}