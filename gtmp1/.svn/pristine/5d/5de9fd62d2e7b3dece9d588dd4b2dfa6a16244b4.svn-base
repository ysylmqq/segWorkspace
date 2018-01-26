package com.chinaGPS.gtmp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.MapChinaAreaPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.MapChinaAreaMapper;
import com.chinaGPS.gtmp.service.IMapChinaAreaService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:MapChinaAreaServiceImpl
 * @Description:行政区域及地图中心点类 包括省、市实现类
 * @author:zfy
 * @date:2013-7-5 上午10:16:38
 */
@Service
public class MapChinaAreaServiceImpl  extends BaseServiceImpl<MapChinaAreaPOJO> implements IMapChinaAreaService {
	@Resource
	private MapChinaAreaMapper<MapChinaAreaPOJO> mapChinaAreaMapper;
    
	@Override
	public List<MapChinaAreaPOJO> getProvinceList(
			MapChinaAreaPOJO mapChinaAreaPOJO) throws Exception {
		return mapChinaAreaMapper.getProvinceList(mapChinaAreaPOJO);
	}

	@Override
	public List<MapChinaAreaPOJO> getCityList(MapChinaAreaPOJO mapChinaAreaPOJO) throws Exception {
		return mapChinaAreaMapper.getCityList(mapChinaAreaPOJO);
	}

	@Override
	public List<Map<String, String>> getProvinceListForPoint() throws Exception {
		return mapChinaAreaMapper.getProvinceListForPoint();
	}

	@Override
	public List<Map<String, String>> getCityListForPoint(Map<String, String> params) throws Exception {
		return mapChinaAreaMapper.getCityListForPoint(params);
	}
	
	@Override
	protected BaseSqlMapper<MapChinaAreaPOJO> getMapper() {
		return mapChinaAreaMapper;
	}
	
}
