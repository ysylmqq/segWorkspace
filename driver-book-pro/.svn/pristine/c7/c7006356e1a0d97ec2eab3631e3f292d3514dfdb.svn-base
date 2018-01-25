package com.chinagps.driverbook.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.ActiveReportMapper;
import com.chinagps.driverbook.service.IActiveReportService;

@Service
@Scope("prototype")
public class ActiveReportServiceImpl implements IActiveReportService {
	
	@Autowired
	private ActiveReportMapper activeReportMapper;

	public List<Map<String, Integer>> timesInPastDays(int days, int times) throws Exception {
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("days", days);
		params.put("times", times);
		return activeReportMapper.timesInPastDays(params);
	}

}
