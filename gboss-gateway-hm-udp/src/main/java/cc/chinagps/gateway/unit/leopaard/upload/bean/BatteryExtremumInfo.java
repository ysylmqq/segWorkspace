package cc.chinagps.gateway.unit.leopaard.upload.bean;

/**
 * 蓄电池极值数据信息
 *
 */
public class BatteryExtremumInfo {
	private int maxVoltage; // 电池单体电压最高值（单位：0.001V)
	private int maxVoltageUnitSn; // 最高电压的电池单体所在包序号
	private int maxVoltageBodySn; // 最高电压的电池单体在包中序号
	private int minVoltage; // 电池单体电压最低值（单位：0.001V)
	private int minVoltageUnitSn; // 最低电压的电池单体所在包序号
	private int minVoltageBodySn; // 最低电压的电池单体在包中序号
	private int maxTemperature; // 电池单体温度最高值（单位：十分之一摄氏度）
	private int maxTemperatureUnitSn; // 最高温度的电池单体所在包序号
	private int maxTemperatureBodySn; // 最高温度的电池单体在包中序号
	private int minTemperature; // 电池单体温度最低值（单位：十分之一摄氏度）
	private int minTemperatureUnitSn; // 最低温度的电池单体所在包序号
	private int minTemperatureBodySn; // 最低温度的电池单体在包中序号

	public int getMaxVoltage() {
		return maxVoltage;
	}

	public void setMaxVoltage(int maxVoltage) {
		this.maxVoltage = maxVoltage;
	}

	public int getMaxVoltageUnitSn() {
		return maxVoltageUnitSn;
	}

	public void setMaxVoltageUnitSn(int maxVoltageUnitSn) {
		this.maxVoltageUnitSn = maxVoltageUnitSn;
	}

	public int getMaxVoltageBodySn() {
		return maxVoltageBodySn;
	}

	public void setMaxVoltageBodySn(int maxVoltageBodySn) {
		this.maxVoltageBodySn = maxVoltageBodySn;
	}

	public int getMinVoltage() {
		return minVoltage;
	}

	public void setMinVoltage(int minVoltage) {
		this.minVoltage = minVoltage;
	}

	public int getMinVoltageUnitSn() {
		return minVoltageUnitSn;
	}

	public void setMinVoltageUnitSn(int minVoltageUnitSn) {
		this.minVoltageUnitSn = minVoltageUnitSn;
	}

	public int getMinVoltageBodySn() {
		return minVoltageBodySn;
	}

	public void setMinVoltageBodySn(int minVoltageBodySn) {
		this.minVoltageBodySn = minVoltageBodySn;
	}

	public int getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(int maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public int getMaxTemperatureUnitSn() {
		return maxTemperatureUnitSn;
	}

	public void setMaxTemperatureUnitSn(int maxTemperatureUnitSn) {
		this.maxTemperatureUnitSn = maxTemperatureUnitSn;
	}

	public int getMaxTemperatureBodySn() {
		return maxTemperatureBodySn;
	}

	public void setMaxTemperatureBodySn(int maxTemperatureBodySn) {
		this.maxTemperatureBodySn = maxTemperatureBodySn;
	}

	public int getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(int minTemperature) {
		this.minTemperature = minTemperature;
	}

	public int getMinTemperatureUnitSn() {
		return minTemperatureUnitSn;
	}

	public void setMinTemperatureUnitSn(int minTemperatureUnitSn) {
		this.minTemperatureUnitSn = minTemperatureUnitSn;
	}

	public int getMinTemperatureBodySn() {
		return minTemperatureBodySn;
	}

	public void setMinTemperatureBodySn(int minTemperatureBodySn) {
		this.minTemperatureBodySn = minTemperatureBodySn;
	}

}
