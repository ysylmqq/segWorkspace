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
import com.gboss.pojo.Customer;
import com.gboss.pojo.web.VerifyPOJO;
import com.gboss.service.UnitService;
import com.gboss.service.VehicleService;
import com.gboss.util.DateUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.controller
 * @ClassName:VehicleController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-5 下午3:39:01
 */
@Controller
public class VehicleController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(VehicleController.class);
	
	@Autowired
	@Qualifier("vehicleService")
	private VehicleService vehicleService;
	
	@Autowired
	@Qualifier("UnitService")
	private UnitService unitService;
	
	@RequestMapping(value = "/vehicle/findVehicleInfoPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findVehicleInfoPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询车辆开始");
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
			result = vehicleService.findVehiclePage(pageSelect, id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询车辆结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/vehicle/tree")
	public @ResponseBody List<HashMap<String, Object>> tree(
			@RequestBody Customer customer,BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		List<HashMap<String, Object>> result = vehicleService.getVehicleTree(customer);
		return result;
	}
	
	@RequestMapping(value = "/vehicle/innetwork")
	@LogOperation(description = "入网报表", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20060)
	public @ResponseBody Page<HashMap<String, Object>> innetwork(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		Object date = pageSelect.getFilter().get("start_date");
		String time = date == null ?null : date.toString();
		if(time==null){
			return PageUtil.getPage(0, pageSelect.getPageNo(), list, pageSelect.getPageSize());
			//time = DateUtil.format(new Date(), DateUtil.YM_DASH);
		}
		OpenLdap ldap = OpenLdapManager.getInstance();
		List<CommonCompany> companylist = ldap.getChildsByCompanyId("3");//获得所有分公司
		int total = companylist.size();
		int start = (pageSelect.getPageNo()-1)*pageSelect.getPageSize();
		int end = pageSelect.getPageNo()*pageSelect.getPageSize();
		int num = total<end?total:end;
		for(int i = start; i < num; i++){
		
			CommonCompany company = companylist.get(i);
		
		//for(CommonCompany company : companylist){
			HashMap<String, Object> map = unitService.getInnetwork(Long.valueOf(company.getCompanyno()), time);
			map.put("subco_name", company.getCompanyname());
			list.add(map);
		}
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}
	
	@RequestMapping(value = "/vehicle/innetworkforPrint")
	public @ResponseBody List<HashMap<String, Object>> innetworkforPrint(
			@RequestBody VerifyPOJO verify,BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		String time = verify.getParameter();
		if(time==null){
			time = DateUtil.format(new Date(), DateUtil.YM_DASH);
		}
		OpenLdap ldap = OpenLdapManager.getInstance();
		List<CommonCompany> companylist = ldap.getChildsByCompanyId("3");//获得所有分公司
		for(CommonCompany company : companylist){
			HashMap<String, Object> map = unitService.getInnetwork(Long.valueOf(company.getCompanyno()), time);
			map.put("subco_name", company.getCompanyname());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(value = "/vehicle/exportExcelinnetwork")
	@LogOperation(description = "入网报表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20060)
	public @ResponseBody void exportExcelinnetwork(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		try {
			Map returnMap = request.getParameterMap();
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
			String time = (String) map.get("start_date");
			if(time==null){
				time = DateUtil.format(new Date(), DateUtil.YM_DASH);
			}
		    
			String[][] title = {{"序号","10"},{"公司名称","30"},{"入网完成","20"},{"其中私车","30"},{"当月脱网","20"},{"在网总数","25"}};
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
			OpenLdap ldap = OpenLdapManager.getInstance();
			List<CommonCompany> companylist = ldap.getChildsByCompanyId("3");//获得所有分公司
			for(CommonCompany company : companylist){
				HashMap<String, Object> innetworkmap = unitService.getInnetwork(Long.valueOf(company.getCompanyno()), time);
				innetworkmap.put("subco_name", company.getCompanyname());
				list.add(innetworkmap);
			}
			
			List valueList = new ArrayList();
			HashMap<String, Object> innetwork = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				values = new String[6];
				innetwork = list.get(i);
				values[0] = String.valueOf(i+1);
				values[1] = innetwork.get("subco_name")==null ?"":innetwork.get("subco_name").toString();
				values[2] = innetwork.get("innetwork")==null ?"":innetwork.get("innetwork").toString();
				values[3] = innetwork.get("privatecar")==null ?"":innetwork.get("privatecar").toString();
				values[4] = innetwork.get("outnetwork")==null ?"":innetwork.get("outnetwork").toString();
				values[5] = innetwork.get("totalnum")==null ?"":innetwork.get("totalnum").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(compannyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "入网汇总", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
	
	
	
	

	@RequestMapping(value = "/vehicle/countServiceFeePage")
	@LogOperation(description = "车辆服务费", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20066)
	public @ResponseBody Page<HashMap<String, Object>> countServiceFeePage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,BindingResult bindingResult,HttpServletRequest request)  {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		int total = 0;
		try {
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			Object start_date = pageSelect.getFilter().get("start_date");
			Object end_date = pageSelect.getFilter().get("end_date");
			boolean flag = false;
			if(com.gboss.util.StringUtils.isNullOrEmpty(start_date) && com.gboss.util.StringUtils.isNullOrEmpty(end_date)){
				flag = true;
			}
			if(flag){
				return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
			}else{
				List<HashMap<String, Object>> serviceList = vehicleService.countServiceFeePage(id, pageSelect.getFilter());
				total = serviceList.size();
				int start = (pageSelect.getPageNo()-1)*pageSelect.getPageSize();
				int end = pageSelect.getPageNo()*pageSelect.getPageSize();
				int num = total<end?total:end;
				for(int i = start; i < num; i++){
					list.add(serviceList.get(i));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}
	
	
	
	 
	@RequestMapping(value = "/vehicle/findServiceFeeByPage")
	@LogOperation(description = "车辆服务费报表", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20066)
	public @ResponseBody
	Page<HashMap<String, Object>> findServiceFeeByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询车辆服务费开始");
		}
		boolean flag = false;
		Page<HashMap<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			Long id = companyId == null ? null : Long.valueOf(companyId);
			String companyCode = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYCODE);
			pageSelect.getFilter().put("companyCode", companyCode);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}

				Object startDate = 	map.get("start_date");
				Object endDate = map.get("end_date");
				Object fee = map.get("fee");
				if(com.gboss.util.StringUtils.isNullOrEmpty(startDate) && com.gboss.util.StringUtils.isNullOrEmpty(endDate)&& com.gboss.util.StringUtils.isNullOrEmpty(fee)){
					flag = true;
				}
				pageSelect.setFilter(map);
			}
			if(flag){
				return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
			}else{
				result = vehicleService.findServiceFeePage(id, pageSelect);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询车辆服务费结束");
		}
		return result;
	}
	
	

	@RequestMapping(value = "/vehicle/exportServiceFeeList")
	@LogOperation(description = "车辆服务费报表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20066)
	public @ResponseBody
	void exportServiceFeeList(
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
			{"序号","10"},{"客户名","20"},{"车牌号","20"},{"车载电话","25"},{"安装日期","25"},{"销售经理","20"},{"服务费(元)","25"}
			};
			List<HashMap<String, Object>> list = vehicleService.countServiceFeePage(id, map);
			List valueList = new ArrayList();
			HashMap<String, Object> stop = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				values = new String[7];
				stop = list.get(i);
				values[0] = String.valueOf(i+1);
				values[1] = stop.get("customer_name")==null ?"":stop.get("customer_name").toString();
				values[2] = stop.get("plate_no")==null ?"":stop.get("plate_no").toString();
				values[3] = stop.get("call_letter")==null ?"":stop.get("call_letter").toString();
				values[4] = stop.get("fix_time")==null ?"":stop.get("fix_time").toString().substring(0, 10);
				values[5] = stop.get("sales")==null ?"":stop.get("sales").toString();
				values[6] = stop.get("total")==null ?"0":stop.get("total").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(compannyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "客户服务费汇总", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
	
	 //入网未录计费汇总
	@RequestMapping(value = "/vehicle/onlineNoFeeByPage")
	@LogOperation(description = "入网未计费报表", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20064)
	public @ResponseBody
	Page<HashMap<String, Object>> onlineNoFeeByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询入网未录计费汇总开始");
		}
		boolean flag = false;
		Page<HashMap<String, Object>> result = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			String companyCode = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYCODE);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}

				Object startDate = 	map.get("start_date");
				Object endDate = map.get("end_date");
				if(com.gboss.util.StringUtils.isNullOrEmpty(startDate) && com.gboss.util.StringUtils.isNullOrEmpty(endDate)){
					flag = true;
				}
				map.put("companyCode", companyCode);
				pageSelect.setFilter(map);
			}
			if(flag){
				return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
			}else{
				result = vehicleService.findOnlineNoFeePage(id, pageSelect);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询入网未录计费汇总结束");
		}
		return result;
	}
	
	

	@RequestMapping(value = "/vehicle/exportOnlineNoFeeList")
	@LogOperation(description = "入网未计费报表导出", type = SystemConst.OPERATELOG_ADD, model_id =20064)
	public @ResponseBody
	void exportOnlineNoFeeList(
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
			{"序号","10"},{"客户名","20"},{"车牌号","20"},{"车载电话","25"},{"销售经理","20"},{"安装日期","25"},{"入网日期","25"}
			};
			List<HashMap<String, Object>> list = vehicleService.findOnlineNoFeeList(id, map);
			List valueList = new ArrayList();
			HashMap<String, Object> entity = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				values = new String[7];
				entity = list.get(i);
				values[0] = String.valueOf(i+1);
				values[1] = entity.get("customer_name")==null ?"":entity.get("customer_name").toString();
				values[2] = entity.get("plate_no")==null ?"":entity.get("plate_no").toString();
				values[3] = entity.get("call_letter")==null ?"":entity.get("call_letter").toString();
				values[4] = entity.get("sales")==null ?"":entity.get("sales").toString();
				values[5] = entity.get("fix_time")==null ?"":entity.get("fix_time").toString().substring(0, 10);
				values[6] = entity.get("create_date")==null ?"":entity.get("create_date").toString().substring(0, 10);
				valueList.add(values);
			}
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(compannyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "入网未计费汇总", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
	/**
	 * @Desc  门店系统 车辆监控功能查询车辆GPS位置信息记录
	 * @param verify
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/webgisList", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> webgisList(
			@RequestBody VerifyPOJO verify,BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String compannyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String parameter = verify.getParameter();
		List<HashMap<String, Object>> list = vehicleService.webgisList(parameter, Long.valueOf(compannyId));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("list", list);
		return result;
	}
	
	
	
	

}

