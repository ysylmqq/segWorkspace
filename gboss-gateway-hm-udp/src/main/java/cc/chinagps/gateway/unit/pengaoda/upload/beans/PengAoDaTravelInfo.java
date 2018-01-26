package cc.chinagps.gateway.unit.pengaoda.upload.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PengAoDaTravelInfo {
	//开始时间
	private Date startTime;
	
	//结束时间
	private Date endTime;

	//里程（单位:米）
	private int distance;
	
	//总油耗（单位:0.1升）
	private int totalOil;
	
	//最大速度（单位:千米/小时）
	private int maxSpeed;
	
	//平均速度（单位:千米/小时）
	private int averageSpeed;
	
	//怠速时长（单位:秒）
	private int idleTime;
	
	//行驶时间
	private int runTime;
	
	//急加速次数
	private int quickSpeedUpCount;
		
	//急刹车次数
	private int quickBrakeCount;
	
	//急转弯次数
	private int quickTurnCount;

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

	public int getTotalOil() {
		return totalOil;
	}

	public void setTotalOil(int totalOil) {
		this.totalOil = totalOil;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(int averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}
	
	public int getRunTime() {
		return runTime;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}

	public int getQuickSpeedUpCount() {
		return quickSpeedUpCount;
	}

	public void setQuickSpeedUpCount(int quickSpeedUpCount) {
		this.quickSpeedUpCount = quickSpeedUpCount;
	}

	public int getQuickBrakeCount() {
		return quickBrakeCount;
	}

	public void setQuickBrakeCount(int quickBrakeCount) {
		this.quickBrakeCount = quickBrakeCount;
	}

	public int getQuickTurnCount() {
		return quickTurnCount;
	}

	public void setQuickTurnCount(int quickTurnCount) {
		this.quickTurnCount = quickTurnCount;
	}
	
	public static PengAoDaTravelInfo parse(Date endGpsTime, String[] params, int startIndex) {
		int position = startIndex;
		String sdistance = params[position ++];
		String stotalOil = params[position ++];
		String smaxSpeed = params[position ++];
		String saverageSpeed = params[position ++];
		String sidleTime = params[position ++];
		String srunTime = params[position ++];
		String squickSpeedUpCount = params[position ++];
		String squickBrakeCount = params[position ++];
		String squickTurnCount = params[position ++];

		PengAoDaTravelInfo travelInfo = new PengAoDaTravelInfo();
		int idleTime = Integer.valueOf(sidleTime);
		int runTime = Integer.valueOf(srunTime);
		long end = endGpsTime.getTime();
		long start = end - idleTime * 1000L - runTime * 1000L;
		
		Date endTime = new Date(end);
		Date startTime = new Date(start);
		
		travelInfo.setStartTime(startTime);
		travelInfo.setEndTime(endTime);
		travelInfo.setDistance(Integer.valueOf(sdistance));
		travelInfo.setTotalOil(Integer.valueOf(stotalOil));
		travelInfo.setMaxSpeed(Integer.valueOf(smaxSpeed));
		travelInfo.setAverageSpeed(Integer.valueOf(saverageSpeed));
		travelInfo.setIdleTime(Integer.valueOf(sidleTime));
		travelInfo.setRunTime(runTime);
		travelInfo.setQuickSpeedUpCount(Integer.valueOf(squickSpeedUpCount));	
		travelInfo.setQuickBrakeCount(Integer.valueOf(squickBrakeCount));
		travelInfo.setQuickTurnCount(Integer.valueOf(squickTurnCount));
		
		return travelInfo;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append("{startTime:").append(sdf.format(startTime));
		sb.append(", endTime:").append(sdf.format(endTime));
		sb.append(", averageSpeed:").append(averageSpeed);
		sb.append(", distance:").append(distance);
		sb.append(", idleTime:").append(idleTime);
		sb.append(", maxSpeed:").append(maxSpeed);
		sb.append(", quickBrakeCount:").append(quickBrakeCount);
		sb.append(", quickSpeedUpCount:").append(quickSpeedUpCount);
		sb.append(", quickTurnCount:").append(quickTurnCount);
		sb.append(", runTime:").append(runTime);
		sb.append(", totalOil:").append(totalOil);
		sb.append("}");
		
		return sb.toString();
	}
}