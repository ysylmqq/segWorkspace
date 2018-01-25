package com.gboss.dao;

import java.util.List;

import com.gboss.pojo.VehicleConf;

public interface VehicleConfDao extends BaseDao{

	public VehicleConf getVehicleConfByCallLetter(String call_letter );
	
	public List<VehicleConf> getVehicleConfs();
	
}
