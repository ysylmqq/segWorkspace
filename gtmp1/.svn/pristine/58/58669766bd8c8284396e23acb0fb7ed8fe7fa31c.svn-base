package com.chinaGPS.gtmp.action.sim;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.action.common.ExcelUtils;
import com.chinaGPS.gtmp.entity.SimReplaceLogPOJO;
import com.chinaGPS.gtmp.entity.SimServerPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.ISimReplaceLogService;
import com.chinaGPS.gtmp.service.ISimServerService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 客户缴费 管理action
 * @author ysy
 *
 */

@Scope("prototype")
@Controller
public class CompanySimAction extends BaseAction implements ModelDriven<SimServerPOJO> {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(CompanySimAction.class);
	
	@Resource
	private SimServerPOJO simServerPOJO;

	@Resource
	private ISimServerService iSimServerService;
	
	@Resource
	private ISimReplaceLogService iSimReplaceLogService;
	
	
	@Resource
	private PageSelect pageSelect;
	private File upload;
	private String uploadFileName;

	private int page;
	private int rows;

	/**
	 * 客户缴费查询，返回分页数据
	 */
	public String search() {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		try {
			String dealerIdStr = getRequest().getParameter("dealerId") ;
			if(StringUtils.isNotEmpty(dealerIdStr)){
				String dealerId = java.net.URLDecoder.decode(dealerIdStr, "utf-8");
				if(StringUtils.isNotEmpty(dealerId)){
					simServerPOJO.setDealerIds(dealerId.split(","));
				}
			}
			renderObject(iSimServerService.getSimServer(simServerPOJO, pageSelect));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	/**
	 * 机械总收益，返回分页数据
	 */
	public String allProfit() {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		try {
			String dealerIdStr = getRequest().getParameter("dealerId") ;
			if(StringUtils.isNotEmpty(dealerIdStr)){
				String dealerId = java.net.URLDecoder.decode(dealerIdStr, "utf-8");
				if(StringUtils.isNotEmpty(dealerId)){
					simServerPOJO.setDealerIds(dealerId.split(","));
				}
			}
			renderObject(iSimServerService.allProfit(simServerPOJO, pageSelect));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	

	/**
	 * 新的更换终端方法
	 * @return
	 */
	public String changeSimSearch(){
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		try {
			SimReplaceLogPOJO simReplaceLog = new SimReplaceLogPOJO();
			String vehicleDef = getRequest().getParameter("vehicleDef");
			if(StringUtils.isNotEmpty(vehicleDef)){
				simReplaceLog.setVehicleDef(vehicleDef);
			}
			renderObject(iSimReplaceLogService.getSimReplaceLog(simReplaceLog, pageSelect));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	public String changeUnitsearch(){
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		try {
			String dealerIdStr = getRequest().getParameter("dealerId") ;
			if(StringUtils.isNotEmpty(dealerIdStr)){
				String dealerId = java.net.URLDecoder.decode(dealerIdStr, "utf-8");
				if(StringUtils.isNotEmpty(dealerId)){
					simServerPOJO.setDealerIds(dealerId.split(","));
				}
			}
			renderObject(iSimServerService.changeUnitsearch(simServerPOJO, pageSelect));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/**
	 * 公司单个sim_server插入
	 */
	public String insert(){
    	UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	simServerPOJO.setOperId(userPOJO.getUserId()+"");
		try {
			iSimServerService.insertSelective(simServerPOJO);
		} catch (Exception e) {
			renderObject(0);
			e.printStackTrace();
			return NONE;
		}
		renderObject(1);
		return NONE;
	}
	
	
	/**
	 * 更新sim_server插入
	 */
	public String updateSimServer(){
    	UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	simServerPOJO.setOperId(userPOJO.getUserId()+"");
		try {
			iSimServerService.updateByPrimaryKeySelective(simServerPOJO);
		} catch (Exception e) {
			renderMsgJson(false, "更新失败");
			e.printStackTrace();
			return NONE;
		}
		renderMsgJson(true, "更新失败");
		return NONE;
	}
	
	
	/**
	 * 获取t_sim_server 
	 * @return
	 */
	public String simServerById(){
		SimServerPOJO customerSimPOJO =  iSimServerService.getSimServerPOJOById(simServerPOJO.getSimNo());
		if(customerSimPOJO != null){
			Date endTime = customerSimPOJO.getEndTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				customerSimPOJO.setEndTime(sdf.parse(sdf.format(endTime)));
				customerSimPOJO.setRemark(sdf.format(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		renderObject(customerSimPOJO);
		return NONE;
	}
	
	/**
	 * 导入功能
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@OperationLog(description = "机械批量导入")
	public String impFromExcel() {
		String msg = "导入成功!";
		Map<String,Object> map = new HashMap<String,Object>();
		ExcelUtils excelUtils = new ExcelUtils();
		Map eMap = null;
		try {
			eMap = excelUtils.readExcelSimServer(upload,"SIM卡号!SIM开卡费!服务开始日期!服务结束日期!备注!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Boolean flag = (Boolean) eMap.get("flag");
		if(!flag){
			map.put("flag","0");
			map.put("tips","模板错误");
			renderObject(map);
			return NONE;
		}
		// 清除临时文件
		upload.delete();
		upload = null;
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	String userId = userPOJO.getUserId()+"";
		List<SimServerPOJO> excelList = new ArrayList<SimServerPOJO>();
		excelList = (List) eMap.get("values");
		List<SimServerPOJO> errorList = null;
		try {
			errorList = iSimServerService.batchInsert(excelList,userId);
		} catch (Exception e) {
			map.put("flag","3");
			map.put("tips","导出数据出错");
			e.printStackTrace();
		}
		if(errorList == null || errorList.size() == 0 ){
			map.put("flag","1");
			map.put("tips","导入成功");
		}else{
			map.put("flag","2");
			map.put("tips","导入失败数据");
			map.put("errorList",errorList);
		}
		renderObject(map);
		return NONE;
	}
	
	
	/**
	 * 公司SIM卡批量注销
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@OperationLog(description = "公司SIM卡批量注销")
	public String impBatchCancelCompanySim() {
		String msg = "注销成功!";
		Map<String,Object> map = new HashMap<String,Object>();
		ExcelUtils excelUtils = new ExcelUtils();
		Map eMap = null;
		try {
			eMap = excelUtils.readExcelBatchCancelSimServer(upload,"SIM卡号!注销日期!注销原因!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Boolean flag = (Boolean) eMap.get("flag");
		if(!flag){
			map.put("flag","0");
			map.put("tips","模板错误");
			renderObject(map);
			return NONE;
		}
		// 清除临时文件
		upload.delete();
		upload = null;
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	String userId = userPOJO.getUserId()+"";
		List<SimServerPOJO> excelList = new ArrayList<SimServerPOJO>();
		excelList = (List) eMap.get("values");
		List<SimServerPOJO> errorList = null;
		try {  //批量注销
			errorList = iSimServerService.batchCancelSim(excelList,userId);
		} catch (Exception e) {
			map.put("flag","3");
			map.put("tips","批量注销数据出错");
			e.printStackTrace();
		}
		if(errorList == null || errorList.size() == 0 ){
			map.put("flag","1");
			map.put("tips","批量注销成功");
		}else{
			map.put("flag","2");
			map.put("tips","注销失败数据");
			map.put("errorList",errorList);
		}
		renderObject(map);
		return NONE;
	}
	
	/**
	 * 导出更换终端
	 * @throws Exception
	 */
		public void exportToExcelChangeUnit() throws Exception {
			List<Object[]> values = new ArrayList<Object[]>();
			String simNo = java.net.URLDecoder.decode(getRequest().getParameter("simNo"), "utf-8");
			String status = java.net.URLDecoder.decode(getRequest().getParameter("status"), "utf-8");
			String openTime = java.net.URLDecoder.decode(getRequest().getParameter("openTime"), "utf-8");
			String endTime = java.net.URLDecoder.decode(getRequest().getParameter("endTime"), "utf-8");
			String vehicleDef = java.net.URLDecoder.decode(getRequest().getParameter("vehicleDef"), "utf-8");
			String dealerId = java.net.URLDecoder.decode(getRequest().getParameter("dealerId"), "utf-8");
			
			SimServerPOJO simServerPOJO = new SimServerPOJO();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (simNo != null && !"".equals(simNo)) {
				simServerPOJO.setSimNo(simNo);
			}
			
			if (status != null && !"".equals(status)) {
				simServerPOJO.setStatus(new BigDecimal(status));
			}

			if (openTime != null && !"".equals(openTime)) {
				simServerPOJO.setOpenTime(sdf.parse(openTime));
			}
			
			if (endTime != null && !"".equals(endTime)) {
				simServerPOJO.setEndTime(sdf.parse(endTime));
			}
			
			if (vehicleDef != null && !"".equals(vehicleDef)) {
				simServerPOJO.setVehicleDef(vehicleDef);
			}
			if (dealerId != null && !"".equals(dealerId)) {
				simServerPOJO.setDealerIds(dealerId.split(","));
			}
			//TODU;
			List<SimServerPOJO> list = iSimServerService.exportChangeUnit(simServerPOJO);
			if(list != null && list.size() > 0 ){
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					SimServerPOJO simServerPOJO2 = (SimServerPOJO) iterator.next();
					String sta = "";
					if( simServerPOJO2.getStatus().intValue() == 1 ){
						sta = "注销";
					}else{
						sta = "开通";
					}
					
					Date openTimes = simServerPOJO2.getOpenTime();
					String  openTimeTem = "";
					if( openTimes != null){
						openTimeTem = sdf.format(openTimes);
					}
					
					Date endTimes = simServerPOJO2.getEndTime();
					String  endTimeTem = "";
					if( endTimes != null){
						endTimeTem = sdf.format(endTimes);
					}
					
					Date stopTimes = simServerPOJO2.getStopTime();
					String  stopTimeTem = "";
					if( stopTimes != null){
						stopTimeTem = sdf.format(stopTimes);
					}
					String statusTips = "";
					String isServer = simServerPOJO2.getIsServer();
					if( isServer != null  && !"".equalsIgnoreCase(isServer) ){
						int  isServerInt = Integer.parseInt(isServer);
						if( isServerInt > 0){
							statusTips = "再有"+isServerInt+"天到期";
						} else if( isServerInt < 0){
							statusTips = "欠费"+isServerInt+"天";
						} else {
							statusTips = "今天到期";
						}
					}else{
						statusTips = "未开通";
					}
					
					Date createTime = simServerPOJO2.getCreateTime();
					String  createTimeStr = "";
					if( createTime != null){
						if( createTimeStr != null){
							createTimeStr = sdf.format(createTime);
						}
					}
					values.add(new Object[] { 
							simServerPOJO2.getVehicleDef(),
							simServerPOJO2.getDistributor(),
							simServerPOJO2.getModelName(),
							simServerPOJO2.getVehicleArg(),
							simServerPOJO2.getUnitSn(),
							simServerPOJO2.getSimNo(),
							openTimeTem,
							endTimeTem,
							sta,
							stopTimeTem,
							simServerPOJO2.getStopReason(),
							simServerPOJO2.getRemark(),
							simServerPOJO2.getUserName(),
							createTimeStr
						});
				}
			}
			String[] headers = new String[] { "整机编号","经销商","机械型号","机械配置","终端序列号","SIM卡号", "服务开始日期","服务结束日期","卡号情况",
					"注销日期", "注销原因", "备注","操作人","创建日期"};
			super.renderExcel("终端换装" + ".xls", headers, values);
		}
		
		

		/**
		 * 导出更换终端
		 * @throws Exception
		 */
			public void exportToExcelChangeSim() throws Exception {
				List<Object[]> values = new ArrayList<Object[]>();
				String vehicleDef = java.net.URLDecoder.decode(getRequest().getParameter("vehicleDef"), "utf-8");
				
				SimReplaceLogPOJO simReplaceLogPOJO = new SimReplaceLogPOJO();
				
				//TODU;
				List<SimReplaceLogPOJO> list = iSimReplaceLogService.exportSimReplaceLog(simReplaceLogPOJO);
				if(list != null && list.size() > 0 ){
					for (Iterator iterator = list.iterator(); iterator.hasNext();) {
						SimReplaceLogPOJO simServerPOJO2 = (SimReplaceLogPOJO) iterator.next();

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						
						Date newStartDate = simServerPOJO2.getNewStartDate();
						String  newStartDateTem = "";
						if( newStartDate != null){
							newStartDateTem = sdf.format(newStartDate);
						}
						
						Date newEndDate = simServerPOJO2.getNewEndDate();
						String  newEndDateTem = "";
						if( newEndDate != null){
							newEndDateTem = sdf.format(newEndDate);
						}
						
						Date oldStartDate = simServerPOJO2.getOldStartDate();
						String  oldStartDateTem = "";
						if( oldStartDate != null){
							oldStartDateTem = sdf.format(oldStartDate);
						}
						
						
						Date oldEndDate = simServerPOJO2.getOldEndDate();
						String  oldEndDateTem = "";
						if( oldEndDate != null){
							oldEndDateTem = sdf.format(oldEndDate);
						}
						
						
						Date replaceDate = simServerPOJO2.getReplaceDate();
						String  replaceDateTem = "";
						if( replaceDate != null){
							replaceDateTem = sdf.format(replaceDate);
						}
						
						Date stampDate = simServerPOJO2.getStamp();
						String  stampDateTem = "";
						if( stampDate != null){
							stampDateTem = sdf.format(stampDate);
						}
						
						
						values.add(new Object[] { 
								simServerPOJO2.getVehicleDef(),
								simServerPOJO2.getNewCallLetter(),
								newStartDateTem,
								newEndDateTem,
								simServerPOJO2.getNewAllFee(),
								simServerPOJO2.getOldCallLetter(),
								oldStartDateTem,
								oldEndDateTem,
								simServerPOJO2.getOldAllFee(),
								simServerPOJO2.getReason(),
								simServerPOJO2.getReplaceUser(),
								replaceDateTem,
								simServerPOJO2.getUserName(),
								stampDateTem
							});
					}
				}
				String[] headers = new String[] { 
						"整机编号","新SIM卡号","新卡服务开始日期","新卡服务结束日期","新卡所有费用","原来的SIM卡号", 
						"原卡服务开始日期","原卡服务结束日期","原卡所有费用","换卡原因", "换卡人", "换卡时间","操作人","操作日期"};
				super.renderExcel("更换SIM卡" + ".xls", headers, values);
			}
		
	
	
	/**
	 * 导出公司SIM信息
	 * @throws Exception
	 */
	public void exportToExcelVehicle() throws Exception {
		List<Object[]> values = new ArrayList<Object[]>();
		String simNo = java.net.URLDecoder.decode(getRequest().getParameter("simNo"), "utf-8");
		String status = java.net.URLDecoder.decode(getRequest().getParameter("status"), "utf-8");
		String openTime = java.net.URLDecoder.decode(getRequest().getParameter("openTime"), "utf-8");
		String endTime = java.net.URLDecoder.decode(getRequest().getParameter("endTime"), "utf-8");
		String vehicleDef = java.net.URLDecoder.decode(getRequest().getParameter("vehicleDef"), "utf-8");
		String dealerId = java.net.URLDecoder.decode(getRequest().getParameter("dealerId"), "utf-8");
		
		SimServerPOJO simServerPOJO = new SimServerPOJO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (simNo != null && !"".equals(simNo)) {
			simServerPOJO.setSimNo(simNo);
		}
		
		if (status != null && !"".equals(status)) {
			simServerPOJO.setStatus(new BigDecimal(status));
		}

		if (openTime != null && !"".equals(openTime)) {
			simServerPOJO.setOpenTime(sdf.parse(openTime));
		}
		
		if (endTime != null && !"".equals(endTime)) {
			simServerPOJO.setEndTime(sdf.parse(endTime));
		}
		
		if (vehicleDef != null && !"".equals(vehicleDef)) {
			simServerPOJO.setVehicleDef(vehicleDef);
		}
		if (dealerId != null && !"".equals(dealerId)) {
			simServerPOJO.setDealerIds(dealerId.split(","));
		}
		//TODU;
		List<SimServerPOJO> list = iSimServerService.exportSimServer(simServerPOJO);
		if(list != null && list.size() > 0 ){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SimServerPOJO simServerPOJO2 = (SimServerPOJO) iterator.next();
				String sta = "";
				if( simServerPOJO2.getStatus().intValue() == 1 ){
					sta = "注销";
				}else{
					sta = "开通";
				}
				
				Date openTimes = simServerPOJO2.getOpenTime();
				String  openTimeTem = "";
				if( openTimes != null){
					openTimeTem = sdf.format(openTimes);
				}
				
				Date endTimes = simServerPOJO2.getEndTime();
				String  endTimeTem = "";
				if( endTimes != null){
					endTimeTem = sdf.format(endTimes);
				}
				
				Date stopTimes = simServerPOJO2.getStopTime();
				String  stopTimeTem = "";
				if( stopTimes != null){
					stopTimeTem = sdf.format(stopTimes);
				}
				String statusTips = "";
				String isServer = simServerPOJO2.getIsServer();
				if( isServer != null  && !"".equalsIgnoreCase(isServer) ){
					int  isServerInt = Integer.parseInt(isServer);
					if( isServerInt > 0){
						statusTips = "再有"+isServerInt+"天到期";
					} else if( isServerInt < 0){
						statusTips = "欠费"+isServerInt+"天";
					} else {
						statusTips = "今天到期";
					}
				}else{
					statusTips = "未开通";
				}
				
				Date createTime = simServerPOJO2.getCreateTime();
				String  createTimeStr = "";
				if( createTime != null){
					if( createTimeStr != null){
						createTimeStr = sdf.format(createTime);
					}
				}
				values.add(new Object[] { 
						simServerPOJO2.getVehicleDef(),
						simServerPOJO2.getDistributor(),
						simServerPOJO2.getModelName(),
						simServerPOJO2.getVehicleArg(),
						simServerPOJO2.getUnitSn(),
						simServerPOJO2.getSimNo(),
						simServerPOJO2.getPayAmount(),
						statusTips,
						openTimeTem,
						endTimeTem,
						sta,
						stopTimeTem,
						simServerPOJO2.getStopReason(),
						simServerPOJO2.getRemark(),
						simServerPOJO2.getUserName(),
						createTimeStr
						});
			}
			
		}
		String[] headers = new String[] { "整机编号","经销商","机械型号","机械配置","终端序列号","SIM卡号", "SIM成本","服务状态","服务开始日期","服务结束日期","卡号状态",
				"注销日期", "注销原因", "备注","操作人","创建日期"};
		super.renderExcel("公司SIM卡" + ".xls", headers, values);
	}
	
	
	/**
	 * 导出机械收益
	 * @throws Exception
	 */
	public void exportToExcelVehicleProfit() throws Exception {
		List<Object[]> values = new ArrayList<Object[]>();
		String simNo = java.net.URLDecoder.decode(getRequest().getParameter("simNo"), "utf-8");
		String status = java.net.URLDecoder.decode(getRequest().getParameter("status"), "utf-8");
		String openTime = java.net.URLDecoder.decode(getRequest().getParameter("openTime"), "utf-8");
		String endTime = java.net.URLDecoder.decode(getRequest().getParameter("endTime"), "utf-8");
		String vehicleDef = java.net.URLDecoder.decode(getRequest().getParameter("vehicleDef"), "utf-8");
		String dealerIdStr = getRequest().getParameter("dealerId") ;
		
		
		SimServerPOJO simServerPOJO = new SimServerPOJO();
		if(StringUtils.isNotEmpty(dealerIdStr)){
			String dealerId = java.net.URLDecoder.decode(dealerIdStr, "utf-8");
			if(StringUtils.isNotEmpty(dealerId)){
				simServerPOJO.setDealerIds(dealerId.split(","));
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (simNo != null && !"".equals(simNo)) {
			simServerPOJO.setSimNo(simNo);
		}
		
		if (status != null && !"".equals(status)) {
			simServerPOJO.setStatus(new BigDecimal(status));
		}

		if (openTime != null && !"".equals(openTime)) {
			simServerPOJO.setOpenTime(sdf.parse(openTime));
		}
		
		if (endTime != null && !"".equals(endTime)) {
			simServerPOJO.setEndTime(sdf.parse(endTime));
		}
		
		if (vehicleDef != null && !"".equals(vehicleDef)) {
			simServerPOJO.setVehicleDef(vehicleDef);
		}
		//TODU;
		List<SimServerPOJO> list = null;
		try{
			list= iSimServerService.allVehicleProfit(simServerPOJO);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(list != null && list.size() > 0 ){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SimServerPOJO simServerPOJO2 = (SimServerPOJO) iterator.next();
				String sta = "";
				String custSta = "";
				try{
					if(simServerPOJO2.getStatus() != null){
						if( simServerPOJO2.getStatus().intValue() == 1 ){
							sta = "注销";
						}else{
							sta = "开通";
						}
					}else{
						sta = "该机型没有安装终端";
					}
					if(simServerPOJO2.getCustStatus() != null){
						if( simServerPOJO2.getCustStatus().intValue() == 1 ){
							custSta = "停机保号";
						}else{
							custSta = "开通";
						}
					}else{
						custSta = "客户没有开通SIM服务";
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				Date openTimes = simServerPOJO2.getOpenTime();
				String  openTimeTem = "";
				if( openTimes != null){
					openTimeTem = sdf.format(openTimes);
				}
				
				Date endTimes = simServerPOJO2.getEndTime();
				String  endTimeTem = "";
				if( endTimes != null){
					endTimeTem = sdf.format(endTimes);
				}
				
				Date stopTimes = simServerPOJO2.getStopTime();
				String  stopTimeTem = "";
				if( stopTimes != null){
					stopTimeTem = sdf.format(stopTimes);
				}
				
				Date createTimes = simServerPOJO2.getCreateTime();
				String  createTimeTem = "";
				if( createTimes != null){
					createTimeTem = sdf.format(createTimes);
				}
				
				String isServerStr = simServerPOJO2.getIsServer();
				if( isServerStr != null && !"".equalsIgnoreCase(isServerStr) ){
					int isServerInt = Integer.parseInt(isServerStr);
					if( isServerInt > 0){
						isServerStr = "再有"+isServerInt+"天到期";
					}else if ( isServerInt < 0){
						isServerStr = "欠费"+isServerInt+"天";
					}else if ( isServerInt == 0){
						isServerStr = "今天到期";
					}
				}else{
					isServerStr = "未开通";
				}
				
				float companyAllSimFee = 0;
				companyAllSimFee = simServerPOJO2.getCompanyAllPay() - simServerPOJO2.getPayAmount().floatValue();
				
				values.add(new Object[] { 
						simServerPOJO2.getVehicleDef(),
						simServerPOJO2.getDistributor(),
						simServerPOJO2.getModelName(),
						simServerPOJO2.getUnitSn(),
						simServerPOJO2.getSimNo(),
						isServerStr,
						openTimeTem,
						endTimeTem,
						sta,
						custSta,
						stopTimeTem,
						simServerPOJO2.getStopReason(),
						simServerPOJO2.getRemark(),
						simServerPOJO2.getUserName(),
						createTimeTem,
						simServerPOJO2.getCustomerSimAllPay(),
						simServerPOJO2.getCustomerStopAllFee(),
						simServerPOJO2.getCustomerAllPay(),
						simServerPOJO2.getPayAmount(),
						companyAllSimFee,
						simServerPOJO2.getCompanyAllPay(),
						simServerPOJO2.getAllProfit()
					});
			}
		}
		String[] headers = new String[] { "整机编号","经销商","机械型号","终端序列号","SIM卡号", "服务状态","服务开始日期","服务结束日期","公司开卡情况","客户开卡情况","注销时间","注销原因",
										"备注","操作人","创建日期","客户SIM缴费总额","客户停机保号总额","客户总缴费","SIM成本","公司SIM缴费总额","公司总缴费","收益"};
		super.renderExcel("机械收益情况" + ".xls", headers, values);
	}
	
	/**
	 * 获取getCustomerPayPOJO By custPayId  
	 * @return
	 */
	public String getSimServerPOJOById(){
		try {
			renderObject(iSimServerService.getSimServerPOJOById(simServerPOJO.getSimNo()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	
	/**
	 * 查询终端信息
	 * @return
	 */
	public String getUnitList(){
		try {
			renderObject(iSimServerService.getUnitList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	/**
	 *终端换装
	 * @return
	 */
	public String changeUnit(){
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	simServerPOJO.setOperId(userPOJO.getUserId()+"");
		try {
			//remark 里面存放的是oldSimNo
			int  res = iSimServerService.changeUnit(simServerPOJO,simServerPOJO.getStopReason());
			if(res == 1){
				renderMsgJson(true, "成功");
				return NONE;
			}else if(res == 0){
				renderMsgJson(true, "客户没有开通SIM卡功能");
				return NONE;
			}else if(res == -1){
				renderMsgJson(true, "新的SIM卡已经被占用");
				return NONE;
			}else if(res == -2){
				renderMsgJson(true, "公司没有开通SIM卡功能");
				return NONE;
			}else {
				renderMsgJson(false, "失败");
				return NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderMsgJson(false, "失败");
			return NONE;
		}
	}
	
	
	/**
	 *终端Sim卡换
	 * @return
	 * @throws ParseException 
	 */
	public String changeSim() throws ParseException{
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		SimReplaceLogPOJO simReplaceLog = new SimReplaceLogPOJO();
		simReplaceLog.setOperId(userPOJO.getUserId()+"");
		
		String vehicleDef = getRequest().getParameter("vehicleDef");
		simReplaceLog.setVehicleDef(vehicleDef);
		
		String newSimNo = getRequest().getParameter("newSimNo");
		simReplaceLog.setNewCallLetter(newSimNo);
		
		String oldSimNo = getRequest().getParameter("oldSimNo");
		simReplaceLog.setOldCallLetter(oldSimNo);
		
		String replaceUser = getRequest().getParameter("replaceUser");
		simReplaceLog.setReplaceUser(replaceUser);
		
		String replaceDate = getRequest().getParameter("replaceDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		simReplaceLog.setReplaceDate(sdf.parse(replaceDate));
		
		String reason = getRequest().getParameter("reason");
		simReplaceLog.setReason(reason);
		
		try {
			//remark 里面存放的是oldSimNo
			int  res = iSimServerService.changeSim(simReplaceLog);
			if(res == 1){
				renderMsgJson(true, "成功");
				return NONE;
			}else if(res == -1){
				//新的SIM卡不存在t_sim_server  也就是说 公司没有开通新卡的SIM卡服务
				renderMsgJson(true, "公司没有开通新卡的SIM卡服务");
				return NONE;
			}else if(res == -2){
				//老的SIM卡不存在t_sim_server  也就是说 
				renderMsgJson(true, "公司没有开通老卡的SIM卡服务");
				return NONE;
			}else if(res == -3){
				// t_customer_sim当中已经存在
				renderMsgJson(true, "新的SIM卡被占用");
				return NONE;
			}else {
				renderMsgJson(false, "失败");
				return NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderMsgJson(false, "失败");
			return NONE;
		}
	}
	
	/**
	 * 插入
	 * @return
	 */
	public String saveSimServer(){
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	simServerPOJO.setOperId(userPOJO.getUserId()+"");
		try {
			boolean  res = iSimServerService.insertSelective(simServerPOJO);
			if(res){
				renderMsgJson(true, "成功");
				return NONE;
			}else{
				renderMsgJson(true, "已存在该SIM");
				return NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderMsgJson(false, "失败");
			return NONE;
		}
	}
	
	/**
	 *   公司注销sim
	 * @return
	 */
	public String stopSimServer(){
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	simServerPOJO.setOperId(userPOJO.getUserId()+"");
		try {
			iSimServerService.updateStatus(simServerPOJO);
		} catch (Exception e) {
			e.printStackTrace();
			renderMsgJson(false, "失败");
			return NONE;
		}
		renderMsgJson(true, "成功");
		return NONE;
	}
	
	/**
	 *   删除公司sim
	 * @return
	 */
	public String delBySImNo(){
		try {
			iSimServerService.delSimServerPOJOById(simServerPOJO.getSimNo());
		} catch (Exception e) {
			e.printStackTrace();
			renderMsgJson(false, "失败");
			return NONE;
		}
		renderMsgJson(true, "成功");
		return NONE;
	}
	
	
	/**
	 * 获取simList
	 * @return
	 */
	public String simServerList(){
		renderObject(iSimServerService.simServerList());
		return NONE;
	}
	
	/**
	 * 获取simList
	 * @return
	 * @throws Exception 
	 */
	public String getUnitPage() throws Exception{
		UnitPOJO unit = new UnitPOJO();
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		renderObject(iSimReplaceLogService.getUnitPage(unit,pageSelect));
		return NONE;
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

	public SimServerPOJO getSimServerPOJO() {
		return simServerPOJO;
	}

	public void setSimServerPOJO(SimServerPOJO simServerPOJO) {
		this.simServerPOJO = simServerPOJO;
	}

	@Override
	public SimServerPOJO getModel() {
		return simServerPOJO;
	}

	public ISimServerService getiSimServerService() {
		return iSimServerService;
	}

	public void setiSimServerService(ISimServerService iSimServerService) {
		this.iSimServerService = iSimServerService;
	}
	
}