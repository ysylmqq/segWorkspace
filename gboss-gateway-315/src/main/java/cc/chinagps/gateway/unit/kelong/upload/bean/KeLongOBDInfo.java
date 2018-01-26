package cc.chinagps.gateway.unit.kelong.upload.bean;

import org.seg.lib.util.Util;

/**
 *@todo：科隆Obd信息包类; 
 *@author：cj
 *@time：2017年5月25日
 *
 */
public class KeLongOBDInfo {
	private int totalDistance; // 总里程（单位：百米）
	private int rotationSpeed; // 发动机转速（单位：转/分）
	private int speed; // OBD速度（单位：0.1千米/小时）
	private int batteryVoltage;// 汽车电瓶电压,单位0.01V（两位小数点）
	private int totalOil;// 累计总油耗，单位mL（毫升）
	private int totalDriveTime;// 累计总行驶时间，单位秒
    private int remainOil;//剩余油量（从can包中取）
    private int dataLen;
    
	public int getDataLen() {
		return dataLen;
	}

	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

	public int getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(int rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(int batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public int getTotalOil() {
		return totalOil;
	}

	public void setTotalOil(int totalOil) {
		this.totalOil = totalOil;
	}

	public int getTotalDriveTime() {
		return totalDriveTime;
	}

	public void setTotalDriveTime(int totalDriveTime) {
		this.totalDriveTime = totalDriveTime;
	}

	public int getRemainOil() {
		return remainOil;
	}

	public void setRemainOil(int remainOil) {
		this.remainOil = remainOil;
	}	
	
	/**
	 * @todo:解析科隆OBD信息
	 * @author:cj
	 * @param:
	 * @return:
	 * @remark:
	 */
	public static KeLongOBDInfo parse(byte[] data, int pos) throws Exception {
		KeLongOBDInfo keLongOBDInfo = new KeLongOBDInfo();
		int startPos = pos;
		//电瓶电压
		int batteryVoltage = Util.getShort(data, pos) & 0xFFFF;
		keLongOBDInfo.setBatteryVoltage(batteryVoltage);
		pos += 2;
		//车速
		int speed = Util.getShort(data, pos) & 0xFFFF;
		keLongOBDInfo.setSpeed(speed);
		pos += 2;
		//发送机转速
		int rotationSpeed = Util.getShort(data, pos) & 0xFFFF;
		keLongOBDInfo.setRotationSpeed(rotationSpeed);
		pos += 2;
		//累计里程
		int totalDistance = Util.getInt(data, pos) & 0xFFFFFFFF;
		keLongOBDInfo.setTotalDistance(totalDistance);
		pos += 4;
		//累计油耗
		int totalOil = Util.getInt(data, pos) & 0xFFFFFFFF;
		keLongOBDInfo.setTotalOil(totalOil);
		pos += 4;
		//累计行驶时间
		int totalDriveTime = Util.getInt(data, pos) & 0xFFFFFFFF;
		keLongOBDInfo.setTotalDriveTime(totalDriveTime);
		pos += 4;
		
		keLongOBDInfo.setDataLen(pos - startPos);
		return keLongOBDInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeLongOBDInfo [totalDistance=");
		builder.append(totalDistance);
		builder.append(", rotationSpeed=");
		builder.append(rotationSpeed);
		builder.append(", speed=");
		builder.append(speed);
		builder.append(", batteryVoltage=");
		builder.append(batteryVoltage);
		builder.append(", totalOil=");
		builder.append(totalOil);
		builder.append(", totalDriveTime=");
		builder.append(totalDriveTime);
		builder.append(", dataLen=");
		builder.append(dataLen);
		builder.append("]");
		return builder.toString();
	}
}
