package cc.chinagps.gateway.unit.seg.upload.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 行程信息(OBD)
 */
public class SegTravelInfo {
	//开始时间
	private Date startTime;
	
	//结束时间
	private Date endTime;
	
	//里程（单位：0.1千米）
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
	
	//疲劳驾驶时长（单位:分钟）
	private int tiredDrivingTime;
	
	//平均转速（单位：转/分）
	private int averageRotationSpeed;
	
	//最高瞬时油耗（单位：0.01升/百公里）
	private int maxOil;
	
	//怠速时长（单位:分钟）
	private int idleTime;

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

	public int getAverageRotationSpeed() {
		return averageRotationSpeed;
	}

	public void setAverageRotationSpeed(int averageRotationSpeed) {
		this.averageRotationSpeed = averageRotationSpeed;
	}

	public int getMaxOil() {
		return maxOil;
	}

	public void setMaxOil(int maxOil) {
		this.maxOil = maxOil;
	}

	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append("{startTime:").append(sdf.format(startTime));
		sb.append(", endTime:").append(sdf.format(endTime));
		sb.append(", distance:").append(distance);
		sb.append(", maxSpeed:").append(maxSpeed);
		sb.append(", overSpeedTime:").append(overSpeedTime);
		sb.append(", quickBrakeCount:").append(quickBrakeCount);
		sb.append(", emergencyBrakeCount:").append(emergencyBrakeCount);
		sb.append(", quickSpeedUpCount:").append(quickSpeedUpCount);
		sb.append(", emergencySpeedUpCount:").append(emergencySpeedUpCount);
		sb.append(", averageSpeed:").append(averageSpeed);
		sb.append(", maxWaterTemperature:").append(maxWaterTemperature);
		sb.append(", maxRotationSpeed:").append(maxRotationSpeed);
		sb.append(", voltage:").append(voltage);
		sb.append(", totalOil:").append(totalOil);
		sb.append(", averageOil:").append(averageOil);
		sb.append(", tiredDrivingTime:").append(tiredDrivingTime);
		sb.append(", averageRotationSpeed:").append(averageRotationSpeed);
		sb.append(", maxOil:").append(maxOil);
		sb.append(", idleTime:").append(idleTime);
		sb.append("}");
		
		return sb.toString();
	}
}