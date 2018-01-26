package com.chinaGPS.gtmp.mapper;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.VehicleModelPOJO;
@Component
public interface VehicleModelMapper<T extends VehicleModelPOJO> extends BaseSqlMapper<T> {
	
}