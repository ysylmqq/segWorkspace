package com.chinaGPS.gtmp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.ServiceStationPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.ServiceStationMapper;
import com.chinaGPS.gtmp.service.IServiceStationService;

@Service @Scope("prototype")
public class ServiceStationServiceImpl extends BaseServiceImpl<ServiceStationPOJO> implements IServiceStationService {
	@Autowired
	private ServiceStationMapper serviceStationMapper;
	
	@Override
	protected BaseSqlMapper<ServiceStationPOJO> getMapper() {
		return this.serviceStationMapper;
	}

}
