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
import com.chinaGPS.gtmp.entity.ReseachAnswerPOJO;
import com.chinaGPS.gtmp.entity.ReseachPOJO;
import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UserInfoPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.ReseachAnswerMapper;
import com.chinaGPS.gtmp.mapper.UnitMapper;
import com.chinaGPS.gtmp.mapper.ReseachMapper;
import com.chinaGPS.gtmp.mapper.VehicleMapper;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.service.ReseachAnswerService;
import com.chinaGPS.gtmp.service.ReseachService;
import com.chinaGPS.gtmp.service.UserInfoService;
import com.chinaGPS.gtmp.util.oracleArray.ListToARRAY;
import com.chinaGPS.gtmp.util.page.PageSelect;
@Service
public class ReseachAnswerServiceImpl extends BaseServiceImpl<ReseachAnswerPOJO> implements ReseachAnswerService {
    @Resource
    private ReseachAnswerMapper<ReseachAnswerPOJO> reseachAnswerMapper;
    
   
    @Override
    protected BaseSqlMapper<ReseachAnswerPOJO> getMapper() {
		return this.reseachAnswerMapper;
    }

	@Override
	public String addInfo(ReseachAnswerPOJO reseachAnswer) throws Exception {
		reseachAnswerMapper.add(reseachAnswer);
		String a = String.valueOf(reseachAnswer.getUserId());
		return a;
	}
	
	
	
	
	@Override
	public String del(ReseachAnswerPOJO reseachAnswer) throws Exception {
		reseachAnswerMapper.edit(reseachAnswer);
		String a = String.valueOf(reseachAnswer.getUserId());
		return a;
	}
	
	@Override
	public String editAnswer(ReseachAnswerPOJO reseachAnswer) throws Exception {
		reseachAnswerMapper.edit(reseachAnswer);
		String a = String.valueOf(reseachAnswer.getUserId());
		return a;
	}
	
	@Override
	public HashMap<String, Object> selectQuestion(ReseachAnswerPOJO reseachAnswer, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("userInfo", reseachAnswer);
		int total = reseachAnswerMapper.countAll(map);
		List<ReseachAnswerPOJO> resultList =  reseachAnswerMapper.selectQuestion(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public ReseachAnswerPOJO getByIdReseachAnswer(ReseachAnswerPOJO reseachAnswer) throws Exception {
		//Map map = new HashMap();
		//map.put("userInfo", userInfo);
		//int total = ReseachMapper.countAll(map);
		ReseachAnswerPOJO result =  reseachAnswerMapper.getByIdReseachAnswer(reseachAnswer);
		System.out.println(result);
		//HashMap<String, Object> result=new HashMap<String, Object>();
		//result.put("total", total);
		//result.put("rows", resultList);
		return result;
	}
	
	@Override
	public ReseachAnswerPOJO getById(ReseachAnswerPOJO reseachAnswer) throws Exception {
		//Map map = new HashMap();
		//map.put("userInfo", userInfo);
		//int total = ReseachMapper.countAll(map);
		ReseachAnswerPOJO result =  reseachAnswerMapper.getById(reseachAnswer.getId());
		//HashMap<String, Object> result=new HashMap<String, Object>();
		//result.put("total", total);
		//result.put("rows", resultList);
		return result;
	}

	


  
	
}