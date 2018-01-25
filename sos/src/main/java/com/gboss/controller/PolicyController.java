package com.gboss.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Customer;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.Model;
import com.gboss.pojo.Policy;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;
import com.gboss.service.CustomerService;
import com.gboss.service.FeeInfoService;
import com.gboss.service.ModelService;
import com.gboss.service.PolicyService;
import com.gboss.service.ServicetimeService;
import com.gboss.service.UnitService;
import com.gboss.service.VehicleService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.controller
 * @ClassName:PolicyController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-29 下午4:18:21
 */
@Controller
public class PolicyController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(PolicyController.class);

	@Autowired
	@Qualifier("policyService")
	private PolicyService policyService;
	
	@Autowired
	private FeeInfoService feeInfoService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	@Qualifier("ServicetimeService")
	private ServicetimeService servicetimeService;
	
	private OpenLdap ldap = OpenLdapManager.getInstance();

	/**
	 * 
	 * @Title:getPolicyNo
	 * @Description: 生成保单单号
	 * @param @param request
	 * @param @return
	 * @param @throws SystemException
	 * @return String
	 */
	@RequestMapping(value = "/policy/getPolicyNo")
	public @ResponseBody
	String getPolicyNo(HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得保单号 开始");
		}
		String results = null;
		try {
			results = policyService.getPolicytNo();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得保单号 结束");
		}
		return results;
	}
	
	
	@RequestMapping(value = "/policy/getModelName")
	public @ResponseBody
	String getModelName(Long id, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得车型信息开始");
		}
		String results = null;
		try {
			Model moedl = modelService.get(Model.class, id);
			results = moedl.getName();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得车型信息结束");
		}
		return results;
	}

	/**
	 * 
	 * @Title:getDetailMsgByCarNum
	 * @Description:获得客户详细信息
	 * @param carNum
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/policy/getDetailMsgByCarNum")
	public @ResponseBody
	HashMap<String, Object> getDetailMsgByCarNum(String carNum,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得客户车辆详细信息开始");
		}
		HashMap<String, Object> results = null;
		try {
			results = policyService.getDetailMsgByCarNum(carNum);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得客户车辆详细信息结束");
		}
		return results;
	}

	/**
	 * 
	 * @Title:add
	 * @Description:保险办理
	 * @param policy
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/policy/add", method = RequestMethod.POST)
	@LogOperation(description = "保险办理", type = SystemConst.OPERATELOG_ADD, model_id = 20040)
	public @ResponseBody
	HashMap add(@RequestBody Policy policy, BindingResult bindingResult,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保险办理开始");
		}
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(StringUtils.isNotBlank(policy.getPh_id_no())){
				policy.setPh_id_no(policy.getPh_id_no().replace(" ", ""));
			}
			if(StringUtils.isNotBlank(policy.getCust_id_no())){
				policy.setCust_id_no(policy.getCust_id_no().replace(" ", ""));
			}
			policy.setSubco_no(companyId == null ? null : Long.valueOf(companyId));
			policy.setStatus(SystemConst.POLICY_NORMAL);
			policy.setIs_print(0);
			policy.setOp_id(userId == null ? null : Long.valueOf(userId));
			if(policy.getCustomer_id() != null && policy.getVehicle_id() != null && policy.getUnit_id() != null){
				if(!policyService.isRightTime(null, policy.getVehicle_id(), policy.getIs_bdate(), policy.getIs_edate())){
					 flag = false;
					 msg = "请检查输入时间段！";
				}
			}else{
				 flag = false;
				 msg = SystemConst.OP_FAILURE;
			}
			Vehicle vehicle1 = new Vehicle();
			vehicle1.setVin(policy.getVin());
			vehicle1.setVehicle_id(policy.getVehicle_id());
			if(vehicleService.is_exist(vehicle1)){
				 flag = false;
				 msg = "车架号已存在！";
			}
			
			if(policyService.is_exist(null, policy.getPolicy_no())){
				 flag = false;
				 msg = "保单已存在！";
			}
			
			//同步修改车辆信息
			if(flag){
				Vehicle vehicle = vehicleService.get(Vehicle.class, policy.getVehicle_id());
				vehicle.setVin(policy.getVin());
				vehicle.setEngine_no(policy.getEngine_no());
				if(policy.getColor()!=null && !policy.getColor().equals(""))
					vehicle.setColor(policy.getColor());
				if(policy.getVehicle_price() != 0)
					vehicle.setBuy_money((float) policy.getVehicle_price());
				if(policy.getRegister_date() != null)
					vehicle.setRegister_date(policy.getRegister_date());
				if(policy.getFix_time() != null){
					Unit unit = unitService.get(Unit.class, policy.getUnit_id());
					if(null != unit){
						unit.setFix_time(policy.getFix_time());
						unitService.update(unit);
					}
				}
				vehicleService.update(vehicle);
				policyService.save(policy);
			}

		} catch (Exception e) {
			flag = false;
			 msg = SystemConst.OP_FAILURE;
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保险办理结束");
		}
		resultMap.put("success", flag);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	/**
	 * 
	 * @Title:update
	 * @Description:保单修改
	 * @param policy
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/policy/update", method = RequestMethod.POST)
	@LogOperation(description = "保险修改", type = SystemConst.OPERATELOG_UPDATE, model_id = 20040)
	public @ResponseBody
	HashMap update(@RequestBody Policy policy, BindingResult bindingResult,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保险修改开始");
		}
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (policy == null || policy.getInsurance_id() == null) {
				flag = false;
				msg = SystemConst.OP_FAILURE;
			
			}else{
				Policy oldPolicy = policyService.get(Policy.class, policy.getInsurance_id());
				//修改盗抢险同时修改对应的计费信息
				//if(oldPolicy.getFee() != policy.getFee()){
					/*FeeInfo info = feeInfoService.getinsuranceInfo(oldPolicy.getVehicle_id());
					if(null != info){
						info.setFee_date(policy.getIs_bdate());
						info.setMonth_fee(policy.getFee());
						info.setAc_amount(policy.getFee() * info.getFee_cycle() );
						info.setReal_amount(policy.getFee() * info.getFee_cycle());
						feeInfoService.saveOrUpdate(info);
					}*/
				//}
				oldPolicy.setRatio(policy.getRatio());
				oldPolicy.setColor(policy.getColor());
				oldPolicy.setAddress(policy.getAddress());
				oldPolicy.setAmount(policy.getAmount());
				oldPolicy.setBarcode(policy.getBarcode());
				oldPolicy.setBirthday(policy.getBirthday());
				oldPolicy.setEngine_no(policy.getEngine_no());
				oldPolicy.setFee(policy.getFee());
				oldPolicy.setFix_time(policy.getFix_time());
				oldPolicy.setGps_code(policy.getGps_code());
				oldPolicy.setIc_no(policy.getIc_no());
		
				oldPolicy.setIs_bdate(policy.getIs_bdate());
				oldPolicy.setIs_buy_tp(policy.getIs_buy_tp());
				oldPolicy.setIs_edate(policy.getIs_edate());
				//oldPolicy.setPh_id_no(policy.getPh_id_no());
				oldPolicy.setPhone(policy.getPhone());
				oldPolicy.setPlate_code(policy.getPlate_code());
				oldPolicy.setPolicy_no(policy.getPolicy_no());
				oldPolicy.setPolicyholder(policy.getPolicyholder());
				oldPolicy.setRegister_date(policy.getRegister_date());
				oldPolicy.setSales(policy.getSales());
				oldPolicy.setTel(policy.getTel());
				oldPolicy.setVehicle_price(policy.getVehicle_price());
				oldPolicy.setVin(policy.getVin());
				oldPolicy.setCustomer_name(policy.getCustomer_name());
			//	oldPolicy.setCust_id_no(policy.getCust_id_no());
				
				oldPolicy.setOp_id(userId == null ? null : Long.valueOf(userId));
				
				if(StringUtils.isNotBlank(policy.getPh_id_no())){
					oldPolicy.setPh_id_no(policy.getPh_id_no().replace(" ", ""));
				}
				if(StringUtils.isNotBlank(policy.getCust_id_no())){
					oldPolicy.setCust_id_no(policy.getCust_id_no().replace(" ", ""));
				}
				
				
				if(!policyService.isRightTime(oldPolicy.getInsurance_id(), oldPolicy.getVehicle_id(), policy.getIs_bdate(), policy.getIs_edate())){
					 flag = false;
					 msg = "请检查输入时间段！";
				}
				Vehicle vehicle1 = new Vehicle();
				vehicle1.setVin(policy.getVin());
				vehicle1.setVehicle_id(oldPolicy.getVehicle_id());
				if(vehicleService.is_exist(vehicle1)){
					 flag = false;
					 msg = "车架号已存在！";
				}
				
				if(policyService.is_exist(oldPolicy.getInsurance_id(), policy.getPolicy_no())){
					 flag = false;
					 msg = "保单已存在！";
				}
				
				//同步修改车辆信息
				if(flag){
					Vehicle vehicle = vehicleService.get(Vehicle.class, oldPolicy.getVehicle_id());
					vehicle.setVin(oldPolicy.getVin());
					if(policy.getColor()!=null && !policy.getColor().equals(""))
						vehicle.setColor(policy.getColor());
					if(policy.getVehicle_price() != 0)
						vehicle.setBuy_money((float) policy.getVehicle_price());
					vehicle.setBuy_money((float) policy.getVehicle_price());
					if(policy.getRegister_date() != null)
						vehicle.setRegister_date(policy.getRegister_date());
					if(policy.getFix_time() != null){
						Unit unit = unitService.get(Unit.class, oldPolicy.getUnit_id());
						if(null != unit){
							unit.setFix_time(policy.getFix_time());
							unitService.update(unit);
						}
					}
					vehicle.setEngine_no(oldPolicy.getEngine_no());
					vehicleService.update(vehicle);
					policyService.saveOrUpdate(oldPolicy);
				}
				
				/*Servicetime servicetime = servicetimeService.getByUnitid(oldPolicy.getUnit_id());
				if(servicetime==null){
					servicetime = new Servicetime();
					servicetime.setCustomer_id(oldPolicy.getCustomer_id());
					servicetime.setUnit_id(oldPolicy.getUnit_id());
					servicetime.setVehicle_id(oldPolicy.getVehicle_id());
					servicetime.setSubco_no(oldPolicy.getSubco_no());
					servicetime.setInsurance_edate(policy.getIs_edate());
					servicetimeService.save(servicetime);
				}else{
					servicetime.setInsurance_edate(policy.getIs_edate());
					servicetimeService.update(servicetime);
				}*/
				
			}

		} catch (Exception e) {
			flag = false;
			msg = SystemConst.OP_FAILURE;
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保险修改结束");
		}
		resultMap.put("success", flag);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	
	
	
	@RequestMapping(value = "/policy/over", method = RequestMethod.POST)
	@LogOperation(description = "保险作废", type = SystemConst.OPERATELOG_UPDATE, model_id = 20040)
	public @ResponseBody
	HashMap over(@RequestBody Policy policy, BindingResult bindingResult,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保险作废开始");
		}
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (policy == null || policy.getInsurance_id() == null) {
				flag = false;
				msg = SystemConst.OP_FAILURE;
			
			}else{
				Policy oldPolicy = policyService.get(Policy.class, policy.getInsurance_id());
				oldPolicy.setRemark(policy.getRemark());
				oldPolicy.setStatus(SystemConst.POLICY_DELETEED);
				policyService.saveOrUpdate(oldPolicy);
			}

		} catch (Exception e) {
			flag = false;
			msg = SystemConst.OP_FAILURE;
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保险作废结束");
		}
		resultMap.put("success", flag);
		resultMap.put("msg", msg);
		return resultMap;
	}

	/**
	 * 
	 * @Title:getTodayPolicy
	 * @Description:获取今日工单列表
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/policy/getTodayPolicy")
	public @ResponseBody
	List<HashMap<String, Object>> getTodayPolicy(HttpServletRequest request)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得当日保险单开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
					Long id = compannyId == null ? null : Long.valueOf(compannyId);
			results = policyService.getTodayPolicy(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得当日保险单结束");
		}
		return results;
	}
	
	/**
	 * 
	 * @Title:findPolicysByPage
	 * @Description:分页查询保单
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/policy/findPolicysByPage")
	@LogOperation(description = "保单汇总报表", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20061)
	public @ResponseBody
	Page<HashMap<String, Object>> findPolicysByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询保单开始");
		}
		boolean flag = false;
		Page<HashMap<String, Object>> result = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
					Long id = compannyId == null ? null : Long.valueOf(compannyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}

			Object startDate = 	map.get("startDate");
			Object endDate = map.get("endDate");
			Object company = map.get("company");
			if(com.gboss.util.StringUtils.isNullOrEmpty(startDate) && com.gboss.util.StringUtils.isNullOrEmpty(endDate) && com.gboss.util.StringUtils.isNullOrEmpty(company)){
				flag = true;
			}
				pageSelect.setFilter(map);
			}
			if(flag){
				return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
			}else{
				result = policyService.findPolicys(id,pageSelect);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询保单结束");
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/policy/policyforPrint")
	@LogOperation(description = "保单汇总报表", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20061)
	public @ResponseBody
	List<HashMap<String, Object>> policyforPrint(
			@RequestBody Map<String, Object> map,
			HttpServletRequest request) throws SystemException {
		List<HashMap<String, Object>> results = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			results =  policyService.getexportExcelPolicy(id,map);
			for (HashMap<String, Object> haspmap : results) {
				String company_name = "";
				if(com.gboss.util.StringUtils.isNotNullOrEmpty(haspmap.get("company"))){
					if(haspmap.get("company").toString().equals("1")){
						company_name = "太平洋保险";
					}else if(haspmap.get("company").toString().equals("2")){
						company_name = "深圳平安保险";
					}
				}
				haspmap.put("company_name", company_name);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}
	
	
	
	
	@RequestMapping(value = "/policy/findCusInfoPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findCusInfoPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询客户开始");
		}
		
		Page<HashMap<String, Object>> result = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
			SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				// map.put("tStatus", -1);
				pageSelect.setFilter(map);
			}
			result = policyService.findCusInfos(pageSelect, id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询客户结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/policy/getEditMsgByCarNum")
	public @ResponseBody
	HashMap<String, Object> getEditMsgByCarNum(Long  id,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保单编辑获取详细信息开始");
		}
		HashMap<String, Object> results = null;
		try {
			results = policyService.getEditMsgByCarNum(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保单编辑获取详细信息结束");
		}
		return results;
	}
	

	@RequestMapping(value = "/policy/getCusMsg")
	public @ResponseBody
	HashMap<String, Object> getCusMsg(@RequestParam(required=false) Long id, Long opid, 
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保单编辑获取详细信息开始");
		}
		HashMap<String, Object> results = null;
		try {
			results = policyService.getCusMsg(id,opid);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保单编辑获取详细信息结束");
		}
		return results;
	}
	
	
	

	@RequestMapping(value = "/policy/updatePolicyprintMsg")
	public @ResponseBody
	HashMap<String, Object> updatePolicyprintMsg(@RequestParam(required=false) Long id,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("编辑保单打印信息开始");
		}
		HashMap<String, Object> results = new HashMap<String, Object>();
		try {
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			Policy policy = policyService.get(Policy.class, id);
			if(null != policy){
				policy.setPrint_op_id(userId == null ? null : Long.valueOf(userId));
				policy.setPrint_stamp(new Date());
				policy.setIs_print(1);
				policyService.update(policy);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保单编辑获取详细信息结束");
		}
		return results;
	}
	
	
	@RequestMapping(value = "/policy/getPolicyMsgByNum")
	public @ResponseBody
	HashMap<String, Object> getPolicyMsgByNum(@RequestParam(required=false) Long pid,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保单编辑获取详细信息开始");
		}
		HashMap<String, Object> results = null;
		try {
			results = policyService.getPolicyMsgByNum(pid);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保单编辑获取详细信息结束");
		}
		return results;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/policy/operatePolicy")
	@LogOperation(description = "保单状态更改", type = SystemConst.OPERATELOG_UPDATE, model_id = 20040)
	public @ResponseBody
	HashMap<String, Object> operatePolicy(@RequestParam(value="ids[]",required=false) List<Long> ids,Integer type, 
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保单状态更改开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = policyService.operatePolicy(ids, type);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
			}else if(result == -100){
				flag = false;
				msg = "请检查要开启项的时间段！";
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保单状态更改结束");
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/policy/exportExcelPolicys")
	@LogOperation(description = "保单汇总报表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20061)
	public @ResponseBody
	void exportExcelPolicys(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		try {
			Map returnMap =   request.getParameterMap();
			//将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
		    Map map = new HashMap();
		    Iterator entries =returnMap.entrySet().iterator();
		    Map.Entry entry = null;
		    String name = "";
		    String value = "";
		    while (entries.hasNext()) {
		        entry = (Map.Entry) entries.next();
		        name = (String) entry.getKey();
		        Object valueObj = entry.getValue();
		        if(null == valueObj){
		            value = "";
		        }else if(valueObj instanceof String[]){
		            String[] values = (String[])valueObj;
		            for(int i=0;i<values.length;i++){
		                value = values[i] + ",";
		            }
		            value = value.substring(0, value.length()-1);
		        }else{
		            value = valueObj.toString();
		        }
		        if("null".equals(value)){
		        	value="";
		        }
		        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)){
		        	map.put(name, value);
		        }
		    }
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
		    
			String[][] title = {{"序号","10"},{"保单号","30"},{"入网名","20"},{"车牌号码","30"},{"销售员","20"},{"投保公司","25"},{"办理日期","40"}};
			List<HashMap<String, Object>> list = policyService.getexportExcelPolicy(id,map);
			List valueList = new ArrayList();
			HashMap<String, Object> policy = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				values = new String[7];
				policy = list.get(i);
				values[0] = String.valueOf(i+1);
				values[1] = policy.get("policyno")==null ?"":policy.get("policyno").toString();
				values[2] = policy.get("loginname")==null ?"":policy.get("loginname").toString();
				values[3] = policy.get("carNum")==null ?"":policy.get("carNum").toString();
				values[4] = policy.get("sales_person")==null ?"":policy.get("sales_person").toString();
				values[5] = policy.get("company")==null ?"":policy.get("company").toString();
				if(values[5].equals("1")){
					values[5] = "太平洋保险";
				}else if(values[5].equals("2")){
					values[5] = "深圳平安保险";
				}
				values[6] = policy.get("stamp")==null ?"":policy.get("stamp").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(compannyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "保险清单汇总", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
	@RequestMapping(value = "/policy/exportExcelPolicysNew")
	@LogOperation(description = "保单汇总报表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20061)
	public @ResponseBody
	void exportExcelPolicysNew(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		try {
			Map returnMap =   request.getParameterMap();
			//将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
		    Map map = new HashMap();
		    Iterator entries =returnMap.entrySet().iterator();
		    Map.Entry entry = null;
		    String name = "";
		    String value = "";
		    while (entries.hasNext()) {
		        entry = (Map.Entry) entries.next();
		        name = (String) entry.getKey();
		        Object valueObj = entry.getValue();
		        if(null == valueObj){
		            value = "";
		        }else if(valueObj instanceof String[]){
		            String[] values = (String[])valueObj;
		            for(int i=0;i<values.length;i++){
		                value = values[i] + ",";
		            }
		            value = value.substring(0, value.length()-1);
		        }else{
		            value = valueObj.toString();
		        }
		        if("null".equals(value)){
		        	value="";
		        }
		        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)){
		        	map.put(name, value);
		        }
		    }
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
		    
			String[][] title = {
			{"序号","10"},{"保单号","30"},{"投保公司","20"},{"入网名","20"},{"意外险投保人","20"},{"投保人身份证号","30"},{"出生年月日","30"},{"车牌号","20"},
			{"发动机号","20"},{"车架号","30"},{"车辆初次登记日期","30"},{"服务开通日期","30"},{"服务截止日期","30"},{"保额(万)","10"},{"赔付比例","10"},{"是否已购盗抢险","25"},
			{"交费方式","20"},{"厂牌型号/颜色","30"},{"车载电话","30"},{"移动电话","30"},{"固定电话","20"},{"产品类型","20"},{"安装日期","30"},{"地址","50"},{"销售员","20"},
			{"安装电工","20"},{"客户类型","30"},{"盗抢险金额","20"},{"服务费金额","30"},{"录入日期","30"},{"录入人","30"},{"备注","30"}
			};
			List<HashMap<String, Object>> list = policyService.getexportExcelPolicy(id,map);
			List valueList = new ArrayList();
			HashMap<String, Object> policy = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				values = new String[32];
				Unit unit = null;
				policy = list.get(i);
				values[0] = String.valueOf(i+1);
				values[1] = policy.get("policy_no")==null ?"":policy.get("policy_no").toString();
				values[2] = policy.get("company")==null ?"":policy.get("company").toString();
				if(values[2].equals("1")){
					values[2] = "太平洋保险";
				}else if(values[2].equals("2")){
					values[2] = "深圳平安保险";
				}
				values[3] = policy.get("loginname")==null ?"":policy.get("loginname").toString();
				values[4] = policy.get("policyholder")==null ?"":policy.get("policyholder").toString();
				values[5] = policy.get("ph_id_no")==null ?"":policy.get("ph_id_no").toString();
				values[6] = policy.get("birthday")==null ?"":policy.get("birthday").toString().substring(0, 10);
				values[7] = policy.get("carNum")==null ?"":policy.get("carNum").toString();
				values[8] = policy.get("engine_no")==null ?"":policy.get("engine_no").toString();
				values[9] = policy.get("vin")==null ?"":policy.get("vin").toString();
				values[10] = policy.get("register_date")==null ?"":policy.get("register_date").toString().substring(0, 10);
				values[11] = policy.get("is_bdate")==null ?"":policy.get("is_bdate").toString().substring(0, 10);
				values[12] = policy.get("is_edate")==null ?"":policy.get("is_edate").toString().substring(0, 10);
				
				String amount = policy.get("amount").toString();
				if(amount != null && amount.length() > 6){
					values[13] = amount.substring(0, amount.length()-6);
				}else{
					values[13] = null;
				}
				String ratio =  policy.get("ratio").toString();
				values[14] = ratio+"%";
				
				values[15] = "";
				String is_buy_tp =policy.get("is_buy_tp").toString();
				if(is_buy_tp.equals("0")){
					values[15] ="否";
				}else{
					values[15] ="是";
				}
				
				values[16] = "按年缴费";
				values[17] = "";
				if(policy.get("model")!=null){
					values[17] = policy.get("model").toString() + "/";
				}
				if(policy.get("color")!=null){
					values[17] = values[16] + policy.get("color").toString();
				}
				values[18] = "";
				if(policy.get("unit_id")!=null){
					unit = unitService.get(Unit.class, Long.valueOf(policy.get("unit_id").toString()));
					if(null != unit)
						values[18] = unit.getCall_letter();
				}
				values[19] = policy.get("phone")==null ?"":policy.get("phone").toString();
				values[20] = policy.get("tel")==null ?"":policy.get("tel").toString();
				values[21] = policy.get("unittype")==null ?"":policy.get("unittype").toString();
				values[22] = policy.get("fix_time")==null ?"":policy.get("fix_time").toString().substring(0, 10);
				
				
				values[23] = policy.get("address")==null ?"":policy.get("address").toString();
				
				values[24] = policy.get("sales_person")==null ?"":policy.get("sales_person").toString();
				values[25] ="";
				if(null != unit)
					values[25] = unit.getWorker();
				
				values[26] = "";
				if(policy.get("customer_id")!=null){
					Customer cu = customerService.get(Customer.class, Long.valueOf(policy.get("customer_id").toString()));
					if(null != cu){
						if(cu.getCust_type() ==0){
							values[26] ="私家车客户";
						}else if(cu.getCust_type() ==1){
							values[26] ="集团客户";
						}else{
							values[26] ="担保公司";
						}
					}
				}
				values[27] = "";
				FeeInfo policy_info = feeInfoService.getFeeInfo(Long.valueOf(policy.get("unit_id").toString()), 3);
				if(null != policy_info)
					values[27] =policy_info.getMonth_fee().toString();
				values[28] = "";
				FeeInfo service_info = feeInfoService.getFeeInfo(Long.valueOf(policy.get("unit_id").toString()), 1);
				if(null != service_info)
					values[28] =service_info.getMonth_fee().toString();
				values[29] = policy.get("stamp")==null ?"":policy.get("stamp").toString().substring(0, 10);
				values[30] = "";
					CommonOperator user =ldap.getOperatorById(policy.get("op_id").toString());
					if(null != user)
						values[30] = user.getOpname();
				values[31] = "";
					if(policy.get("remark")!=null){
						values[31] =policy.get("remark").toString();
					}
				valueList.add(values);
			}
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(compannyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "保险清单汇总", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
	
	
	@RequestMapping(value = "/policy/transferl", method = RequestMethod.POST)
	@LogOperation(description = "保险办理", type = SystemConst.OPERATELOG_ADD, model_id = 20040)
	public @ResponseBody
	HashMap transferl(@RequestBody Policy policy, BindingResult bindingResult,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保险过户开始");
		}
		boolean flag = false;
		String msg = SystemConst.OP_FAILURE;
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(null != policy){
				Policy oldPolicy = policyService.get(Policy.class, policy.getTransferl_id());
				policy.setSubco_no(companyId == null ? null : Long.valueOf(companyId));
				policy.setStatus(oldPolicy.getStatus());
				policy.setIs_transfer(1);
				policyService.save(policy);
				oldPolicy.setStatus(SystemConst.POLICY_DELETEED);
				policyService.update(oldPolicy);
				if(policy.getInsurance_id() !=null){
					 flag = true;
					 msg = SystemConst.OP_SUCCESS;
				}
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保险过户理结束");
		}
		resultMap.put("success", flag);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	
	@RequestMapping(value = "/policy/findPolicyPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findPolicyPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询车辆盗抢险记录开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
			SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			result = policyService.findPolicyPage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询客户结束");
		}
		return result;
	}

	

}
