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
import com.chinaGPS.gtmp.mapper.ProjectInfoMapper;
import com.chinaGPS.gtmp.mapper.UnitMapper;
import com.chinaGPS.gtmp.mapper.UserInfoMapper;
import com.chinaGPS.gtmp.mapper.VehicleMapper;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.service.ProjectInfoService;
import com.chinaGPS.gtmp.service.UserInfoService;
import com.chinaGPS.gtmp.util.oracleArray.ListToARRAY;
import com.chinaGPS.gtmp.util.page.PageSelect;
@Service
public class ProjectInfoServiceImpl extends BaseServiceImpl<ProjectInfoPOJO> implements ProjectInfoService {
    @Resource
    private ProjectInfoMapper<ProjectInfoPOJO> projectInfoMapper;

	@Override
	protected BaseSqlMapper<ProjectInfoPOJO> getMapper() {
		return this.projectInfoMapper;
	}
    
	
	@Override
	public String addProjectInfo(ProjectInfoPOJO projectInfo) throws Exception {
		projectInfoMapper.addProjectInfo(projectInfo);
		String a = String.valueOf(projectInfo.getId());
		return a;
	}
	
	@Override
	public HashMap<String, Object> selectProjectInfo(ProjectInfoPOJO projectInfo, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("projectInfo", projectInfo);
		int total = projectInfoMapper.countAll(map);
		List<ProjectInfoPOJO> resultList =  projectInfoMapper.selectProjectInfo(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public ProjectInfoPOJO getById(ProjectInfoPOJO projectInfo) throws Exception {
		ProjectInfoPOJO result =  projectInfoMapper.getById(projectInfo.getId());
		return result;
	}
	
	@Override
	public String del(ProjectInfoPOJO projectInfo) throws Exception {
		projectInfoMapper.edit(projectInfo);
		String a = String.valueOf(projectInfo.getId());
		return a;
	}
	
	@Override
	public String editProjectInfo(ProjectInfoPOJO projectInfo) throws Exception {
		projectInfoMapper.edit(projectInfo);
		String a = String.valueOf(projectInfo.getId());
		return a;
	}



	

	
	

	


  
	
}