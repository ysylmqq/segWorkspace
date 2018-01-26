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

import com.chinaGPS.gtmp.entity.ProjectInfoPOJO;
import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UserInfoPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.UnitMapper;
import com.chinaGPS.gtmp.mapper.UserInfoMapper;
import com.chinaGPS.gtmp.mapper.VehicleMapper;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.service.UserInfoService;
import com.chinaGPS.gtmp.util.oracleArray.ListToARRAY;
import com.chinaGPS.gtmp.util.page.PageSelect;
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoPOJO> implements UserInfoService {
    @Resource
    private UserInfoMapper<UserInfoPOJO> userInfoMapper;
   
    @Override
    protected BaseSqlMapper<UserInfoPOJO> getMapper() {
		return this.userInfoMapper;
    }

	@Override
	public String addInfo(UserInfoPOJO userInfo) throws Exception {
		userInfoMapper.add(userInfo);
		String a = String.valueOf(userInfo.getUserId());
		return a;
	}
	
	
	
	
	@Override
	public String del(UserInfoPOJO userInfo) throws Exception {
		userInfoMapper.edit(userInfo);
		String a = String.valueOf(userInfo.getUserId());
		return a;
	}
	
	@Override
	public String editAnswer(UserInfoPOJO userInfo) throws Exception {
		userInfoMapper.edit(userInfo);
		String a = String.valueOf(userInfo.getUserId());
		return a;
	}
	
	@Override
	public HashMap<String, Object> selectQuestion(UserInfoPOJO userInfo, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("userInfo", userInfo);
		int total = userInfoMapper.countAll(map);
		List<UserInfoPOJO> resultList =  userInfoMapper.selectQuestion(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public UserInfoPOJO getById(UserInfoPOJO userInfo) throws Exception {
		//Map map = new HashMap();
		//map.put("userInfo", userInfo);
		//int total = userInfoMapper.countAll(map);
		UserInfoPOJO result =  userInfoMapper.getById(userInfo.getId());
		//HashMap<String, Object> result=new HashMap<String, Object>();
		//result.put("total", total);
		//result.put("rows", resultList);
		return result;
	}

	


  
	
}