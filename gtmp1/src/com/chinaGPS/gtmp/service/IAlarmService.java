package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.AlarmPOJO;
import com.chinaGPS.gtmp.entity.AlarmTypePOJO;
import com.chinaGPS.gtmp.entity.DynamicMalfunctionPOJO;
import com.chinaGPS.gtmp.entity.UserAlarmTypesPOJO;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IHistoryService
 * @Description:警情Service
 * @author:zfy
 * @date:2013-4-9 上午10:15:43
 */
public interface IAlarmService extends BaseService<AlarmPOJO> {
    public int editAlarms(List<String> idList) throws Exception;
    public List<Object> statisticAlarm(HashMap map,HashMap propertyMap) throws Exception;
    public List<DynamicMalfunctionPOJO> statisticAlarmToPOJO(HashMap map,HashMap propertyMap) throws Exception;
    public List<AlarmTypePOJO> getAllAlarmType(HashMap map) throws Exception;
	    /**
	  　　* 函 数 名 :getUserAlarmTypes
	  　　* 功能描述：获得用户自己设置要查看的哪些警情类型
	  　　* 输入参数:
	  　　* @param 
	  　　* @return List<UserAlarmTypesPOJO>
	  　　* @throws Exception　　
	  　　* 创 建 人:周峰炎
	  　　* 日 期:2013-7-19
	  　　* 修 改 人:
	  　　* 修 改 日 期:
	  　　* 修 改 原 因:
	   */
    public List<UserAlarmTypesPOJO> getUserAlarmTypes(Long userId) throws Exception;
  
	  /**
	  　　* 函 数 名 :insertUserAlarmTypes
	  　　* 功能描述：添加用户警情类型设置信息
	  　　* 输入参数:
	  　　* @param 
	  　　* @return int
	  　　* @throws Exception　
	  　　* 创 建 人:周峰炎
	  　　* 日 期:2013-7-19
	  　　* 修 改 人:
	  　　* 修 改 日 期:
	  　　* 修 改 原 因:
	   */
    public int insertUserAlarmTypes(UserAlarmTypesPOJO userAlarmTypesPOJO) throws Exception;
  
	  /**
	　　* 函 数 名 :deleteUserAlarmTypes
	　　* 功能描述：删除用户警情类型设置信息
	　　* 输入参数:
	　　* @param 
	　　* @return int
	　　* @throws  Exception　
	　　* 创 建 人:周峰炎
	　　* 日 期:2013-7-19
	　　* 修 改 人:
	　　* 修 改 日 期:
	　　* 修 改 原 因:
	 */
    public int deleteUserAlarmTypes(Long userId) throws Exception;
    
    /**
     * 修改警情过滤、综合查询关注项
     * @param userAlarmTypesPOJO
     * @return
     * @throws Exception
     */
    public boolean setUserAlarmTypes(UserAlarmTypesPOJO userAlarmTypesPOJO) throws Exception;
    
}