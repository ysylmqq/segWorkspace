package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.TestPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.VehicleMapper;
import com.chinaGPS.gtmp.mapper.VehicleTestMapper;
import com.chinaGPS.gtmp.service.IVehicleTestService;
import com.chinaGPS.gtmp.util.page.PageSelect;

@Service
public class VehicleTestServiceImpl extends BaseServiceImpl<TestPOJO> implements IVehicleTestService {
	@Resource
	private VehicleTestMapper<TestPOJO> vehicleTestMapper;
	
	@Resource
	private VehicleMapper<VehiclePOJO> vehicleMapper;

	@Override
	protected BaseSqlMapper<TestPOJO> getMapper() {
		return this.vehicleTestMapper;
	}

	@Override
	public boolean editTest(HashMap map) throws Exception {
		return vehicleTestMapper.editTest(map) > 0 ? true : false;
	}

	@Override
	public HashMap<String, Object> getTests(TestPOJO testPOJO, PageSelect pageSelect) throws Exception {
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("test", testPOJO);
		int total = vehicleTestMapper.countAll(map);
		List<TestPOJO> resultList = vehicleTestMapper.getByPage(map, new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public HashMap<String, Object> getTestCommands (TestCommandPOJO testCommandPOJO, PageSelect pageSelect) throws Exception {
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("testCommand", testCommandPOJO);
		int total = vehicleTestMapper.countTestCommand(map);
		List<TestCommandPOJO> resultList = vehicleTestMapper.getByPageTestCommand(map, new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public boolean editVehicleStatus(HashMap map) throws Exception {
		return vehicleTestMapper.editVehicleStatus(map) > 0 ? true : false;
	}

	@Override
	public void saveOrUpdateTest(TestPOJO testPOJO) throws Exception {	
		TestPOJO test= vehicleTestMapper.getTestByUnitId(testPOJO.getUnitId());
		if(test != null){
			testPOJO.setHasdata(1);
		}
		vehicleTestMapper.saveOrUpdateTest(testPOJO);
		if(testPOJO.getTestResult()==0){
			VehiclePOJO entity = new VehiclePOJO();
			entity.setVehicleId(testPOJO.getVehicleId());
			vehicleMapper.editTest(entity);
		}
	}

	@Override
	public TestPOJO getTestByUnitId(String unitId) throws Exception {
		return vehicleTestMapper.getTestByUnitId(unitId);
	}

}