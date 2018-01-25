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
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			result = rptSer.findUnfixedStatisticsPage(id, pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}

}
