package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.List;

public class BatteryUnitInfo {
	private int unitSn; // 包序号
	private int batteryType; // 电池类型: 铅酸电池:0x01, 镍氢电池:0x02, 磷酸铁锂电池:0x03,
								// 锰酸锂电池:0x04,
								// 钴酸锂电池:0x05, 三元材料电池:0x06, 聚合物锂离子电池:0x07,
								// 其他电池:-1
	private String manufacturer; // 生产厂商代码
	private long manufacturDate; // 制造日期(从1970-1-1 0:0:0开始的毫秒数), 为了提高处理速度,不用字符串
	private int nominalCapacity; // 额定能量（单位：0.1度,千卡时）
	private int ratedVoltage; // 额定电压（单位：0.1V)

	public int getUnitSn() {
		return unitSn;
	}

	public void setUnitSn(int unitSn) {
		this.unitSn = unitSn;
	}

	public int getBatteryType() {
		return batteryType;
	}

	public void setBatteryType(int batteryType) {
		this.batteryType = batteryType;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public long getManufacturDate() {
		return manufacturDate;
	}

	public void setManufacturDate(long manufacturDate) {
		this.manufacturDate = manufacturDate;
	}

	public int getNominalCapacity() {
		return nominalCapacity;
	}

	public void setNominalCapacity(int nominalCapacity) {
		this.nominalCapacity = nominalCapacity;
	}

	public int getRatedVoltage() {
		return ratedVoltage;
	}

	public void setRatedVoltage(int ratedVoltage) {
		this.ratedVoltage = ratedVoltage;
	}
	
	public static List<BatteryUnitInfo> parse(byte[] data,int offset,int batteryNum){
		
		return null;
	} 

}
