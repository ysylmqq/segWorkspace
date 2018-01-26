package com.chinaGPS.gtmp.action.vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.Flags.Flag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.TestPOJO;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.service.IVehicleTestService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.DateUtil;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.StringUtils;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @Package:com.chinaGPS.gtmp.action.vehicle
 * @ClassName:VehicleTestAction
 * @Description:机械测试管理器
 * @author:zfy
 * @date:Dec 14, 2013 2:48:19 PM
 *
 */
@Scope("prototype")
@Controller  
public class VehicleTestAction extends BaseAction implements ModelDriven<TestPOJO> {
	private static final long serialVersionUID = 4844835396709661367L;
	private static Logger logger = LoggerFactory.getLogger(VehicleTestAction.class);
	
	@Resource
    private IVehicleTestService vehicleTestService;
    @Resource
	private IVehicleService vehicleService;
    @Resource
	private TestPOJO vehicleTestPOJO;
    public TestPOJO getVehicleTestPOJO() {
		return vehicleTestPOJO;
	}

	public void setVehicleTestPOJO(TestPOJO vehicleTestPOJO) {
		this.vehicleTestPOJO = vehicleTestPOJO;
	}

	@Resource
	private TestCommandPOJO testCommandPOJO;
    @Resource
	private PageSelect pageSelect;
	
	private int page;
	private int rows;
	private List<String> idList;//设置通过/不通过的test_id
	private List<String> vehicleIdList;//修改通过的vehicle_id的状态
	
	 private String testTimeStr;//测试开始时间
		private String testTimeStr2;//测试结束时间
		private String qaTimeStr;//质检开始时间
		private String qaTimeStr2;//质检结束时间
	/**
	 * @Description:处理查询测试信息
	 * @return
	 * @throws
	 */
	public String searchTest() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			vehicleTestPOJO.setTestAEnable(1);// 是否测试:是
			result = vehicleTestService.getTests(vehicleTestPOJO, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	}
	
	/**
	 * @Description:处理查询测试指令信息
	 * @return
	 * @throws
	 */
	public String searchTestCommand() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			if (vehicleTestPOJO.getUnitId() != null) {
				testCommandPOJO.setUnitId(vehicleTestPOJO.getUnitId());
			}
			result = vehicleTestService.getTestCommands(testCommandPOJO,
					pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	}
	
	/**
	 * @Description:修改测试信息,用于质检
	 * @return
	 * @throws
	 */
	@OperationLog(description = "机械质检修改")
	public void updateTest() {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		Long userId = (Long) getSession().get(Constants.USER_ID);
		HashMap map = new HashMap();
		map.put("qaResult", vehicleTestPOJO.getQaResult());
		map.put("qaUserId", userId);
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			if (idList != null && idList.size() > 0) {
				map.put("testIds", idList);
				vehicleTestService.editTest(map);
				map.clear();
				map.put("vehicleIds", vehicleIdList);
				if (vehicleTestPOJO.getQaResult() == 0) {// 如果正常则修改机械状态					
					map.put("status", Constants.VEHICLE_STATE2);					
				}else {
					map.put("status", Constants.VEHICLE_STATE1);
					map.put("testFlag",0);
				}
				vehicleTestService.editVehicleStatus(map);
			}

			// 写入操作日志
			Object[] array = vehicleIdList.toArray();
			StringBuffer sBuffer = new StringBuffer();
			for (Object integer : array) {
				sBuffer.append(integer).append(",");
			}
			if (sBuffer.length() > 0) {
				// logger("机械质检(vehicleIds="+sBuffer.deleteCharAt(sBuffer.length()-1)+")");//写入操作日志

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
			msg = OP_FAIL;
		}
		renderMsgJson(flag, msg);
	}

	public void exportToExcel() {
		List<Object[]> values = new ArrayList<Object[]>();
		try {
			vehicleTestPOJO.setVehicleDef(java.net.URLDecoder
					.decode(vehicleTestPOJO.getVehicleDef()));
			// 开始时间
			if (StringUtils.isNotBlank(testTimeStr)) {
				vehicleTestPOJO.setTestTime(DateUtil.parse(java.net.URLDecoder
						.decode(testTimeStr), DateUtil.YMD_DASH_WITH_FULLTIME));
			}
			if (StringUtils.isNotBlank(testTimeStr)) {
				vehicleTestPOJO
						.setTestTime2(DateUtil.parse(java.net.URLDecoder
								.decode(testTimeStr2),
								DateUtil.YMD_DASH_WITH_FULLTIME));
			}
			if (StringUtils.isNotBlank(qaTimeStr)) {
				vehicleTestPOJO.setQaTime(DateUtil.parse(java.net.URLDecoder
						.decode(qaTimeStr), DateUtil.YMD_DASH_WITH_FULLTIME));
			}
			if (StringUtils.isNotBlank(qaTimeStr2)) {
				vehicleTestPOJO.setQaTime2(DateUtil.parse(java.net.URLDecoder
						.decode(qaTimeStr2), DateUtil.YMD_DASH_WITH_FULLTIME));
			}
			vehicleTestPOJO.setTestAEnable(1);// 是否测试:是
			List<TestPOJO> list = vehicleTestService.getList(vehicleTestPOJO);
			String testResult = "正常";
			String testAEnable = "正常";
			String testADisable = "正常";
			String testBEnable = "正常";
			String testBDisable = "正常";
			String testProtectEnable = "正常";
			String testProtectDisable = "正常";
			String qaResult = "正常";
			for (TestPOJO testPOJO : list) {
				if (testPOJO.getTestResult() != null) {
					if (testPOJO.getTestResult().intValue() == 1) {
						testResult = "异常";
					}
				}
				if (testPOJO.getTestAEnable() != null) {
					if (testPOJO.getTestAEnable().intValue() == 1) {
						testAEnable = "异常";
					}
				}
				if (testPOJO.getTestADisable() != null) {
					if (testPOJO.getTestADisable().intValue() == 1) {
						testADisable = "异常";
					}
				}
				if (testPOJO.getTestBEnable() != null) {
					if (testPOJO.getTestBEnable().intValue() == 1) {
						testBEnable = "异常";
					}
				}
				if (testPOJO.getTestBDisable() != null) {
					if (testPOJO.getTestBDisable().intValue() == 1) {
						testBDisable = "异常";
					}
				}
				if (testPOJO.getTestProtectEnable() != null) {
					if (testPOJO.getTestProtectEnable().intValue() == 1) {
						testProtectEnable = "异常";
					}
				}
				if (testPOJO.getTestProtectDisable() != null) {
					if (testPOJO.getTestProtectDisable().intValue() == 1) {
						testProtectDisable = "异常";
					}
				}
				if (testPOJO.getQaResult() != null) {
					if (testPOJO.getQaResult().intValue() == 1) {
						qaResult = "异常";
					}
				}

				values.add(new Object[] {
						testPOJO.getVehicleDef(),
						testPOJO.getTestUserName(),
						DateUtil.format(testPOJO.getTestTime(),
								DateUtil.YMD_DASH_WITH_FULLTIME24),
						testResult,
						testAEnable,
						testADisable,
						testBEnable,
						testBDisable,
						testProtectEnable,
						testProtectDisable,
						qaResult,
						DateUtil.format(testPOJO.getQaTime(),
								DateUtil.YMD_DASH_WITH_FULLTIME24) });
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String[] headers = new String[] { "整机编号", "测试人", "测试时间", "测试结果",
				"定位测试结果", "使能A锁测试结果", "解除A锁", "使能B锁", "解除B锁", "使能防拆保护",
				"禁止防拆保护" };
		super.renderExcel("机械质检" + ".xls", headers, values);
	}
	 
	
	public String saveOrUpdateTest() throws Exception {
		String msg = "";
		boolean result = true;
		if (vehicleTestPOJO != null) {
			try {
				Long userId = (Long) getSession().get(Constants.USER_ID);
				vehicleTestPOJO.setTestUserId(userId);
				boolean flag = true;
				if(vehicleTestPOJO.getTest_reserve1()!=0 ||
				   vehicleTestPOJO.getTestADisable()!=0 ||
				   vehicleTestPOJO.getTestAEnable()!=0 ||
				   vehicleTestPOJO.getTestBDisable()!=0 ||
				   vehicleTestPOJO.getTestBEnable()!=0 ||
				   vehicleTestPOJO.getTestProtectDisable()!=0 ||
				   vehicleTestPOJO.getTestProtectEnable()!=0){
				   vehicleTestPOJO.setTestResult(1);				   
				}else{
					vehicleTestPOJO.setTestResult(0);
				}
				vehicleTestService.saveOrUpdateTest(vehicleTestPOJO);
				result = true;//vehicleService.saveOrUpdateTest(vehiclePOJO);
				msg = OP_SUCCESS;
			} catch (RuntimeException e) {
				e.printStackTrace();
				result = false;
				msg = OP_FAIL;
			}
		}
		renderMsgJson(result, msg);
		return NONE;
	}
	
	@Override
	public TestPOJO getModel() {
	    return vehicleTestPOJO;
	}

	public PageSelect getPageSelect() {
	    return pageSelect;
	}

	public void setPageSelect(PageSelect pageSelect) {
	    this.pageSelect = pageSelect;
	}

	public int getPage() {
	    return page;
	}

	public void setPage(int page) {
	    this.page = page;
	}

	public int getRows() {
	    return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public TestCommandPOJO getTestCommandPOJO() {
		return testCommandPOJO;
	}

	public void setTestCommandPOJO(TestCommandPOJO testCommandPOJO) {
		this.testCommandPOJO = testCommandPOJO;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public List<String> getVehicleIdList() {
		return vehicleIdList;
	}

	public void setVehicleIdList(List<String> vehicleIdList) {
		this.vehicleIdList = vehicleIdList;
	}

	public String getTestTimeStr() {
		return testTimeStr;
	}

	public void setTestTimeStr(String testTimeStr) {
		this.testTimeStr = testTimeStr;
	}

	public String getTestTimeStr2() {
		return testTimeStr2;
	}

	public void setTestTimeStr2(String testTimeStr2) {
		this.testTimeStr2 = testTimeStr2;
	}

	public String getQaTimeStr() {
		return qaTimeStr;
	}

	public void setQaTimeStr(String qaTimeStr) {
		this.qaTimeStr = qaTimeStr;
	}

	public String getQaTimeStr2() {
		return qaTimeStr2;
	}

	public void setQaTimeStr2(String qaTimeStr2) {
		this.qaTimeStr2 = qaTimeStr2;
	}
	
}