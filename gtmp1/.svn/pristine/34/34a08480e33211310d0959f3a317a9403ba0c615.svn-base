package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.ProjectInfoPOJO;
import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UserInfoPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * 
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IVehicleService
 * @Description:机械操作
 * @author:肖克
 * @date:Dec 14, 2012 11:59:59 AM
 *
 */
public interface UserInfoService{
	
	/**
	 * 
	 * @Title:addInfo
	 * @Description:记录问题
	 * @param UserInfoPOJO
	 * @return
	 * @throws
	 */
	public String addInfo(UserInfoPOJO userInfo) throws Exception;
	
	
	/**
	 * 
	 * @Title:del
	 * @Description:删除问题
	 * @param UserInfoPOJO
	 * @return
	 * @throws
	 */
	public String del(UserInfoPOJO userInfo) throws Exception;
	
	/**
	 * 
	 * @Title:edit
	 * @Description:回复问题
	 * @param UserInfoPOJO
	 * @return
	 * @throws
	 */
	public String editAnswer(UserInfoPOJO userInfo) throws Exception;
	
	
	
	/**
	 * 
	 * @Title:selectQuestion
	 * @Description:查询问题记录
	 * @param UserInfoPOJO
	 * @param HashMap<String, Object>
	 * @return
	 * @throws
	 */
	public HashMap<String, Object> selectQuestion(UserInfoPOJO userInfo,PageSelect pageSelect) throws Exception;
	
	/**
	 * 
	 * @Title:getById
	 * @Description:查询具体问题记录
	 * @param UserInfoPOJO
	 * @param UserInfoPOJO
	 * @return
	 * @throws
	 */
	public UserInfoPOJO getById(UserInfoPOJO userInfo) throws Exception;
	
	
	
}