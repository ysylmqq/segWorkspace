package com.gboss.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.gboss.pojo.CustVehicle;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.FeePayment;
import com.gboss.pojo.Servicetime;
import com.gboss.pojo.Unit;
import com.gboss.pojo.web.ErrorMsg;
import com.gboss.pojo.web.FeePaymentMsg;
import com.gboss.service.CustVehicleService;
import com.gboss.service.FeeInfoService;
import com.gboss.service.FeePaymentService;
import com.gboss.service.ServicetimeService;
import com.gboss.service.UnitService;
import com.gboss.service.VehicleService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:PaymentController
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-18 上午8:43:58
 */
@Controller
public class PaymentController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(PaymentController.class);

	
	@Autowired
	@Qualifier("FeePaymentService")
	private FeePaymentService feePaymentService;
	
	@Autowired
	@Qualifier("ServicetimeService")
	private ServicetimeService servicetimeService;
	
	@Autowired
	@Qualifier("UnitService")
	private UnitService unitService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private FeeInfoService feeInfoService;
	
	@Autowired
	private CustVehicleService custVehicleService;
	
	@RequestMapping(value = "/payment/add",method = RequestMethod.POST)
	@LogOperation(description = "添加缴费记录", type = SystemConst.OPERATELOG_ADD ,model_id=20050)
	public @ResponseBody HashMap add(@RequestBody List<FeePayment> feePayments, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String orgid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		String name = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_USERNAME);
		Long unit_id = 0L;
		Long cust_id = 0L;
		Long vehicle_id = 0L;
		Integer feetype_id = 0;
		Date e_date = null;
		Integer s_months = 0;
		Integer s_days = 0;
		boolean flag=true;
		FeePayment isExit =null;
		
		String msg=SystemConst.OP_SUCCESS;
		try {
			if(feePayments == null || feePayments.size() ==0){
				flag=false;
				msg="不存在缴费项目，添加缴费失败!";
			}
			for(FeePayment feePayment:feePayments){
				unit_id = feePayment.getUnit_id();
				cust_id = feePayment.getCustomer_id();
				vehicle_id = feePayment.getVehicle_id();
				feetype_id = feePayment.getFeetype_id();
				feePayment.setSubco_no(Long.valueOf(companyid));
				feePayment.setOp_id(Long.valueOf(userId));
				feePayment.setOrg_id(Long.valueOf(orgid));
				feePayment.setAgent_name(name);
				feePayment.setFlag(0);
				feePayment.setPay_time(new Date());
				s_months = feePayment.getS_months();
				s_days = feePayment.getS_days();
				if(feePayment.getReal_amount() == null){
					feePayment.setReal_amount(0F);
				}
				//Date s_date = feePayment.getS_date();
				if(null == feePayment.getItem_id() ){
					feePayment.setItem_id(0);
				}
			
				if(unit_id!=null){
					Unit unit = unitService.getUnitByid(unit_id);
					cust_id = unit.getCustomer_id();
					vehicle_id = unit.getVehicle_id();
					feePayment.setCustomer_id(unit.getCustomer_id());
					feePayment.setVehicle_id(unit.getVehicle_id());
				}else if(vehicle_id!=null){
					List<Unit> unitList = unitService.getByVehicle_id(vehicle_id);
					feePayment.setVehicle_id(vehicle_id);
					if(unitList.size()>0){
						Unit unit = unitList.get(0);
						unit_id = unit.getUnit_id();
						cust_id = unit.getCustomer_id();
						feePayment.setUnit_id(unit.getUnit_id());
						feePayment.setCustomer_id(unit.getCustomer_id());
					}
				}else{
						resultMap.put(SystemConst.SUCCESS, false);
						resultMap.put(SystemConst.MSG, "数据有误！");
						return resultMap;
				}
				isExit = feePaymentService.getLastFeePayment(unit_id, feetype_id);
				/*if(null != isExit)
					s_date = isExit.getE_date();
				e_date = getEndDate(s_date, s_months, s_days);
				feePayment.setE_date(e_date);*/
				feePaymentService.add(feePayment);
				//同步更改计费表服务截止时间
				FeeInfo info = feeInfoService.getFeeInfo(unit_id, feetype_id);
				info.setFee_sedate(e_date);
				feeInfoService.update(info);
			}
		} catch (Exception e) {
			flag=false;
			msg="添加缴费失败!";
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	
	
	
	
	@RequestMapping(value = "/payment/saveprivate", method = RequestMethod.POST)
	@LogOperation(description = "私家车缴费", type = SystemConst.OPERATELOG_ADD, model_id = 20050)
	public @ResponseBody
	HashMap saveprivate(
			@RequestBody FeePaymentMsg feePaymentMsg,
			BindingResult bindingResult, HttpServletRequest request)
			throws Exception {
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String username = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String org_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		//保存缴费总表
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		 resultMap.put("success", true);
		 resultMap.put("msg", SystemConst.OP_SUCCESS);
		try {
			 feePaymentService.addfeePayMent(feePaymentMsg, userId, username, companyid, org_id, request);
		} catch (Exception e) {
			 ErrorMsg error = (ErrorMsg) request.getSession().getAttribute(SystemConst.ERROR_MSG);
			 if(null != error){
				 resultMap.put("success", error.getFlag());
				 resultMap.put("msg", error.getMsg());
			 }
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/payment/saveCompany", method = RequestMethod.POST)
	@LogOperation(description = "集客缴费", type = SystemConst.OPERATELOG_ADD, model_id = 20051)
	public @ResponseBody
	HashMap saveCompany(
			@RequestBody FeePaymentMsg feePaymentMsg,
			BindingResult bindingResult, HttpServletRequest request)
			throws Exception {
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String username = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String org_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		//保存缴费总表
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		 resultMap.put("success", true);
		 resultMap.put("msg", SystemConst.OP_SUCCESS);
		try {
			 feePaymentService.addfeePayMent(feePaymentMsg, userId, username, companyid, org_id, request);
		} catch (Exception e) {
			 ErrorMsg error = (ErrorMsg) request.getSession().getAttribute(SystemConst.ERROR_MSG);
			 if(null != error){
				 resultMap.put("success", error.getFlag());
				 resultMap.put("msg", error.getMsg());
			 }
		}
		return resultMap;
	}
	
	
	
	public Date getEndDate(Date s_date, Integer s_month, Integer s_days){
		if(null !=s_date ){
			Calendar cl = Calendar.getInstance();
			cl.setTime(s_date);
			cl.add(Calendar.MONTH, s_month);
			cl.add(Calendar.DATE, s_days);
			return cl.getTime();
		}
		return null;
	}
	
	
	
	@RequestMapping(value = "/payment/save", method = RequestMethod.POST)
	@LogOperation(description = "私家车收费办理", type = SystemConst.OPERATELOG_ADD,  model_id = 20050)
	public @ResponseBody
	HashMap add(@RequestBody FeePayment feePayment, BindingResult bindingResult,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("私家车收费办理开始");
		}
		boolean flag = false;
		String msg = SystemConst.OP_FAILURE;
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
				feePayment.setSubco_no(companyId == null ? null : Long.valueOf(companyId));
				feePayment.setOp_id(userId == null ? null : Long.valueOf(userId));
				
				
				feePaymentService.save(feePayment);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("私家车收费办理结束");
		}
		resultMap.put("success", flag);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	
	@RequestMapping(value = "/payment/addBatch",method = RequestMethod.POST)
	@LogOperation(description = "批量添加缴费记录", type = SystemConst.OPERATELOG_ADD,  model_id = 20051)
	public @ResponseBody HashMap addBatch(@RequestBody List<FeePayment> feePayments, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String orgid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		List<Long> uidList = new ArrayList<Long>();
		List<Long> cidList = new ArrayList<Long>();
		List<Long> vidList = new ArrayList<Long>();
		List<Date> e_dates = new ArrayList<Date>();
		List<Integer> typeidList = new ArrayList<Integer>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		for(FeePayment feePayment:feePayments){
			Unit unit = unitService.getByCustAndVehicle(feePayment.getCustomer_id(), feePayment.getVehicle_id());
			Long unit_id = unit.getUnit_id();
			Date s_date = null;
			Date e_date = null;
			Servicetime stime = servicetimeService.getByUnitid(unit_id);
			if(stime!=null && feePayment.getFeetype_id()==1 && stime.getService_edate()!=null){
				s_date = stime.getService_edate();
			}else if(stime!=null && feePayment.getFeetype_id()==2 && stime.getSim_edate()!=null){
				s_date = stime.getSim_edate();
			}else{
				s_date = unit.getService_date();
			}
			if(s_date==null){
				s_date = new Date();
			}
			
			Calendar cl = Calendar.getInstance();
			cl.setTime(s_date);
			cl.add(Calendar.MONTH, feePayment.getS_months());
			e_date = cl.getTime();
			if(!uidList.contains(unit_id)){
				uidList.add(unit_id);
				cidList.add(feePayment.getCustomer_id());
				vidList.add(feePayment.getVehicle_id());
				e_dates.add(e_date);
				typeidList.add(feePayment.getFeetype_id());
			}
			feePayment.setSubco_no(Long.valueOf(companyid));
			feePayment.setOp_id(Long.valueOf(userId));
			feePayment.setOrg_id(Long.valueOf(orgid));
			feePayment.setPay_time(new Date());
			feePayment.setUnit_id(unit_id);
		/*	feePayment.setS_date(s_date);
			feePayment.setE_date(e_date);*/
			feePaymentService.add(feePayment);
		}
		for(int i=0;i<uidList.size();i++){
			Long unit_id = uidList.get(i);
			Long cust_id = cidList.get(i);
			Long vehicle_id = vidList.get(i);
			Date e_date = e_dates.get(i);
			Integer feetype_id = typeidList.get(i);
			Servicetime servicetime = servicetimeService.getByUnitid(unit_id);
			if(servicetime==null){
				servicetime = new Servicetime();
				servicetime.setCustomer_id(cust_id);
				servicetime.setUnit_id(unit_id);
				servicetime.setVehicle_id(vehicle_id);
				servicetime.setSubco_no(Long.valueOf(companyid));
				servicetime.setService_edate(e_date);
				if(feetype_id==1){
					servicetime.setService_edate(e_date);
				}else if(feetype_id==2){
					servicetime.setSim_edate(e_date);
				}else if(feetype_id==3){
					servicetime.setInsurance_edate(e_date);
				}
				servicetimeService.save(servicetime);
			}else{
				servicetime.setService_edate(e_date);
				if(feetype_id==1){
					servicetime.setService_edate(e_date);
				}else if(feetype_id==2){
					servicetime.setSim_edate(e_date);
				}else if(feetype_id==3){
					servicetime.setInsurance_edate(e_date);
				}
				servicetimeService.update(servicetime);
			}
		}
		resultMap.put("success", true);
		resultMap.put("msg", "添加缴费记录成功!");
		return resultMap;
	}
	
	
	@RequestMapping(value = "/payment/getPaymentRecords")
	public @ResponseBody
	List<HashMap<String, Object>> getPaymentRecords(Long cust_id,Long vehicle_id,Long unit_id,HttpServletRequest request)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得历史缴费记录开始");
		}
		List<HashMap<String, Object>> results = new ArrayList<HashMap<String,Object>>();
		try {
			if(cust_id != null || vehicle_id !=null || unit_id != null){
				results = feePaymentService.getPaymentRecords(cust_id, vehicle_id, unit_id);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得历史缴费记录结束");
		}
		return results;
	}
	
	
	
	@RequestMapping(value = "/payment/findPaymentRecordsPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findPaymentRecordsPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页私家车查询历史缴费记录开始");
		}
		Page<HashMap<String, Object>> result = null;
		Map map = null;
		try {
			if (pageSelect != null) {
				map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			
			
			if (StringUtils.isNotNullOrEmpty(map.get("cust_type"))) {
				if (StringUtils.isNotNullOrEmpty(map.get("unit_id"))) {
					Long unit_id = Long.valueOf(map.get("unit_id").toString());
					Unit unit = unitService.getUnitByid(unit_id);
					map.put("mark", true);
					map.put("unit_id", null);
					map.put("cust_id", unit.getCustomer_id());
					
				}else if (StringUtils.isNotNullOrEmpty(map.get("vehicle_id"))) {
					Long v_id =  Long.valueOf(map.get("vehicle_id").toString());
					List<CustVehicle> list = custVehicleService.getByVehicleid(v_id);
					map.put("cust_id", list.get(0).getCustomer_id());
					map.put("vehicle_id", null);
					map.put("mark", true);
				}
			}
			
			
			//if(com.gboss.util.StringUtils.isNotNullOrEmpty(map.get("unit_id")) || com.gboss.util.StringUtils.isNotNullOrEmpty(map.get("vehicle_id"))){
				result = feePaymentService.findPaymentRecordsPage(pageSelect);
			//}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询私家车历史缴费记录结束");
		}
		return result;
	}
	
	
	
	
	
	

	@RequestMapping(value = "/payment/findfeeDetailPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findfeeDetailPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询缴费明细记录开始");
		}
		System.out.println(pageSelect.getFilter().get("id").toString());
		
		Page<HashMap<String, Object>> result = null;
		Map map = null;
		try {
			if (pageSelect != null) {
				map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
				result = feePaymentService.findfeeDetailPage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询缴费明细记录结束");
		}
		return result;
	}
	
	
	
	
	@RequestMapping(value = "/payment/findPaymentRecordsPage2")
	public @ResponseBody
	Page<HashMap<String, Object>> findPaymentRecordsPage2(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询集客历史缴费记录开始");
		}
		Page<HashMap<String, Object>> result = null;
		Map map = null;
		try {
			if (pageSelect != null) {
				map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
				result = feePaymentService.findPaymentRecordsPage2(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询集客历史缴费记录结束");
		}
		return result;
	}
	

}

