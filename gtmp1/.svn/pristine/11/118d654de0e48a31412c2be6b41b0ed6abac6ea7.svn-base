package com.chinaGPS.gtmp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.VehicleTypePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.VehicleTypeMapper;
import com.chinaGPS.gtmp.service.IVehicleTypeService;
@Service
public class VehicleTypeServiceImpl extends BaseServiceImpl<VehicleTypePOJO> implements IVehicleTypeService{
    @Resource
    private VehicleTypeMapper<VehicleTypePOJO> vehicleTypeMapper;

	@Override
	public List<VehicleTypePOJO> getList(VehicleTypePOJO vehicleTypePOJO) throws Exception {
		return vehicleTypeMapper.getList(vehicleTypePOJO);
	}
	
	@Override
	protected BaseSqlMapper<VehicleTypePOJO> getMapper() {
		return this.vehicleTypeMapper;
	}
	
}