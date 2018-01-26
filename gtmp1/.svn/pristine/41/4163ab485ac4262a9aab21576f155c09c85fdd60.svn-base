package com.chinaGPS.gtmp.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chinaGPS.gtmp.entity.AvgWorkTime;
import com.chinaGPS.gtmp.entity.DynamicMalfunctionPOJO;
import com.chinaGPS.gtmp.entity.VehicleUnitPOJO;
import com.chinaGPS.gtmp.entity.VehicleWorkPOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IHistoryService
 * @Description:统计查询Service
 * @author:zfy
 * @date:2013-4-9 上午10:15:43
 */
public interface IStatisticQueryService extends BaseService<VehicleWorkPOJO> {
    /**
     * @Title:getUnWorkByPage
     * @Description:获得未工作机械
     * @param vehicleWorkPOJO
     * @param pageSelect
     * @return
     * @throws DataAccessException
     * @throws
     */
    public HashMap<String, Object> getUnWorkByPage(VehicleWorkPOJO vehicleWorkPOJO,PageSelect pageSelect) throws Exception;
    /**
     * @Title:getUnWorkByPage
     * @Description:获得未上报机械
     * @param vehicleWorkPOJO
     * @param pageSelect
     * @return
     * @throws DataAccessException
     * @throws
     */
    public HashMap<String, Object> getUnUploadByPage(VehicleWorkPOJO vehicleWorkPOJO,PageSelect pageSelect) throws Exception;
    /**
     * @Title:statisticTimeQuantum
     * @Description:机械工作时间段统计
     * @param map
     * @return
     * @throws
     */
    //public List<HashMap> statisticTimeQuantum(Map<String, Serializable> map) throws Exception;
    public List<AvgWorkTime> statisticTimeQuantum(Map<String, Serializable> map) throws Exception;
    
    /**
     * @Title:getTimeQuanDetailPage
     * @Description:获得工作时间段统计详细信息
     * @param vehicleWorkPOJO
     * @param pageSelect
     * @return
     * @throws DataAccessException
     * @throws
     */
    public HashMap<String, Object> getTimeQuanDetailPage(VehicleWorkPOJO vehicleWorkPOJO,PageSelect pageSelect) throws Exception;
   
    /**
     * @Title:statisticAvgWorkHours
     * @Description:平均工作时间统计
     * @param map
     * @param propertyMap
     * @return
     * @throws ClassNotFoundException
     * @throws
     */
    public List<Object> statisticAvgWorkHours(HashMap map,HashMap propertyMap) throws Exception;
    
    /**
     * @Title:statisticAvgWorkHoursToPOJO
     * @Description:平均工作时间统计
     * @param map
     * @param propertyMap
     * @return
     * @throws ClassNotFoundException
     * @throws
     */
    public List<DynamicMalfunctionPOJO> statisticAvgWorkHoursToPOJO(HashMap map,HashMap propertyMap) throws Exception;
    
    /**
     * @Title:statisticWorkedYears
     * @Description:机械使用年数统计
     * @param map
     * @return
     * @throws
     */
    public List<Object> statisticWorkedYears(Map<String, Object> conditionMap, Map<String, Object> propertyMap) throws Exception;
   
    /**
     * @Title:statisticDistribute
     * @Description:机械分布统计
     * @param map
     * @return
     * @throws
     */
    public List<VehicleUnitPOJO> statisticDistribute(Map<String, Serializable> map) throws Exception;
    /**
     * @Title:statisticWorkHours
     * @Description:机械工作时间统计
     * @param map
     * @return
     * @throws
     */
    public  List<Object> statisticWorkHours(HashMap map,HashMap propertyMap) throws Exception;
    
    /**
     * @Title:statisticWorkHours
     * @Description:机械年工作时间统计
     * @param map
     * @return
     * @author zhouwei
     * @throws
     */
    public  List<VehicleWorkPOJO> statisticWorkHoursYear(Map map) throws Exception;
    
    /**
     * @Title:statisticWorkHoursTOPOJO
     * @Description:机械工作时间统计
     * @param map
     * @return
     * @throws
     */
    public  List<DynamicMalfunctionPOJO> statisticWorkHoursToPOJO(HashMap map,HashMap propertyMap) throws Exception;
    
    /**
     * @Title:getWorkedYearsDetailPage
     * @Description:获得使用年数统计详细信息
     * @param vehicleWorkPOJO
     * @param pageSelect
     * @return
     * @throws DataAccessException
     * @throws
     */
    public HashMap<String, Object> getWorkedYearsDetailPage(VehicleWorkPOJO vehicleWorkPOJO,PageSelect pageSelect) throws Exception;
    /**
     * @Title:statisticDistribute4Export
     * @Description:机械分布统计 导出
     * @param map
     * @return
     * @throws DataAccessException
     * @throws
     */
    public List<VehicleWorkPOJO> statisticDistribute4Export(Map<String, Serializable> map) throws Exception;
 
 
}