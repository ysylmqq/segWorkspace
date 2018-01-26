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
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.action.common.ExcelUtils;
import com.chinaGPS.gtmp.entity.AlarmPOJO;
import com.chinaGPS.gtmp.entity.AreaPointPOJO;
import com.chinaGPS.gtmp.entity.CustomerPayPOJO;
import com.chinaGPS.gtmp.entity.CustomerSimPOJO;
import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.SimServerPOJO;
import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.TestPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.service.ICustomerPayService;
import com.chinaGPS.gtmp.service.ICustomerSimService;
import com.chinaGPS.gtmp.service.ISimServerService;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.service.IVehicleTestService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.DateUtil;
import com.chinaGPS.gtmp.util.FormatUtil;
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
public class CustomerSimAction extends BaseAction implements ModelDriven<CustomerSimPOJO> {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(CustomerSimAction.class);
	
	@Resource
	private CustomerSimPOJO customerSimPOJO;

	@Resource
	private ICustomerSimService iCustomerSimService;
	
	@Resource
	private ICustomerPayService iCustomerPayService;
	
	@Resource
	private ISimServerService iSimServerService;
	
	
	@Resource
	private PageSelect pageSelect;
	private File upload;
	private String uploadFileName;

	private int page;
	private int rows;

	/**
	 * 客户SIM查询，返回分页数据
	 */
	public String search() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		String dealerIdStr = getRequest().getParameter("dealerId") ;
		if(StringUtils.isNotEmpty(dealerIdStr)){
			String dealerId = java.net.URLDecoder.decode(dealerIdStr, "utf-8");
			if(StringUtils.isNotEmpty(dealerId)){
				customerSimPOJO.setDealerIds(dealerId.split(","));
			}
		}
		renderObject(iCustomerSimService.getCustomerSimPage(customerSimPOJO, pageSelect));
		return NONE;
	}

	/**
	 * 客户申请开通SIM卡服务
	 */
	public String insert(){
    	UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	customerSimPOJO.setOperId(userPOJO.getUserId()+"");
    	//先判断公司SIM功能是否开通
    	SimServerPOJO simServerPOJO = iSimServerService.getSimServerPOJOById(customerSimPOJO.getSimNo());
    	if(simServerPOJO == null ){
			renderObject(3);
    		return NONE;
    	}
    	
    	boolean res = true;
		try {
			res = iCustomerSimService.insertSelective(customerSimPOJO);
		} catch (Exception e) {
			renderObject(0);
			e.printStackTrace();
			return NONE;
		}
		if( res ){
			renderObject(1);
		}else{
			renderObject(2);
		}
		return NONE;
	}
	
	/**
	 * 批量停机保号
	 */
	public String batchStopSimServer(){
    	UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	customerSimPOJO.setOperId(userPOJO.getUserId()+"");
		try {
			iCustomerSimService.batchStopSimServer(customerSimPOJO);
		} catch (Exception e) {
			renderMsgJson(false, "批量停机保号失败");
			e.printStackTrace();
			return NONE;
		}
		renderMsgJson(true, "批量停机保号成功");
		return NONE;
	}
	
	
	/**
	 * 更新sim_server插入
	 */
	public String updateCustomerServer(){
    	UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	customerSimPOJO.setOperId(userPOJO.getUserId()+"");
		try {
			iCustomerSimService.updateByPrimaryKeySelective(customerSimPOJO);
		} catch (Exception e) {
			renderMsgJson(false, "更新失败");
			e.printStackTrace();
			return NONE;
		}
		renderMsgJson(true, "更新成功");
		return NONE;
	}
	
	
	/**
	 * 获取t_sim_server 
	 * @return
	 */
	public String simServerById(){
		CustomerSimPOJO customerSimPOJO1 =  iCustomerSimService.getCustomerSimPOJOById(customerSimPOJO.getSimNo());
		if(customerSimPOJO1 != null){
			Date endTime = customerSimPOJO1.getEndTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				customerSimPOJO1.setEndTime(sdf.parse(sdf.format(endTime)));
				customerSimPOJO1.setRemark(sdf.format(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		renderObject(customerSimPOJO);
		return NONE;
	}
	
	
	/**
	 * 批量导入停机保号
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@OperationLog(description = "机械批量导入")
	public String batchStopServer() {
    	UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		String msg = "导入成功!";
		Map<String,Object> map = new HashMap<String,Object>();
		ExcelUtils excelUtils = new ExcelUtils();
		Map eMap = null;
		try {
			eMap = excelUtils.readExcelCustomerStopSimServer(upload,"SIM卡号!停机保号开始日期!停机保号结束日期!停机保号月费用!停机保号原因!");
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

		List<CustomerSimPOJO> excelList = new ArrayList<CustomerSimPOJO>();
		excelList = (List) eMap.get("values");
		List<CustomerSimPOJO> errorList = null;
		try {
			errorList = iCustomerSimService.batchStopSimServer(excelList,userPOJO.getUserId());
		} catch (Exception e) {
			map.put("flag","3");
			map.put("tips","导入数据出错");
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
	 * 导入功能
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@OperationLog(description = "机械批量导入")
	public String impFromExcel() {
    	UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		String msg = "导入成功!";
		Map<String,Object> map = new HashMap<String,Object>();
		ExcelUtils excelUtils = new ExcelUtils();
		Map eMap = null;
		try {
			eMap = excelUtils.readExcelCustomerSimServer(upload,"SIM卡号!服务开始日期!服务结束日期!备注!");
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

		List<CustomerSimPOJO> excelList = new ArrayList<CustomerSimPOJO>();
		excelList = (List) eMap.get("values");
		List<CustomerSimPOJO> errorList = null;
		try {
			errorList = iCustomerSimService.batchInsert(excelList,userPOJO.getUserId());
		} catch (Exception e) {
			map.put("flag","3");
			map.put("tips","导入数据出错");
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
	
	//公司SIM卡信息导出 simNo=&openTime=&endTime=&vehicleDef=&status=
		public void exportToExcelVehicle() throws Exception {
			List<Object[]> values = new ArrayList<Object[]>();
			String simNo = java.net.URLDecoder.decode(getRequest().getParameter("simNo"), "utf-8");
			String status = java.net.URLDecoder.decode(getRequest().getParameter("status"), "utf-8");
			String openTime = java.net.URLDecoder.decode(getRequest().getParameter("openTime"), "utf-8");
			String endTime = java.net.URLDecoder.decode(getRequest().getParameter("endTime"), "utf-8");
			String vehicleDef = java.net.URLDecoder.decode(getRequest().getParameter("vehicleDef"), "utf-8");
			String dealerId = java.net.URLDecoder.decode(getRequest().getParameter("dealerId"), "utf-8");

			CustomerSimPOJO simServerPOJO = new CustomerSimPOJO();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (simNo != null && !"".equals(simNo)) {
				simServerPOJO.setSimNo(simNo);
			}
			
			if (status != null && !"".equals(status)) {
				simServerPOJO.setStatus(new BigDecimal(status));
			}

			if (openTime != null && !"".equals(openTime)) {
				simServerPOJO.setStartTime(sdf.parse(openTime));
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
			List<CustomerSimPOJO> list = iCustomerSimService.exportCustomerSim(simServerPOJO);
			if(list != null && list.size() > 0 ){
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					CustomerSimPOJO simServerPOJO2 = (CustomerSimPOJO) iterator.next();
					String sta = "";
					if(simServerPOJO2.getStatus() == null ){
						sta= "未开通";
					}else if( simServerPOJO2.getStatus().intValue() == 1 ){
						sta = "停机保号";
					}else{
						sta = "开通";
					}
					
					Date openTimes = simServerPOJO2.getStartTime();
					String  openTimeTem = "";
					if( openTimes != null){
						openTimeTem = sdf.format(openTimes);
					}
					
					Date endTimes = simServerPOJO2.getEndTime();
					String  endTimeTem = "";
					if( endTimes != null){
						endTimeTem = sdf.format(endTimes);
					}
					
					Date stopStartTimes = simServerPOJO2.getStopStartTime();
					String  stopStartTimeTem = "";
					if( stopStartTimes != null){
						stopStartTimeTem = sdf.format(stopStartTimes);
					}
					
					Date stopEndTimes = simServerPOJO2.getStopEndTime();
					String  stopEndTimeTem = "";
					if( stopEndTimes != null){
						stopEndTimeTem = sdf.format(stopEndTimes);
					}
					
					Date createTime = simServerPOJO2.getCreateTime();
					String  createTimeStr = "";
					if( createTime != null){
						if( createTimeStr != null){
							createTimeStr = sdf.format(createTime);
						}
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
					values.add(new Object[] { 
							simServerPOJO2.getVehicleDef(),
							simServerPOJO2.getDistributor(),
							simServerPOJO2.getModelName(),
							simServerPOJO2.getVehicleArg(),
							simServerPOJO2.getUnitSn(),
							simServerPOJO2.getSimNo(),
							statusTips, //服务状态 
							openTimeTem,
							endTimeTem,
							sta,
							stopStartTimeTem,
							stopEndTimeTem,
							simServerPOJO2.getStopSaveFee(),
							simServerPOJO2.getStopReason(),
							simServerPOJO2.getRemark(),
							simServerPOJO2.getUserName(),
							createTimeStr});
				}
			}
			String[] headers = new String[] { "整机编号","经销商","机械型号","机械配置","终端序列号","SIM卡号", "服务状态","服务开始日期","服务结束日期","状态",
					"停机保号开始日期", "停机保号结束日期","停机保号累计费用","停机保号原因","备注","操作人","开通时间"};
			super.renderExcel("客户SIM信息" + ".xls", headers, values);
		}
	
	/**
	 * 获取getCustomerPayPOJO By custPayId  
	 * @return
	 */
	public String getCustomerSimPOJOById(){
		try {
			renderObject(iCustomerSimService.getCustomerSimPOJOById(customerSimPOJO.getSimNo()));
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
	 * 保存CustomerSim
	 * @return
	 */
	public String saveCustomerSim(){
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		customerSimPOJO.setOperId(userPOJO.getUserId()+"");
		try {
			boolean  res = iCustomerSimService.insertSelective(customerSimPOJO);
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
	public String stopCustomerSim(){
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		customerSimPOJO.setOperId(userPOJO.getUserId()+"");
		try {
			iCustomerSimService.updateStatus(customerSimPOJO);
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
	public String customerSimList(){
		renderObject(iCustomerSimService.customerSimList());
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

	@Override
	public CustomerSimPOJO getModel() {
		return customerSimPOJO;
	}
	
	
}