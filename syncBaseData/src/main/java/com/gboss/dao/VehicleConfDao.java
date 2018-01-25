package com.gboss.dao;

import com.gboss.pojo.VehicleConf;

public interface VehicleConfDao extends BaseDao{

	public VehicleConf getVehicleConfByCallLetter(String call_letter );
	
}
