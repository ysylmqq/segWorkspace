package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *动力蓄电池电压信息
 */
public class BatteryVoltageInfo {
	private int batteryNum;//单体蓄电池总数
	private int batteryPackageNum;//动力蓄电池包总数
	private List<BatteryUnitVoltageInfo> batteryUnitVoltageInfos = new ArrayList<BatteryUnitVoltageInfo>();//每包蓄电池电压信息列表 
	
	public int getBatteryNum() {
		return batteryNum;
	}

	public void setBatteryNum(int batteryNum) {
		this.batteryNum = batteryNum;
	}

	public int getBatteryPackageNum() {
		return batteryPackageNum;
	}

	public void setBatteryPackageNum(int batteryPackageNum) {
		this.batteryPackageNum = batteryPackageNum;
	}

	public List<BatteryUnitVoltageInfo> getBatteryUnitVoltageInfos() {
		return batteryUnitVoltageInfos;
	}

	public void setBatteryUnitVoltageInfos(List<BatteryUnitVoltageInfo> batteryUnitVoltageInfos) {
		this.batteryUnitVoltageInfos = batteryUnitVoltageInfos;
	}

}
