package cc.chinagps.gateway.unit.kelx.upload.beans;

import java.util.Date;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.kelx.util.KlxTimeFormatUtil;

public class KlxTravelInfo {
	//开始时间
	private Date startTime;
	
	//结束时间
	private Date endTime;
	
	//里程（单位：千米）
	private int distance;
	
	//最大速度（单位：千米/小时）
	private int maxSpeed;
	
	//超速时长（单位:秒）
	private int overSpeedTime;
	
	//急刹车次数
	private int quickBrakeCount;
	
	//紧急刹车次数
	private int emergencyBrakeCount;
	
	//急加速次数
	private int quickSpeedUpCount;
	
	//紧急加速次数
	private int emergencySpeedUpCount;
	
	//平均速度（单位：千米/小时）
	private int averageSpeed;
	
	//发动机最高水温（单位：℃）
	private int maxWaterTemperature;
	
	//发动机最高工作转速（单位：转/分）
	private int maxRotationSpeed;
	
	//电压值（单位：0.1V）
	private int voltage;
	
	//总油耗（单位：0.01升）
	private int totalOil;
	
	//平均油耗（单位：0.01升/百公里）
	private int averageOil;
	
	//疲劳驾驶时长（单位:10分钟）
	private int tiredDrivingTime;
	
	//行程序号(0-19)
	private int index;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public int getEmergencyBrakeCount() {
		return emergencyBrakeCount;
	}

	public void setEmergencyBrakeCount(int emergencyBrakeCount) {
		this.emergencyBrakeCount = emergencyBrakeCount;
	}

	public int getQuickSpeedUpCount() {
		return quickSpeedUpCount;
	}

	public void setQuickSpeedUpCount(int quickSpeedUpCount) {
		this.quickSpeedUpCount = quickSpeedUpCount;
	}

	public int getEmergencySpeedUpCount() {
		return emergencySpeedUpCount;
	}

	public void setEmergencySpeedUpCount(int emergencySpeedUpCount) {
		this.emergencySpeedUpCount = emergencySpeedUpCount;
	}

	public int getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(int averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public int getMaxWaterTemperature() {
		return maxWaterTemperature;
	}

	public void setMaxWaterTemperature(int maxWaterTemperature) {
		this.maxWaterTemperature = maxWaterTemperature;
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

	public int getTiredDrivingTime() {
		return tiredDrivingTime;
	}

	public void setTiredDrivingTime(int tiredDrivingTime) {
		this.tiredDrivingTime = tiredDrivingTime;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public static KlxTravelInfo parse(byte[] data, int offset) throws Exception{
		int position = offset;
		
		String str_endTime = "20" + Util.bcd2str(data, position, 6);
		Date endTime = KlxTimeFormatUtil.parseGMT8(str_endTime);		
		position += 6;
		
		String str_startTime = "20" + Util.bcd2str(data, position, 6);
		Date startTime = KlxTimeFormatUtil.parseGMT8(str_startTime);		
		position += 6;
		
		int distance = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int maxSpeed = data[position++] & 0xFF;
		
		int overSpeedTime = Util.getShort(data, position) & 0xFFFF;
		position += 2;

		int quickBrakeCount = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int emergencyBrakeCount = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int quickSpeedUpCount = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int emergencySpeedUpCount = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int averageSpeed = data[position++] & 0xFF;
		
		int maxWaterTemperature = data[position++] & 0xFF;
		
		int maxRotationSpeed = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int voltage = data[position++] & 0xFF;
		
		int totalOil = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int averageOil = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int tiredDrivingTime = data[position++] & 0xFF;
		
		int index = data[position++] & 0xFF;
		
		KlxTravelInfo travelInfo = new KlxTravelInfo();
		travelInfo.setEndTime(endTime);
		travelInfo.setStartTime(startTime);
		travelInfo.setDistance(distance);
		travelInfo.setMaxSpeed(maxSpeed);
		travelInfo.setOverSpeedTime(overSpeedTime);
		travelInfo.setQuickBrakeCount(quickBrakeCount);
		travelInfo.setEmergencyBrakeCount(emergencyBrakeCount);
		travelInfo.setQuickSpeedUpCount(quickSpeedUpCount);
		travelInfo.setEmergencySpeedUpCount(emergencySpeedUpCount);
		travelInfo.setAverageSpeed(averageSpeed);
		travelInfo.setMaxWaterTemperature(maxWaterTemperature);
		travelInfo.setMaxRotationSpeed(maxRotationSpeed);
		travelInfo.setVoltage(voltage);
		travelInfo.setTotalOil(totalOil);
		travelInfo.setAverageOil(averageOil);
		travelInfo.setTiredDrivingTime(tiredDrivingTime);
		travelInfo.setIndex(index);
		
		return travelInfo;
	}
}