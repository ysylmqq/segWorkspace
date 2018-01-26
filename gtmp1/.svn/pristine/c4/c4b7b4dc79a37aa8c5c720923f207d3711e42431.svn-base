package com.chinaGPS.gtmp.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
@Component
public interface MaintainMapper extends BaseSqlMapper {
	public List<HashMap<String, Object>> getVehicleInfo() throws Exception;
    
    public List<HashMap<String, Object>> getMaintainPeriod() throws Exception;
    
    public List<HashMap<String, Object>> getPushBind(HashMap<String, Object> map) throws Exception;
    
    public int countPushLogByUserId(Map map);
    public void insertPushLog(Map map);
    
}