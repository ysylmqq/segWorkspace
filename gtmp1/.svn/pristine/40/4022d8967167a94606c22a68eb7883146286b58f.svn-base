package com.chinaGPS.gtmp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.SimReplaceLogPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.SimReplaceLogMapper;
import com.chinaGPS.gtmp.mapper.UnitMapper;
import com.chinaGPS.gtmp.service.ISimReplaceLogService;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:ISimServerService
 * @Description:玉柴重工使用SIM卡service
 * @author:于松亚
 * @date:2017-3-21 上午10:15:43
 */
@Service
public class SimReplaceLogServiceImpl extends BaseServiceImpl<SimReplaceLogPOJO> implements ISimReplaceLogService {
	@Resource
	private SimReplaceLogMapper<SimReplaceLogPOJO> simReplaceLogMapper;
	
	@Resource
	private UnitMapper<UnitPOJO> unitMapper;

	@Override
	protected BaseSqlMapper<SimReplaceLogPOJO> getMapper() {
		return this.simReplaceLogMapper;
	}

	@Override
	public HashMap<String, Object> getSimReplaceLog(SimReplaceLogPOJO simReplaceLog,
			PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("simReplaceLog", simReplaceLog);
		int total = simReplaceLogMapper.countAll(map);
		List<SimReplaceLogPOJO> resultList =  simReplaceLogMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public void save(SimReplaceLogPOJO simReplaceLog) throws Exception {
		simReplaceLogMapper.insertSelective(simReplaceLog);
	}

	@Override
	public List<SimReplaceLogPOJO> exportSimReplaceLog(SimReplaceLogPOJO simReplaceLog)  {
		Map map = new HashMap();
		map.put("simReplaceLog", simReplaceLog);
		return simReplaceLogMapper.selectAll(map);
	}

	@Override
	public HashMap<String, Object> getUnitPage(UnitPOJO unit,
			PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("unit", unit);
		int total = unitMapper.countAll(map);
		List<UnitPOJO> resultList =  unitMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

}
