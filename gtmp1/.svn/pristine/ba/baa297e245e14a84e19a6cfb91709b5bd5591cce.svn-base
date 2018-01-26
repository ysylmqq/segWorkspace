package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.AlarmPOJO;
import com.chinaGPS.gtmp.entity.AlarmTypePOJO;
import com.chinaGPS.gtmp.entity.UserAlarmTypesPOJO;
@Component
public interface AlarmMapper<T extends AlarmPOJO> extends BaseSqlMapper<T> {
	public int editAlarms(List<String> idList);
    /**
     * @Title:statisticAlarm
     * @Description:统计警情
     * @param map
     * @return
     * @throws
     */
    public List<HashMap> statisticAlarm(Map<String, Serializable> map)  throws Exception;
	    /**
	  　　* 函 数 名 :getAllAlarmType
	  　　* 功能描述：获得所有的警情类型
	  　　* 输入参数:
	  　　* @param 
	  　　* @return List<AlarmTypePOJO>
	  　　* @throws Exception　　
	  　　* 创 建 人:周峰炎
	  　　* 日 期:2013-7-19
	  　　* 修 改 人:
	  　　* 修 改 日 期:
	  　　* 修 改 原 因:
	   */
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
  	public int updateUserAlarmTypes(UserAlarmTypesPOJO userAlarmTypesPOJO) throws Exception;
  	
  	/**
  	 * 获取用户是否已经设置警情过滤、综合查询关注项
  	 * @param userAlarmTypesPOJO
  	 * @return
  	 * @throws Exception
  	 */
  	public int getUserAlarmTypesCount(UserAlarmTypesPOJO userAlarmTypesPOJO) throws Exception;
}