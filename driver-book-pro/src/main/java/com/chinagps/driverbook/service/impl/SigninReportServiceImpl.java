package com.chinagps.driverbook.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.SigninReportMapper;
import com.chinagps.driverbook.pojo.BarSeries;
import com.chinagps.driverbook.service.ISigninReportService;

@Service
@Scope("prototype")
public class SigninReportServiceImpl extends BaseServiceImpl<BarSeries> implements ISigninReportService {

	@Autowired
	private SigninReportMapper signinReportMapper;
	
	public List<String> categories(String beginTime, String endTime) throws Exception {
		HashMap<String, String> params = new HashMap<String ,String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		return signinReportMapper.categories(params);
	}

	public List<Integer> loginInfo(String beginTime, String endTime, int deviceType) throws Exception {
		HashMap<String, String> params = new HashMap<String ,String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		params.put("deviceType", String.valueOf(deviceType));
		return signinReportMapper.loginInfo(params);
	}

}
