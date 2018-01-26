package com.chinaGPS.gtmp.service;

import java.util.List;
import java.util.Map;

import com.chinaGPS.gtmp.entity.MapChinaAreaPOJO;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IMapChinaAreaService
 * @Description:行政区域及地图中心点类 包括省、市Service
 * @author:zfy
 * @date:2013-7-5 上午10:15:43
 */
public interface IMapChinaAreaService extends BaseService<MapChinaAreaPOJO> {
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
	public List<MapChinaAreaPOJO> getProvinceList(MapChinaAreaPOJO mapChinaAreaPOJO) throws Exception;
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
	public List<MapChinaAreaPOJO> getCityList(MapChinaAreaPOJO mapChinaAreaPOJO) throws Exception;
	
	public List<Map<String, String>> getProvinceListForPoint() throws Exception;
	
	public List<Map<String, String>> getCityListForPoint(Map<String, String> params) throws Exception;
	
}