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
import com.gboss.service.ReportService;
import com.gboss.util.PageSelect;
import com.gboss.util.Utils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

@Controller
@RequestMapping(value="/rpt")
public class ReportController extends BaseController {

	static Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	@Qualifier("reportService")
	private ReportService rptSer;
	
	private OpenLdap ldap = OpenLdapManager.getInstance();
	
	/**
	 * 入网汇总查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="joinNetSum", method=RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> findJoinNetSumPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request,
			HttpServletResponse response)throws SystemException{
		Page<Map<String, Object>> result = new Page<Map<String,Object>>();
		try{
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
			}
			result = rptSer.findJoinNetSumPage(pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}

	/**
	 * 入网明细查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="joinNetDetail", method=RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> findJoinNetDetailPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request,
			HttpServletResponse response)throws SystemException{
		Page<Map<String, Object>> result = new Page<Map<String,Object>>();
		try{
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
			}
			Map map = pageSelect.getFilter();
			Object sId = map.get("s_id");
			if(com.gboss.util.StringUtils.isNullOrEmpty(sId)){
				return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
			}
			result = rptSer.findJoinNetDetailPage(pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}
	

	/**
	 * 离网汇总查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="offNetSum", method=RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> findOffNetSumPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request,
			HttpServletResponse response)throws SystemException{
		Page<Map<String, Object>> result = new Page<Map<String,Object>>();
		try{
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
			}
			result = rptSer.findOffNetSumPage(pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}

	/**
	 * 离网明细查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="offNetDetail", method=RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> findOffNetDetailPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request,
			HttpServletResponse response)throws SystemException{
		Page<Map<String, Object>> result = new Page<Map<String,Object>>();
		try{
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
			}
			Map map = pageSelect.getFilter();
			Object sId = map.get("s_id");
			if(com.gboss.util.StringUtils.isNullOrEmpty(sId)){
				return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
			}
			result = rptSer.findOffNetDetailPage(pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}

	/**
	 * 未开通汇总查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="unServiceSum", method=RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> findUnServiceSumPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request,
			HttpServletResponse response)throws SystemException{
		Page<Map<String, Object>> result = new Page<Map<String,Object>>();
		try{
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
			}
			result = rptSer.findUnServiceSumPage(pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}

	/**
	 * 未开通明细查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="unServiceDetail", method=RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> findUnServiceDetailPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request,
			HttpServletResponse response)throws SystemException{
		Page<Map<String, Object>> result = new Page<Map<String,Object>>();
		try{
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
			}
			Map map = pageSelect.getFilter();
			Object sId = map.get("s_id");
			if(com.gboss.util.StringUtils.isNullOrEmpty(sId)){
				return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
			}
			result = rptSer.findUnServiceDetailPage(pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * 获取某品牌下车型
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="getModels", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> findModelList(@RequestBody Map<String, Object> param,
			HttpServletRequest request,
			HttpServletResponse response)throws SystemException{
		return rptSer.findModelList(param);
	}

	/**
	 * 获取ldap下某车厂4s店
	 * @param param
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "get4SShop", method = RequestMethod.POST)
	public @ResponseBody List<CommonCompany> getBranchs(@RequestBody Map<String, Object> param, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String companyno = Utils.isNullOrEmpty(param.get("companyno"))? "201" : param.get("companyno").toString();
		List<CommonCompany> list = ldap.getChildsByCompanyId(companyno);
		List<CommonCompany> companyList = new ArrayList<CommonCompany>();
		for (CommonCompany commonCompany : list) {
			if(commonCompany.getCompanytype() != null && commonCompany.getCompanytype().equals("6")){
				companyList.add(commonCompany);
			}
		}
		return companyList;
	}
	
	/**
	 * 库存汇总查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="stockStatistics", method=RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> stockStatistics(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request,
			HttpServletResponse response)throws SystemException{
		Page<Map<String, Object>> result = new Page<Map<String,Object>>();
		try{
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
			}
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			result = rptSer.findStockStatisticsPage(id, pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * 未安装汇总查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value="unfixedStatistics", method=RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> unfixedStatistics(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request,
			HttpServletResponse response)throws SystemException{
		Page<Map<String, Object>> result = new Page<Map<String,Object>>();
		try{
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
			}
			String compannyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			result = rptSer.findUnfixedStatisticsPage(id, pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}

	/**
	 * 私家车周统计查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "privateWeekCount", method = RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> privateWeekCount(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request, HttpServletResponse response)throws SystemException{
		Page<Map<String, Object>> result = new Page<Map<String,Object>>();
		boolean flag = false;
		try{
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				Object startDate = 	map.get("startDate");
				Object endDate = map.get("endDate");
				if(com.gboss.util.StringUtils.isNullOrEmpty(startDate) && com.gboss.util.StringUtils.isNullOrEmpty(endDate)){
					flag = true;
				}
				String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
				String companyCode = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYCODE);
				map.put("subco_no", companyId);
				map.put("companyCode", companyCode);
				pageSelect.setFilter(map);
			}
			if(flag){
				return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
			}else{
				result = rptSer.findPrivateWeekPage(pageSelect.getFilter(), pageSelect.getPageNo(), pageSelect.getPageSize());
			}
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * 私家车平台统计导出
	 * @param request
	 * @param response
	 * @throws SystemException
	 */
	@RequestMapping("exportPrivateWeek")
	public @ResponseBody void exportPrivateWeek(HttpServletRequest request, HttpServletResponse response) throws SystemException {
		try {
			Map<String, Object> params = parseReqParam2(request);
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			params.put("subco_no", companyId);
			List<Map<String, Object>> list = rptSer.listPrivateWeek(params);
			String[][] title = {{"投资公司", "20"}, {"总数(在网/离网)", "20"}, {"统计日期", "30"}, {"上周数据", "20"},
					 {"新增入网", "20" }, { "新增离网", "20" }, {"本周数据", "20"}, {"净增长", "20"}};
			List valueList = new ArrayList();
			Map<String, Object> map = null;
			String[] values = null;
			int listLenth = list.size();
			for (int i = 0; i < listLenth; i++) {
				values = new String[8];
				map = list.get(i);
				values[0] = map.get("subco_name") == null ? "" : map.get("subco_name").toString();
				values[1] = map.get("in_off") == null ? "" : map.get("in_off").toString();
				values[2] = map.get("date_str") == null ? "" : map.get("date_str").toString();
				values[3] = map.get("last") == null ? "" : map.get("last").toString();
				values[4] = map.get("newjoin") == null ? "" : map.get("newjoin").toString();
				values[5] = map.get("nowoff") == null ? "" : map.get("nowoff").toString();
				values[6] = map.get("innet") == null ? "" : map.get("innet").toString();
				values[7] = map.get("increase") == null ? "" : map.get("increase").toString();
				valueList.add(values);
			}
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany = openLdap.getCompanyById(companyId);
			CreateExcel_PDF_CSV.createExcel(valueList, response, "私家车平台数量统计", title, 
					commonCompany.getCnfullname(), commonCompany.getEnfullname(), false);
		}catch(Exception e){
			
		}
	}
}
