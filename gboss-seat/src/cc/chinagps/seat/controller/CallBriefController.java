package cc.chinagps.seat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.service.CallBriefService;
import cc.chinagps.seat.view.CallinBizzExcelView;
import cc.chinagps.seat.view.CalloutlogBizzExcelView;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

@Controller
@RequestMapping("/callbrief")
public class CallBriefController  extends BasicController  {

	//@Autowired
	private CallBriefService callBriefService;
	
	@RequestMapping("/call_in")
	@ResponseBody
	public String getCallinBriefs(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param) 
					throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request, serverType, businessType, param);
		param.put("type", "1");
		Map<String, Object> resultMap = getBriefMap(brief, query, request, param);
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/call_out")
	@ResponseBody
	public String getCalloutBriefs(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param) 
					throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request, serverType, businessType, param);
		param.put("type", "2");
		Map<String, Object> resultMap = getBriefMap(brief, query, request, param);
		return JSONUtil.serialize(resultMap);
	}

	private void setMap(HttpServletRequest request, Integer[] serverType,
			Integer[] businessType, Map<String, String> param) {
		//服务大类
		if(serverType!=null){
			if(serverType.length>0){
				param.put("p_st_ids", java.util.Arrays.toString(serverType).replace("[", "").replace("]", ""));
			}
		}
		//服务类型
		if(businessType!=null){
			if(businessType.length>0){
				param.put("st_ids", java.util.Arrays.toString(businessType).replace("[", "").replace("]", ""));
			}
		}
		
		param.put("op_id", request.getParameter("jobNum"));//操作人员ID
		param.put("phone", request.getParameter("phone"));//电话
		param.put("customer_name", request.getParameter("name"));//客户名称
		param.put("subco_no", request.getParameter("companyNo"));//归属公司
		param.put("keywords", request.getParameter("partNo"));//未使用
		param.put("vehicle_id", request.getParameter("vehicleId"));//车牌或者呼号对应的车辆iD
		param.put("kld", request.getParameter("kld") == null?"1":request.getParameter("kld"));//颗粒度
		param.put("isConnect", request.getParameter("isConnect") == null?"0":request.getParameter("isConnect"));//接通情况(报表用)
	}

	private Map<String, Object> getBriefMap(BriefTable brief,
			ReportCommonQuery query, HttpServletRequest request,
			Map<String, String> param) {
		
		if (query.getCompanyNo() == null) {
			query.setCompanyNo(getLoginUserCompanyNo(request));
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rbList;
		ReportCommonResponse commResp = callBriefService.getCallInBriefsCount(brief, query, param);
		if (commResp.getRecordsTotal() == 0) {
			rbList = new ArrayList<Map<String, Object>>();
		} else {
			rbList = callBriefService.getCallInBriefs(brief, query, param);
		}
		resultMap.putAll(commResp.getCommonRespMap());
		resultMap.put("data", rbList);
		resultMap.put("success", true);
		return resultMap;
	}
	
	@RequestMapping("/call_in/export")
	public ModelAndView exportBriefin(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param){
		setMap(request, serverType, businessType, param);
		param.put("type", "1");
		Map<String, Object> resultMap = getBriefMap(brief, query, request, param);
		return new ModelAndView("exportinbrief", resultMap);
	}
	
	@RequestMapping("/call_out/export")
	public ModelAndView exportBriefout(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param){
		setMap(request, serverType, businessType, param);
		param.put("type", "2");
		Map<String, Object> resultMap = getBriefMap(brief, query, request, param);
		return new ModelAndView("exportoutbrief", resultMap);
	}
	
	@RequestMapping("/call_in_statistics")
	@ResponseBody
	public String getCallInSuperStatistics(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param) 
					throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request, serverType, businessType, param);
		param.put("type", "1");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data",callBriefService.getCallInSuperStatistics(brief, query, param));
		resultMap.put("success",true);
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/call_in_statistics/export")
	@ResponseBody
	public ModelAndView getCallInSuperStatisticsexport(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param) 
					throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request, serverType, businessType, param);
		param.put("type", "1");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data",callBriefService.getCallInSuperStatistics(brief, query, param));
		resultMap.put("success",true);
		
		return new ModelAndView(new CallinBizzExcelView(1), resultMap);
	}
	
	@RequestMapping("/call_out_statistics")
	@ResponseBody
	public String getCalloutSuperStatistics(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param) 
					throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request, serverType, businessType, param);
		param.put("type", "2");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data",callBriefService.getCallInSuperStatistics(brief, query, param));
		resultMap.put("success",true);
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/call_out_statistics/export")
	@ResponseBody
	public ModelAndView getCalloutSuperStatisticsexport(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param) 
					throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request, serverType, businessType, param);
		param.put("type", "2");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data",callBriefService.getCallInSuperStatistics(brief, query, param));
		resultMap.put("success",true);
		
		return new ModelAndView(new CallinBizzExcelView(2), resultMap);
	}
	
	/**
	 * 呼出日志统计表
	 * @param brief
	 * @param query
	 * @param request
	 * @param serverType
	 * @param businessType
	 * @param param
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/call_log_statistics")
	@ResponseBody
	public String getCalloutFlagStatistics(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param) 
					throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request, serverType, businessType, param);
		param.put("type", "2");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data",callBriefService.getCalloutFlagStatistics(brief, query, param));
		resultMap.put("success",true);
		return JSONUtil.serialize(resultMap);
	}
	
	/**
	 * 外呼日志导出
	 * @param brief
	 * @param query
	 * @param request
	 * @param serverType
	 * @param businessType
	 * @param param
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/call_log_statistics/export")
	@ResponseBody
	public ModelAndView getCalloutlogSuperStatisticsexport(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param) 
					throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request, serverType, businessType, param);
		param.put("type", "2");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data",callBriefService.getCalloutFlagStatistics(brief, query, param));
		resultMap.put("success",true);
		return new ModelAndView(new CalloutlogBizzExcelView(), resultMap);
	}
	
	/**
	 * 外呼统计报表:	根据外呼简报汇总数据
	 * @param brief
	 * @param query
	 * @param request
	 * @param serverType
	 * @param businessType
	 * @param param
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/callout_success_statistics")
	@ResponseBody
	public String getCalloutStatistics(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param) 
					throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request, serverType, businessType, param);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rbList;
		ReportCommonResponse commResp = callBriefService.getCalloutStatisticsCount(brief, query, param);
		if (commResp.getRecordsTotal() == 0) {
			rbList = new ArrayList<Map<String, Object>>();
		} else {
			rbList = callBriefService.getCalloutStatistics(brief, query, param);
		}
		resultMap.putAll(commResp.getCommonRespMap());
		resultMap.put("data", rbList);
		resultMap.put("success", true);
		return JSONUtil.serialize(resultMap);
	}
	
	/**
	 * 外呼统计报表:	根据外呼简报汇总数据 导出
	 * @param brief
	 * @param query
	 * @param request
	 * @param serverType
	 * @param businessType
	 * @param param
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/callout_success_statistics/export")
	@ResponseBody
	public ModelAndView calloutStatistics_export(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam(value="serverType" , required = false)Integer[] serverType,
			@RequestParam(value="businessType" , required = false)Integer[] businessType,Map<String, String> param) 
					throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request, serverType, businessType, param);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data",callBriefService.getCalloutStatistics(brief, query, param));
		resultMap.put("success",true);
		return new ModelAndView("exportcalloutstatistics", resultMap);
		
	}
	
	
	
}
