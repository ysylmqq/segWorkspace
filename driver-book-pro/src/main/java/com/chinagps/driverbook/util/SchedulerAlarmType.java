package com.chinagps.driverbook.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.listener.AppNoticeListener;
import com.chinagps.driverbook.service.IAlarmControlService;
import com.chinagps.driverbook.service.IUnitService;


@Service
public class SchedulerAlarmType{
	
	@Autowired
	public IUnitService iUnitService;
	
	@Autowired
	private IAlarmControlService iAlarmService;
	
	public void  refreshAlarmDate(){
		AppNoticeListener.callLetterList = iUnitService.getUnitCallLetterSubcoNo("201");
		AppNoticeListener.alarmControlList = iAlarmService.getAlarmControlList();
	}
}
