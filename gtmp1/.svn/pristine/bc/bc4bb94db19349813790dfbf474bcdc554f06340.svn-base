package com.chinaGPS.gtmp.service;

import java.util.HashMap;

import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.TestPOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;


public interface IVehicleTestService extends BaseService<TestPOJO>{
    /**
     * @Title:editTest
     * @Description:修改测试信息，用于质检
     * @param map
     * @return
     * @throws
     */
    public boolean editTest(HashMap map) throws Exception;
    /**
     * @Title:getTests
     * @Description:获取测试信息
     * @param testPOJO
     * @param pageSelect
     * @return
     * @throws
     */
    public HashMap<String, Object> getTests(TestPOJO testPOJO,
		PageSelect pageSelect) throws Exception;
    /**
     * @Title:getTestCommands
     * @Description:获取测试指令信息
     * @param testCommandPOJO
     * @param pageSelect
     * @return
     * @throws
     */
    public HashMap<String, Object> getTestCommands(TestCommandPOJO testCommandPOJO,
		PageSelect pageSelect) throws Exception;
    /**
     * @Title:editVehicleStatus
     * @Description:修改机械状态
     * @param map
     * @return
     * @throws
     */
    public boolean editVehicleStatus(HashMap map) throws Exception;
    
    public void saveOrUpdateTest(TestPOJO testPOJO)throws Exception;
    
    public TestPOJO getTestByUnitId(String unitId) throws Exception;
}