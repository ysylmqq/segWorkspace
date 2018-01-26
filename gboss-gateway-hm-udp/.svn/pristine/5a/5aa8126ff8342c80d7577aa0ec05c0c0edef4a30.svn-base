package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.ArrayList;
import java.util.List;

import cc.chinagps.gateway.unit.leopaard.util.LeopaardParseUtil;
import cc.chinagps.gateway.util.Util;

public class LeopaardUBIInfo {
	private final static int cycle = 15;
	private List<Integer> hourOils; // 瞬时油耗（单位：0.1升/小时）
	private int remainOil; // 剩余油量（单位：0.1升）
	private int remainPercentOil; // 剩余油量百分比（单位：0.1%）
	private int totalDistance; // 总里程（单位：百米）
	private List<Integer> rotationSpeeds; // 发动机转速（单位：转/分）
	private List<Integer> speeds; // OBD速度（单位：0.1千米/小时）
	private List<Integer> wheelAngles;// 方向盘位置信息
	private List<Integer> wheelRotationSpeeds;// 方向盘的转速
	private List<Integer> wheelAccelerateSpeeds;// 方向盘纵向加速度
	private List<Integer> gearsSigns;// 档位信号

	private int lfTyreStatusBit;// 左前轮胎压状态有效位
	private int lfTyreStatus;// 左前轮胎压状态
	private int lfTyrePressure;// 左前轮胎压

	private int rfTyreStatusBit;// 右前轮胎压状态有效位
	private int rfTyreStatus;// 右前轮胎压状态
	private int rfTyrePressure;// 右前轮胎压

	private int lbTyreStatusBit;// 左后轮胎压状态有效位
	private int lbTyreStatus;// 左后轮胎压状态
	private int lbTyrePressure;// 左后轮胎压

	private int rbTyreStatusBit;// 右后轮胎压状态有效位
	private int rbTyreStatus;// 右后轮胎压状态
	private int rbTyrePressure;// 右后轮胎压

	private int ubiDataLen;// 仪表3数据长度

	public int getUbiDataLen() {
		return ubiDataLen;
	}

	public void setUbiDataLen(int ubiDataLen) {
		this.ubiDataLen = ubiDataLen;
	}
	
	public int getRemainPercentOil() {
		return remainPercentOil;
	}

	public void setRemainPercentOil(int remainPercentOil) {
		this.remainPercentOil = remainPercentOil;
	}

	public List<Integer> getHourOils() {
		return hourOils;
	}

	public void setHourOils(List<Integer> hourOils) {
		this.hourOils = hourOils;
	}

	public int getRemainOil() {
		return remainOil;
	}

	public void setRemainOil(int remainOil) {
		this.remainOil = remainOil;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

	public List<Integer> getRotationSpeeds() {
		return rotationSpeeds;
	}

	public void setRotationSpeeds(List<Integer> rotationSpeeds) {
		this.rotationSpeeds = rotationSpeeds;
	}

	public List<Integer> getSpeeds() {
		return speeds;
	}

	public void setSpeeds(List<Integer> speeds) {
		this.speeds = speeds;
	}

	public List<Integer> getWheelAngles() {
		return wheelAngles;
	}

	public void setWheelAngles(List<Integer> wheelAngles) {
		this.wheelAngles = wheelAngles;
	}

	public List<Integer> getWheelRotationSpeeds() {
		return wheelRotationSpeeds;
	}

	public void setWheelRotationSpeeds(List<Integer> wheelRotationSpeeds) {
		this.wheelRotationSpeeds = wheelRotationSpeeds;
	}

	public List<Integer> getWheelAccelerateSpeeds() {
		return wheelAccelerateSpeeds;
	}

	public void setWheelAccelerateSpeeds(List<Integer> wheelAccelerateSpeeds) {
		this.wheelAccelerateSpeeds = wheelAccelerateSpeeds;
	}

	public List<Integer> getGearsSigns() {
		return gearsSigns;
	}

	public void setGearsSigns(List<Integer> gearsSigns) {
		this.gearsSigns = gearsSigns;
	}

	public int getLfTyreStatusBit() {
		return lfTyreStatusBit;
	}

	public void setLfTyreStatusBit(int lfTyreStatusBit) {
		this.lfTyreStatusBit = lfTyreStatusBit;
	}

	public int getLfTyreStatus() {
		return lfTyreStatus;
	}

	public void setLfTyreStatus(int lfTyreStatus) {
		this.lfTyreStatus = lfTyreStatus;
	}

	public int getLfTyrePressure() {
		return lfTyrePressure;
	}

	public void setLfTyrePressure(int lfTyrePressure) {
		this.lfTyrePressure = lfTyrePressure;
	}

	public int getRfTyreStatusBit() {
		return rfTyreStatusBit;
	}

	public void setRfTyreStatusBit(int rfTyreStatusBit) {
		this.rfTyreStatusBit = rfTyreStatusBit;
	}

	public int getRfTyreStatus() {
		return rfTyreStatus;
	}

	public void setRfTyreStatus(int rfTyreStatus) {
		this.rfTyreStatus = rfTyreStatus;
	}

	public int getRfTyrePressure() {
		return rfTyrePressure;
	}

	public void setRfTyrePressure(int rfTyrePressure) {
		this.rfTyrePressure = rfTyrePressure;
	}

	public int getLbTyreStatusBit() {
		return lbTyreStatusBit;
	}

	public void setLbTyreStatusBit(int lbTyreStatusBit) {
		this.lbTyreStatusBit = lbTyreStatusBit;
	}

	public int getLbTyreStatus() {
		return lbTyreStatus;
	}

	public void setLbTyreStatus(int lbTyreStatus) {
		this.lbTyreStatus = lbTyreStatus;
	}

	public int getLbTyrePressure() {
		return lbTyrePressure;
	}

	public void setLbTyrePressure(int lbTyrePressure) {
		this.lbTyrePressure = lbTyrePressure;
	}

	public int getRbTyreStatusBit() {
		return rbTyreStatusBit;
	}

	public void setRbTyreStatusBit(int rbTyreStatusBit) {
		this.rbTyreStatusBit = rbTyreStatusBit;
	}

	public int getRbTyreStatus() {
		return rbTyreStatus;
	}

	public void setRbTyreStatus(int rbTyreStatus) {
		this.rbTyreStatus = rbTyreStatus;
	}

	public int getRbTyrePressure() {
		return rbTyrePressure;
	}

	public void setRbTyrePressure(int rbTyrePressure) {
		this.rbTyrePressure = rbTyrePressure;
	}

	public static LeopaardUBIInfo parse(byte[] data, int position) throws Exception {
		LeopaardUBIInfo leopaardUBIInfo = new LeopaardUBIInfo();
		int startPos = position;
		
		List<Integer> hourOilList = new ArrayList<Integer>();
		for (int i = 0; i < cycle; i++) {
			hourOilList.add((int) data[position++]);
		}
		leopaardUBIInfo.setHourOils(hourOilList);

		int remainOil = Util.getShort(data, position);//TripA平均油耗
		//leopaardUBIInfo.setRemainOil(remainOil * 10);
		leopaardUBIInfo.setRemainPercentOil(remainOil * 10);
		position += 2;
		position += 2;//TripB平均油耗略过
		position ++;//仪表显示模式略过
		int totalDistance = LeopaardParseUtil.getIntFrom3Byte(data, position);
		leopaardUBIInfo.setTotalDistance(totalDistance * 100);
		position += 3;

		List<Integer> rotationSpeedList = new ArrayList<Integer>();
		for (int i = 0; i < cycle; i++) {
			rotationSpeedList.add((int) Util.getShort(data, position));
			position += 2;
		}
		leopaardUBIInfo.setRotationSpeeds(rotationSpeedList);
		
		List<Integer> speedList = new ArrayList<Integer>();
		for (int i = 0; i < cycle; i++) {
			speedList.add((int) Util.getShort(data, position));
			position += 2;
		}
		leopaardUBIInfo.setSpeeds(speedList);
		
		List<Integer> wheelAngleList = new ArrayList<Integer>();
		for (int i = 0; i < cycle; i++) {
			wheelAngleList.add((int) Util.getShort(data, position));
			position += 2;
		}
		leopaardUBIInfo.setWheelAngles(wheelAngleList);
		
		List<Integer> wheelRotationList = new ArrayList<Integer>();
		for (int i = 0; i < cycle; i++) {
			wheelRotationList.add((int) Util.getShort(data, position));
			position += 2;
		}
		leopaardUBIInfo.setWheelRotationSpeeds(wheelRotationList);
		
		List<Integer> wheelAccelerateList = new ArrayList<Integer>();
		for (int i = 0; i < cycle; i++) {
			wheelAccelerateList.add((int) Util.getShort(data, position));
			position += 2;
		}
		leopaardUBIInfo.setWheelAccelerateSpeeds(wheelAccelerateList);
		
		List<Integer> gearsSignList = new ArrayList<Integer>();
		for (int i = 0; i < cycle; i++) {
			gearsSignList.add((int) data[position++]);
		}
		leopaardUBIInfo.setGearsSigns(gearsSignList);

		int lfTyreStatusBit = data[position++];// 左前轮胎压状态有效位
		leopaardUBIInfo.setLfTyreStatusBit(lfTyreStatusBit);
		int lfTyreStatus = data[position++];// 左前轮胎压状态
		leopaardUBIInfo.setLfTyreStatus(lfTyreStatus);

		int lbTyreStatusBit = data[position++];// 左后轮胎压状态有效位
		leopaardUBIInfo.setLbTyreStatusBit(lbTyreStatusBit);
		int lbTyreStatus = data[position++];// 左后轮胎压状态
		leopaardUBIInfo.setLbTyreStatus(lbTyreStatus);

		int rfTyreStatusBit = data[position++];// 右前轮胎压状态有效位
		leopaardUBIInfo.setRfTyreStatusBit(rfTyreStatusBit);
		int rfTyreStatus = data[position++];// 右前轮胎压状态
		leopaardUBIInfo.setRfTyreStatus(rfTyreStatus);

		int rbTyreStatusBit = data[position++];// 右后轮胎压状态有效位
		leopaardUBIInfo.setRbTyreStatusBit(rbTyreStatusBit);
		int rbTyreStatus = data[position++];// 右后轮胎压状态
		leopaardUBIInfo.setRbTyreStatus(rbTyreStatus);

		int lfTyrePressure = data[position++];// 左前轮胎压
		leopaardUBIInfo.setLfTyrePressure(lfTyrePressure*4);
		int lbTyrePressure = data[position++];// 左后轮胎压
		leopaardUBIInfo.setLbTyrePressure(lbTyrePressure*4);
		int rfTyrePressure = data[position++];// 右前轮胎压
		leopaardUBIInfo.setRfTyrePressure(rfTyrePressure*4);
		int rbTyrePressure = data[position++];// 右后轮胎压
		leopaardUBIInfo.setRbTyrePressure(rbTyrePressure*4);

		int dataLen = position - startPos;
		leopaardUBIInfo.setUbiDataLen(dataLen);

		return leopaardUBIInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LeopaardUBIInfo [hourOils=");
		builder.append(hourOils);
		builder.append(", remainOil=");
		builder.append(remainOil);
		builder.append(", totalDistance=");
		builder.append(totalDistance);
		builder.append(", rotationSpeeds=");
		builder.append(rotationSpeeds);
		builder.append(", speeds=");
		builder.append(speeds);
		builder.append(", wheelAngles=");
		builder.append(wheelAngles);
		builder.append(", wheelRotationSpeeds=");
		builder.append(wheelRotationSpeeds);
		builder.append(", wheelAccelerateSpeeds=");
		builder.append(wheelAccelerateSpeeds);
		builder.append(", gearsSigns=");
		builder.append(gearsSigns);
		builder.append(", lfTyreStatusBit=");
		builder.append(lfTyreStatusBit);
		builder.append(", lfTyreStatus=");
		builder.append(lfTyreStatus);
		builder.append(", lfTyrePressure=");
		builder.append(lfTyrePressure);
		builder.append(", rfTyreStatusBit=");
		builder.append(rfTyreStatusBit);
		builder.append(", rfTyreStatus=");
		builder.append(rfTyreStatus);
		builder.append(", rfTyrePressure=");
		builder.append(rfTyrePressure);
		builder.append(", lbTyreStatusBit=");
		builder.append(lbTyreStatusBit);
		builder.append(", lbTyreStatus=");
		builder.append(lbTyreStatus);
		builder.append(", lbTyrePressure=");
		builder.append(lbTyrePressure);
		builder.append(", rbTyreStatusBit=");
		builder.append(rbTyreStatusBit);
		builder.append(", rbTyreStatus=");
		builder.append(rbTyreStatus);
		builder.append(", rbTyrePressure=");
		builder.append(rbTyrePressure);
		builder.append(", ubiDataLen=");
		builder.append(ubiDataLen);
		builder.append("]");
		return builder.toString();
	}

}
