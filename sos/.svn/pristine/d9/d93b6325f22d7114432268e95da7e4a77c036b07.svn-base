package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Channel;
import com.gboss.pojo.ChannelGroup;
import com.gboss.pojo.ChannelSalesmanager;
import com.gboss.pojo.Channelcontacts;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:ChannelService
 * @Description:代理商管理业务层接口
 * @author:zfy
 * @date:2013-8-26 下午2:40:44
 */
public interface ChannelService extends BaseService {
	/**
	 * @Title:addChannelAndContracts
	 * @Description:添加代理商以及代理商与联系人的关系
	 * @param channel
	 * @return
	 * @throws SystemException
	 */
	public int addChannelAndContracts(Channel channel) throws SystemException;
	/**
	 * @Title:updateChannelAndContracts
	 * @Description:修改代理商以及代理商与联系人的关系
	 * @param channel
	 * @return
	 * @throws SystemException
	 */
	public int updateChannelAndContracts(Channel channel) throws SystemException;
	
	/**
	 * @Title:deleteChannelAndcontracts
	 * @Description:根据代理商id删除代理商和联系人
	 * @param channelId
	 * @return
	 * @throws SystemException
	 */
	public int deleteChannelAndcontracts(Long channelId) throws SystemException;
	
	/**
	 * @Title:findChannels
	 * @Description:分页查询代理商
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findChannels(PageSelect<Channel> pageSelect) throws SystemException;
	
	/**
	 * @Title:findChannels
	 * @Description:查询代理商
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findChannels(Map<String, Object> map) throws SystemException;

	/**
	 * @Title:updateSalesManager
	 * @Description:修改代理商与销售经理的关系
	 * @param channelSalesmanager
	 * @return
	 * @throws SystemException
	 */
	public int updateSalesManager(ChannelSalesmanager channelSalesmanager) throws SystemException;
	
	/**
	 * @Title:addSalesManager
	 * @Description:添加代理商与销售经理的关系
	 * @param channelSalesmanager
	 * @return
	 * @throws SystemException
	 */
	public int addSalesManager(ChannelSalesmanager channelSalesmanager) throws SystemException;
	
	/**
	 * @Title:deleteSalesManager
	 * @Description:删除代理商与销售经理的关系
	 * @param channelSalesmanager
	 * @return
	 * @throws SystemException
	 */
	public int deleteSalesManagers(List<Long> ids) throws SystemException;
	
	/**
	 * @Title:getChannelAndContacts
	 * @Description:根据id查找代理商
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String,Object> getChannelAndContacts(Long id)throws SystemException;
	
	/**
	 * @Title:findChannelManagers
	 * @Description:分页查询代理商和销售经理关系 
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findChannelManagers(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	/**
	 * @Title:findAllChannelManagers
	 * @Description:查询所有代理商和销售经理关系 
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllChannelManagers(Map<String, Object> map) throws SystemException;
	/**
	 * @Title:getChannelManager
	 * @Description:得到未过时的销售经理
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> getChannelManager(Map<String, Object> map) throws SystemException;
	/**
	 * @Title:getChannel4Self
	 * @Description:得到为自身营业厅的代理商
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> getChannel4Self(Map<String, Object> map) throws SystemException;

	/**
	 * @Title:saveChannelGroup
	 * @Description:保存代理商所属集团
	 * @param channelGroup
	 * @return
	 * @throws SystemException
	 */
	public int saveChannelGroup(ChannelGroup channelGroup)throws SystemException;
	
	/**
	 * @Title:deleteChannelGroups
	 * @Description:删除代理商所属集团
	 * @param ids
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> deleteChannelGroups(List<Long> ids)throws SystemException;
	/**
	 * @Title:findChannelGroups
	 * @Description:查询代理商所属集团
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findChannelGroups(Map<String, Object> conditionMap) throws SystemException;

}

