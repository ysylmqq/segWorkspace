package com.chinaGPS.gtmp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.VehicleModelPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.VehicleModelMapper;
import com.chinaGPS.gtmp.service.IVehicleModelService;

@Service
public class VehicleModelServiceImpl extends BaseServiceImpl<VehicleModelPOJO>  implements IVehicleModelService{
    @Resource
    private VehicleModelMapper<VehicleModelPOJO> vehicleModelMapper;
    
    @Override
    protected BaseSqlMapper<VehicleModelPOJO> getMapper() {
		return this.vehicleModelMapper;
    }
    
	@Override
	public List<VehicleModelPOJO> getList(VehicleModelPOJO vehicleModelPOJO) throws Exception {
		return vehicleModelMapper.getList(vehicleModelPOJO);
	}
	
}