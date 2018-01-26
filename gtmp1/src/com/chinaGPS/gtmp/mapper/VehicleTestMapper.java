package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.TestPOJO;

@Component
public interface VehicleTestMapper<T extends TestPOJO> extends BaseSqlMapper<T> {

	public int editTest(Map map) throws Exception;

	public int countTestCommand(Map map) throws Exception;

	public List<TestCommandPOJO> getByPageTestCommand(Map<String, Serializable> map, RowBounds rowBounds) throws Exception;

	public int editVehicleStatus(Map map) throws Exception;
	
	public void saveOrUpdateTest(TestPOJO testPOJO)throws Exception;
	
	public TestPOJO getTestByUnitId(String unitId) throws Exception;

}