package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.MapPushRelationPOJO;
import com.chinaGPS.gtmp.entity.MessagePOJO;
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
public interface MessageService{
	
	
	
	/**
	 * 
	 * @Title:addProjectInfo
	 * @Description:记录工程信息发布
	 * @param UserInfoPOJO
	 * @return
	 * @throws
	 */
	public String addProjectInfo(MessagePOJO messageInfo) throws Exception;
	
	
	
	/**
	 * 
	 * @Title:addProjectInfo
	 * @Description:记录工程信息发布
	 * @param UserInfoPOJO
	 * @return
	 * @throws
	 */
	public List<MapPushRelationPOJO> selectMessageService() throws Exception;
	
	/**
	 * 
	 * @Title:editProjectInfo
	 * @Description:编辑工程信息
	 * @param ProjectInfoPOJO
	 * @return
	 * @throws
	 */
	public String editProjectInfo(MessagePOJO messageInfo) throws Exception;
	
	/**
	 * 
	 * @Title:selectProjectInfo
	 * @Description:查询工程信息记录
	 * @param ProjectInfoPOJO
	 * @param HashMap<String, Object>
	 * @return
	 * @throws
	 */
	public HashMap<String, Object> selectProjectInfo(MessagePOJO messageInfo,PageSelect pageSelect) throws Exception;
	
	/**
	 * 
	 * @Title:getById
	 * @Description:查询具体工程信息记录
	 * @param ProjectInfoPOJO
	 * @param ProjectInfoPOJO
	 * @return
	 * @throws
	 */
	public MessagePOJO getById(MessagePOJO messageInfo) throws Exception;
	
	/**
	 * 
	 * @Title:del
	 * @Description:删除工程信息
	 * @param UserInfoPOJO
	 * @return
	 * @throws
	 */
	public String del(MessagePOJO messageInfo) throws Exception;
	
}