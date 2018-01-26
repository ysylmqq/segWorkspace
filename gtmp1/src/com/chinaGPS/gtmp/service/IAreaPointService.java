package com.chinaGPS.gtmp.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chinaGPS.gtmp.entity.AreaPointPOJO;
import com.chinaGPS.gtmp.entity.AreaPointVehiclePOJO;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IAreaPointService
 * @Description:库存区域接口
 * @author:zfy
 * @date:2013-7-12 下午05:31:54
 */
public interface IAreaPointService extends BaseService<AreaPointPOJO> {
	/**
	　　* 函 数 名 :checkAreaPointName
	　　* 功能描述：判断区域名称是否存在
	　　* 输入参数:
	　　* @param 
	　　* @return int
	　　* @throws 无异常处理　　
	　　* 创 建 人:周峰炎
	　　* 日 期:2013-7-16
	　　* 修 改 人:
	　　* 修 改 日 期:
	　　* 修 改 原 因:
	 */
	public int checkAreaPointName(AreaPointPOJO areaPointPOJO) throws Exception;
	
	public String getSimNo(Long vid)throws Exception;
	public void saveAreaVehicles(AreaPointVehiclePOJO areaPointVehiclePOJO) throws Exception;
	public void relieveAreaVehicle(AreaPointVehiclePOJO areaPointVehiclePOJO) throws Exception;
	public int getAreaVehiclesCountAll(Map<String, Serializable> map)throws Exception;
	public List<AreaPointVehiclePOJO> getAreaVehicles(Map<String, Serializable> map,RowBounds rowBounds)throws Exception;
}