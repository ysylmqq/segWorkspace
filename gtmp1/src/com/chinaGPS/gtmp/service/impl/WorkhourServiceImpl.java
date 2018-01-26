package com.chinaGPS.gtmp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.CustomerSimPOJO;
import com.chinaGPS.gtmp.entity.VehicleWorkPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.WorkhourMapper;
import com.chinaGPS.gtmp.service.IWorkhourService;
import com.chinaGPS.gtmp.util.page.PageSelect;
@Service
public class WorkhourServiceImpl extends BaseServiceImpl<VehicleWorkPOJO> implements IWorkhourService {
	@Resource
	private WorkhourMapper<VehicleWorkPOJO> workhourMapper;
	
	@Override
	public List<Map<String, String>> totalWorkhour(VehicleWorkPOJO vehicleWorkPOJO) throws Exception {
		return workhourMapper.totalWorkhour(vehicleWorkPOJO);
	}

	@Override
	public Map<String, Object> totalWorkhourPage(VehicleWorkPOJO vehicleWorkPOJO, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("work", vehicleWorkPOJO);
		int total = workhourMapper.totalWorkhourPageCountAll(map);
		List<Map<String, String>> resultList =  workhourMapper.totalWorkhourPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public Map<String, Object> totalWorkhourDetail(VehicleWorkPOJO vehicleWorkPOJO, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("work", vehicleWorkPOJO);
		int total = workhourMapper.totalWorkhourDetailCountAll(map);
		List<Map<String, String>> resultList =  workhourMapper.totalWorkhourDetail(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public List<Map<String, String>> dailyWorkhour(VehicleWorkPOJO vehicleWorkPOJO) throws Exception {
		return workhourMapper.dailyWorkhour(vehicleWorkPOJO);
	}

	@Override
	public Map<String, Object> dailyWorkhourDetail(VehicleWorkPOJO vehicleWorkPOJO, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("work", vehicleWorkPOJO);
		int total = workhourMapper.dailyWorkhourDetailCountAll(map);
		List<Map<String, String>> resultList =  workhourMapper.dailyWorkhourDetail(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	protected BaseSqlMapper<VehicleWorkPOJO> getMapper() {
		return workhourMapper;
	}


}
