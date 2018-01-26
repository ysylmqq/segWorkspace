package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.sql.ARRAY;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinaGPS.gtmp.entity.MapPushRelationPOJO;
import com.chinaGPS.gtmp.entity.MessagePOJO;
import com.chinaGPS.gtmp.entity.ProjectInfoPOJO;
import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UserInfoPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.MessageMapper;
import com.chinaGPS.gtmp.mapper.ProjectInfoMapper;
import com.chinaGPS.gtmp.mapper.UnitMapper;
import com.chinaGPS.gtmp.mapper.UserInfoMapper;
import com.chinaGPS.gtmp.mapper.VehicleMapper;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.service.MessageService;
import com.chinaGPS.gtmp.service.ProjectInfoService;
import com.chinaGPS.gtmp.service.UserInfoService;
import com.chinaGPS.gtmp.util.oracleArray.ListToARRAY;
import com.chinaGPS.gtmp.util.page.PageSelect;
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessagePOJO> implements MessageService {
    @Resource
    private MessageMapper<MessagePOJO> messageMapper;

	@Override
	protected BaseSqlMapper<MessagePOJO> getMapper() {
		return this.messageMapper;
	}
    
	
	@Override
	public String addProjectInfo(MessagePOJO message) throws Exception {
		messageMapper.addProjectInfo(message);
		String a = String.valueOf(message.getId());
		return a;
	}
	
	
	@Override
	public List<MapPushRelationPOJO> selectMessageService() throws Exception {
		
		return messageMapper.selectMessageService();
	}
	
	@Override
	public HashMap<String, Object> selectProjectInfo(MessagePOJO message, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("projectInfo", message);
		int total = messageMapper.countAll(map);
		List<MessagePOJO> resultList =  messageMapper.selectProjectInfo(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public MessagePOJO getById(MessagePOJO message) throws Exception {
		MessagePOJO result =  messageMapper.getById(message.getId());
		return result;
	}
	
	@Override
	public String del(MessagePOJO message) throws Exception {
		messageMapper.edit(message);
		String a = String.valueOf(message.getId());
		return a;
	}
	
	@Override
	public String editProjectInfo(MessagePOJO message) throws Exception {
		messageMapper.edit(message);
		String a = String.valueOf(message.getId());
		return a;
	}



	

	
	

	


  
	
}