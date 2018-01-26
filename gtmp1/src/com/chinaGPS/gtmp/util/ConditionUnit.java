package com.chinaGPS.gtmp.util;
/**
 * 工况单位枚举类
 * @author zfy
 */
public enum ConditionUnit {
	TEMPERATURE("℃"),//温度单位
	PRESSURE("MPa"),//压力
	PERCENT("%"),//油位
	ROTATE_SPEED("r/min"),//转速
	VOLTAGE_PRESSURE("V"),//电压
	PRESSURE2("MPa"),//挖掘机前泵主压压力
	CURRENT("mA"),//电流	
	DEGREE("度"),//度	
	SPEED("km/h");//速度
	
	private final String value;
	
	private ConditionUnit(String value){
		this.value=value;
	}
	public String getValue() {
	     return value;
	}
	
	public static void main(String[] args) {
		System.out.println(ConditionUnit.CURRENT.getValue());
	}
	
}
