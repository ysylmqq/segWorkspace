package com.chinaGPS.gtmp.action.vehicle;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.action.common.ExcelUtils;
import com.chinaGPS.gtmp.entity.AlarmPOJO;
import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.TestPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.service.IVehicleTestService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.DateUtil;
import com.chinaGPS.gtmp.util.FormatUtil;
import com.chinaGPS.gtmp.util.HttpURLConnectionUtil;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 文 件 名 :VehicleAction.java CopyRright (c) 2012:赛格导航 文件编号：0000001 创 建 人：肖克 创 建
 * 日 期：2012-12-12 描 述： 机械管理控制器 修 改 人： 修 改 日 期： 修 改 原 因: 版 本 号：1.0
 */

@Scope("prototype")
@Controller
public class VehicleAction extends BaseAction implements ModelDriven<VehiclePOJO> {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(VehicleAction.class);
	
	@Resource
	private IVehicleService vehicleService;
	@Resource
	private IVehicleTestService vehicleTestService;
	@Resource
	private VehiclePOJO vehiclePOJO;
	@Resource
	private TestPOJO testPOJO;
	@Resource
	private TestCommandPOJO testCommandPOJO;

	@Resource
	private PageSelect pageSelect;

	private String testPerson;

	private String testTime;

	private File upload;

	private String uploadFileName;

	private int page;
	private int rows;

	/** 批量操作的机械ID */
	private List<String> vehicleIds = new ArrayList<String>();

	/**
	 * 查询机械信息，返回分页数据
	 */
	public String search() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		HashMap map = new HashMap();
		if (vehiclePOJO.getIsValid() == null) { // 默认检索有效的数据
			vehiclePOJO.setIsValid(0);
		}
		if("全部".equals(vehiclePOJO.getVehicleCode())){
			vehiclePOJO.setVehicleCode("");
		}
		if(vehiclePOJO.getDealerId() != null){
			String temp = vehiclePOJO.getDealerId();
			if(temp.equals("")){
				
			}else{				
				String[] dealerIds=FormatUtil.strToFormat(temp).split(",");
				vehiclePOJO.setDealerIds(dealerIds);
				vehiclePOJO.setDealerId(null);
			}
			}
		// 判断是否是经销商
				UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
				List<RolePOJO> roles = userPOJO.getRoles();
				boolean isDealer = false;// 是否是经销商
				boolean isLeaseHold = false; // 是否是融资租赁
				DepartPOJO departPOJO = null;
				if (!roles.isEmpty()) {
					if (roles.size() == 1) {
						RolePOJO role = roles.get(0);
						if (role.getRoleId() == Constants.DEALER_ROLE_ID) {// 如果是经销商的话
							isDealer = true;
						} else if (role.getRoleId() == Constants.LEASEHOLD_ROLE_ID) { // 如果是融资租赁
							isLeaseHold = true;
						}
					}
				}

				if (isDealer) {// 经销商
					String[] dealerIds = new String[1];// 查询更多条件中的经销商数组
					departPOJO = userPOJO.getDepartInfo();
					dealerIds[0] = String.valueOf(departPOJO.getDealerId());
					map.put("dealerIds", dealerIds);

				} else if (isLeaseHold) {
					map.put("leaseFlag", 1);
				}

				// 机械分组
				if (getSession().get(Constants.VEHICLE_STATUS) != null) {
					List<Integer> vehicleStatus = (List<Integer>) getSession().get(
							Constants.VEHICLE_STATUS);
					if (vehicleStatus != null && !vehicleStatus.isEmpty()) {
						map.put("vehicleStatus", vehicleStatus);
					} else {
						if (isDealer) {// 经销商
							vehicleStatus.add(Constants.VEHICLE_STATE3);
						} else {
							// 如果没有设置数据权限，则默认查一个组为0，实际上没这个组，即不查询出来
							vehicleStatus.add(0);
						}
						map.put("vehicleStatus", vehicleStatus);
					}
				}
				
		renderObject(vehicleService.getVehicles(vehiclePOJO, pageSelect));
		return NONE;
	}

	/**
	 * 查询机械测试指令信息，返回分页数据
	 */
	public String testsearch() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		renderObject(vehicleService.getVehiclesTest(vehiclePOJO, pageSelect));
		return NONE;
	}

	/**
	 * 查询测试时初始位置信息
	 */
	public String selectLastPosition() throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		// gps位置信息----初始化时从数据库中取最后位置信息进行展示
		TLastPositionPOJO tLastPositionPOJO = vehicleService
				.selectLastPosition(vehiclePOJO);
		if(tLastPositionPOJO.getLat()!=null && tLastPositionPOJO.getLon()!=null&&!tLastPositionPOJO.getLat().equals("0")&&!tLastPositionPOJO.getLon().equals("0")){
			String ret = HttpURLConnectionUtil.getAddress(HttpURLConnectionUtil.getBaiduLonlat(""+tLastPositionPOJO.getLon()+","+tLastPositionPOJO.getLat()));
			if(ret!=null && !ret.equals("")){
				tLastPositionPOJO.setReferencePosition(ret);
			}
		}
		// 工况信息----初始化时从数据库中取最后位置信息进行展示
		TLastConditionsPOJO tLastConditionsPOJO = vehicleService
				.selectLastCondition(vehiclePOJO);

		result.put("tLastPosition", tLastPositionPOJO);
		result.put("tLastConditions", tLastConditionsPOJO);
		renderObject(result);
		return NONE;
	}

	/**
	 * 确认测试
	 * 
	 * @return
	 */
	@OperationLog(description = "机械测试结果保存")
	public String saveOrUpdateTest() throws Exception {
		String msg = "";
		boolean result = true;
		if (vehiclePOJO != null) {
			try {
				// String uId=getRequest().getParameter("unitId");
				Long userId = (Long) getSession().get(Constants.USER_ID);
				vehiclePOJO.setTestUserId(userId.intValue());
				result = false;//vehicleService.saveOrUpdateTest(vehiclePOJO);
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

	/**
	 * 查询终端信息
	 */
	public String searchUnitId() throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<String> unitIdList = vehicleService.getUnitIdList();

		List<UnitPOJO> Mlist = new ArrayList<UnitPOJO>();
		for (String uid : unitIdList) {
			UnitPOJO unitPOJO = new UnitPOJO();
			unitPOJO.setUnitSn(uid);
			Mlist.add(unitPOJO);
		}
		result.put("unitSn", Mlist);
		renderObject(result);
		return NONE;
	}
	
	/**
	 * 查询所有待安装的终端信息（机械注册页面扫条码用）
	 */
	public String searchUsefulUnitInfoList() {
		try {
			List<UnitPOJO> materialNoList = vehicleService.getUsefulUnitInfoList();
			renderObject(materialNoList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return NONE;
	}

	/**
	 * 查询终端信息，根据unitId
	 */
	public String searchByUnitId() throws Exception {
		String unitId = getRequest().getParameter("obj");
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("datas", vehicleService.searchByUnitId(unitId));
		renderObject(result);
		return NONE;
	}

	/**
	 * 查询机械信息，返回list
	 */
	public String getList() throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("datas", vehicleService.getList(vehiclePOJO));
		renderObject(result);
		return NONE;
	}

	/**
	 * 获得机械信息，返回到修改页面
	 * 
	 * @return
	 */
	public String getById() throws Exception {
		vehiclePOJO = vehicleService.getById(vehiclePOJO.getVehicleId());
		// int count = vehicleService.getTestById(vehiclePOJO.getVehicleId());
		if(vehiclePOJO.getUnitId() != null){
			testPOJO = vehicleTestService.getTestByUnitId(vehiclePOJO.getUnitId());
		}		 
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("datas", vehiclePOJO);
		result.put("test", testPOJO);
		// result.put("count", count);
		renderObject(result);
		// renderObject(vehiclePOJO);
		return NONE;
	}

	/**
	 * 解除绑定
	 * 
	 * @return
	 */
	@OperationLog(description = "终端解除绑定")
	public String removeUnitSn() {
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();
		boolean flag = false;
		try {
			flag = vehicleService.unbindVehicleUnit(vehiclePOJO);
			result.put("datas", flag);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	}

	/**
	 * 获得机械信息，返回到机械测试页面
	 * 
	 * @return
	 */
	public String enterTest() throws Exception {
		vehiclePOJO = vehicleService.getById(vehiclePOJO.getVehicleId());
		UserPOJO user = (UserPOJO) getSession().get(Constants.USER_INFO);
		testPerson = (String) user.getUserName();// 得到用户名
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		testTime = sdf.format(new Date());
		return "enterTest";
	}

	/**
	 * 新增或修改机械信息
	 * 
	 * @return
	 */
	@OperationLog(description = "机械保存")
	public String saveOrUpdate() {
		String msg = "";
		boolean result = true;
		if (vehiclePOJO != null) {
			try {
				//由于控件id取名为modelName，实际存的是modelId.
				vehiclePOJO.setModelId(Long.valueOf(vehiclePOJO.getModelName()));
				result = vehicleService.saveOrUpdate(vehiclePOJO);
				msg = OP_SUCCESS;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
				msg = OP_FAIL;
			}
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	
	/**
	 * @Title:select
	 * @Description:
	 * @return
	 * @throws
	 */
	@OperationLog(description = "根据vehicleId查询")
	public String selectVehicleByVehicleId() {
		try {
			renderObject(vehicleService.selectVehicleByVehicleId(vehiclePOJO.getVehicleId().toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	/**
	 * @Title:update
	 * @Description:
	 * @return
	 * @throws
	 */
	@OperationLog(description = "更新销售日期")
	public String updateSaleDate() {
		try {
			vehicleService.updateVehicleSaleDate(vehiclePOJO);
			renderMsgJson(true,"成功");
		} catch (Exception e) {
			e.printStackTrace();
			renderMsgJson(false,"失败");
		}
		return NONE;
	}
	
	/**
	 * @Title:delete
	 * @Description:
	 * @return
	 * @throws
	 */
	@OperationLog(description = "机械逻辑删除")
	public String delete() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			vehicleService.delVehicleById(vehiclePOJO.getVehicleId());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	
	/**
	 * 批量物理删除机械信息
	 * 
	 * @return
	 */
	@OperationLog(description = "机械物理删除")
	public String deleteInRecycle() {
		boolean result = false;
		String msg = OP_FAIL;
		try {
			result = vehicleService.deleteInRecycle(vehicleIds);
			msg = OP_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	/**
	 * 批量还原
	 * 
	 * @return
	 */
	@OperationLog(description = "机械批量还原")
	public String updateVehiclesIsValid() {
		boolean result = true;
		String msg = OP_SUCCESS;

		try {
			vehicleService.updateVehiclesIsValid(vehicleIds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	/**
	 * 修改机械信息
	 * 
	 * @return
	 */
	@OperationLog(description = "机械修改")
	public String update() {
		boolean flag = false;
		String msg = OP_FAIL;
		try {
			flag = vehicleService.saveOrUpdate(vehiclePOJO);
			msg = OP_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(flag, msg);
		return NONE;
	}

	@OperationLog(description = "机械修改状态")
	public String updateStatus() {
		boolean flag = false;
		String msg = OP_FAIL;
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);

		try {
			vehiclePOJO.setUserName(userPOJO.getUserName()); // 保存修改人姓名
			flag = vehicleService.updateStatus(vehiclePOJO);
			msg = OP_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(flag, msg);
		return NONE;
	}

	/**
	 * 查询机械测试指令
	 * 
	 * @return
	 */
	public String getCommandType() throws Exception {
		TestCommandPOJO TestCommandPOJO2 = new TestCommandPOJO();
		TestCommandPOJO2.setCommandTypeId(null);
		TestCommandPOJO2.setCommandTypeName("全部");
		List<TestCommandPOJO> result = new ArrayList<TestCommandPOJO>();
		result.add(TestCommandPOJO2);
		result.addAll(vehicleService.getCommandType());

		renderObject(result);

		return NONE;
	}

	/**
	 * 导入功能
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@OperationLog(description = "机械批量导入")
	public void impFromExcel() throws Exception {
		String msg = "导入成功!";
		Boolean result = false;
		ExcelUtils excelUtils = new ExcelUtils();
		Map eMap = excelUtils.readExcel(upload);
		if (eMap == null || "".equals(eMap)) {
			msg = "文格为空或格式错误";
			renderMsgJson(result, msg);
			return;
		}
		// 清除临时文件
		upload.delete();
		upload = null;

		List<VehiclePOJO> excelList = new ArrayList<VehiclePOJO>();
		excelList = (List) eMap.get("values");
		for (VehiclePOJO ve : excelList) {

			if (ve.getUnitSn() == null || "".equals(ve.getUnitSn())) {
				msg = "文件格式错误!";
				break;
			}
			result = vehicleService.saveExcel(ve);
			if (!result) {
				msg = "excel中整机编号为【" + ve.getVehicleDef() + "】的数据已经存在!";
				break;
			}
		}

		// if(result){
		renderMsgJson(result, msg);
		// }

	}

	//机械测试日志记录导出
	public void exportToExcel() throws Exception {
		List<Object[]> values = new ArrayList<Object[]>();
		String vehicleDef = java.net.URLDecoder.decode(getRequest()
				.getParameter("vehicleDef"), "utf-8");
		String startTime = java.net.URLDecoder.decode(getRequest()
				.getParameter("startTime"));
		String endTime = java.net.URLDecoder.decode(getRequest().getParameter(
				"endTime"));
		String vehicleType = java.net.URLDecoder.decode(getRequest()
				.getParameter("vehicleType"));
		VehiclePOJO vehiclePOJO = new VehiclePOJO();
		if (vehicleType != null && !"".equals(vehicleType)) {
			vehiclePOJO.setTypeId(Long.valueOf(vehicleType));
		}
		vehiclePOJO.setVehicleDef(vehicleDef);
		vehiclePOJO.setFixDateStart(DateUtil.parse(startTime,
				"yyyy-MM-dd HH:mm:ss"));
		vehiclePOJO.setFixDateEnd(DateUtil
				.parse(endTime, "yyyy-MM-dd HH:mm:ss"));
		vehicleService.getVehiclesTest(vehiclePOJO, pageSelect);
		List<TestCommandPOJO> list = vehicleService.selectExcel(vehiclePOJO);
		for (TestCommandPOJO testCommandPOJO : list) {
			if (testCommandPOJO.getCommandResult() == null) {
				testCommandPOJO.setComResult("失败");
			}
			if (testCommandPOJO.getCommandResult() != null) {
				if (testCommandPOJO.getCommandResult() == 0) {
					testCommandPOJO.setComResult("成功");
				}
			}
			if ("00".equals(testCommandPOJO.getUnitBack())) {
				testCommandPOJO.setUnitBack("成功");
			} else {
				testCommandPOJO.setUnitBack("失败");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data = sdf.format(testCommandPOJO.getStamp());
			values.add(new Object[] { testCommandPOJO.getCommandSn(),
					testCommandPOJO.getVehicleDef(),
					testCommandPOJO.getUserName(),
					testCommandPOJO.getCommandTypeName(),
					testCommandPOJO.getParam(), testCommandPOJO.getComResult(),
					testCommandPOJO.getUnitBack(), data });
		}
		String[] headers = new String[] { "流水号", "整机编号", "指令下发操作人", "指令类型",
				"指令类型参数", "网关回应", "终端回应", "测试时间" };
		super.renderExcel("机械测试日志" + ".xls", headers, values);

	}
	
	//机械注册导出
	public void exportToExcelVehicle() throws Exception {
		List<Object[]> values = new ArrayList<Object[]>();
		String unitSn = java.net.URLDecoder.decode(getRequest()	.getParameter("unitSn"), "utf-8");
		String simNo = java.net.URLDecoder.decode(getRequest().getParameter("simNo"), "utf-8");
		String vehicleDef = java.net.URLDecoder.decode(getRequest().getParameter("vehicleDef"), "utf-8");
		String modelName = java.net.URLDecoder.decode(getRequest().getParameter("modelName"), "utf-8");
		String vehicleCode = java.net.URLDecoder.decode(getRequest().getParameter("vehicleCode"), "utf-8");
		String vehicleArg = java.net.URLDecoder.decode(getRequest().getParameter("vehicleArg"), "utf-8");
		String status = java.net.URLDecoder.decode(getRequest().getParameter("status"), "utf-8");
		String fixMan = java.net.URLDecoder.decode(getRequest().getParameter("fixMan"), "utf-8");
		String fixDateStart = java.net.URLDecoder.decode(getRequest().getParameter("fixDateStart"), "utf-8");
		String fixDateEnd = java.net.URLDecoder.decode(getRequest().getParameter("fixDateEnd"), "utf-8");
		String testFlag = java.net.URLDecoder.decode(getRequest().getParameter("testFlag"), "utf-8");
		VehiclePOJO vehiclePOJO = new VehiclePOJO();
		if (unitSn != null && !"".equals(unitSn)) {
			vehiclePOJO.setUnitSn(unitSn);
		}
		if (simNo != null && !"".equals(simNo)) {
			vehiclePOJO.setSimNo(simNo);
		}
		if (vehicleDef != null && !"".equals(vehicleDef)) {
			vehiclePOJO.setVehicleDef(vehicleDef);
		}
		if (status != null && !"".equals(status)) {
			vehiclePOJO.setStatus(Integer.valueOf(status));
		}
		if (modelName != null && !"".equals(modelName)) {
			vehiclePOJO.setModelName(modelName);
		}
		if (vehicleCode != null && !"".equals(vehicleCode)) {
			vehiclePOJO.setVehicleCode(vehicleCode);
		}
		if (vehicleArg != null && !"".equals(vehicleArg)) {
			vehiclePOJO.setVehicleArg(vehicleArg);
		}
		if (fixMan != null && !"".equals(fixMan)) {
			vehiclePOJO.setFixMan(fixMan);
		}
		if (fixDateStart != null && !"".equals(fixDateStart)) {
			vehiclePOJO.setFixDateStart(DateUtil.parse(fixDateStart,"yyyy-MM-dd HH:mm:ss"));
		}
		if (fixDateEnd != null && !"".equals(fixDateEnd)) {
			vehiclePOJO.setFixDateEnd(DateUtil.parse(fixDateEnd,"yyyy-MM-dd HH:mm:ss"));
		}
		if (testFlag != null && !"".equals(testFlag)) {
			vehiclePOJO.setTestFlag(Integer.valueOf(testFlag));
		}
		//TODU;
		List<VehiclePOJO> list = vehicleService.getByVihicleList(vehiclePOJO);
		for (VehiclePOJO VehiclePOJO : list) {
			String data ="";
			String fixDate="";
			String statusTime="";
			String status1="";
			String testFlag1="";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!"".equals(VehiclePOJO.getStamp()) && VehiclePOJO.getStamp()!=null ){
			data = sdf.format(VehiclePOJO.getStamp());
			}
			if(!"".equals(VehiclePOJO.getFixDate()) && VehiclePOJO.getFixDate()!=null ){
				fixDate = sdf.format(VehiclePOJO.getFixDate());
			}
			if(!"".equals(VehiclePOJO.getStatusTime()) && VehiclePOJO.getStatusTime()!=null ){
				statusTime = sdf.format(VehiclePOJO.getStatusTime());
			}
			if(VehiclePOJO.getStatus()==1){
				status1 = "测试组";
			}
			if(VehiclePOJO.getStatus()==2){
				status1 = "已测组";
			}
			if(VehiclePOJO.getStatus()==3){
				status1 = "销售组";
			}
			if(VehiclePOJO.getStatus()==8){
				status1 = "法务组";
			}
			if(VehiclePOJO.getStatus()==9){
				status1 = "停用组";
			}
			if(VehiclePOJO.getTestFlag()==0){
				testFlag1 = "测试未通过";
			}
			if(VehiclePOJO.getTestFlag()==1){
				testFlag1 = "测试通过";
			}
			
			
			values.add(new Object[] { 
					VehiclePOJO.getVehicleDef(),
					VehiclePOJO.getModelName(),
					VehiclePOJO.getVehicleCode(),
					VehiclePOJO.getVehicleArg(),
					VehiclePOJO.getUnitSn(),
					VehiclePOJO.getSimNo(),
					VehiclePOJO.getFixMan(),
					fixDate,
					status1,
					statusTime,
					testFlag1, 
					data });
			
		}
		String[] headers = new String[] { "整机编号", "机械型号", "机械代码","机械配置","终端序列号",
				"SIM卡号", "终端安装人", "终端安装日期", "机械状态(组)", "状态修改时间", "测试结果","最后修改时间"};
		super.renderExcel("机械注册" + ".xls", headers, values);

	}
	
	
	public void selectVehicleMod() throws Exception{
		HashMap<String, Object> result = new HashMap<String, Object>();
		VehiclePOJO vePOJO = vehicleService.selectVehicleMod(vehiclePOJO);
		result.put("vePOJO", vePOJO);
		renderObject(result);
	}
	
	

	public IVehicleService getVehicleService() {
		return vehicleService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public VehiclePOJO getVehiclePOJO() {
		return vehiclePOJO;
	}

	public void setVehiclePOJO(VehiclePOJO vehiclePOJO) {
		this.vehiclePOJO = vehiclePOJO;
	}

	public PageSelect getPageSelect() {
		return pageSelect;
	}

	public void setPageSelect(PageSelect pageSelect) {
		this.pageSelect = pageSelect;
	}

	public String getTestPerson() {
		return testPerson;
	}

	public void setTestPerson(String testPerson) {
		this.testPerson = testPerson;
	}

	public String getTestTime() {
		return testTime;
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

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	@Override
	public VehiclePOJO getModel() {
		return vehiclePOJO;
	}

	public TestPOJO getTestPOJO() {
		return testPOJO;
	}

	public void setTestPOJO(TestPOJO testPOJO) {
		this.testPOJO = testPOJO;
	}

	public TestCommandPOJO getTestCommandPOJO() {
		return testCommandPOJO;
	}

	public void setTestCommandPOJO(TestCommandPOJO testCommandPOJO) {
		this.testCommandPOJO = testCommandPOJO;
	}

	public List<String> getVehicleIds() {
		return vehicleIds;
	}

	public void setVehicleIds(List<String> vehicleIds) {
		this.vehicleIds = vehicleIds;
	}

	public IVehicleTestService getVehicleTestService() {
		return vehicleTestService;
	}

	public void setVehicleTestService(IVehicleTestService vehicleTestService) {
		this.vehicleTestService = vehicleTestService;
	}

}