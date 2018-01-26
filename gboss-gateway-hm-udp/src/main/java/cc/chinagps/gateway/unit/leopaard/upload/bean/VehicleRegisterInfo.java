package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.leopaard.define.LeopaardDefines;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardTimeFormatUtil;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardParseUtil;

public class VehicleRegisterInfo {
	private long registerTime; // 注册时间(从1970-1-1 0:0:0开始的毫秒数), 为了提高处理速度,不用字符串
	private String callLetter; // 车辆呼号
	private String plateNo; // 车牌号
	private String unitNo; // 车载终端编号
	private String vin; // 车架号
	private String engineNo; // 发动机号
	private List<BatteryUnitInfo> batterys = new ArrayList<BatteryUnitInfo>(); // 电池列表

	public long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(long registerTime) {
		this.registerTime = registerTime;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public List<BatteryUnitInfo> getBatterys() {
		return batterys;
	}

	public void setBatterys(List<BatteryUnitInfo> batterys) {
		this.batterys = batterys;
	}

	public static VehicleRegisterInfo parse(byte[] data, int offset) throws Exception {
		VehicleRegisterInfo vehicleRegisterInfo = new VehicleRegisterInfo();
		int position = offset;

		String str_time = "20" + Util.bcd2str(data, position, 6);
		position += 6;
		Date registerTime = LeopaardTimeFormatUtil.parseGMT0(str_time);
		vehicleRegisterInfo.setRegisterTime(registerTime.getTime());

		position += 2;// 注册流水号跳过
		String plateNo = LeopaardParseUtil.getString(data, position, 8, LeopaardDefines.CHARSET);
		vehicleRegisterInfo.setPlateNo(plateNo);
		position += 8;
		String unitNo = LeopaardParseUtil.getString(data, position, 12, LeopaardDefines.CHARSET);
		vehicleRegisterInfo.setUnitNo(unitNo);
		position += 12;
		byte batteryPackageNum = data[position];
		position += 1;
		for (int i = 0; i < batteryPackageNum; i++) {
			BatteryUnitInfo batteryUnitInfo = new BatteryUnitInfo();
			byte packageNo = data[position];
			position += 1;
			batteryUnitInfo.setUnitSn(packageNo);
			String manufacturer = LeopaardParseUtil.getString(data, position, 4, LeopaardDefines.CHARSET);
			batteryUnitInfo.setManufacturer(manufacturer);
			position += 4;
			byte batteryType = data[position];
			batteryUnitInfo.setBatteryType(batteryType);
			position += 1;
			short nominalCapacity = Util.getShort(data, position);
			batteryUnitInfo.setNominalCapacity(nominalCapacity);
			position += 2;
			short ratedVoltage = Util.getShort(data, position);
			batteryUnitInfo.setRatedVoltage(ratedVoltage);
			position += 2;
			String manufacturDateStr = "20" + Util.bcd2str(data, position, 3) + "000000";
			Date manufacturDate = LeopaardTimeFormatUtil.parseGMT0(manufacturDateStr);
			batteryUnitInfo.setManufacturDate(manufacturDate.getTime());
			position += 3;
			position += 2;// 流水号跳过
			position += 5;// 预留字节跳过
			vehicleRegisterInfo.getBatterys().add(batteryUnitInfo);
		}

		return vehicleRegisterInfo;
	}

}
