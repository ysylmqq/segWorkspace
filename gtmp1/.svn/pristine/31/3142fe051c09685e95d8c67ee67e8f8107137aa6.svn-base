package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.AvgWorkTime;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.entity.VehicleUnitPOJO;
import com.chinaGPS.gtmp.entity.VehicleWorkPOJO;
@Component
public interface StatisticsQueryMapper<T extends VehicleWorkPOJO> extends BaseSqlMapper<T> {
    /**
     * @Title:getUnWorkByPage
     * @Description:获得未工作机械
     * @param map
     * @param rowBounds
     * @return
     * @throws Exception
     * @throws
     */
    public List<VehicleWorkPOJO> getUnWorkByPage(Map<String,Serializable> map,RowBounds rowBounds) throws Exception;
    /**
     * @Title:countUnWorkAll
     * @Description:获得未工作机械数量
     * @param map
     * @return
     * @throws Exception
     * @throws
     */
    public int countUnWorkAll(Map<String, Serializable> map) throws Exception;
    /**
     * @Title:getUnUploadByPage
     * @Description:获得未上报机械
     * @param map
     * @param rowBounds
     * @return
     * @throws Exception
     * @throws
     */
    public List<VehicleWorkPOJO> getUnUploadByPage(Map<String,Serializable> map,RowBounds rowBounds) throws Exception;
    /**
     * @Title:countUnUploadAll
     * @Description:获得未上报机械数量
     * @param map
     * @return
     * @throws Exception
     * @throws
     */
    public int countUnUploadAll(Map<String, Serializable> map) throws Exception;
    
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
     * @Description:工作时间段统计详细信息
     * @param map
     * @param rowBounds
     * @return
     * @throws Exception
     * @throws
     */
    public List<VehiclePOJO> getTimeQuanDetailPage(Map<String,Serializable> map,RowBounds rowBounds) throws Exception;
    /**
     * @Title:getTimeQuanDetailPage
     * @Description:工作时间段统计详细信息
     * @param map
     * @param rowBounds
     * @return
     * @throws Exception
     * @throws
     */
    public List<VehiclePOJO> getTimeQuanDetailPageVehicle(Map<String,Serializable> map,RowBounds rowBounds) throws Exception;
    /**
     * @Title:countTimeQuanDetaill
     * @Description:获得工作时间段统计详细信息数量
     * @param map
     * @return
     * @throws Exception
     * @throws
     */
    public int countTimeQuanDetail(Map<String, Serializable> map) throws Exception;
    /**
     * @Title:statisticAvgWorkHours
     * @Description:平均工作时间统计
     * @param map
     * @return
     * @throws
     */
    public List<HashMap> statisticAvgWorkHours(Map<String, Serializable> map) throws Exception;
    
    
    /**
     * @Title:statisticWorkedYears
     * @Description:机械使用年数统计
     * @param map
     * @return
     * @throws
     */
    public List<HashMap<String, Object>> statisticWorkedYears(Map<String, Object> map) throws Exception;
   
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
    public List<HashMap> statisticWorkHours(Map<String, Serializable> map) throws Exception;
    /**
     * @Title:getTimeQuanDetailPage
     * @Description:使用年限统计详细信息
     * @param map
     * @param rowBounds
     * @return
     * @throws Exception
     * @throws
     */
    public List<VehiclePOJO> getWorkedYearsDetailPage(Map<String,Serializable> map,RowBounds rowBounds) throws Exception;
    /**
     * @Title:countWorkedYearsDetail
     * @Description:获得使用年限统计详细信息数量
     * @param map
     * @return
     * @throws Exception
     * @throws
     */
    public int countWorkedYearsDetail(Map<String, Serializable> map) throws Exception;
    /**
     * @Title:statisticDistribute4Export
     * @Description:机械分布统计 导出
     * @param map
     * @return
     * @throws Exception
     * @throws
     */
    public List<VehicleWorkPOJO> statisticDistribute4Export(Map<String, Serializable> map) throws Exception;
    
    /**
     * @Title:statisticDistribute4Export
     * @Description:机械年工作时间统计
     * @param map
     * @return
     * @author zhouwei
     * @throws Exception
     * @throws
     */
    public List<VehicleWorkPOJO> statisticWorkHoursYear(Map map) throws Exception;
 
}