package com.chinagps.driverbook.dao;

import com.chinagps.driverbook.pojo.OilPrice;

public interface OilPriceMapper extends BaseSqlMapper<OilPrice> {
	
	public OilPrice findByProvince(String province);
	
}
