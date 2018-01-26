package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *动力蓄电池每包电压信息
 *
 */
public class BatteryUnitVoltageInfo {
	private int unitSn; // 包序号
	private int batteryNum;//该包电池总数
	private List<Integer> batteryVoltages = new ArrayList<Integer>(); // 单体蓄电池电压列表

	public int getUnitSn() {
		return unitSn;
	}

	public void setUnitSn(int unitSn) {
		this.unitSn = unitSn;
	}

	public int getBatteryNum() {
		return batteryNum;
	}

	public void setBatteryNum(int batteryNum) {
		this.batteryNum = batteryNum;
	}

	public List<Integer> getBatteryVoltages() {
		return batteryVoltages;
	}

	public void setBatteryVoltages(List<Integer> batteryVoltages) {
		this.batteryVoltages = batteryVoltages;
	}

}
