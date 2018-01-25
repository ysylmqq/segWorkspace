package com.gboss.dao;

import java.util.Map;

public interface AccountDao extends BaseDao {
	
	public Map<String, Object> getAccountInfoByVin(String vin) ;
	
}
