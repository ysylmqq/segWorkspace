package cc.chinagps.gateway.unit.leopaard.upload.bean;

import cc.chinagps.gateway.unit.leopaard.util.LeopaardParseUtil;
import cc.chinagps.gateway.util.Util;

public class LeopaardOBDInfo {
	private int remainOil; // 剩余油量（单位：0.1升）
	private int remainPercentOil; // 剩余油量百分比（单位：0.1%）
	private int averageOil; // 平均油耗（单位：0.1升/百公里）
	private int hourOil; // 瞬时油耗（单位：0.1升/小时）
	private int totalDistance; // 总里程（单位：百米）
	private int waterTemperature; // 冷却液温度,水温（单位：℃）
	private int reviseOil; // 燃油修正（0.1%）
	private int rotationSpeed; // 发动机转速（单位：转/分）
	private int intakeAirTemperature; // 进气温度（单位：℃）
	private int airDischange; // 空气流量(g/s)
	private int speed; // OBD速度（单位：0.1千米/小时）
	private int remainDistance; // 续航(剩余)里程（单位：米）
	private byte signLevel;//通信模块信号强度
	
	
	private int lfTyreStatusBit;//左前轮胎压状态有效位
	private int lfTyreStatus;//左前轮胎压状态
	private int lfTyrePressure;//左前轮胎压
	
	private int rfTyreStatusBit;//右前轮胎压状态有效位
	private int rfTyreStatus;//右前轮胎压状态
	private int rfTyrePressure;//右前轮胎压
	
	private int lbTyreStatusBit;//左后轮胎压状态有效位
	private int lbTyreStatus;//左后轮胎压状态
	private int lbTyrePressure;//左后轮胎压
	
	private int rbTyreStatusBit;//右后轮胎压状态有效位
	private int rbTyreStatus;//右后轮胎压状态
	private int rbTyrePressure;//右后轮胎压
	
	
	// 下面是新能源车增加的部分
	private int gears; // 档位：-1:倒档, 0:空档, 1:1档, 2:2档, 3:3档, 4:4档, 5:5档, 6:6档,
						// 7:7档, 15:前进档, 16:制动档
	private boolean isDrive; // 驱动是否有效
	private int driveRatio; // 加速踏板行程值: 有效值范围：0～100（表示 0%～100%）,最小计量单元：1%
	private boolean isBreak; // 制动是否有效
	private int breakRatio; // 制动踏板行程值: 有效值范围：0～100（表示 0%～100%）,最小计量单元：1%
	private int chargeStatus; // 充放电状态：1:充电, 2:放电, -1:无效数据
	private int motorCtrlTemperature; // 电机控制器温度, （单位：十分之一摄氏度）
	private int motorTemperature; // 电机温度, （单位：十分之一摄氏度）
	private int motorSpeed; // 电机转速
	private int motorVoltage; // 电机电压（单位：0.1V)
	private int motorCurrent; // 电机电流（单位：1毫安)
	private int airconTemperature; // 空调设定温度, （单位：十分之一摄氏度）
	private int batteryTotalVoltage; // 动力蓄电池总电压（单位：0.1V)
	private int batteryTotalCurrent; // 动力蓄电池总电流（单位：1毫安)
	private int remaindCapacity; // 剩余能量（单位：o.1度,千卡时）
	private int resistance; // 绝缘电阻（单位：欧母Ω）
	
	private int odbDataLen;//仪表3数据长度

	public int getOdbDataLen() {
		return odbDataLen;
	}

	public void setOdbDataLen(int odbDataLen) {
		this.odbDataLen = odbDataLen;
	}

	public int getRemainOil() {
		return remainOil;
	}

	public void setRemainOil(int remainOil) {
		this.remainOil = remainOil;
	}

	public int getRemainPercentOil() {
		return remainPercentOil;
	}

	public void setRemainPercentOil(int remainPercentOil) {
		this.remainPercentOil = remainPercentOil;
	}

	public int getAverageOil() {
		return averageOil;
	}

	public void setAverageOil(int averageOil) {
		this.averageOil = averageOil;
	}
	


	public int getHourOil() {
		return hourOil;
	}

	public void setHourOil(int hourOil) {
		this.hourOil = hourOil;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

	public int getWaterTemperature() {
		return waterTemperature;
	}

	public void setWaterTemperature(int waterTemperature) {
		this.waterTemperature = waterTemperature;
	}

	public int getReviseOil() {
		return reviseOil;
	}

	public void setReviseOil(int reviseOil) {
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

	public int getAirDischange() {
		return airDischange;
	}

	public void setAirDischange(int airDischange) {
		this.airDischange = airDischange;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getRemainDistance() {
		return remainDistance;
	}

	public void setRemainDistance(int remainDistance) {
		this.remainDistance = remainDistance;
	}


	public byte getSignLevel() {
		return signLevel;
	}

	public void setSignLevel(byte signLevel) {
		this.signLevel = signLevel;
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

	public int getGears() {
		return gears;
	}

	public void setGears(int gears) {
		this.gears = gears;
	}

	public boolean isDrive() {
		return isDrive;
	}

	public void setDrive(boolean isDrive) {
		this.isDrive = isDrive;
	}

	public int getDriveRatio() {
		return driveRatio;
	}

	public void setDriveRatio(int driveRatio) {
		this.driveRatio = driveRatio;
	}

	public boolean isBreak() {
		return isBreak;
	}

	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}

	public int getBreakRatio() {
		return breakRatio;
	}

	public void setBreakRatio(int breakRatio) {
		this.breakRatio = breakRatio;
	}

	public int getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(int chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public int getMotorCtrlTemperature() {
		return motorCtrlTemperature;
	}

	public void setMotorCtrlTemperature(int motorCtrlTemperature) {
		this.motorCtrlTemperature = motorCtrlTemperature;
	}

	public int getMotorTemperature() {
		return motorTemperature;
	}

	public void setMotorTemperature(int motorTemperature) {
		this.motorTemperature = motorTemperature;
	}

	public int getMotorSpeed() {
		return motorSpeed;
	}

	public void setMotorSpeed(int motorSpeed) {
		this.motorSpeed = motorSpeed;
	}

	public int getMotorVoltage() {
		return motorVoltage;
	}

	public void setMotorVoltage(int motorVoltage) {
		this.motorVoltage = motorVoltage;
	}

	public int getMotorCurrent() {
		return motorCurrent;
	}

	public void setMotorCurrent(int motorCurrent) {
		this.motorCurrent = motorCurrent;
	}

	public int getAirconTemperature() {
		return airconTemperature;
	}

	public void setAirconTemperature(int airconTemperature) {
		this.airconTemperature = airconTemperature;
	}

	public int getBatteryTotalVoltage() {
		return batteryTotalVoltage;
	}

	public void setBatteryTotalVoltage(int batteryTotalVoltage) {
		this.batteryTotalVoltage = batteryTotalVoltage;
	}

	public int getBatteryTotalCurrent() {
		return batteryTotalCurrent;
	}

	public void setBatteryTotalCurrent(int batteryTotalCurrent) {
		this.batteryTotalCurrent = batteryTotalCurrent;
	}

	public int getRemaindCapacity() {
		return remaindCapacity;
	}

	public void setRemaindCapacity(int remaindCapacity) {
		this.remaindCapacity = remaindCapacity;
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}
	
	
	public static LeopaardOBDInfo parse(LeopaardOBDInfo leopaardOBDInfo,byte[] data,int position)throws Exception{
		if (leopaardOBDInfo == null)
			leopaardOBDInfo = new LeopaardOBDInfo();
		int startPos = position;
		int hourOil3 = data[position];// 瞬时油耗（单位：十分之一/H）
		leopaardOBDInfo.setHourOil(hourOil3 * 10);
		position++;
		int averageOil3 =  Util.getShort(data, position);// 平均油耗（十分之一升/百公里）
		leopaardOBDInfo.setAverageOil(averageOil3 * 10);//TripA平均油耗
		position += 2;
		position += 2;//TripB平均油耗略过
		int remainOil3 = Util.getShort(data, position);
		//leopaardOBDInfo.setRemainOil(remainOil3 * 10);
		leopaardOBDInfo.setRemainPercentOil(remainOil3 * 10);
		position += 2;
		//int intakeAirTemperature = data[position];
		//leopaardOBDInfo.setIntakeAirTemperature(intakeAirTemperature);
		//position++;
		int totalDistance3 = LeopaardParseUtil.getIntFrom3Byte(data, position);
		leopaardOBDInfo.setTotalDistance(totalDistance3 * 100);
		position += 3;
		int waterTemperature3 = data[position];
		leopaardOBDInfo.setWaterTemperature(waterTemperature3);
		position++;
		int rotationSpeed3 = Util.getShort(data, position);
		leopaardOBDInfo.setRotationSpeed(rotationSpeed3);
		position += 2;
		int obdSpeed3 = Util.getShort(data, position);
		leopaardOBDInfo.setSpeed(obdSpeed3);
		position += 2;
		int remainDistance3 = Util.getShort(data, position);//TripA续航里程
		leopaardOBDInfo.setRemainDistance(remainDistance3 * 100);
		position += 2;
		position += 2;//TripB续航里程略过
		position++;//仪表显示模式略过
		byte signLevel3 = data[position++];
		leopaardOBDInfo.setSignLevel(signLevel3);
		
		int lfTyreStatusBit = data[position++];//左前轮胎压状态有效位
		leopaardOBDInfo.setLfTyreStatusBit(lfTyreStatusBit);
		int lfTyreStatus= data[position++];//左前轮胎压状态
		leopaardOBDInfo.setLfTyreStatus(lfTyreStatus);
		
		int lbTyreStatusBit = data[position++];//左后轮胎压状态有效位
		leopaardOBDInfo.setLbTyreStatusBit(lbTyreStatusBit);
		int lbTyreStatus= data[position++];//左后轮胎压状态
		leopaardOBDInfo.setLbTyreStatus(lbTyreStatus);
		
		int rfTyreStatusBit = data[position++];//右前轮胎压状态有效位
		leopaardOBDInfo.setRfTyreStatusBit(rfTyreStatusBit);
		int rfTyreStatus= data[position++];//右前轮胎压状态
		leopaardOBDInfo.setRfTyreStatus(rfTyreStatus);
		
		int rbTyreStatusBit = data[position++];//右后轮胎压状态有效位
		leopaardOBDInfo.setRbTyreStatusBit(rbTyreStatusBit);
		int rbTyreStatus= data[position++];//右后轮胎压状态
		leopaardOBDInfo.setRbTyreStatus(rbTyreStatus);
		
		int lfTyrePressure= data[position++];//左前轮胎压 
		leopaardOBDInfo.setLfTyrePressure(lfTyrePressure*4);
		int lbTyrePressure= data[position++];//左后轮胎压 
		leopaardOBDInfo.setLbTyrePressure(lbTyrePressure*4);
		int rfTyrePressure= data[position++];//右前轮胎压 
		leopaardOBDInfo.setRfTyrePressure(rfTyrePressure*4);
		int rbTyrePressure= data[position++];//右后轮胎压 
		leopaardOBDInfo.setRbTyrePressure(rbTyrePressure*4);
		
		int dataLen = position - startPos;
		leopaardOBDInfo.setOdbDataLen(dataLen);
		
		return leopaardOBDInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LeopaardOBDInfo [remainOil=");
		builder.append(remainOil);
		builder.append(", remainPercentOil=");
		builder.append(remainPercentOil);
		builder.append(", averageOil=");
		builder.append(averageOil);
		builder.append(", hourOil=");
		builder.append(hourOil);
		builder.append(", totalDistance=");
		builder.append(totalDistance);
		builder.append(", waterTemperature=");
		builder.append(waterTemperature);
		builder.append(", reviseOil=");
		builder.append(reviseOil);
		builder.append(", rotationSpeed=");
		builder.append(rotationSpeed);
		builder.append(", intakeAirTemperature=");
		builder.append(intakeAirTemperature);
		builder.append(", airDischange=");
		builder.append(airDischange);
		builder.append(", speed=");
		builder.append(speed);
		builder.append(", remainDistance=");
		builder.append(remainDistance);
		builder.append(", signLevel=");
		builder.append(signLevel);
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
		builder.append(", gears=");
		builder.append(gears);
		builder.append(", isDrive=");
		builder.append(isDrive);
		builder.append(", driveRatio=");
		builder.append(driveRatio);
		builder.append(", isBreak=");
		builder.append(isBreak);
		builder.append(", breakRatio=");
		builder.append(breakRatio);
		builder.append(", chargeStatus=");
		builder.append(chargeStatus);
		builder.append(", motorCtrlTemperature=");
		builder.append(motorCtrlTemperature);
		builder.append(", motorTemperature=");
		builder.append(motorTemperature);
		builder.append(", motorSpeed=");
		builder.append(motorSpeed);
		builder.append(", motorVoltage=");
		builder.append(motorVoltage);
		builder.append(", motorCurrent=");
		builder.append(motorCurrent);
		builder.append(", airconTemperature=");
		builder.append(airconTemperature);
		builder.append(", batteryTotalVoltage=");
		builder.append(batteryTotalVoltage);
		builder.append(", batteryTotalCurrent=");
		builder.append(batteryTotalCurrent);
		builder.append(", remaindCapacity=");
		builder.append(remaindCapacity);
		builder.append(", resistance=");
		builder.append(resistance);
		builder.append("]");
		return builder.toString();
	}
	
}
