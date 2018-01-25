package com.chinagps.driverbook.dao;

import com.chinagps.driverbook.pojo.Linkman;

public interface LinkmanMapper extends BaseSqlMapper<Linkman> {

	public Linkman findById(Long linkmanId);
	
	public Linkman findByCustomerId(Long customerId);
	
}
