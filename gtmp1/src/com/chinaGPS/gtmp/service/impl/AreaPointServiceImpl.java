package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.AreaPointPOJO;
import com.chinaGPS.gtmp.entity.AreaPointVehiclePOJO;
import com.chinaGPS.gtmp.mapper.AreaPointMapper;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.service.IAreaPointService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:AreaPointServiceImpl
 * @Description:库存区域业务层实现类
 * @author:zfy
 * @date:2013-7-15 上午10:31:54
 */
@Service
public class AreaPointServiceImpl extends BaseServiceImpl<AreaPointPOJO> implements IAreaPointService{
    @Resource
    private AreaPointMapper<AreaPointPOJO> areaPointMapper;
    
    @Override
    protected BaseSqlMapper<AreaPointPOJO> getMapper() {
		return this.areaPointMapper;
    }
    
	@Override
	public int checkAreaPointName(AreaPointPOJO areaPointPOJO) throws Exception {
		return areaPointMapper.checkAreaPointName(areaPointPOJO);
	}

	@Override
	public void saveAreaVehicles(AreaPointVehiclePOJO areaPointVehiclePOJO)
			throws Exception {
			 areaPointMapper.saveAreaVehicles(areaPointVehiclePOJO);
	}

	@Override
	public String getSimNo(Long vid)throws Exception {
		return areaPointMapper.getSimNo(vid);
	}


	@Override
	public int getAreaVehiclesCountAll(Map<String, Serializable> map)
			throws Exception {
		return areaPointMapper.getAreaVehiclesCountAll(map);
	}

	@Override
	public List<AreaPointVehiclePOJO> getAreaVehicles(
			Map<String, Serializable> map, RowBounds rowBounds)
			throws Exception {
		return areaPointMapper.getAreaVehicles(map, rowBounds);
	}

	@Override
	public void relieveAreaVehicle(AreaPointVehiclePOJO areaPointVehiclePOJO)
			throws Exception {
		areaPointMapper.relieveAreaVehicle(areaPointVehiclePOJO);		
	}

}