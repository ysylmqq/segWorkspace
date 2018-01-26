package cc.chinagps.gateway.unit.beforemarket.hm.upload.beans;

import org.seg.lib.util.Util;

public class HMOBDInfo {
	//数据长度(字节)
	private int dataLength;
		
	private int hourOil;
	
	private int averageOil;
	
	private int totalDistance;
	
	private int remainOil;
	
	private int waterTemperature;
	
	private byte reviseOil;
	
	private int rotationSpeed;
	
	private int intakeAirTemperature;
	
	private int speed;
	
	private int remainDistance;
	
	private Integer signal;

	public Integer getSignal() {
		return signal;
	}

	public void setSignal(Integer signal) {
		this.signal = signal;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public int getRemainDistance() {
		return remainDistance;
	}

	public void setRemainDistance(int remainDistance) {
		this.remainDistance = remainDistance;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHourOil() {
		return hourOil;
	}

	public void setHourOil(int hourOil) {
		this.hourOil = hourOil;
	}

	public int getAverageOil() {
		return averageOil;
	}

	public void setAverageOil(int averageOil) {
		this.averageOil = averageOil;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

	public int getRemainOil() {
		return remainOil;
	}

	public void setRemainOil(int remainOil) {
		this.remainOil = remainOil;
	}

	public int getWaterTemperature() {
		return waterTemperature;
	}

	public void setWaterTemperature(int waterTemperature) {
		this.waterTemperature = waterTemperature;
	}

	public byte getReviseOil() {
		return reviseOil;
	}

	public void setReviseOil(byte reviseOil) {
		this.reviseOil = reviseOil;
	}

	public int getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(int rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public int getIntakeAirTemperature() {
		return intakeAirTemperature;
	}

	public void setIntakeAirTemperature(int intakeAirTemperature) {
		this.intakeAirTemperature = intakeAirTemperature;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("hourOil:").append(hourOil);
		sb.append(", averageOil:").append(averageOil);
		sb.append(", remainOil:").append(remainOil);
		sb.append(", intakeAirTemperature:").append(intakeAirTemperature);
		sb.append(", totalDistance:").append(totalDistance);
		sb.append(", waterTemperature:").append(waterTemperature);
		sb.append(", reviseOil:").append(reviseOil);
		sb.append(", rotationSpeed:").append(rotationSpeed);
		sb.append(", speed:").append(speed);
		sb.append(", remainDistance:").append(remainDistance);
		sb.append(", signal:").append(signal);
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 解码
	 */
	public static HMOBDInfo parse(byte[] data, int offset){
		//前2字节为长度		
		int position = offset;
		short dataLength = Util.getShort(data, position);
		position += 2;
		
		int hourOil = data[position] & 0xFF;
		position ++;
		
		int averageOil = data[position] & 0xFF;
		position ++;
		
		int remainOil = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		byte b_intakeAirTemperature = data[position];
		int intakeAirTemperature = (b_intakeAirTemperature & 0xFF) - 40;
		int totalDistance = Util.getInt(data, position) & 0xFFFFFF;
		position += 4;
		
		int waterTemperature = (data[position] & 0xFF) - 40;
		position ++;
		
		byte b_reviseOil = data[position];
		position ++;
		
		int rotationSpeed = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int speed = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int remainDistance = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		HMOBDInfo obdInfo = new HMOBDInfo();
		obdInfo.setDataLength(dataLength);
		obdInfo.setHourOil(hourOil);
		obdInfo.setAverageOil(averageOil);
		obdInfo.setRemainOil(remainOil);
		obdInfo.setIntakeAirTemperature(intakeAirTemperature);
		obdInfo.setTotalDistance(totalDistance);
		obdInfo.setWaterTemperature(waterTemperature);
		obdInfo.setReviseOil(b_reviseOil);
		obdInfo.setRotationSpeed(rotationSpeed);
		obdInfo.setSpeed(speed);
		obdInfo.setRemainDistance(remainDistance);
		
		if(dataLength >= 19){
			int signal = data[position];
			position ++;
			obdInfo.setSignal(signal);
		}
		
		return obdInfo;
	}
	
	/**
	 * 编码
	 */
	public byte[] encode(){
	    byte[] data = new byte[18 + (signal != null? 1: 0)];
	    byte[] bs_len = Util.getShortByte((short) data.length);
	    byte bs_hour_oil = (byte) hourOil;
	    byte bs_averageOil = (byte) averageOil;
	    byte[] bs_remainOil = Util.getShortByte((short) remainOil);
	    byte bs_intakeAirTemperature = (byte) (intakeAirTemperature + 40);
	    byte[] bs_totalDistance = Util.getIntByte(totalDistance);
	    byte bs_waterTemperature = (byte) (waterTemperature + 40); 
	    //byte b_reviseOil
	    byte[] bs_rotationSpeed = Util.getShortByte((short) rotationSpeed);
	    byte[] bs_speed = Util.getShortByte((short) speed);
	    byte[] bs_remainDistance = Util.getShortByte((short) remainDistance);
	    
	    int position = 0;
	    System.arraycopy(bs_len, 0, data, position, bs_len.length);
	    position += bs_len.length;
	    
	    data[position] = bs_hour_oil;
	    position += 1;
	    
	    data[position] = bs_averageOil;
	    position += 1;
	    
	    System.arraycopy(bs_remainOil, 0, data, position, bs_remainOil.length);
	    position += bs_remainOil.length;
	    
	    System.arraycopy(bs_totalDistance, 0, data, position, bs_totalDistance.length);
	    data[position] = bs_intakeAirTemperature;
	    position += bs_totalDistance.length;
	    
	    data[position] = bs_waterTemperature;
	    position += 1;
	    
	    data[position] = reviseOil;
	    position += 1;
	    
	    System.arraycopy(bs_rotationSpeed, 0, data, position, bs_rotationSpeed.length);
	    position += bs_rotationSpeed.length;
	    
	    System.arraycopy(bs_speed, 0, data, position, bs_speed.length);
	    position += bs_speed.length;
	    
	    System.arraycopy(bs_remainDistance, 0, data, position, bs_remainDistance.length);
	    position += bs_remainDistance.length;
	    
	    if(signal != null){
	    	int isignal = signal;
	    	data[position] = (byte) isignal;
		    position += 1;
	    }
	    
	    return data;
	}
	
	public static void main(String[] args) {
		HMOBDInfo obd = new HMOBDInfo();
		obd.setHourOil(10);
		obd.setAverageOil(20);
		obd.setRemainOil(30);			
		obd.setIntakeAirTemperature(40);
		obd.setTotalDistance(50);
		obd.setWaterTemperature(60);
		obd.setReviseOil((byte) 70);
		obd.setRotationSpeed(80);
		obd.setSpeed(90);
		obd.setRemainDistance(100);
		obd.setSignal(30);
		
		System.out.println("obd0:" + obd);
		byte[] data = obd.encode();
		//System.out.println("data:" + HexUtil.byteToHexStr(data));
		HMOBDInfo.parse(data, 0);
		System.out.println("obd1:" + obd);
	}
}