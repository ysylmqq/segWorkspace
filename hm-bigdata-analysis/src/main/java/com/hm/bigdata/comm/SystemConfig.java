package com.hm.bigdata.comm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("SystemConfig")
public class SystemConfig {

	@Value("${alarm.falutType}")
    private String alarmfaultType;

	public String getAlarmfaultType() {
		return alarmfaultType;
	}

	public void setAlarmfaultType(String alarmfaultType) {
		this.alarmfaultType = alarmfaultType;
	}
}