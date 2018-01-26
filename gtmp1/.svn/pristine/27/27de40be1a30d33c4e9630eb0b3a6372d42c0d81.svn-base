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
public class CustomerPayAction extends BaseAction implements ModelDriven<CustomerPayPOJO> {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(CustomerPayAction.class);
	
	@Resource
	private CustomerPayPOJO customerPayPOJO;

	@Resource
	private ICustomerPayService customerPayService;
	
	@Resource
	private ICustomerSimService iCustomerSimService;
	
	
	@Resource
	private PageSelect pageSelect;
	private File upload;
	private String uploadFileName;

	private int page;
	private int rows;

	/**
	 * 客户缴费查询，返回分页数据
	 */
	public String search() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		String dealerIdStr = getRequest().getParameter("dealerId") ;
		if(StringUtils.isNotEmpty(dealerIdStr)){
			String dealerId = java.net.URLDecoder.decode(dealerIdStr, "utf-8");
			if(StringUtils.isNotEmpty(dealerId)){
				customerPayPOJO.setDealerIds(dealerId.split(","));
			}
		}

		renderObject(customerPayService.getCustomerPays(customerPayPOJO, pageSelect));
		return NONE;
	}

	/**
	 * 单个用户缴费
	 */
	public String custPay(){
    	UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		customerPayPOJO.setOperId(userPOJO.getUserId()+"");
		int  res = 0;
		try {
			 res = customerPayService.custPay(customerPayPOJO);
		} catch (Exception e) {
			renderObject(-2);
			e.printStackTrace();
			return NONE;
		}
		renderObject(res);
		return NONE;
	}
	
	/**
	 * 获取simList
	 * @return
	 */
	public String simList(){
		renderObject(customerPayService.simList());
		return NONE;
	}
	

	/**
	 * 查询车辆的相关信息
	 * @return
	 */
	public String vehicleInfo(){
		renderObject(customerPayService.vehicleInfo());
		return NONE;
	}
	
	
	
	/**
	 * 获取customerSim  
	 * @return
	 */
	public String customerSimById(){
		CustomerSimPOJO customerSimPOJO =  iCustomerSimService.getCustomerSimById(customerPayPOJO.getSimNo());
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
	public String impFromExcel() throws Exception {
		String msg = "缴费成功!";
		Map<String,Object> map = new HashMap<String,Object>();
		ExcelUtils excelUtils = new ExcelUtils();
		Map eMap = excelUtils.readExcelCustomerPay(upload,"SIM卡号!缴费金额!服务结束日期!备注!");
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

		List<CustomerPayPOJO> excelList = new ArrayList<CustomerPayPOJO>();
		excelList = (List) eMap.get("values");
    	UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	String userId = userPOJO.getUserId()+"";
		List<CustomerPayPOJO> errorList  = customerPayService.importCustomerPay(excelList,userId);
		if(errorList == null || errorList.size() == 0 ){
			map.put("flag","1");
			map.put("tips","缴费成功");
		}else{
			map.put("flag","2");
			map.put("tips","导入失败数据");
			map.put("errorList",errorList);
		}
		renderObject(map);
		return NONE;
	}
	
	//公司SIM卡信息导出
	public void exportToExcelVehicle() throws Exception {
		List<Object[]> values = new ArrayList<Object[]>();
		String simNo = java.net.URLDecoder.decode(getRequest().getParameter("simNo"), "utf-8");
		String startTime = java.net.URLDecoder.decode(getRequest().getParameter("startTime"), "utf-8");
		String endTime = java.net.URLDecoder.decode(getRequest().getParameter("endTime"), "utf-8");
		String payAmount = java.net.URLDecoder.decode(getRequest().getParameter("payAmount"), "utf-8");
		String createTime = java.net.URLDecoder.decode(getRequest().getParameter("createTime"), "utf-8");
		String vehicleDef = java.net.URLDecoder.decode(getRequest().getParameter("vehicleDef"), "utf-8");
		String dealerId = java.net.URLDecoder.decode(getRequest().getParameter("dealerId"), "utf-8");

		CustomerPayPOJO customerPayPOJO = new CustomerPayPOJO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (simNo != null && !"".equals(simNo)) {
			customerPayPOJO.setSimNo(simNo);
		}
		
		if (payAmount != null && !"".equals(payAmount)) {
			customerPayPOJO.setPayAmount(new BigDecimal(payAmount));
		}

		if (startTime != null && !"".equals(startTime)) {
			customerPayPOJO.setStartTime(sdf.parse(startTime));
		}
		
		if (endTime != null && !"".equals(endTime)) {
			customerPayPOJO.setEndTime(sdf.parse(endTime));
		}
		
		if (createTime != null && !"".equals(createTime)) {
			customerPayPOJO.setEndTime(sdf.parse(createTime));
		}
		if (vehicleDef != null && !"".equals(vehicleDef)) {
			customerPayPOJO.setVehicleDef(vehicleDef);
		}
		if (dealerId != null && !"".equals(dealerId)) {
			customerPayPOJO.setDealerIds(dealerId.split(","));
		}
		//TODU;
		List<CustomerPayPOJO> list = customerPayService.allCustomerPay(customerPayPOJO);
		if(list != null && list.size() > 0 ){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				CustomerPayPOJO customerPayPOJO2 = (CustomerPayPOJO) iterator.next();
				
				Date startTimes = customerPayPOJO2.getStartTime();
				String  startTimeTem = "";
				if( startTimes != null){
					startTimeTem = sdf.format(startTimes);
				}
				
				Date endTimes = customerPayPOJO2.getEndTime();
				String  endTimeTem = "";
				if( endTimes != null){
					endTimeTem = sdf.format(endTimes);
				}
				
				Date createTimes = customerPayPOJO2.getCreateTime();
				String  createTimeTem = "";
				if( createTimeTem != null){
					createTimeTem = sdf.format(createTimes);
				}
				//vehicleDef   unitSn
				
				values.add(new Object[] { 
						customerPayPOJO2.getVehicleDef(),
						customerPayPOJO2.getDistributor(),
						customerPayPOJO2.getModelName(),
						customerPayPOJO2.getVehicleArg(),
						customerPayPOJO2.getUnitSn(),
						customerPayPOJO2.getSimNo(),
						startTimeTem,
						endTimeTem,
						customerPayPOJO2.getPayAmount(),
						customerPayPOJO2.getUserName(),
						createTimeTem,
						customerPayPOJO2.getRemark()});
			}
		}
		String[] headers = new String[] { "整机编号","经销商","机械型号","机械配置", "终端序列号", "SIM卡号","服务开始日期","服务结束日期","缴费金额","操作人","缴费时间","备注"};
		super.renderExcel("客户缴费信息表" + ".xls", headers, values);
	}
	
	/**
	 * 获取getCustomerPayPOJO By custPayId  
	 * @return
	 */
	public String getCustomerPayPOJOById(){
		try {
			renderObject(customerPayService.getCustomerPayPOJO(customerPayPOJO.getCustPayId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	
	/**
	 * 删除最新的缴费记录
	 * @return
	 */
	public String delCustomerPayPOJOById(){
		try {
			customerPayService.updateStatus(customerPayPOJO);
		} catch (Exception e) {
			e.printStackTrace();
			renderMsgJson(false,"失败");
			return NONE;
		}
		renderMsgJson(true,"成功");
		return NONE;
	}
	
	@Override
	public CustomerPayPOJO getModel() {
		return customerPayPOJO;
	}
	
	
	public CustomerPayPOJO getCustomerPayPOJO() {
		return customerPayPOJO;
	}

	public void setCustomerPayPOJO(CustomerPayPOJO customerPayPOJO) {
		this.customerPayPOJO = customerPayPOJO;
	}

	public ICustomerPayService getCustomerPayService() {
		return customerPayService;
	}

	public void setCustomerPayService(ICustomerPayService customerPayService) {
		this.customerPayService = customerPayService;
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
	
	
}