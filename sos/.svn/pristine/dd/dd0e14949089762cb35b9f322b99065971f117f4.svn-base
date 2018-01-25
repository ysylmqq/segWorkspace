package com.gboss.controller;

import java.util.ArrayList;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.UnitType;
import com.gboss.service.UnitService;
import com.gboss.service.UnitTypeService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.Utils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.controller
 * @ClassName:UnitController
 * @author:bzhang
 * @date:2014-6-10 下午2:51:12
 */
@Controller
public class UnitController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(UnitController.class);

	@Autowired
	private UnitService unitService;
	
	@Autowired
	private UnitTypeService unitTypeService;

	@RequestMapping(value = "/unit/getUnitMsgBypage")
	@LogOperation(description = "指标监管报表", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20062)
	public @ResponseBody
	Page<HashMap<String, Object>> getUnitMsgBypage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询指标监管报表开始");
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
				Object startDate = 	map.get("startDate");
				Object endDate = map.get("endDate");
				Object customer_name = map.get("customer_name");
				if(com.gboss.util.StringUtils.isNullOrEmpty(startDate) && com.gboss.util.StringUtils.isNullOrEmpty(endDate) && com.gboss.util.StringUtils.isNullOrEmpty(customer_name)){
					flag = true;
				}
				// map.put("tStatus", -1);
				pageSelect.setFilter(map);
			}
			if(flag){
				return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
			}else{
				result = unitService.getUnitMsgBypage(id, pageSelect);
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询指标监管报表结束");
		}
		return result;
	}

	
	
	/**
	 * 获取打印数据列表
	 * @Title:findUnits
	 * @Description:
	 * @param map
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/unit/findUnits")
	public @ResponseBody
	List<HashMap<String, Object>> findUnits(
			@RequestBody Map<String, Object> map,
			HttpServletRequest request) throws SystemException {
		List<HashMap<String, Object>> results = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			results = unitService.findUnits(id, map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}
	
	
	
	
	
	@RequestMapping(value = "/unit/exportExcelUnits")
	@LogOperation(description = "指标监管报表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20062)
	public @ResponseBody
	void exportExcelUnits(HttpServletRequest request,
			HttpServletResponse response) throws SystemException {
		try {
			Map returnMap = request.getParameterMap();
			// 将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
			Map map = new HashMap();
			Iterator entries = returnMap.entrySet().iterator();
			Map.Entry entry = null;
			String name = "";
			String value = "";
			while (entries.hasNext()) {
				entry = (Map.Entry) entries.next();
				name = (String) entry.getKey();
				Object valueObj = entry.getValue();
				if (null == valueObj) {
					value = "";
				} else if (valueObj instanceof String[]) {
					String[] values = (String[]) valueObj;
					for (int i = 0; i < values.length; i++) {
						value = values[i] + ",";
					}
					value = value.substring(0, value.length() - 1);
				} else {
					value = valueObj.toString();
				}
				if ("null".equals(value)) {
					value = "";
				}
				if (StringUtils.isNotBlank(name)
						&& StringUtils.isNotBlank(value)) {
					map.put(name, value);
				}
			}
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = companyId == null ? null : Long.valueOf(companyId);
			if (StringUtils.isNotBlank(companyId)) { 
				map.put("companyId", companyId);
			}
			String[][] title = { { "序号", "10" }, { "客户姓名", "20" },
					 { "车牌号", "30" }, { "销售经理", "20" },{ "是否交单", "20" },
					 { "审核是否通过", "20" },
					 { " 入网时间", "40" }, { "审核备注", "50" } };
			List<HashMap<String, Object>> list = unitService.getexportExcelUnits(id, map);
			List valueList = new ArrayList();
			HashMap<String, Object> unit = null;
			String[] values = null;
			int listLenth = list.size();
			for (int i = 0; i < listLenth; i++) {
				values = new String[9];
				unit = list.get(i);
				values[0] = String.valueOf(i + 1);
				values[1] = unit.get("customer_name") == null ? "" : unit.get(
						"customer_name").toString();
				values[2] = unit.get("car_num") == null ? "" : unit.get(
						"car_num").toString();
				values[3] = unit.get("sales") == null ? "" : unit.get(
						"sales").toString();
				values[4] = unit.get("d_id") == null ? "否" :"是";
				values[5] ="否";
				if(com.gboss.util.StringUtils.isNotNullOrEmpty(unit.get("flag")) &&  unit.get("flag").toString().equals("1")){
					values[5] ="是";
				}
				values[6] = unit.get("register_date") == null ? "" : unit.get("register_date").toString().substring(0, 19);
				values[7] = unit.get("remark") == null ? "" : unit.get("remark").toString();
				valueList.add(values);
			}
			// 获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany = openLdap.getCompanyById(companyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "指标监管报表",
					title, commonCompany.getCnfullname(),
					commonCompany.getEnfullname(), false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
	@RequestMapping(value = "/unit/getUnitType")
	public @ResponseBody
	Page<UnitType> getUnitType(
			@RequestBody PageSelect<UnitType> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<UnitType> result = unitTypeService.findUnitType(pageSelect);
		return result;
	}
	
	/**
	 * @Title:findUnitInNets
	 * @Description:查询入网车辆列表  服务开通日报表
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/unit/findUnitInNets")
	@LogOperation(description = "安装用户查询", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20063)
	public @ResponseBody
	Page<HashMap<String, Object>> findUnitInNets(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
				String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
				String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
				String companyCode = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYCODE);
				if (StringUtils.isNotBlank(userId)) {
					OpenLdap openLdap = OpenLdapManager.getInstance();
					//判断是否是分公司
					int level=openLdap.getUserCompanyLevel(companyId);
					//是否是总部，特殊处理
					String ishead = Utils.isNullOrEmpty((request.getSession().getAttribute(
							SystemConst.ACCOUNT_ISHQ)))? "false":request.getSession().getAttribute(
									SystemConst.ACCOUNT_ISHQ).toString();
					boolean ishq = Boolean.valueOf(ishead);
					if(level==0){//总部
						
					}else if(level==1){//分公司
						if(!ishq){
							pageSelect.getFilter().put("subcoNo", Long.valueOf(companyId));
						}
					}else if(level==2){//营业处
						if(!ishq){
							pageSelect.getFilter().put("subcoNo", Long.valueOf(companyId));
						}
					}
					pageSelect.getFilter().put("companyCode", companyCode);
				}
			}
			results = unitService.findUnitInNetsBypage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		return results;
	}
	
	@RequestMapping(value = "/unit/exportExcel4UnitInNets")
	@LogOperation(description = "安装用户报表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20063)
	public void exportExcel4UnitInNets(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出安装用户报表 开始");
		}
		try {
			//条件
			Map returnMap =   parseReqParam2(request);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String userName = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			//是否是总部，特殊处理
			String ishead = Utils.isNullOrEmpty((request.getSession().getAttribute(
					SystemConst.ACCOUNT_ISHQ)))? "false":request.getSession().getAttribute(
							SystemConst.ACCOUNT_ISHQ).toString();
			boolean ishq = Boolean.valueOf(ishead);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(companyId);
			if(level==0){//总部
				
			}else{//分公司或者营业处
				if(!ishq){
					returnMap.put("subcoNo", Long.valueOf(companyId));
				}
			}
			List<HashMap<String, Object>> results = unitService.findAllUnitInNets(returnMap);
			String[][] title = {{"序号","10"},{"客户名称","20"},{"联系方式","20"},{"身份证","20"},{"客户类型","20"},{"分公司","20"},{"车牌号码","20"},{"发动机号","40"},{"车架号","20"},{"车型/颜色","20"},{"车载号码","20"},{"产品类型","10"},{"安装日期","15"},{"开通日期","15"},{"销售经理","10"},{"安装电工","10"},{"安装地点","15"},{"销售网点","10"},{"入网地","10"},{"通信模式","10"},{"服务费","10"},{"服务截止日期","15"},{"SIM卡流量费","10"},{"SIM卡截止日期","15"},{"坐席备注","40"}};
			int columnIndex=0;
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int titleLength=title.length;
			for(int i=0; i<listLenth; i++){
				values = new String[titleLength];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("customerName"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("linkmanPhone"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("idNo"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("custType"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("subcoName"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("plateNo"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("engineNo"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("vin"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("vcolor"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("callLetter"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("unittype"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("fixTime"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("serviceDate"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("sales"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("worker"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("place"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("branch"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("area"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("mode"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("realAmount1"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("feeSedate1"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("realAmount2"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("feeSedate2"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("remark"));
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	       /* StringBuffer sb=new StringBuffer();
	        sb.append("服务开通报表[");
	        if(returnMap.get("startDate")!=null && StringUtils.isNotBlank(returnMap.get("startDate").toString())){//开始日期
				sb.append(returnMap.get("startDate")).append("~");
			}
			if(returnMap.get("endDate")!=null && StringUtils.isNotBlank(returnMap.get("endDate").toString())){//结束日期
				sb.append(returnMap.get("endDate"));
			}
			if(StringUtils.isNotBlank(userName)){
				sb.append("，制表人：").append(userName).append("，");
			}
			sb.append("制表时间：").append(DateUtil.formatNowTime()).append("]");*/
			CreateExcel_PDF_CSV.createExcel(valueList, response, "服务开通报表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出安装用户报表 结束");
		}
	}
	
	@RequestMapping(value = "/unit/findHmUnitInNets")
	@LogOperation(description = "海马安装用户查询", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20082)
	public @ResponseBody
	Page<HashMap<String, Object>> findHmUnitInNets(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			//subcoNo参数暂时固定为海马201，通过界面传递，入网时间字段与“安装用户查询”查询不同，来源表不同
			results = unitService.findUnitInNetsBypage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		return results;
	}
	
	@RequestMapping(value = "/unit/exportExcelHmUnitInNets")
	@LogOperation(description = "海马安装用户报表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20082)
	public void exportExcelHmUnitInNets(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出海马安装用户报表 开始");
		}
		try {
			//条件
			Map returnMap =   parseReqParam2(request);
			String companyId = Utils.isNullOrEmpty(returnMap.get("subcoNo"))?"201":returnMap.get("subcoNo").toString();
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(companyId);
			List<HashMap<String, Object>> results = unitService.findAllUnitInNets(returnMap);
			String[][] title = {{"序号","10"},{"客户名称","20"},{"联系方式","20"},{"身份证","20"},{"客户类型","20"},{"分公司","20"},
					{"车牌号码","20"},{"发动机号","40"},{"车架号","20"},{"条码","20"},{"配置简码","20"},{"车型","20"},{"颜色","20"},{"车载号码","20"},{"产品类型","10"},{"销售日期","15"},{"安装日期","15"},{"绑定日期","15"},{"销售经理","10"},{"安装电工","10"},{"安装地点","15"},{"销售网点","10"},{"入网地","10"},{"通信模式","10"},{"服务费","10"},{"服务截止日期","15"},{"SIM卡流量费","10"},{"SIM卡截止日期","15"},{"坐席备注","40"}};
			int columnIndex=0;
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int titleLength=title.length;
			for(int i=0; i<listLenth; i++){
				values = new String[titleLength];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("customerName"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("linkmanPhone"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("idNo"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("custType"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("subcoName"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("plateNo"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("engineNo"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("vin"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("content"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("equip_code"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("model_name"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("color"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("callLetter"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("unittype"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("buyDate"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("fixTime"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("createDate"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("sales"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("worker"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("place"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("branch"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("area"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("mode"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("realAmount1"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("feeSedate1"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("realAmount2"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("feeSedate2"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("remark"));
				valueList.add(values);
			}
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			CreateExcel_PDF_CSV.createExcel(valueList, response, "海马服务开通报表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出海马安装用户报表 结束");
		}
	}
	
	
	@RequestMapping(value = "/unit/exportExcelHmUnitInSales")
	@LogOperation(description = "海马销售报表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20082)
	public void exportExcelHmUnitInSales(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出海马销售报表 开始");
		}
		try {
			//条件
			Map returnMap =   parseReqParam2(request);
			String companyId = Utils.isNullOrEmpty(returnMap.get("subcoNo"))?"201":returnMap.get("subcoNo").toString();
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(companyId);
			List<HashMap<String, Object>> results = unitService.findAllUnitInNets(returnMap);
			String[][] title = {{"序号","10"},{"客户名称","20"},{"联系方式","20"},{"身份证","20"},{"客户类型","20"},{"分公司","20"},
					{"车牌号码","20"},{"发动机号","40"},{"车架号","20"},{"配置简码","20"},{"车型","20"},{"颜色","20"},{"车载号码","20"},
					{"产品类型","10"},{"销售日期","15"},{"安装日期","15"},{"开通日期","15"},{"销售经理","10"},{"安装电工","10"},{"安装地点","15"},{"销售网点","10"},
					{"入网地","10"},{"通信模式","10"},{"服务费","10"},{"服务截止日期","15"},{"SIM卡流量费","10"},{"SIM卡截止日期","15"},
					{"坐席备注","40"}};
			int columnIndex=0;
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int titleLength=title.length;
			for(int i=0; i<listLenth; i++){
				values = new String[titleLength];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("customerName"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("linkmanPhone"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("idNo"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("custType"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("subcoName"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("plateNo"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("engineNo"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("vin"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("equip_code"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("model_name"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("color"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("callLetter"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("unittype"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("buyDate"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("fixTime"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("serviceDate"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("sales"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("worker"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("place"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("branch"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("area"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("mode"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("realAmount1"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("feeSedate1"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("realAmount2"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("feeSedate2"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("remark"));
				valueList.add(values);
			}
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			CreateExcel_PDF_CSV.createExcel(valueList, response, "海马销售报表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出海马销售报表 结束");
		}
	}
}
