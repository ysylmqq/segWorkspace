package com.chinaGPS.gtmp.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chinaGPS.gtmp.entity.CompositeQueryConditionPOJO;
import com.chinaGPS.gtmp.entity.DealerAreaPOJO;
import com.chinaGPS.gtmp.entity.DealerVehiclePOJO;
import com.chinaGPS.gtmp.entity.VehicleSalePOJO;
import com.chinaGPS.gtmp.entity.VehicleUnitPOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IDealerAreaService
 * @Description:经销商片区接口
 * @author:zfy
 * @date:Dec 11, 2012 2:25:45 PM
 */
public interface OwnerService extends BaseService<DealerAreaPOJO> {
    /**
     * @Title:getVehilclesInDealer
     * @Description:运营里搜索机械信息
     * @param map
     * @return
     * @throws
     */
    public List<DealerVehiclePOJO> getVehilcles(HashMap map) throws Exception;
    /**
	　　* 函 数 名 :getVehilclesCount
	　　* 功能描述：运营里搜索机械信息的个数
	　　* 输入参数:
	　　* @param 
	　　* @return int
	　　* @throws 无异常处理　　
	　　* 创 建 人:周峰炎
	　　* 日 期:2013-7-4
	　　* 修 改 人:
	　　* 修 改 日 期:
	　　* 修 改 原 因:
	 */
	public int getVehilclesCount(HashMap map) throws Exception;
    /**
     * @Title:getVehilclesInDealer
     * @Description:根据经销商查询机械
     * @param map
     * @return VehicleUnitPOJO
     * @throws
     */
    public List<VehicleUnitPOJO> getVehilclesInDealer(HashMap map) throws Exception;
    
    /**
     * @Title:queryComposite
     * @Description:综合查询
     * @param compositeQueryConditionPOJO
     * @param select
     * @return
     * @throws
     */
    public HashMap queryComposite(CompositeQueryConditionPOJO compositeQueryConditionPOJO,PageSelect select) throws Exception;
    
    /**
     * @Title:queryHistoryCondition
     * @Description:历史工况查询
     * @param 
     * @param select
     * @return
     * @throws
     */
    public HashMap queryHistoryCondition(Map<String, Serializable> map,PageSelect select) throws Exception;
    /**
     * @Title:getVehicleSaleByPage
     * @Description:机械销售查询
     * @param 
     * @param select
     * @return
     * @throws
     */
    public HashMap getVehicleSaleByPage(Map<String, Serializable> map,PageSelect select) throws Exception;
    
    
    /**
     * @Title:getVehicle4saler
     * @Description:已销售机械
     * @param map
     * @return List
     * @throws
     */
    public List<Map> getVehicle4sale(Map<String, Object> map) throws Exception;
    /**
     * @Title:getOwner4sale
     * @Description:机主信息查询
     * @param
     * @return
     * @throws
     */
    public List<Map> getOwner4sale(Map<String, Object> map) throws Exception;
    
    /**
     * @param entity
     * @return 添加机械经销商机主绑定关系信息
     * @throws DataAccessException
     */
	public int addDealerVechicle(VehicleSalePOJO entity) throws Exception;
	
	/**
     * @param entity
     * @return 修改机械经销商机主绑定关系信息
     * @throws DataAccessException
     */
	public int editDealerVechicle(VehicleSalePOJO entity) throws Exception;

 
	/**
     * @param entity
     * @return 修改销售信息
     * @throws DataAccessException
     */
	public int editVechicle(VehicleSalePOJO entity) throws Exception;

 
}