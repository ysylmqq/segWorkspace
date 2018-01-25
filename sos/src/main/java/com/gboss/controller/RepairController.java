package com.gboss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Barcode;
import com.gboss.pojo.Repair;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;
import com.gboss.pojo.web.CustInfo;
import com.gboss.pojo.web.VerifyPOJO;
import com.gboss.service.BarcodeService;
import com.gboss.service.RepairService;
import com.gboss.util.PageSelect;
import com.gboss.util.Utils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:RepairController
 * @author:xiaoke
 * @date:2015-2-11 上午11:49:14
 */
@Controller
public class RepairController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RepairController.class);
	
	@Autowired
	private RepairService repairService;
	
	@Autowired
	@Qualifier("BarcodeService")
	private BarcodeService bcodeSer;
	
	/**
	 * 查询维修历史记录
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/repair/findRepairByPage")
	public @ResponseBody
	Page<Map<String, Object>> findRepairByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询维修记录开始");
		}
		Page<Map<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			CustInfo custInfo = (CustInfo) request.getSession().getAttribute(SystemConst.CUST_INFO);
			Long company_id = companyId == null ? null : Long.valueOf(companyId);
			Long unit_id = 0L;
			Long vehicle_id = 0L;
			if(custInfo!=null){
				Unit unit = custInfo.getUnit();
				if(unit!=null){
					unit_id = unit.getUnit_id();
				}
				Vehicle vehicle = custInfo.getVehicle();
				if(vehicle!=null){
					vehicle_id = vehicle.getVehicle_id();
				}
			}
			if(pageSelect.getFilter().get("qtype") != null){
				if(pageSelect.getFilter().get("vehicleId") == null){
					unit_id = 0L;
					vehicle_id = 0L;
				}
			}
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				map.put("subco_no", company_id);
				map.put("unit_id", unit_id);
				map.put("vehicle_id", vehicle_id);
				pageSelect.setFilter(map);
			}
			result =repairService.findRepairPage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询维修记录结束");
		}
		return result;
	}
	
	/**
	 * 维修进度查询
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/repair/findRepairHisPage")
	public @ResponseBody
	Page<Map<String, Object>> findRepairHisPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询维修订单开始");
		}
		Page<Map<String, Object>> result = null;
		try{
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			Long company_id = companyId == null ? null : Long.valueOf(companyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				map.put("subco_no", company_id);
				pageSelect.setFilter(map);
			}
			result =repairService.findRepairOrderPage(pageSelect);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询维修订单结束");
		}
		return result;
	}
	
	/**
	 * 中心查询车辆现在维修状态
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/repair/findRepairNow", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> findRepiarRegByConditon(@RequestBody Map<String, Object> param, HttpServletRequest request
			, HttpServletResponse response)throws SystemException{
		Map<String, Object> map = new HashMap<String, Object>();
		Repair reserve = repairService.getRepairByVehicleId(param);
		param.put("search_type", "latest");
		Repair befRe = repairService.getRepairByVehicleId(param);
		map.put("repair", reserve);
		map.put("beforeSym", befRe == null ? null : befRe.getSymptom());
		map.put("success", true);
		return map;
	}
	
	/**
	 * 保存车辆维修信息
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="/repair/addReserve", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> addVehicleReserve(@RequestBody Map<String, Object> param, HttpServletRequest request
			, HttpServletResponse response)throws SystemException{
		Map<String, Object> map = new HashMap<String, Object>();
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String user_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String user_name = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		param.put("subco_no", companyId);
		param.put("op_id", user_id);
		param.put("op_name", user_name);
		try{
			map = repairService.saveVehicleReserve(param);
		}catch(Exception e){
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	/**
	 * 将维修发送给营业厅
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="/repair/sendReserve", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> sendReserve(@RequestBody Map<String, Object> param, HttpServletRequest request
			, HttpServletResponse response)throws SystemException{
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String user_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String user_name = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		if(Utils.isNullOrEmpty(param.get("subco_no"))){
			param.put("subco_no", companyId);
		}
		param.put("op_id", user_id);
		param.put("op_name", user_name);
		return repairService.sendReserve(param);
	}

	@RequestMapping(value = "/repair/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(@RequestBody Repair repair, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String user_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		if(repair!=null){
			repair.setRegOpId(Long.valueOf(user_id));
			repair.setSubcoNo(Long.valueOf(companyId));
			repairService.save(repair);
			map.put("success", true);
			map.put("msg", "操作成功");
		}else{
			map.put("success", false);
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	
	
	@RequestMapping(value = "/repair/update", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(@RequestBody Repair repair, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		String user_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		if(repair!=null){
			repair.setRegOpId(Long.valueOf(user_id));
			repairService.update(repair);
			map.put("success", true);
			map.put("msg", "操作成功");
		}else{
			map.put("success", false);
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	

	@RequestMapping(value = "/repair/delete")
	public @ResponseBody
	HashMap<String, Object> delete(@RequestBody List<Long> repair_ids,
			HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(Long repair_id:repair_ids){
			repairService.delete(Repair.class, repair_id);
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}
	
	/**
	 * 获取主机编号信息
	 * @param verify
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="/repair/getBarcode", method=RequestMethod.POST)
	public @ResponseBody Barcode getBarcode(@RequestBody VerifyPOJO verify, HttpServletRequest request) 
		throws SystemException{
		Barcode bcode = bcodeSer.getBarcodeByUnitId(Long.valueOf(verify.getParameter()));
		return bcode;
	}
	
	/**
	 * 获取工单分页
	 * @param pageSelect
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getJobNo", method = RequestMethod.POST)
	public @ResponseBody
	Page<Repair> getVehicles(@RequestBody PageSelect<Repair> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String searchValue = pageSelect.getSearchValue();
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String ishq = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ISHQ);
		if("0".equals(searchValue)){
			companyid = "2";
		}
		Page<Repair> result = repairService.search(pageSelect,
				Long.valueOf(companyid));
		return result;
	}
	
	
	@RequestMapping(value="/repair/saveRepairReserve", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveRepairReserve(@RequestBody Map<String, Object> param, HttpServletRequest request)
			throws SystemException {
		String user_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String user_name = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		param.put("op_id", user_id);
		param.put("op_name", user_name);
		return repairService.saveRepairReserve(param);
	}
	
	@RequestMapping(value="/repair/saveRepairPaiGong", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveRepairPaiGong(@RequestBody Map<String, Object> param, HttpServletRequest request)
			throws SystemException {
		String user_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String user_name = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		param.put("op_id", user_id);
		param.put("op_name", user_name);
		return repairService.saveRepairPaiGong(param);
	}
	
	@RequestMapping(value="/repair/saveRepairDoing", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveRepairDoing(@RequestBody Map<String, Object> param, HttpServletRequest request)
			throws SystemException {
		String user_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String user_name = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		param.put("op_id", user_id);
		param.put("op_name", user_name);
		return repairService.saveRepairDoing(param);
	}
	
	@RequestMapping(value="/repair/saveRepairSuccess", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveRepairSuccess(@RequestBody Map<String, Object> param, HttpServletRequest request)
			throws SystemException {
		String user_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String user_name = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		param.put("op_id", user_id);
		param.put("op_name", user_name);
		return repairService.saveRepairSuccess(param);
	}
	
	@RequestMapping(value="/repair/saveRepairUndo", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveRepairUndo(@RequestBody Map<String, Object> param, HttpServletRequest request)
			throws SystemException {
		String user_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String user_name = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		param.put("op_id", user_id);
		param.put("op_name", user_name);
		return repairService.saveRepairUndo(param);
	}
	
	/**
	 * 导出维修记录
	 * @param request
	 * @param response
	 * @throws SystemException
	 */
	@RequestMapping("/repair/exportRepairDt")
	public void exportRepairDt(HttpServletRequest request, HttpServletResponse response) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("车辆维修历史导出开始");
		}
		try{
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			
			Map map=parseReqParam2(request);

			if(map.get("qtype") != null){
				if(map.get("vehicleId") == null){
					map.put("vehicle_id", 0L);
				}else{
					map.put("vehicle_id", map.get("vehicleId"));
				}
			}
			
			String[][] title = {{"序号","10"},{"维修单号","20"},{"故障现象","14"},{"维护电工","14"},{"维护地点","20"}
				,{"操作员","10"},{"工单状态","10"},{"处理时间","14"},{"备注","20"},{"跟踪情况","20"}};
			
			
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (StringUtils.isNotBlank(userId)) {
				//判断是否是分公司
				int level=openLdap.getUserCompanyLevel(companyId);
				if(level==0){//总部
					
				}else if(level==1){//分公司
					map.put("subcoNo", Long.valueOf(companyId));
				}else if(level==2){//营业处
					map.put("subcoNo", Long.valueOf(companyId));
				}
			}
			
			List<Map<String, Object>> results = repairService.findRepairDt(map);
			List valueList = new ArrayList();
			Map<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int titleLength=title.length;
			int columnIndex=0;
			for(int i=0; i<listLenth; i++){
				values = new String[titleLength];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("jobNo"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("symptom"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("worker"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("rpPlace"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("acpOpName"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("statusName"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("stamp"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("regRemark"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("traceResult"));
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "车辆维修记录报表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		}catch(Exception e){
			
		}
	}
}

