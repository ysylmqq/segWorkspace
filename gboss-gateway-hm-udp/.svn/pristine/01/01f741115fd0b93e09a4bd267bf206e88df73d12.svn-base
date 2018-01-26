package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.Date;

import cc.chinagps.gateway.unit.leopaard.define.LeopaardDefines;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardParseUtil;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardTimeFormatUtil;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.Util;

public class LeopaardGPSInfo {
	private long gpsTime;

	private byte locStatus;

	private boolean isLoc;

	private double lng;

	private double lat;

	private int speed;

	private int course;

	private int height;

	private int positionInfoDataLen;

	private LeopaardOBDInfo leopaardOBDInfo;

	private BatteryVoltageInfo batteryVoltageInfo;

	private BatteryTemperatureInfo batteryTemperatureInfo;

	private BatteryExtremumInfo batteryExtremumInfo;

	private LeopaardStatusInfo leopaardStatusInfo;

	private LeopaardFaultInfo leopaardFaultInfo;

	private LeopaardFaultLightInfo leopaardFaultLightInfo;

	private LeopaardUBIInfo leopaardUBIInfo;

	private LeopaardUBIStatus leopaardUBIStatus;

	public LeopaardUBIInfo getLeopaardUBIInfo() {
		return leopaardUBIInfo;
	}

	public void setLeopaardUBIInfo(LeopaardUBIInfo leopaardUBIInfo) {
		this.leopaardUBIInfo = leopaardUBIInfo;
	}

	public LeopaardUBIStatus getLeopaardUBIStatus() {
		return leopaardUBIStatus;
	}

	public void setLeopaardUBIStatus(LeopaardUBIStatus leopaardUBIStatus) {
		this.leopaardUBIStatus = leopaardUBIStatus;
	}

	public int getPositionInfoDataLen() {
		return positionInfoDataLen;
	}

	public void setPositionInfoDataLen(int positionInfoDataLen) {
		this.positionInfoDataLen = positionInfoDataLen;
	}

	public LeopaardFaultLightInfo getLeopaardFaultLightInfo() {
		return leopaardFaultLightInfo;
	}

	public void setLeopaardFaultLightInfo(LeopaardFaultLightInfo leopaardFaultLightInfo) {
		this.leopaardFaultLightInfo = leopaardFaultLightInfo;
	}

	public LeopaardFaultInfo getLeopaardFaultInfo() {
		return leopaardFaultInfo;
	}

	public void setLeopaardFaultInfo(LeopaardFaultInfo leopaardFaultInfo) {
		this.leopaardFaultInfo = leopaardFaultInfo;
	}

	public long getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(long gpsTime) {
		this.gpsTime = gpsTime;
	}

	public LeopaardOBDInfo getLeopaardOBDInfo() {
		return leopaardOBDInfo;
	}

	public void setLeopaardOBDInfo(LeopaardOBDInfo leopaardOBDInfo) {
		this.leopaardOBDInfo = leopaardOBDInfo;
	}

	public BatteryVoltageInfo getBatteryVoltageInfo() {
		return batteryVoltageInfo;
	}

	public void setBatteryVoltageInfo(BatteryVoltageInfo batteryVoltageInfo) {
		this.batteryVoltageInfo = batteryVoltageInfo;
	}

	public BatteryTemperatureInfo getBatteryTemperatureInfo() {
		return batteryTemperatureInfo;
	}

	public void setBatteryTemperatureInfo(BatteryTemperatureInfo batteryTemperatureInfo) {
		this.batteryTemperatureInfo = batteryTemperatureInfo;
	}

	public BatteryExtremumInfo getBatteryExtremumInfo() {
		return batteryExtremumInfo;
	}

	public void setBatteryExtremumInfo(BatteryExtremumInfo batteryExtremumInfo) {
		this.batteryExtremumInfo = batteryExtremumInfo;
	}

	public LeopaardStatusInfo getLeopaardStatusInfo() {
		return leopaardStatusInfo;
	}

	public void setLeopaardStatusInfo(LeopaardStatusInfo leopaardStatusInfo) {
		this.leopaardStatusInfo = leopaardStatusInfo;
	}

	public byte getLocStatus() {
		return locStatus;
	}

	public void setLocStatus(byte locStatus) {
		this.locStatus = locStatus;
	}

	public boolean isLoc() {
		return isLoc;
	}

	public void setLoc(boolean isLoc) {
		this.isLoc = isLoc;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public static LeopaardGPSInfo parsePositionInfo(LeopaardGPSInfo leopaardGPSInfo, byte[] data, int position)
			throws Exception {
		if (leopaardGPSInfo == null)
			leopaardGPSInfo = new LeopaardGPSInfo();
		int startPos = position;
		// 中国范围是北纬东经
		byte locStatus = data[position];
		boolean isLoc = ((locStatus & 0x01) != 0);
		leopaardGPSInfo.setLoc(isLoc);
		leopaardGPSInfo.setLocStatus(locStatus);
		boolean isSouth = ((locStatus & 0x02) != 0);
		boolean isWest = ((locStatus & 0x04) != 0);
		position += 1;
		String gpsTimeStr = "20" + cc.chinagps.gateway.util.Util.getDateTime(data, position, 6);
		Date gpsTime = LeopaardTimeFormatUtil.parseGMT8(gpsTimeStr);
		leopaardGPSInfo.setGpsTime(gpsTime.getTime());
		position += 6;
		int lng = Util.getInt(data, position);
		if (isSouth)
			lng = -1 * lng;
		position += 4;
		int lat = Util.getInt(data, position);
		if (isWest)
			lat = -1 * lat;
		position += 4;
		leopaardGPSInfo.setLat(lat);
		leopaardGPSInfo.setLng(lng);
		int gpsSpeed = Util.getShort(data, position);
		leopaardGPSInfo.setSpeed(gpsSpeed);
		position += 2;
		int course = Util.getShort(data, position);
		leopaardGPSInfo.setCourse(course);
		position += 2;
		int height = Util.getShort(data, position);
		leopaardGPSInfo.setHeight(height);
		position += 2;
		position += 2;// 预留

		int dataLen = position - startPos;
		leopaardGPSInfo.setPositionInfoDataLen(dataLen);
		return leopaardGPSInfo;
	}

	public static LeopaardGPSInfo parseAckGps(LeopaardGPSInfo leopaardGPSInfo, LeopaardStatusInfo leopaardStatusInfo,
			LeopaardOBDInfo leopaardOBDInfo, byte[] data, int offset) throws Exception {
		if (leopaardGPSInfo == null) {
			leopaardGPSInfo = new LeopaardGPSInfo();
		}

		if (leopaardStatusInfo == null) {
			leopaardStatusInfo = new LeopaardStatusInfo();
		}

		if (leopaardOBDInfo == null) {
			leopaardOBDInfo = new LeopaardOBDInfo();
		}

		int position = offset;

		leopaardGPSInfo = LeopaardGPSInfo.parsePositionInfo(leopaardGPSInfo, data, position);
		position += leopaardGPSInfo.getPositionInfoDataLen();

		int statusDataLen = 5;
		leopaardStatusInfo = LeopaardStatusInfo.parse(data, position);
		leopaardStatusInfo.setDataLen(statusDataLen);
		position += statusDataLen;

		leopaardOBDInfo = LeopaardOBDInfo.parse(leopaardOBDInfo, data, position);
		position += leopaardOBDInfo.getOdbDataLen();

		leopaardGPSInfo.setLeopaardStatusInfo(leopaardStatusInfo);
		leopaardGPSInfo.setLeopaardOBDInfo(leopaardOBDInfo);
		return leopaardGPSInfo;
	}

	public static LeopaardGPSInfo parse(byte[] data, int offset) throws Exception {
		LeopaardGPSInfo leopaardGPSInfo = new LeopaardGPSInfo();
		int position = offset;

		String collectTime = "20" + cc.chinagps.gateway.util.Util.getDateTime(data, position, 6);
		position += 6;

		BatteryVoltageInfo batteryVoltageInfo = null;
		BatteryTemperatureInfo batteryTemperatureInfo = null;
		LeopaardOBDInfo leopaardOBDInfo = null;
		BatteryExtremumInfo batteryExtremumInfo = null;
		LeopaardStatusInfo leopaardStatusInfo = null;
		LeopaardFaultInfo leopaardFaultInfo = null;
		LeopaardECUConfig leopaardECUConfig = null;
		LeopaardFaultLightInfo leopaardFaultLightInfo = null;
		LeopaardTerminalInfo leopaardTerminalInfo = null;
		LeopaardUBIInfo leopaardUBIInfo = null;
		LeopaardUBIStatus leopaardUBIStatus = null;

		while (position < data.length) {
			if (position == data.length - 1)
				break;
			int infoType = data[position++] & 0xFF;
			switch (infoType) {
			case 0x01:
				batteryVoltageInfo = new BatteryVoltageInfo();
				int batteryNum = Util.getShort(data, position);
				batteryVoltageInfo.setBatteryNum(batteryNum);
				position += 2;
				byte packageNum = data[position];
				batteryVoltageInfo.setBatteryPackageNum(packageNum);
				position += 1;
				for (int i = 0; i < packageNum; i++) {
					BatteryUnitVoltageInfo batteryUnitVoltageInfo = new BatteryUnitVoltageInfo();
					byte packageNo = data[position];
					batteryUnitVoltageInfo.setUnitSn(packageNo);
					position += 1;
					byte packageBatteryNum = data[position];
					batteryUnitVoltageInfo.setBatteryNum(packageBatteryNum);
					position += 1;
					for (int j = 0; j < packageBatteryNum; j++) {
						int voltage = Util.getShort(data, position);
						batteryUnitVoltageInfo.getBatteryVoltages().add(Integer.valueOf(voltage));
						position += 2;
					}
					batteryVoltageInfo.getBatteryUnitVoltageInfos().add(batteryUnitVoltageInfo);
				}

				break;
			case 0x02:
				batteryTemperatureInfo = new BatteryTemperatureInfo();
				int probeNum = Util.getShort(data, position);
				batteryTemperatureInfo.setTemperatureProbeNum(probeNum);
				position += 2;
				byte packageNum1 = data[position];
				batteryTemperatureInfo.setBatteryPackageNum(packageNum1);
				position += 1;
				for (int i = 0; i < packageNum1; i++) {
					BatteryUnitTemperatureInfo batteryUnitTemperatureInfo = new BatteryUnitTemperatureInfo();
					byte packageNo = data[position];
					batteryUnitTemperatureInfo.setUnitSn(packageNo);
					position += 1;
					byte packageProbeNum = data[position];
					batteryUnitTemperatureInfo.setTemperatureProbeNum(packageProbeNum);
					position += 1;
					for (int j = 0; j < packageProbeNum; j++) {
						int temperature = Util.getShort(data, position);
						batteryUnitTemperatureInfo.getTemperatures().add(Integer.valueOf(temperature));
						position += 2;
					}
					batteryTemperatureInfo.getBatteryUnitTemperatureInfos().add(batteryUnitTemperatureInfo);
				}

				break;
			case 0x03:
				if (leopaardOBDInfo == null) {
					leopaardOBDInfo = new LeopaardOBDInfo();
				}
				int speed = Util.getShort(data, position);
				leopaardOBDInfo.setSpeed(speed);
				position += 2;
				int totalDistance = Util.getInt(data, position);
				leopaardOBDInfo.setTotalDistance(totalDistance);
				position += 4;
				byte gears = data[position];
				leopaardOBDInfo.setGears(gears);
				position += 1;
				byte driveRatio = data[position];
				leopaardOBDInfo.setDriveRatio(driveRatio);
				position += 1;
				byte breakRatio = data[position];
				leopaardOBDInfo.setBreakRatio(breakRatio);
				position += 1;
				byte chargeStatus = data[position];
				leopaardOBDInfo.setChargeStatus(chargeStatus);
				position += 1;
				byte motorCtrlTemperature = data[position];
				leopaardOBDInfo.setMotorCtrlTemperature(motorCtrlTemperature);
				position += 1;
				int motorSpeed = Util.getShort(data, position);
				leopaardOBDInfo.setMotorSpeed(motorSpeed);
				position += 2;
				byte motorTemperature = data[position];
				leopaardOBDInfo.setMotorTemperature(motorTemperature);
				position += 1;
				int motorVoltage = Util.getShort(data, position);
				leopaardOBDInfo.setMotorVoltage(motorVoltage);
				position += 2;
				int motorCurrent = Util.getShort(data, position);
				leopaardOBDInfo.setMotorCurrent(motorCurrent);
				position += 2;
				byte airconTemperature = data[position];
				leopaardOBDInfo.setAirconTemperature(airconTemperature);
				position += 1;
				position += 7;// 预留

				break;
			case 0x04:
				leopaardGPSInfo = LeopaardGPSInfo.parsePositionInfo(leopaardGPSInfo, data, position);
				position += leopaardGPSInfo.getPositionInfoDataLen();
				break;
			case 0x05:
				// 补发信息
				break;
			case 0x06:
				batteryExtremumInfo = new BatteryExtremumInfo();
				byte maxVoltageBodySn = data[position];
				batteryExtremumInfo.setMaxVoltageBodySn(maxVoltageBodySn);
				position += 1;
				byte maxVoltageUnitSn = data[position];
				batteryExtremumInfo.setMaxVoltageUnitSn(maxVoltageUnitSn);
				position += 1;
				int maxVoltage = Util.getShort(data, position);
				batteryExtremumInfo.setMaxVoltage(maxVoltage);
				position += 2;

				byte minVoltageBodySn = data[position];
				batteryExtremumInfo.setMinVoltageBodySn(minVoltageBodySn);
				position += 1;
				byte minVoltageUnitSn = data[position];
				batteryExtremumInfo.setMinVoltageUnitSn(minVoltageUnitSn);
				position += 1;
				int minVoltage = Util.getShort(data, position);
				batteryExtremumInfo.setMinVoltage(minVoltage);
				position += 2;

				byte maxTemperatureBodySn = data[position];
				batteryExtremumInfo.setMaxTemperatureBodySn(maxTemperatureBodySn);
				position += 1;
				byte maxTemperatureUnitSn = data[position];
				batteryExtremumInfo.setMaxTemperatureUnitSn(maxTemperatureUnitSn);
				position += 1;
				int maxTemperature = Util.getShort(data, position);
				batteryExtremumInfo.setMaxTemperature(maxTemperature);
				position += 2;

				byte minTemperatureBodySn = data[position];
				batteryExtremumInfo.setMaxTemperatureBodySn(minTemperatureBodySn);
				position += 1;
				byte minTemperatureUnitSn = data[position];
				batteryExtremumInfo.setMinTemperatureUnitSn(minTemperatureUnitSn);
				position += 1;
				int minTemperature = Util.getShort(data, position);
				batteryExtremumInfo.setMinTemperature(minTemperature);
				position += 2;

				if (leopaardOBDInfo == null) {
					leopaardOBDInfo = new LeopaardOBDInfo();
				}
				int batteryTotalVoltage = Util.getShort(data, position);
				leopaardOBDInfo.setBatteryTotalVoltage(batteryTotalVoltage);
				position += 2;
				int batteryTotalCurrent = Util.getShort(data, position);
				leopaardOBDInfo.setBatteryTotalCurrent(batteryTotalCurrent);
				position += 2;
				byte soc = data[position];
				position += 1;
				int remaindCapacity = Util.getShort(data, position);
				leopaardOBDInfo.setRemaindCapacity(remaindCapacity);
				position += 2;
				int resistance = Util.getShort(data, position);
				leopaardOBDInfo.setResistance(resistance);
				position += 2;
				position += 5;// 预留
				break;
			case 0x80:
				int statusDataLen = 5;
				leopaardStatusInfo = new LeopaardStatusInfo();
				leopaardStatusInfo = LeopaardStatusInfo.parse(data, position);
				leopaardStatusInfo.setDataLen(statusDataLen);
				position += statusDataLen;
				break;
			case 0x81:
				if (leopaardOBDInfo == null) {
					leopaardOBDInfo = new LeopaardOBDInfo();
				}
				int hourOil = data[position];// 瞬时油耗（单位：十分之一/H）
				leopaardOBDInfo.setHourOil(hourOil * 10);
				position++;
				int averageOil = Util.getShort(data, position);// 平均油耗（十分之一升/百公里）
				leopaardOBDInfo.setAverageOil(averageOil * 10);//TripA平均油耗
				position += 2;
				position += 2;//TripB平均油耗略过
				
				int remainOil = Util.getShort(data, position);
				//leopaardOBDInfo.setRemainOil(remainOil * 10);
				leopaardOBDInfo.setRemainPercentOil(remainOil * 10);
				position += 2;
				// int intakeAirTemperature = data[position];
				// leopaardOBDInfo.setIntakeAirTemperature(intakeAirTemperature);
				// position++;
				int totalDistance1 = LeopaardParseUtil.getIntFrom3Byte(data, position);
				leopaardOBDInfo.setTotalDistance(totalDistance1 * 100);
				position += 3;
				int waterTemperature = data[position];
				leopaardOBDInfo.setWaterTemperature(waterTemperature);
				position++;
				int rotationSpeed = Util.getShort(data, position);
				leopaardOBDInfo.setRotationSpeed(rotationSpeed);
				position += 2;
				int obdSpeed = Util.getShort(data, position);
				leopaardOBDInfo.setSpeed(obdSpeed);
				position += 2;
				int remainDistance = Util.getShort(data, position);//TripA续航里程
				leopaardOBDInfo.setRemainDistance(remainDistance * 100);
				position += 2;
				position += 2;//TripB续航里程略过
				position ++;//仪表显示模式略过
				byte signLevel = data[position++];
				leopaardOBDInfo.setSignLevel(signLevel);
				break;
			case 0x82:
				leopaardFaultInfo = LeopaardFaultInfo.parse(data, position);
				position += leopaardFaultInfo.getDataLength();
				break;
			case 0x83:
				leopaardTerminalInfo = new LeopaardTerminalInfo();
				String softwareVersion = LeopaardParseUtil.getString(data, position, 4, LeopaardDefines.CHARSET);
				position += 4;
				leopaardTerminalInfo.setSoftwareVersion(softwareVersion);
				String hardwareVersion = LeopaardParseUtil.getString(data, position, 4, LeopaardDefines.CHARSET);
				position += 4;
				leopaardTerminalInfo.setHardwareVersion(hardwareVersion);
				String vin = LeopaardParseUtil.getString(data, position, 17, LeopaardDefines.CHARSET);
				position += 17;
				leopaardTerminalInfo.setVin(vin);
				String barCode = LeopaardParseUtil.getString(data, position, 19, LeopaardDefines.CHARSET);
				position += 19;
				leopaardTerminalInfo.setBarCode(barCode);
				String manufactureTimeStr = "20" + Util.getDateTime(data, position, 6);
				Date manufactureTime = LeopaardTimeFormatUtil.parseGMT0(manufactureTimeStr);
				leopaardTerminalInfo.setManufactureTime(manufactureTime.getTime());
				position += 6;
				String softwareGenTimeStr = "20" + Util.getDateTime(data, position, 6);
				Date softwareGenTime = LeopaardTimeFormatUtil.parseGMT0(softwareGenTimeStr);
				leopaardTerminalInfo.setSoftwareGenTime(softwareGenTime.getTime());
				position += 6;
				String lastUpgradeTimeStr = "20" + Util.getDateTime(data, position, 6);
				Date lastUpgradeTime = LeopaardTimeFormatUtil.parseGMT0(lastUpgradeTimeStr);
				leopaardTerminalInfo.setLastUpgradeTime(lastUpgradeTime.getTime());
				position += 6;
				break;
			case 0x84:
				leopaardFaultLightInfo = LeopaardFaultLightInfo.parse(data, position);
				position += leopaardFaultLightInfo.getDataLength();
				break;
			case 0x85:
				byte deliverDataType = data[position++];
				if (leopaardStatusInfo == null) {
					leopaardStatusInfo = new LeopaardStatusInfo();
				}
				switch (deliverDataType) {
				case 0x01:
					// 定时上报
					// leopaardStatusInfo.addStatus(status);
					break;
				case 0x02:
					// ACC ON上报
					leopaardStatusInfo.addStatus(200);
					break;
				case 0x03:
					// ACC OFF上报
					leopaardStatusInfo.addStatus(201);
					break;
				case 0x04:
					// 休眠上报
					leopaardStatusInfo.addStatus(68);
					break;
				case 0x05:
					// 关机上报
					leopaardStatusInfo.addStatus(69);
					break;
				case 0x06:
					// 警情上报
					// leopaardStatusInfo.addStatus(68);
					break;
				case 0x07:
					// 车身状况变化上报
					// leopaardStatusInfo.addStatus(68);
					break;
				case 0x08:
					// I-call上报
					// leopaardStatusInfo.addStatus(68);
					break;
				case 0x09:
					// E-call上报
					// leopaardStatusInfo.addStatus(68);
					break;
				case 0x0A:
					// 故障信息上报
					leopaardStatusInfo.addStatus(212);
					break;
				case 0x0B:
					// 熄火未关灯上报
					leopaardStatusInfo.addStatus(202);
					break;
				case 0x0C:
					// 熄火未关门上报
					leopaardStatusInfo.addStatus(203);
					break;
				case 0x0D:
					// 熄火未上锁上报
					leopaardStatusInfo.addStatus(204);
					break;
				case 0x0E:
					// 点火上报
					leopaardStatusInfo.addStatus(206);
					break;
				case 0x0F:
					// 熄火上报
					leopaardStatusInfo.addStatus(205);
					break;
				case 0x10:
					// 远程启动：异常熄火上报
					leopaardStatusInfo.addStatus(208);
					break;
				case 0x11:
					// 远程启动：定时熄火上报
					leopaardStatusInfo.addStatus(209);
					break;
				case 0x12:
					// 远程启动：退出远程启动模式上报
					leopaardStatusInfo.addStatus(210);
					break;
				case 0x13:
					// 故障灯上报
					leopaardStatusInfo.addStatus(207);
					break;
				case 0x14:
					// UBI信息上报
					//leopaardStatusInfo.addStatus(207);
					break;
				case 0x15:
					// 受控车辆设置结果上报
					//leopaardStatusInfo.addStatus(207);
					break;
				default:
					break;
				}
				break;
			case 0x86:
				// leopaardECUConfig = LeopaardECUConfig.parse(data, position);
				// position += leopaardECUConfig.getDataLength();
				int totalDistance2 = LeopaardParseUtil.getIntFrom3Byte(data, position);
				if (leopaardOBDInfo == null)
					leopaardOBDInfo = new LeopaardOBDInfo();
				leopaardOBDInfo.setTotalDistance(totalDistance2 * 100);
				position += 3;
				break;
			case 0x87:
				if (leopaardOBDInfo == null)
					leopaardOBDInfo = new LeopaardOBDInfo();
				leopaardOBDInfo = LeopaardOBDInfo.parse(leopaardOBDInfo, data, position);
				position += leopaardOBDInfo.getOdbDataLen();
				break;
			case 0x88:
				if (leopaardUBIInfo == null)
					leopaardUBIInfo = new LeopaardUBIInfo();
				leopaardUBIInfo = LeopaardUBIInfo.parse(data, position);
				position += leopaardUBIInfo.getUbiDataLen();
				break;
			case 0x89:
				if (leopaardUBIStatus == null)
					leopaardUBIStatus = new LeopaardUBIStatus();
				leopaardUBIStatus = LeopaardUBIStatus.parse(data, position);
				position += leopaardUBIStatus.DATA_LENGTH;
				break;
			case 0x8A://受控车辆配置结果
				position+=4;
				break;
			default:
				break;
			}
		}
		leopaardGPSInfo.setBatteryVoltageInfo(batteryVoltageInfo);
		leopaardGPSInfo.setBatteryTemperatureInfo(batteryTemperatureInfo);
		leopaardGPSInfo.setLeopaardOBDInfo(leopaardOBDInfo);
		leopaardGPSInfo.setBatteryExtremumInfo(batteryExtremumInfo);
		leopaardGPSInfo.setLeopaardStatusInfo(leopaardStatusInfo);
		leopaardGPSInfo.setLeopaardFaultInfo(leopaardFaultInfo);
		leopaardGPSInfo.setLeopaardFaultLightInfo(leopaardFaultLightInfo);
		leopaardGPSInfo.setLeopaardUBIInfo(leopaardUBIInfo);
		leopaardGPSInfo.setLeopaardUBIStatus(leopaardUBIStatus);
		return leopaardGPSInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LeopaardGPSInfo [gpsTime=");
		builder.append(gpsTime);
		builder.append(", locStatus=");
		builder.append(locStatus);
		builder.append(", isLoc=");
		builder.append(isLoc);
		builder.append(", lng=");
		builder.append(lng);
		builder.append(", lat=");
		builder.append(lat);
		builder.append(", speed=");
		builder.append(speed);
		builder.append(", course=");
		builder.append(course);
		builder.append(", height=");
		builder.append(height);
		builder.append(", positionInfoDataLen=");
		builder.append(positionInfoDataLen);
		builder.append(", leopaardOBDInfo=");
		builder.append(leopaardOBDInfo);
		builder.append(", batteryVoltageInfo=");
		builder.append(batteryVoltageInfo);
		builder.append(", batteryTemperatureInfo=");
		builder.append(batteryTemperatureInfo);
		builder.append(", batteryExtremumInfo=");
		builder.append(batteryExtremumInfo);
		builder.append(", leopaardStatusInfo=");
		builder.append(leopaardStatusInfo);
		builder.append(", leopaardFaultInfo=");
		builder.append(leopaardFaultInfo);
		builder.append(", leopaardFaultLightInfo=");
		builder.append(leopaardFaultLightInfo);
		builder.append(", leopaardUBIInfo=");
		builder.append(leopaardUBIInfo);
		builder.append(", leopaardUBIStatus=");
		builder.append(leopaardUBIStatus);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		String s = "2e2e02fe898601166271001741860000060036110109110b1285010401110109110b1106cfbcf4015a1eef000000000000000080000000000081000000000000000000000000000015aa";

		s = "2e2e02fe8986011662710017422800000d00ed11010d1000308514040111020710003006cfbce3015a1ee0000000050000000088000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000089000004000004c0";
		try {
			LeopaardPackage leopaardPackage = LeopaardPackage.parse(HexUtil.hexToBytes(s));
			System.out.println(leopaardPackage);
			LeopaardGPSInfo gps = LeopaardGPSInfo.parse(leopaardPackage.getDataUnit(), 0);

			System.out.println(gps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
