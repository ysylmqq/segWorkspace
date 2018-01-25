package com.chinagps.driverbook.dao;

import java.util.List;
import java.util.Map;

import com.chinagps.driverbook.pojo.AppBind;

public interface AppBindMapper extends BaseSqlMapper<AppBind> {

	public AppBind findForICE(String callLetter);
	
	public List<AppBind> findByCallLetter(String callLetter);
	
	public List<AppBind> findByCustomerIdsAndOrigin(Map<String, Object> params);
	
}
