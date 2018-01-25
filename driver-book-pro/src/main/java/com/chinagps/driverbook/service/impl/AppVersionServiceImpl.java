package com.chinagps.driverbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.AppVersionMapper;
import com.chinagps.driverbook.pojo.AppVersion;
import com.chinagps.driverbook.service.IAppVersionService;

@Service
@Scope("prototype")
public class AppVersionServiceImpl extends BaseServiceImpl<AppVersion> implements IAppVersionService {

	@Autowired
	@Qualifier("appVersionMapper")
	private AppVersionMapper appVersionMapper;
	
	public AppVersion latest(Integer origin) throws Exception {
		return appVersionMapper.findLatest(origin);
	}

}
