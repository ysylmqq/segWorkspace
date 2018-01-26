package cc.chinagps.gateway.unit.beforemarket.hm.upload.beans;

import java.text.SimpleDateFormat;

import org.seg.lib.util.Util;

public class HMTravelInfo {
	//数据长度(字节)
	private int dataLength;
	
	//起点GPS信息
	private HMGPSInfo startGPS;
	
	//里程（单位：百米）
	private int distance;

	//最大速度（单位：百米/小时）
	private int maxSpeed;

	//超速时长（单位:秒）
	private int overSpeedTime;

	//急刹车次数
	private int quickBrakeCount;

	//急加速次数
	private int quickSpeedUpCount;

	//平均速度（单位：千米/小时）
	private int averageSpeed;

	//发动机最高工作转速（单位：转/分）
	private int maxRotationSpeed;
	
	//电压值（单位：V）
	private int voltage;
	
	//总油耗（单位：0.01升）
	private int totalOil;
	
	//平均油耗（单位：0.01升/百公里）
	private int averageOil;
	
	//发动机最高水温（单位：℃）
	private int maxWaterTemperature;
	
	//行程序号
	private int index;

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public HMGPSInfo getStartGPS() {
		return startGPS;
	}

	public void setStartGPS(HMGPSInfo startGPS) {
		this.startGPS = startGPS;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getOverSpeedTime() {
		return overSpeedTime;
	}

	public void setOverSpeedTime(int overSpeedTime) {
		this.overSpeedTime = overSpeedTime;
	}

	public int getQuickBrakeCount() {
		return quickBrakeCount;
	}

	public void setQuickBrakeCount(int quickBrakeCount) {
		this.quickBrakeCount = quickBrakeCount;
	}

	public int getQuickSpeedUpCount() {
		return quickSpeedUpCount;
	}

	public void setQuickSpeedUpCount(int quickSpeedUpCount) {
		this.quickSpeedUpCount = quickSpeedUpCount;
	}

	public int getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(int averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public int getMaxRotationSpeed() {
		return maxRotationSpeed;
	}

	public void setMaxRotationSpeed(int maxRotationSpeed) {
		this.maxRotationSpeed = maxRotationSpeed;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

	public int getTotalOil() {
		return totalOil;
	}

	public void setTotalOil(int totalOil) {
		this.totalOil = totalOil;
	}

	public int getAverageOil() {
		return averageOil;
	}

	public void setAverageOil(int averageOil) {
		this.averageOil = averageOil;
	}

	public int getMaxWaterTemperature() {
		return maxWaterTemperature;
	}

	public void setMaxWaterTemperature(int maxWaterTemperature) {
		this.maxWaterTemperature = maxWaterTemperature;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public static HMTravelInfo parse(byte[] data, int offset) throws Exception{
		HMTravelInfo travelInfo = new HMTravelInfo();
		//前2字节为长度
		int position = offset;
		short dataLength = Util.getShort(data, position);
		position += 2;
		
		HMGPSInfo startGPS = HMGPSInfo.parse(data, position);
		//position += startGPS.getDataLength();
		position += HMGPSInfo.DATA_LENGTH;
		
		int distance = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int maxSpeed = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int overSpeedTime = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int quickBrakeCount= Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int quickSpeedUpCount= Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int averageSpeed= Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int maxRotationSpeed= Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int voltage = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int totalOil = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int averageOil = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int maxWaterTemperature = (data[position] & 0xFF) - 40;
		position += 1;
		
		int index = data[position] & 0xFF;
		
		travelInfo.setDataLength(dataLength);
		travelInfo.setStartGPS(startGPS);
		travelInfo.setDistance(distance);
		travelInfo.setMaxSpeed(maxSpeed);
		travelInfo.setOverSpeedTime(overSpeedTime);
		travelInfo.setQuickBrakeCount(quickBrakeCount);
		travelInfo.setQuickSpeedUpCount(quickSpeedUpCount);
		travelInfo.setAverageSpeed(averageSpeed);
		travelInfo.setMaxRotationSpeed(maxRotationSpeed);
		travelInfo.setVoltage(voltage);
		travelInfo.setTotalOil(totalOil);
		travelInfo.setAverageOil(averageOil);
		travelInfo.setMaxWaterTemperature(maxWaterTemperature);
		travelInfo.setIndex(index);
		
		return travelInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("startGPS:").append(startGPS);
		sb.append(", distance:").append(distance);
		sb.append(", maxSpeed:").append(maxSpeed);
		sb.append(", overSpeedTime:").append(overSpeedTime);
		sb.append(", quickBrakeCount:").append(quickBrakeCount);
		
		sb.append(", quickSpeedUpCount:").append(quickSpeedUpCount);
		
		sb.append(", averageSpeed:").append(averageSpeed);
		sb.append(", maxRotationSpeed:").append(maxRotationSpeed);
		sb.append(", voltage:").append(voltage);
		sb.append(", totalOil:").append(totalOil);
		sb.append(", averageOil:").append(averageOil);
		sb.append(", maxWaterTemperature:").append(maxWaterTemperature);
		sb.append(", index:").append(index);
		sb.append("}");
		
		return sb.toString();
	}
	
	public byte[] encode() throws Exception{
		byte[] bs_gps = startGPS.encode();
		byte[] data = new byte[bs_gps.length + 24];
		
		byte[] bs_length = Util.getShortByte((short) data.length);
		
		byte[] bs_distance = Util.getShortByte((short) distance);
		byte[] bs_maxSpeed = Util.getShortByte((short) maxSpeed);
		byte[] bs_overSpeedTime = Util.getShortByte((short) overSpeedTime);
		byte[] bs_quickBrakeCount = Util.getShortByte((short) quickBrakeCount);
		byte[] bs_quickSpeedUpCount = Util.getShortByte((short) quickSpeedUpCount);
		byte[] bs_averageSpeed = Util.getShortByte((short) averageSpeed);
		byte[] bs_maxRotationSpeed = Util.getShortByte((short) maxRotationSpeed);
		byte[] bs_voltage = Util.getShortByte((short) voltage);
		byte[] bs_totalOil = Util.getShortByte((short) totalOil);
		byte[] bs_averageOil = Util.getShortByte((short) averageOil);
		
		int position = 0;
		System.arraycopy(bs_length, 0, data, position, bs_length.length);
		position +=  bs_length.length;
		
		System.arraycopy(bs_gps, 0, data, position, bs_gps.length);
		position +=  bs_gps.length;
		
		System.arraycopy(bs_distance, 0, data, position, bs_distance.length);
		position +=  bs_distance.length;
		
		System.arraycopy(bs_maxSpeed, 0, data, position, bs_maxSpeed.length);
		position +=  bs_maxSpeed.length;
		
		System.arraycopy(bs_overSpeedTime, 0, data, position, bs_overSpeedTime.length);
		position +=  bs_overSpeedTime.length;
		
		System.arraycopy(bs_quickBrakeCount, 0, data, position, bs_quickBrakeCount.length);
		position +=  bs_quickBrakeCount.length;
		
		System.arraycopy(bs_quickSpeedUpCount, 0, data, position, bs_quickSpeedUpCount.length);
		position +=  bs_quickSpeedUpCount.length;
		
		System.arraycopy(bs_averageSpeed, 0, data, position, bs_averageSpeed.length);
		position +=  bs_averageSpeed.length;
		
		System.arraycopy(bs_maxRotationSpeed, 0, data, position, bs_maxRotationSpeed.length);
		position +=  bs_maxRotationSpeed.length;
		
		System.arraycopy(bs_voltage, 0, data, position, bs_voltage.length);
		position +=  bs_voltage.length;
		
		System.arraycopy(bs_totalOil, 0, data, position, bs_totalOil.length);
		position +=  bs_totalOil.length;
		
		System.arraycopy(bs_averageOil, 0, data, position, bs_averageOil.length);
		position +=  bs_averageOil.length;
		
		data[position] = (byte) (maxWaterTemperature + 40);
		position += 1;
		
		data[position] = (byte) index;
		position += 1;
		
		return data;
	}
	
	public static void main(String[] args) throws Exception {
		String sdate = "2015-03-19 10:20:30";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		HMGPSInfo gps = new HMGPSInfo();
		gps.setCourse(50);
		gps.setGpsTime(sdf.parse(sdate));
		gps.setHeight(10);
		gps.setLat(23.123456);
		gps.setLng(123.123456);
		gps.setLoc(true);
		gps.setSpeed(600);
		
		HMTravelInfo t = new HMTravelInfo();
		t.setAverageOil(10);
		t.setAverageSpeed(20);
		t.setDistance(30);
		t.setIndex(1);
		t.setMaxRotationSpeed(40);
		t.setMaxSpeed(50);
		t.setMaxWaterTemperature(60);
		t.setOverSpeedTime(70);
		t.setQuickBrakeCount(80);
		t.setQuickSpeedUpCount(90);
		t.setStartGPS(gps);
		t.setTotalOil(100);
		t.setVoltage(110);
		System.out.println("tc:" + t);
		
		byte[] data = t.encode();
		HMTravelInfo pt = HMTravelInfo.parse(data, 0);
		
		System.out.println("tp:" + pt);
	}
}