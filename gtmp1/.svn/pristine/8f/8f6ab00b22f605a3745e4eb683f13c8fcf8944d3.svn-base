package com.chinaGPS.gtmp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
*      
* 类名称：IMaintainService   
* 类描述：    强保推送
* 创建人：dengyan  
* 创建时间：2016-2-17 下午3:45:53    
* 修改备注：   
* @version    
*
 */
public interface IMaintainService {
    public List<HashMap<String, Object>> getVehicleInfo() throws Exception;
    public List<HashMap<String, Object>> getMaintainPeriod() throws Exception;
    public List<HashMap<String, Object>> getPushBind(ArrayList<String> userIds) throws Exception;
    public int countPushLogByUserId(Map map) throws Exception;
    public void insertPushLog(Map map) throws Exception;
}