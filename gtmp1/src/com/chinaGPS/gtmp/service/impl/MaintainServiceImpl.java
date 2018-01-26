package com.chinaGPS.gtmp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.service.IMaintainService;
import com.chinaGPS.gtmp.mapper.MaintainMapper;
@Service
public class MaintainServiceImpl implements IMaintainService {
	@Resource
	private MaintainMapper maintainMapper;
	@Override
	public List<HashMap<String, Object>> getVehicleInfo() throws Exception {
		return maintainMapper.getVehicleInfo();
	}

	@Override
	public List<HashMap<String, Object>> getMaintainPeriod() throws Exception {
		List<HashMap<String, Object>> list=maintainMapper.getMaintainPeriod();
		return maintainMapper.getMaintainPeriod();
	}

	@Override
	public List<HashMap<String, Object>> getPushBind(ArrayList<String> userIds)
			throws Exception {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("ids", userIds);
		return maintainMapper.getPushBind(map);
	}

	@Override
	public int countPushLogByUserId(Map map) throws Exception {
		// TODO Auto-generated method stub
		return  maintainMapper.countPushLogByUserId(map);
	}

	@Override
	public void insertPushLog(Map map) throws Exception {
		// TODO Auto-generated method stub
		maintainMapper.insertPushLog(map);
	}

}
