package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 动力蓄电池温度信息
 */
public class BatteryTemperatureInfo {
	private int temperatureProbeNum;// 动力蓄电池包温度探针总数
	private int batteryPackageNum;// 动力蓄电池包总数
	private List<BatteryUnitTemperatureInfo> batteryUnitTemperatureInfos = new ArrayList<BatteryUnitTemperatureInfo>(); // 每包蓄电池温度信息列表

	public int getTemperatureProbeNum() {
		return temperatureProbeNum;
	}

	public void setTemperatureProbeNum(int temperatureProbeNum) {
		this.temperatureProbeNum = temperatureProbeNum;
	}

	public int getBatteryPackageNum() {
		return batteryPackageNum;
	}

	public void setBatteryPackageNum(int batteryPackageNum) {
		this.batteryPackageNum = batteryPackageNum;
	}

	public List<BatteryUnitTemperatureInfo> getBatteryUnitTemperatureInfos() {
		return batteryUnitTemperatureInfos;
	}

	public void setBatteryUnitTemperatureInfos(List<BatteryUnitTemperatureInfo> batteryUnitTemperatureInfos) {
		this.batteryUnitTemperatureInfos = batteryUnitTemperatureInfos;
	}

}
