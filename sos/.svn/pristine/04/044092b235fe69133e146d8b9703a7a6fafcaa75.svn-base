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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.service.HMReportService;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

@Controller
public class HMReportController extends BaseController {
	
	static Logger logger = Logger.getLogger(HMReportController.class);
	
	@Autowired
	@Qualifier("hmReportService")
	private HMReportService hmReportSer;

	/**
	 * 分页阶段查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/hm/findPeriodPage", method = RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> findPeriodPage(@RequestBody PageSelect<Map<String, Object>> pageSelect
			, HttpServletRequest request, HttpServletResponse response)throws SystemException{
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
			}
			result = hmReportSer.findPeriodPage(pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * 阶段查询导出
	 * @param request
	 * @param response
	 * @throws SystemException
	 */
	@RequestMapping(value = "/hm/exportPeriod", method = RequestMethod.GET)
	public void exprotPeriodList(HttpServletRequest request, HttpServletResponse response)throws SystemException{
		try{
			Map<String, Object> params = parseReqParam2(request);
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			params.put("subco_no", companyId);
			List<Map<String, Object>> results = hmReportSer.findPeriodList(params);
			
			String subcoNo = (params.get("subcoNo") == null ? "" : params.get("subcoNo").toString());
			if(StringUtils.isBlank(subcoNo)){
				subcoNo = companyId;
			}
			
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(subcoNo);
			String[][] title = {{"序号","8"},{"呼号","14"},{"阶段","10"},{"通话时长","14"},{"流量","14"},{"开始时间","16"},
					{"结束时间", "16"},{"天数","10"},{"生成时间", "16"}};
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
				values[columnIndex] = Utils.clearNull(valueData.get("call_letter"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("periodName"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("voice_time"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("data"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("s_date"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("e_date"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("days"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("stamp"));
				valueList.add(values);
			}
			
			CreateExcel_PDF_CSV.createExcel(valueList, response, "SIM卡数据/通话阶段报表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * 历史记录查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/hm/findHistoryPage", method = RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> findSimHistoryPage(@RequestBody PageSelect<Map<String, Object>> pageSelect
			, HttpServletRequest request, HttpServletResponse response)throws SystemException{
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
			}
			result = hmReportSer.findSimHistoryPage(pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}

	/**
	 * 历史查询导出
	 * @param request
	 * @param response
	 * @throws SystemException
	 */
	@RequestMapping(value = "/hm/exportHistory", method = RequestMethod.GET)
	public void exprotHistoryList(HttpServletRequest request, HttpServletResponse response)throws SystemException{
		try{
			Map<String, Object> params = parseReqParam2(request);
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			params.put("subco_no", companyId);
			List<Map<String, Object>> results = hmReportSer.findHistoryList(params);
			
			String subcoNo = (params.get("subcoNo") == null ? "" : params.get("subcoNo").toString());
			if(StringUtils.isBlank(subcoNo)){
				subcoNo = companyId;
			}
			
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(subcoNo);
			String[][] title = {{"序号","8"},{"呼号","14"},{"通话时长","14"},{"流量","14"},{"月份","10"},
					{"天数","10"},{"生成时间", "16"}};
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
				values[columnIndex] = Utils.clearNull(valueData.get("call_letter"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("voice_time"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("data"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("month"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("days"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("stamp"));
				valueList.add(values);
			}
			CreateExcel_PDF_CSV.createExcel(valueList, response, "SIM卡数据/通话历史报表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * 当前月份查询
	 * @param pageSelect
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/hm/findCurrentPage", method = RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> findSimCurrentPage(@RequestBody PageSelect<Map<String, Object>> pageSelect
			, HttpServletRequest request, HttpServletResponse response)throws SystemException{
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
			}
			result = hmReportSer.findSimCurrentPage(pageSelect);
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * 当前查询导出
	 * @param request
	 * @param response
	 * @throws SystemException
	 */
	@RequestMapping(value = "/hm/exportCurrent", method = RequestMethod.GET)
	public void exprotCurrentList(HttpServletRequest request, HttpServletResponse response)throws SystemException{
		try{
			Map<String, Object> params = parseReqParam2(request);
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			params.put("subco_no", companyId);
			List<Map<String, Object>> results = hmReportSer.findCurrentList(params);

			String subcoNo = (params.get("subcoNo") == null ? "" : params.get("subcoNo").toString());
			if(StringUtils.isBlank(subcoNo)){
				subcoNo = companyId;
			}
			
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(subcoNo);
			String[][] title = {{"序号","8"},{"呼号","14"},{"阶段","10"},{"通话时长","14"},{"流量","14"},{"月份","10"},
					{"天数","10"},{"生成时间", "16"}};
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
				values[columnIndex] = Utils.clearNull(valueData.get("call_letter"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("periodName"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("voice_time"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("data"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("month"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("days"));
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("stamp"));
				valueList.add(values);
			}
			CreateExcel_PDF_CSV.createExcel(valueList, response, "SIM卡数据/通话当月报表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
	}
	
}
