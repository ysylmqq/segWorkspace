package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 动力蓄电池每包温度信息
 */
public class BatteryUnitTemperatureInfo {
	private int unitSn = 1; // 包序号
	private int temperatureProbeNum; // 该包动力蓄电池温度探针总数
	private List<Integer> temperatures = new ArrayList<Integer>(); // 每包温度探针的温度列表（探针数不一定等于单体电池数）（单位：十分之一摄氏度）

	public int getUnitSn() {
		return unitSn;
	}

	public void setUnitSn(int unitSn) {
		this.unitSn = unitSn;
	}

	public int getTemperatureProbeNum() {
		return temperatureProbeNum;
	}

	public void setTemperatureProbeNum(int temperatureProbeNum) {
		this.temperatureProbeNum = temperatureProbeNum;
	}

	public List<Integer> getTemperatures() {
		return temperatures;
	}

	public void setTemperatures(List<Integer> temperatures) {
		this.temperatures = temperatures;
	}

}
