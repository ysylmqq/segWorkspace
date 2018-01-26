package com.chinaGPS.gtmp.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.MapChinaAreaPOJO;


/**
 * @Package:com.chinaGPS.gtmp.mapper
 * @ClassName:MapChinaAreaMapper
 * @Description:行政区域及地图中心点类 包括省、市Mapper
 * @author:zfy
 * @date:2013-7-5 上午10:11:11
 */
@Component
public interface MapChinaAreaMapper<T extends MapChinaAreaPOJO> extends BaseSqlMapper<T> {
	/**
	　　* 函 数 名 :getProvinceList
	　　* 功能描述：获得省
	　　* 输入参数:
	　　* @param 
	　　* @return List<T>
	　　* @throws 无异常处理　　
	　　* 创 建 人:周峰炎
	　　* 日 期:2013-7-5
	　　* 修 改 人:
	　　* 修 改 日 期:
	　　* 修 改 原 因:
	 */
	public List<T> getProvinceList(MapChinaAreaPOJO mapChinaAreaPOJO) throws Exception;
	/**
	　　* 函 数 名 :getCityList
	　　* 功能描述：获得市
	　　* 输入参数:
	　　* @param 
	　　* @return List<T>
	　　* @throws 无异常处理　　
	　　* 创 建 人:周峰炎
	　　* 日 期:2013-7-5
	　　* 修 改 人:
	　　* 修 改 日 期:
	　　* 修 改 原 因:
	　　
	 */
	public List<T> getCityList(MapChinaAreaPOJO mapChinaAreaPOJO) throws Exception;
	
	public List<Map<String, String>> getProvinceListForPoint() throws Exception;
	
	public List<Map<String, String>> getCityListForPoint(Map<String, String> params) throws Exception;
}