package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.DicMalfunctionCode;
import com.chinaGPS.gtmp.entity.DynamicMalfunctionPOJO;
import com.chinaGPS.gtmp.entity.MalfunctionPOJO;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IMalfunctionService
 * @Description:故障诊断Service
 * @author:zfy
 * @date:2013-5-3 上午10:15:43
 */
public interface IMalfunctionService extends BaseService<MalfunctionPOJO> {
    /**
     * @Title:getDicMalfunctionCode
     * @Description:得到故障码
     * @param malfunctionPOJO
     * @return
     * @throws
     */
    public List<DicMalfunctionCode> getDicMalfunctionCode(DicMalfunctionCode dicMalfunctionCode) throws Exception;
    /**
     * @Title:statisticMalfunction
     * @Description:故障统计
     * @param map
     * @param columnsNameArray
     * @param propertyMap
     * @return
     * @throws ClassNotFoundException
     * @throws
     */
    public List<Object> statisticMalfunction(HashMap map,HashMap propertyMap) throws Exception;
    public List<DynamicMalfunctionPOJO> statisticMalfunctionToPOJO(HashMap map,HashMap propertyMap) throws Exception;
    
}