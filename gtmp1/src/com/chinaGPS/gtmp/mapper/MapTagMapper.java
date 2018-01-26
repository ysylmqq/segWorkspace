package com.chinaGPS.gtmp.mapper;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.MapTagPOJO;

/**
 * @Package:com.chinaGPS.gtmp.mapper
 * @ClassName:MapTagMapper
 * @Description:
 * @author:zfy
 * @date:2013-4-17 下午05:31:54
 */
@Component
public interface MapTagMapper<T extends MapTagPOJO> extends BaseSqlMapper<T> {

}

