package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Channel;
import com.gboss.pojo.ChannelGroup;
import com.gboss.pojo.Channelcontacts;

/**
 * @Package:com.gboss.dao
 * @ClassName:ChannelDao
 * @Description:代理商管理数据持久层接口
 * @author:zfy
 * @date:2013-8-26 下午2:38:17
 */
public interface ChannelDao extends BaseDao {
	/**
	 * @Title:deleteContractsByChannelId
	 * @Description:根据代理商id删除联系人
	 * @param channelId
	 * @return
	 * @throws SystemException
	 */
	public int deleteContractsByChannelId(Long channelId) throws SystemException;
	/**
	 * deleteSalesManagerByChannelId
	 * @Description:根据代理商id删除代理商与销售经理的关系
	 * @param channelId
	 * @return
	 * @throws SystemException
	 */
	public int deleteSalesManagerByChannelId(Long channelId) throws SystemException;
	/**
	 * @Title:findChannels
	 * @Description:分页查询代理商
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findChannels(Map<String, Object> conditionMap,String order,boolean isDesc,int pageNo,int pageSize) throws SystemException;
	
	/**
	 * @Title:findChannels
	 * @Description:查询代理商
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findChannels(Map<String, Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:countChannels
	 * @Description:查询代理商记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countChannels(Map<String, Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:updateSalesManagersIsDel
	 * @Description:批量设置原来的销售经理过时
	 * @param channelId
	 * @return
	 * @throws SystemException
	 */
	public int updateSalesManagersIsDel(Long channelId) throws SystemException;
	
	/**
	 * @Title:findContactsByChannelId
	 * @Description:根据代理商id查找联系人
	 * @param channelId
	 * @return
	 * @throws SystemException
	 */
	public List<Channelcontacts> findContactsByChannelId(Long channelId) throws SystemException;
	
	/**
	 * @Title:findChannelManagers
	 * @Description:分页查询代理商和销售经理的关系
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findChannelManagers(Map<String, Object> conditionMap,String order,boolean isDesc,int pageNo,int pageSize) throws SystemException;
	
	/**
	 * @Title:countChannelManagers
	 * @Description:查询代理商和销售经理的关系记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countChannelManagers(Map<String, Object> conditionMap)throws SystemException;
	
	/**
	 * @Title:deleteSalesManagers
	 * @Description:批量删除销售经理与代理商的关系
	 * @param idList
	 * @return
	 * @throws SystemException
	 */
	public int deleteSalesManagers(List<Long> idList) throws SystemException;
	/**
	 * @Title:checkChannelName
	 * @Description:判断代理商名称是否存在
	 * @param channel
	 * @return
	 * @throws SystemException
	 */
	public boolean checkChannelName(Channel channel) throws SystemException;
	/**
	 * @Title:checkChannelGroupName
	 * @Description:判断代理商所属集团的中文、或者英文名称是否存在
	 * @param channelGroup
	 * @param isEn 是否是英文
	 * @return
	 * @throws SystemException
	 */
	public boolean checkChannelGroupName(ChannelGroup channelGroup,boolean isEn) throws SystemException;
	
	/**
	 * @Title:checkChannelGroupIsUsing
	 * @Description:判断代理商所属集团是否在使用
	 * @param channelGroup
	 * @return
	 * @throws SystemException
	 */
	public boolean checkChannelGroupIsUsing(Long groupId) throws SystemException;
	/**
	 * @Title:findChannelGroups
	 * @Description:查询代理商所属集团
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findChannelGroups(Map<String, Object> conditionMap) throws SystemException;

}

