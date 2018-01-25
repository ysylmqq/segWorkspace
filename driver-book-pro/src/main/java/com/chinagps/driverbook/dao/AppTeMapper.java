package com.chinagps.driverbook.dao;

import com.chinagps.driverbook.pojo.AppTe;

public interface AppTeMapper extends BaseSqlMapper<AppTe> {

	public AppTe findByIMEI(String imei);
	
}
