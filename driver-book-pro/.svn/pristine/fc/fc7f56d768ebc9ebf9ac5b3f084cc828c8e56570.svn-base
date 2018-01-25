package com.chinagps.driverbook.service;

import java.util.List;

import com.chinagps.driverbook.pojo.AlarmControl;

public interface IAlarmControlService {
	/**
	 * 根据警情status_id 来获取警情
	 * @param id
	 * @return
	 */
	public AlarmControl getAlarmControlByStatusId(Integer status_id );
	
	/**
	 *alarmControl 更新是 开启还是拦截
	 * @param id
	 */
	public void updateAlarmControl(AlarmControl alarmControl);
	
	public List<AlarmControl> getAlarmControlList();
	
	/**
	 *  开启或关闭 要拦截的警情
	 * @param statusIdList  列如:2,3,4,5
	 * @param isOpen true:开启 , false:关闭
	 */
	public void updateAlarmControlIsOpen(String statusIdList,boolean isOpen);
	
	
}
