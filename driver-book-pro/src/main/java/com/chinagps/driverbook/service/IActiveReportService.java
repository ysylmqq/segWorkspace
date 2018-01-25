package com.chinagps.driverbook.service;

import java.util.List;
import java.util.Map;

public interface IActiveReportService {
	
	public List<Map<String, Integer>> timesInPastDays(int days, int times) throws Exception;
	
}
