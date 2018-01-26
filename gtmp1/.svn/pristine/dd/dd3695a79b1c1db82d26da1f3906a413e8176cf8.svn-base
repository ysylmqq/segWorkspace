package com.chinaGPS.gtmp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.MapTagPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.MapTagMapper;
import com.chinaGPS.gtmp.service.IMapTagService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:MapTagServiceImpl
 * @Description:
 * @author:zfy
 * @date:Dec 11, 2012 2:43:44 PM
 *
 */
@Service
public class MapTagServiceImpl extends BaseServiceImpl<MapTagPOJO> implements IMapTagService{
    @Resource
    private MapTagMapper<MapTagPOJO> mapTagMapper;
    
    @Override
    protected BaseSqlMapper<MapTagPOJO> getMapper() {
		return this.mapTagMapper;
    }

}