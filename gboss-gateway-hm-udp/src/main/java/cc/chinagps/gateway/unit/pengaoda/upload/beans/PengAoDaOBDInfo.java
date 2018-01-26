package cc.chinagps.gateway.unit.pengaoda.upload.beans;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.util.HexUtil;

public class PengAoDaOBDInfo {
	private int bodyLength;
	
	private byte flag;
	
	private Integer faultCount;
	
	private Double engineLoad;
	
	private Integer warterTemperature;
	
	private Double rotationSpeed;
	
	private Integer speed;
	
	private Integer intakeAirTemperature;
	
	private Integer runTime;
	
	private Double voltage;
	
	//空燃比系数
	private Double airFuelRatio;
	
	private Integer outsideTemperature;
	
	//节气门开度
	private Double throttle;
	
	private Long oilPerHour;
	
	private Double oilPerHundredKm;
	
	private Long distance;
	
	private Double remainOil;
	
	private Double remainPercentOil;

	public int getBodyLength() {
		return bodyLength;
	}

	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}

	public byte getFlag() {
		return flag;
	}

	public void setFlag(byte flag) {
		this.flag = flag;
	}
	
	public Integer getFaultCount() {
		return faultCount;
	}

	public void setFaultCount(Integer faultCount) {
		this.faultCount = faultCount;
	}

	public Double getEngineLoad() {
		return engineLoad;
	}

	public void setEngineLoad(Double engineLoad) {
		this.engineLoad = engineLoad;
	}

	public Integer getWarterTemperature() {
		return warterTemperature;
	}

	public void setWarterTemperature(Integer warterTemperature) {
		this.warterTemperature = warterTemperature;
	}

	public Double getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(Double rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getIntakeAirTemperature() {
		return intakeAirTemperature;
	}

	public void setIntakeAirTemperature(Integer intakeAirTemperature) {
		this.intakeAirTemperature = intakeAirTemperature;
	}

	public Integer getRunTime() {
		return runTime;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	public Double getVoltage() {
		return voltage;
	}

	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}

	public Double getAirFuelRatio() {
		return airFuelRatio;
	}

	public void setAirFuelRatio(Double airFuelRatio) {
		this.airFuelRatio = airFuelRatio;
	}

	public Integer getOutsideTemperature() {
		return outsideTemperature;
	}

	public void setOutsideTemperature(Integer outsideTemperature) {
		this.outsideTemperature = outsideTemperature;
	}

	public Double getThrottle() {
		return throttle;
	}

	public void setThrottle(Double throttle) {
		this.throttle = throttle;
	}

	public Long getOilPerHour() {
		return oilPerHour;
	}

	public void setOilPerHour(Long oilPerHour) {
		this.oilPerHour = oilPerHour;
	}

	public Double getOilPerHundredKm() {
		return oilPerHundredKm;
	}

	public void setOilPerHundredKm(Double oilPerHundredKm) {
		this.oilPerHundredKm = oilPerHundredKm;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Double getRemainOil() {
		return remainOil;
	}

	public void setRemainOil(Double remainOil) {
		this.remainOil = remainOil;
	}

	public Double getRemainPercentOil() {
		return remainPercentOil;
	}

	public void setRemainPercentOil(Double remainPercentOil) {
		this.remainPercentOil = remainPercentOil;
	}

	public static PengAoDaOBDInfo parse(byte[] data, int offset){
		PengAoDaOBDInfo obd = new PengAoDaOBDInfo();
		int position = offset;
		
		int bodyLength = Util.getShort(data, position) & 0xFFFF;
		if(bodyLength == 0){
			return obd;
		}
		
		position += 2;
		obd.setBodyLength(bodyLength);
		
		byte flag = data[position];
		obd.setFlag(flag);
		
		int sumBodyLen = 1;
		while(sumBodyLen < bodyLength){
			int pid = data[position + sumBodyLen] & 0xFF;
			handlePID(obd, pid, data, position + sumBodyLen + 1);
			sumBodyLen += 5;
		}
		
		return obd;
	}
	
	private static void handlePID(PengAoDaOBDInfo obd, int pid, byte[] data, int dataOffset){
		switch (pid) {
			case 0x1:
				//故障码数
				int v1 = data[dataOffset] & 0x7F;
				obd.setFaultCount(v1);
				break;
			case 0x4:
				//发动机负荷
				double v4 = (data[dataOffset] & 0xFF) * 100 / 255.0;
				obd.setEngineLoad(v4);
				break;
			case 0x5:
				//水温
				int v5 = (data[dataOffset] & 0xFF) - 40;
				obd.setWarterTemperature(v5);
				break;
			case 0xC:
				//发动机转速
				double vc = (Util.getShort(data, dataOffset) & 0xFFFF) / 4.0;			
				obd.setRotationSpeed(vc);
				break;
			case 0xD:
				//车速
				int vd = (data[dataOffset] & 0xFF);
				obd.setSpeed(vd);
				break;
			case 0xF:
				int vf = (data[dataOffset] & 0xFF) - 40;
				obd.setIntakeAirTemperature(vf);
				break;
			case 0x1F:
				//发动机运行时间
				int v1f = (Util.getShort(data, dataOffset) & 0xFFFF);
				obd.setRunTime(v1f);
				break;
			case 0x42:
				//蓄电池电压
				double v42 = (Util.getShort(data, dataOffset) & 0xFFFF) / 1000.0;
				obd.setVoltage(v42);
				break;
			case 0x44:
				//空燃比系数
				double v44 = (Util.getShort(data, dataOffset) & 0xFFFF) * 0.0000305;
				obd.setAirFuelRatio(v44);
				break;
			case 0x46:
				//车外温度
				int v46 = (data[dataOffset] & 0xFF) - 40;
				obd.setOutsideTemperature(v46);
				break;
			case 0x4C:
				//节气门开度
				double v4c = (data[dataOffset] & 0xFF) * 100 / 255.0;
				obd.setThrottle(v4c);
				break;
			case 0x88:
				//每小时油耗
				long v88 = Util.getInt(data, dataOffset) & 0xFFFFFFFFL;
				obd.setOilPerHour(v88);
				break;
			case 0x89:
				//百公里油耗
				double v89 = (Util.getInt(data, dataOffset) & 0xFFFFFFFFL) / 10.0;
				obd.setOilPerHundredKm(v89);
				break;
			case 0x8A:
				//里程
				long v8a = Util.getInt(data, dataOffset) & 0xFFFFFFFFL;
				obd.setDistance(v8a);
				break;
			case 0x8B:
				//剩余油量
				long v8b_a = Util.getInt(data, dataOffset) & 0xFFFFFFFFL;
				if(v8b_a >= 0x8000){
					//%
					double v8b = (v8b_a - 0x8000) / 10.0;
					obd.setRemainPercentOil(v8b);
				}else{
					//L
					double v8b = v8b_a / 10.0;
					obd.setRemainOil(v8b);
				}
				break;
			default:
				break;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{bodyLength:").append(bodyLength);
		sb.append(", flag:0x").append(HexUtil.toHexStr(flag).toUpperCase());
		sb.append(", faultCount:").append(faultCount);
		sb.append(", engineLoad:").append(engineLoad);
		sb.append(", warterTemperature:").append(warterTemperature);
		sb.append(", rotationSpeed:").append(rotationSpeed);
		sb.append(", speed:").append(speed);
		sb.append(", runTime:").append(runTime);
		sb.append(", voltage:").append(voltage);
		sb.append(", airFuelRatio:").append(airFuelRatio);
		sb.append(", outsideTemperature:").append(outsideTemperature);
		sb.append(", throttle:").append(throttle);
		sb.append(", oilPerHour:").append(oilPerHour);
		sb.append(", oilPerHundredKm:").append(oilPerHundredKm);
		sb.append(", distance:").append(distance);
		sb.append(", remainOil:").append(remainOil);
		sb.append(", remainPercentOil:").append(remainPercentOil);
		sb.append(", intakeAirTemperature:").append(intakeAirTemperature);
		sb.append("}");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		int v = Integer.MAX_VALUE;
		long b = 0xFFFFFFFFL;
		
		System.out.println(b > v);
	}
}
