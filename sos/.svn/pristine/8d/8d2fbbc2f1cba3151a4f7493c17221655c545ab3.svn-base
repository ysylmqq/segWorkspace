package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.ChannelDao;
import com.gboss.pojo.Channel;
import com.gboss.pojo.ChannelGroup;
import com.gboss.pojo.ChannelSalesmanager;
import com.gboss.pojo.Channelcontacts;
import com.gboss.service.ChannelService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.UUIDCreater;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ChannelServiceImpl
 * @Description:代理商管理业务层实现类
 * @author:zfy
 * @date:2013-8-26 下午2:41:23
 */
@Service("channelService")
@Transactional
public class ChannelServiceImpl extends BaseServiceImpl implements
		ChannelService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ChannelServiceImpl.class);
	
	@Autowired  
	@Qualifier("channelDao")
	private ChannelDao channelDao;
	@Override
	public int addChannelAndContracts(Channel channel) throws SystemException {
		int result=1;
		if(channel==null){
			result=-1;
		}else{
			//判断代理商名称是否存在
			if(channelDao.checkChannelName(channel)){
				result=2;
			}else{
				//如果是营业厅，则每个营业厅只能存在一个,自动把之前的删除掉,再添加
				if(SystemConst.DICT_CHANNEL_SELF.longValue()==channel.getDictId().longValue()){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("companyId", channel.getCompanyId());
					map.put("orgId", channel.getOrgId());
					map.put("dictId", channel.getDictId());
					List<HashMap<String, Object>> mapList=channelDao.findChannels(map);
					if(mapList!=null && !mapList.isEmpty()){
						for (HashMap<String, Object> hashMap : mapList) {
							if(hashMap!=null && hashMap.get("id")!=null && StringUtils.isNotBlank(hashMap.get("id").toString())){
								deleteChannelAndcontracts(Long.valueOf(hashMap.get("id").toString()));
							}
						}
					}
				}
				
				Long salesManagerId=channel.getSalesManagerId();//销售经理ID
				String salesManagerName=channel.getSalesManagerName();//销售经理名称
				channelDao.save(channel);
				Long id=channel.getId();
				//保存联系人信息
				List<Channelcontacts> channelcontacts=channel.getChannelcontacts();
				if(channelcontacts!=null&&!channelcontacts.isEmpty()){
					for (Channelcontacts channelcontact : channelcontacts) {
						channelcontact.setChannelId(id);
						channelDao.save(channelcontact);
					}
				}
				//保存销售经理关系
				if(salesManagerId!=null){
					ChannelSalesmanager channelSalesmanager=new ChannelSalesmanager();
					channelSalesmanager.setChannelId(id);
					channelSalesmanager.setManagerId(salesManagerId);
					channelSalesmanager.setManagerName(salesManagerName);
					channelSalesmanager.setIsdel(0);
					addSalesManager2(channelSalesmanager);
				}
			}
		}
		return result;
	}
	@Override
	public int updateChannelAndContracts(Channel channel) throws SystemException {
		int result=1;
		if(channel==null || channel.getId()==null){
			result=-1;
		}else{
			Long channelId=channel.getId();
			if(channelDao.get(Channel.class, channelId)!=null){
				//判断代理商名称是否存在
				if(channelDao.checkChannelName(channel)){
					result=2;
				}else{
					List<Channelcontacts> channelcontacts=channel.getChannelcontacts();
					//如果是营业厅，则每个营业厅只能存在一个,自动把之前的删除掉,再添加
					if(SystemConst.DICT_CHANNEL_SELF.longValue()==channel.getDictId().longValue()){
						Map<String,Object> map=new HashMap<String, Object>();
						map.put("companyId", channel.getCompanyId());
						map.put("orgId", channel.getOrgId());
						map.put("dictId", channel.getDictId());
						map.put("exceptId", channel.getId());
						List<HashMap<String, Object>> mapList=channelDao.findChannels(map);
						if(mapList!=null && !mapList.isEmpty()){
							for (HashMap<String, Object> hashMap : mapList) {
								if(hashMap!=null && hashMap.get("id")!=null && StringUtils.isNotBlank(hashMap.get("id").toString())){
									deleteChannelAndcontracts(Long.valueOf(hashMap.get("id").toString()));
								}
							}
						}
					}
					//保存代理商信息
					Long salesManagerId=channel.getSalesManagerId();//销售经理ID
					String salesManagerName=channel.getSalesManagerName();//销售经理名称
					channelDao.merge(channel);
					//保存联系人信息
					//先删后加
					channelDao.deleteContractsByChannelId(channelId);
					if(channelcontacts!=null&&!channelcontacts.isEmpty()){
						for (Channelcontacts channelcontact : channelcontacts) {
							channelcontact.setChannelId(channelId);
							channelDao.save(channelcontact);
						}
					}
					
					//保存销售经理关系
					if(salesManagerId!=null){
						ChannelSalesmanager channelSalesmanager=new ChannelSalesmanager();
						channelSalesmanager.setChannelId(channelId);
						channelSalesmanager.setManagerId(salesManagerId);
						channelSalesmanager.setManagerName(salesManagerName);
						channelSalesmanager.setIsdel(0);
						addSalesManager2(channelSalesmanager);
					}
				}
			}else{
				result=0;
			}
		}
		return result;
	}
	@Override
	public int deleteChannelAndcontracts(Long channelId)
			throws SystemException {
		int result=1;
		if(channelId==null){
			result=-1;
		}else{
			if(channelDao.get(Channel.class, channelId)!=null){
				//先删除代理商与联系人的关系
				channelDao.deleteContractsByChannelId(channelId);
				//再删除代理商与销售经理的关系
				channelDao.deleteSalesManagerByChannelId(channelId);
				//最后删除代理商
				channelDao.delete(Channel.class,channelId);
			}else{
				result=0;
			}
		}
		return result;
	}
	@Override
	public Page<HashMap<String, Object>> findChannels(PageSelect<Channel> pageSelect) throws SystemException {
		if(pageSelect==null){
			return null;
		}else{
	        int total=channelDao.countChannels(pageSelect.getFilter());
	        List<HashMap<String, Object>> channelList=channelDao.findChannels(pageSelect.getFilter(),pageSelect.getOrder(),pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
	        return PageUtil.getPage(total, pageSelect.getPageNo(), channelList, pageSelect.getPageSize());
		}
	}
	@Override
	public int addSalesManager(ChannelSalesmanager channelSalesmanager)
			throws SystemException {
		int result=1;
		if(channelSalesmanager==null||channelSalesmanager.getChannelId()==null){
			result=-1;
		}else{
			//判断这个销售经理是否已经设置过了
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("managerId", channelSalesmanager.getManagerId());
			map.put("channelId", channelSalesmanager.getChannelId());
			List<HashMap<String,Object>> dataList=channelDao.findChannelManagers(map,null,true,0,0);
			if(dataList!=null && !dataList.isEmpty()){
				result=0;
			}else{
				//先设置之前的销售经理为过时
				if(channelSalesmanager.getIsdel()==0){
					channelDao.updateSalesManagersIsDel(channelSalesmanager.getChannelId());
				}
				//再 添加
				channelSalesmanager.setIsdel(0);//未过时
				channelDao.save(channelSalesmanager);
			}
			
		}
		return result;
	}
	
	public int addSalesManager2(ChannelSalesmanager channelSalesmanager)
			throws SystemException {
		int result=1;
		if(channelSalesmanager==null||channelSalesmanager.getChannelId()==null){
			result=-1;
		}else{
			//判断这个销售经理是否已经设置过了
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("managerId", channelSalesmanager.getManagerId());
			map.put("channelId", channelSalesmanager.getChannelId());
			List<HashMap<String,Object>> dataList=channelDao.findChannelManagers(map,null,true,0,0);
			if(dataList!=null && !dataList.isEmpty()){//已设置过
				result=0;
				//再判断是否过时，如果之前的设置为已过时，则设置为未过时，否则不处理
				ChannelSalesmanager channelSalesmanagerOld=null;
				for (HashMap<String, Object> hashMap : dataList) {
					if(hashMap.get("isdel") !=null && hashMap.get("id")!=null){
						channelSalesmanagerOld=channelDao.get(ChannelSalesmanager.class, Long.valueOf(hashMap.get("id").toString()));
						if(channelSalesmanagerOld!=null && channelSalesmanagerOld.getIsdel()==1 &&channelSalesmanager!=null){
								//先设置之前的销售经理为过时
								if(channelSalesmanager.getIsdel()==0){
									channelDao.updateSalesManagersIsDel(channelSalesmanager.getChannelId());
								}
								//再修改为未过时
								channelSalesmanagerOld.setIsdel(0);
								channelDao.merge(channelSalesmanagerOld);
						}
					}
				}
			}else{//未设置过
				//先设置之前的销售经理为过时
				if(channelSalesmanager.getIsdel()==0){
					channelDao.updateSalesManagersIsDel(channelSalesmanager.getChannelId());
				}
				//再 添加
				channelSalesmanager.setIsdel(0);//未过时
				channelDao.save(channelSalesmanager);
			}
			
		}
		return result;
	}
	@Override
	public int deleteSalesManagers(List<Long> ids) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			channelDao.deleteSalesManagers(ids);
		}
		return result;
	}
	@Override
	public List<HashMap<String, Object>> findChannels(Map<String, Object> map)
			throws SystemException {
		return channelDao.findChannels(map);
	}
	@Override
	public HashMap<String,Object> getChannelAndContacts(Long id) throws SystemException {
		Channel channel = null;
		HashMap<String,Object> map=new HashMap<String, Object>();
		List<HashMap<String,Object>> salesManagers=null;
		if (id!=null) {
			channel = channelDao.get(Channel.class, id);
			if(channel!=null){
				//联系人
				channel.setChannelcontacts(channelDao.findContactsByChannelId(id));
				//销售经理
				map.put("channelId", id);
				salesManagers=findAllChannelManagers(map);
				//设置未过时的销售经理
				if(salesManagers!=null && !salesManagers.isEmpty()){
					for (HashMap<String, Object> hashMap : salesManagers) {
						if(hashMap.get("isdel") !=null && "0".equals(hashMap.get("isdel").toString()) && hashMap.get("managerId")!=null){
							channel.setSalesManagerId((Long)hashMap.get("managerId"));
							//销售经理名称
							channel.setSalesManagerName(StringUtils.clearNull(hashMap.get("managerName")));
							break;
						}
					}
				}
				
				//获得所属集团名称
				Long groupId=channel.getGroupId();
				if(groupId!=null){
					ChannelGroup channelGroup=channelDao.get(ChannelGroup.class, groupId);
					if(channelGroup!=null){
						channel.setGroupName(channelGroup.getCnName());
					}
				}
			}
		}
		map.remove("channelId");
		map.put("channel", channel);
		map.put("salesManagers", salesManagers);
		return map;
	}
	
	@Override
	public Page<HashMap<String, Object>> findChannelManagers(PageSelect<Map<String, Object>> pageSelect)
			throws SystemException {
		    int total=channelDao.countChannelManagers(pageSelect.getFilter());
	        List<HashMap<String, Object>> list=channelDao.findChannelManagers(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(),pageSelect.getPageSize());
	        if (list != null && !list.isEmpty()) {
	        	OpenLdap openLdap = OpenLdapManager.getInstance();
	        	HashMap<String, String> managerNameMap=new HashMap<String, String>();
	        	String managerName= null;
	        	String managerId=null;
	        	CommonOperator commonOperator=null;
	        	for (HashMap<String, Object> hashMap : list) {
	        		managerName = null;
	        		managerId=null;
					if (hashMap.get("managerId") != null) {
						managerId=hashMap.get("managerId").toString();
						if (managerNameMap.get(managerId) != null) {
							managerName = managerNameMap.get(managerId) ;
						} else {
							commonOperator = openLdap.getOperatorById(managerId);
							if (commonOperator != null) {
								managerNameMap.put(managerId, commonOperator.getOpname());
								managerName = commonOperator.getOpname() ;
							}
						}
					}
					hashMap.put("managerName", managerName);
	          }
	        }
	      return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	    
	}
	@Override
	public int updateSalesManager(ChannelSalesmanager channelSalesmanager)
			throws SystemException {
		int result=1;
		if(channelSalesmanager==null||channelSalesmanager.getChannelId()==null){
			result=-1;
		}else{
			Long id=channelSalesmanager.getId();
			if(channelDao.get(ChannelSalesmanager.class, id)!=null){
				//判断这个销售经理是否已经设置过了
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("managerId", channelSalesmanager.getManagerId());
				map.put("channelId", channelSalesmanager.getChannelId());
				map.put("id2", channelSalesmanager.getId());
				List<HashMap<String,Object>> dataList=channelDao.findChannelManagers(map,null,true,0,0);
				if(dataList!=null && !dataList.isEmpty()){
					result=2;
				}else{
					if(channelSalesmanager.getIsdel()==0){//不过时
						//先设置之前的销售经理为过时
						channelDao.updateSalesManagersIsDel(channelSalesmanager.getChannelId());
					}
					//再修改
					channelDao.merge(channelSalesmanager);
					
				}
			}else{
				result=0;
			}
		}
		return result;
	}
	@Override
	public List<HashMap<String, Object>> findAllChannelManagers(
			Map<String, Object> map) throws SystemException {
	        return channelDao.findChannelManagers(map,null,false,0,0);
	}
	@Override
	public HashMap<String, Object> getChannelManager(@RequestBody Map<String, Object> map)
			throws SystemException {
		HashMap<String, Object> result=null;
		if(map!=null){
			List<HashMap<String, Object>> salesManagers=findAllChannelManagers(map);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(salesManagers!=null && !salesManagers.isEmpty()){
				result=salesManagers.get(0);
				if(result.get("managerId")!=null){
					CommonOperator	commonOperator = openLdap.getOperatorById(result.get("managerId").toString());
					if (commonOperator != null) {
						result.put("managerName", commonOperator.getOpname());
					}
				}
			}
		}
		return result;
	}
	@Override
	public HashMap<String, Object> getChannel4Self(Map<String, Object> map)
			throws SystemException {
		List<HashMap<String,Object>> dataList=channelDao.findChannels(map);
		if(dataList!=null && !dataList.isEmpty()){
			return dataList.get(0);
		}else{
			return null;
		}
	}
	@Override
	public int saveChannelGroup(ChannelGroup channelGroup)
			throws SystemException {
		int result=1;
		if(channelGroup==null){
			result=-1;
		}else{
			if(channelGroup.getId()!=null){//编辑
				//判断是否存在
				if(channelDao.get(ChannelGroup.class, channelGroup.getId())!=null){
					/*//判断英文名是否存在
					if(channelDao.checkChannelGroupName(channelGroup, true)){
						result=2;
					}else{*/
						//判断中文名是否存在
						if(channelDao.checkChannelGroupName(channelGroup, false)){
							result=3;
						}else{
							channelDao.merge(channelGroup);
						}
					//}
				}else{//不存在
					return 0;
				}
			}else{//新增
				/*//判断英文名是否存在
				if(channelDao.checkChannelGroupName(channelGroup, true)){
					result=2;
				}else{*/
					//判断中文名是否存在
					if(channelDao.checkChannelGroupName(channelGroup, false)){
						result=3;
					}else{
						channelDao.save(channelGroup);
					}
				//}
			}
		}
		return result;
	}
	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public HashMap<String, Object> deleteChannelGroups(List<Long> ids)
			throws SystemException {
		HashMap<String, Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if(ids==null || ids.isEmpty()){
			flag=false;
			msg="参数不正确!";
		}else{
			ChannelGroup channelGroup=null;
			for (Long id : ids) {
				//先判断是否存在
				channelGroup=channelDao.get(ChannelGroup.class, id);
				if(channelGroup!=null){
					//存在就删除
					//删除前判断是否在使用
					if(channelDao.checkChannelGroupIsUsing(id)){
						flag=false;
						msg="名称为["+channelGroup.getCnName()+"]的集团正在使用,不能删除!";
						throw new RuntimeException(msg);
					}else{
						//删除所属集团
						channelDao.delete(ChannelGroup.class, id);
					}
				}
			}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}
	@Override
	public List<HashMap<String, Object>> findChannelGroups(
			Map<String, Object> conditionMap) throws SystemException {
		return channelDao.findChannelGroups(conditionMap);
	}
}

