package cc.chinagps.seat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.service.CallOutReportService;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

@Controller
@RequestMapping(value="/callout/service",produces="text/plain;charset=UTF-8")
public class CallOutReportController extends BasicController {

	//@Autowired
	private CallOutReportService callOutReportService;
	
	@RequestMapping(value="/marketing",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String marketing(ReportCommonQuery query,HttpServletRequest request,Map<String, String> param)  throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request,  param);
		param.put("action", "marketing");
		Map<String, Object> resultMap = getResult(query, param);
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping(value="/fee",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String fee(ReportCommonQuery query,HttpServletRequest request,Map<String, String> param)  throws JSONException {
		/**
		 * 全部查询参数放入map
		 */
		setMap(request,  param);
		param.put("action", "fee");
		Map<String, Object> resultMap = getResult(query, param);
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/marketing/export")
	@ResponseBody
	public ModelAndView marketingExport(ReportCommonQuery query,HttpServletRequest request,Map<String, String> param)  throws JSONException {
		setMap(request,  param);
		param.put("action", "marketing");
		Map<String, Object> resultMap = getResult(query, param);
		return new ModelAndView("exportmarketing", resultMap);
	}
	
	@RequestMapping("/fee/export")
	@ResponseBody
	public ModelAndView feeExport(ReportCommonQuery query,HttpServletRequest request,Map<String, String> param)  throws JSONException {
		setMap(request,  param);
		param.put("action", "fee");
		Map<String, Object> resultMap = getResult(query, param);
		return new ModelAndView("exportfee", resultMap);
	}

	private Map<String, Object> getResult(ReportCommonQuery query,Map<String, String> param) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rbList;
		ReportCommonResponse commResp = callOutReportService.getCount(query, param);
		if (commResp.getRecordsTotal() == 0) {
			rbList = new ArrayList<Map<String, Object>>();
		} else {
			rbList = callOutReportService.getResults(query, param);
		}
		resultMap.putAll(commResp.getCommonRespMap());
		resultMap.put("data", rbList);
		resultMap.put("success", true);
		return resultMap;
	}

	private void setMap(HttpServletRequest request, Map<String, String> param) {
		
		param.put("customer_name", request.getParameter("linkman"));//客户姓名
		param.put("plate_no", request.getParameter("plate_no"));//车牌
		param.put("subco_no", request.getParameter("subco_no"));//归属公司
		param.put("call_letter", request.getParameter("call_letter"));//车载电话
		//服务费截止时间
		param.put("fee_sedate", request.getParameter("fee_sedate"));//车载电话
		
	}	
}
