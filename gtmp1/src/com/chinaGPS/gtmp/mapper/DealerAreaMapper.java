package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.CompositePOJO;
import com.chinaGPS.gtmp.entity.ConditionsPOJO;
import com.chinaGPS.gtmp.entity.DealerAreaPOJO;
import com.chinaGPS.gtmp.entity.DealerVehiclePOJO;
import com.chinaGPS.gtmp.entity.VehicleSalePOJO;
import com.chinaGPS.gtmp.entity.VehicleUnitPOJO;

@Component
public interface DealerAreaMapper<T extends DealerAreaPOJO> extends BaseSqlMapper<T> {
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
     * @Title:VehicleUnitPOJO
     * @Description:根据经销商查询机械
     * @param map
     * @return
     * @throws
     */
    public List<VehicleUnitPOJO> getVehilclesInDealer(HashMap map) throws Exception;
    
    /**
     * @Title:queryComposite
     * @Description:综合查询
     * @param compositeQueryConditionPOJO
     * @return
     * @throws
     */
    public List<CompositePOJO> queryComposite(Map<String, Serializable> map,RowBounds rowBounds) throws Exception;
    
    /**
     * @Title:countComposite
     * @Description:综合查询结果记录数
     * @param compositeQueryConditionPOJO
     * @return
     * @throws
     */
    public int countComposite(Map<String, Serializable> map) throws Exception;
    
    /**
     * @Title:queryComposite
     * @Description:历史工况
     * @param 
     * @return
     * @throws
     */
    public List<ConditionsPOJO> queryHistoryCondition(Map<String, Serializable> map,RowBounds rowBounds) throws Exception;
    
    /**
     * @Title:countComposite
     * @Description:历史工况记录数
     * @param compositeQueryConditionPOJO
     * @return
     * @throws
     */
    public int countHistoryCondition(Map<String, Serializable> map) throws Exception;
    
    
    /**
     * @Title:getVehicleSaleByPage
     * @Description:机械销售信息查询
     * @param
     * @return
     * @throws
     */
    public List<VehicleSalePOJO> getVehicleSaleByPage(Map<String, Serializable> map,RowBounds rowBounds) throws Exception;
    
    /**
     * @Title:countVehicleSale
     * @Description:机械销售信息查询结果记录数
     * @param compositeQueryConditionPOJO
     * @return
     * @throws
     */
    public int countVehicleSale(Map<String, Serializable> map) throws Exception;
    
    /**
     * @Title:getVehicle4sale
     * @Description:机械已销售信息查询
     * @param
     * @return
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
     * @return 修改机械销售信息
     * @throws DataAccessException
     */
	public int editVechicle(VehicleSalePOJO entity) throws Exception;
	
	public int isDataByDealer(VehicleSalePOJO entity) throws Exception;
	
	/**
	 * 
	 * @param entity
	 * @return 导入工况信息
	 * @throws Exception
	 */
	public int insertWorkinfo(ConditionsPOJO entity)throws Exception;
}