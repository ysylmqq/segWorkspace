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
import com.chinaGPS.gtmp.entity.ReseachPOJO;
import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UserInfoPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.UnitMapper;
import com.chinaGPS.gtmp.mapper.ReseachMapper;
import com.chinaGPS.gtmp.mapper.VehicleMapper;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.service.ReseachService;
import com.chinaGPS.gtmp.service.UserInfoService;
import com.chinaGPS.gtmp.util.oracleArray.ListToARRAY;
import com.chinaGPS.gtmp.util.page.PageSelect;
@Service
public class ReseachServiceImpl extends BaseServiceImpl<ReseachPOJO> implements ReseachService {
    @Resource
    private ReseachMapper<ReseachPOJO> ReseachMapper;
   
    @Override
    protected BaseSqlMapper<ReseachPOJO> getMapper() {
		return this.ReseachMapper;
    }

	@Override
	public String addInfo(ReseachPOJO reseach) throws Exception {
		ReseachMapper.add(reseach);
		String a = String.valueOf(reseach.getUserId());
		return a;
	}
	
	
	
	
	@Override
	public String del(ReseachPOJO reseach) throws Exception {
		ReseachMapper.edit(reseach);
		String a = String.valueOf(reseach.getUserId());
		return a;
	}
	
	@Override
	public String editAnswer(ReseachPOJO reseach) throws Exception {
		ReseachMapper.edit(reseach);
		String a = String.valueOf(reseach.getUserId());
		return a;
	}
	
	@Override
	public HashMap<String, Object> selectQuestion(ReseachPOJO reseach, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("userInfo", reseach);
		int total = ReseachMapper.countAll(map);
		List<ReseachPOJO> resultList =  ReseachMapper.selectQuestion(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	
	@Override
	public HashMap<String, Object> selectReseachList(ReseachPOJO reseach, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("reseach", reseach);
		int total = ReseachMapper.countReseachList(map);
		List<ReseachPOJO> resultList =  ReseachMapper.selectReseachList(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public ReseachPOJO getById(ReseachPOJO reseach) throws Exception {
		//Map map = new HashMap();
		//map.put("userInfo", userInfo);
		//int total = ReseachMapper.countAll(map);
		ReseachPOJO result =  ReseachMapper.getById(reseach.getId());
		//HashMap<String, Object> result=new HashMap<String, Object>();
		//result.put("total", total);
		//result.put("rows", resultList);
		return result;
	}
	
	@Override
	public String editReseach(ReseachPOJO reseach) throws Exception {
		ReseachMapper.edit(reseach);
		String a = String.valueOf(reseach.getId());
		return a;
	}

	


  
	
}