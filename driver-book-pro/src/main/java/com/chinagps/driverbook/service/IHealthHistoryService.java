package com.chinagps.driverbook.service;

import java.util.Map;

import com.chinagps.driverbook.pojo.HealthHistory;
import com.chinagps.driverbook.pojo.ReturnValue;

public interface IHealthHistoryService extends BaseService<HealthHistory> {

	public ReturnValue healthHistory(Map<String, Object> params, ReturnValue rv) throws Exception;
	
}
