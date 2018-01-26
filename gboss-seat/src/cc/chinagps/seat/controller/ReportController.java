package cc.chinagps.seat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

import cc.chinagps.seat.bean.ReportAlarm;
import cc.chinagps.seat.bean.ReportBrief;
import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.ReportLocateFault;
import cc.chinagps.seat.bean.ReportManyGps;
import cc.chinagps.seat.bean.ReportResponseRatio;
import cc.chinagps.seat.bean.ReportSendCmd;
import cc.chinagps.seat.bean.ReportStolenVehicle;
import cc.chinagps.seat.bean.ReportUnitCom;
import cc.chinagps.seat.bean.ReportUnreportStat;
import cc.chinagps.seat.bean.table.AlarmTable;
import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.bean.table.SendCmdTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;
import cc.chinagps.seat.service.ReportService;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

@Controller
@RequestMapping("/report")
public class ReportController extends BasicController {
	
	//@Autowired
	private ReportService reportService;
	
	@RequestMapping("/brief")
	@ResponseBody
	public String getAllBrief(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam Map<String, String> param) throws JSONException {
		Map<String, Object> resultMap = getBriefMap(
				brief, query, request, param);
		return JSONUtil.serialize(resultMap);
	}

	@RequestMapping("/brief/export")
	public ModelAndView exportBrief(BriefTable brief, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam Map<String, String> param) {
		Map<String, Object> resultMap = getBriefMap(
				brief, query, request, param);
		return new ModelAndView("exportbrief", resultMap);
	}
	
	private Map<String, Object> getBriefMap(BriefTable brief,
			ReportCommonQuery query, 
			HttpServletRequest request,
			Map<String, String> param) {
		if (query.getCompanyNo() == null) {
			query.setCompanyNo(getLoginUserCompanyNo(request));
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ReportBrief> rbList;
		ReportCommonResponse commResp = reportService.getBriefCommonResp(
				brief, query, param);
		if (commResp.getRecordsTotal() == 0) {
			rbList = new ArrayList<ReportBrief>();
		} else {
			rbList = reportService.getBrief(brief, query, param);
		}
		resultMap.putAll(commResp.getCommonRespMap());
		resultMap.put("data", rbList);
		resultMap.put("success", true);
		return resultMap;
	}
	
	@RequestMapping("/stolenvehicle")
	@ResponseBody
	public String getStolenVehicle(StolenVehicleTable stolenVehicle, 
			@RequestParam Map<String, Object> paramMap,
			@Valid ReportCommonQuery query,
			HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = getStolenVehicleMap(
				stolenVehicle, paramMap, query, request);
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/stolenvehicle/export")
	public ModelAndView getStolenVehicleReport(StolenVehicleTable stolenVehicle, 
			@RequestParam Map<String, Object> paramMap,
			@Valid ReportCommonQuery query,
			HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = getStolenVehicleMap(
				stolenVehicle, paramMap, query, request);
		return new ModelAndView("exportstolenvehicle", resultMap);
	}
	
	private Map<String, Object> getStolenVehicleMap(
			StolenVehicleTable stolenVehicle, Map<String, Object> paramMap,
			ReportCommonQuery query,
			HttpServletRequest request) {
		if (query.getCompanyNo() == null) {
			query.setCompanyNo(getLoginUserCompanyNo(request));
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ReportCommonResponse commResp = reportService.getStolenVehicleCommonResp(
				stolenVehicle, paramMap, query);
		List<ReportStolenVehicle> rsvList;
		if (commResp.getRecordsTotal() == 0) {
			rsvList = new ArrayList<ReportStolenVehicle>();
		} else {
			rsvList = reportService.getStolenVehicle(
					stolenVehicle, paramMap, query);
		}
		resultMap.putAll(commResp.getCommonRespMap());
		
//		StolenVehicleTable sv;
//		for (ReportStolenVehicle rsv : rsvList) {
//			sv = rsv.getStolenVehicle();
//		}
		
		resultMap.put("data", rsvList);
		resultMap.put("success", true);
		
		return resultMap;
	}

	@RequestMapping("/respratio")
	@ResponseBody
	public String getAlarmRespRatio(
			@Valid ReportCommonQuery query,
			int interval,
			HttpServletRequest request) throws JSONException {
		return JSONUtil.serialize(getResponseRatioMap(query, interval,
				request));
	}

	private Map<String, Object> getResponseRatioMap(ReportCommonQuery query,
			int interval, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (query.getCompanyNo() == null) {
			query.setCompanyNo(getLoginUserCompanyNo(request));
		}
		// 响应率返回参数不可能等于0，不需要判断
		ReportCommonResponse commResp = reportService.getAlarmCommonResp(query, interval);
		List<ReportResponseRatio> arrList = reportService.getAlarmResponseRatio(query, interval);
		resultMap.put("success", true);
		resultMap.put("data", arrList);
		resultMap.putAll(commResp.getCommonRespMap());
		return resultMap;
	}
	
	@RequestMapping("/respratio/export")
	public ModelAndView exportAlarmRespRatio(
			@Valid ReportCommonQuery query,
			int interval,
			HttpServletRequest request) {
		Map<String, Object> resultMap = getResponseRatioMap(query, interval, request);
		return new ModelAndView("exportAlarmRespRatio", resultMap);
	}
	
	@RequestMapping("/unreportstat")
	@ResponseBody
	public String getUnreportStat(ReportCommonQuery query,
			@RequestParam Map<String, Object> paramMap,
			HttpServletRequest request) throws JSONException {
		return JSONUtil.serialize(getUnreportStatMap(paramMap, query, request));
	}
	
	@RequestMapping("/unreportstat/export")
	public ModelAndView exportUnreportStat(ReportCommonQuery query,
			@RequestParam Map<String, Object> paramMap,
			HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = getUnreportStatMap(paramMap, query, request);
		return new ModelAndView("exportUnreportStat", resultMap);
	}
	
	private Map<String, Object> getUnreportStatMap(
			Map<String, Object> paramMap, ReportCommonQuery query,
			HttpServletRequest request) {
		if (query.getCompanyNo() == null) {
			query.setCompanyNo(getLoginUserCompanyNo(request));
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ReportCommonResponse commResp = reportService.getUnreportStatCommResp(paramMap, query);
		List<ReportUnreportStat> rusList;
		if (commResp.getRecordsTotal() == 0) {
			rusList = Collections.emptyList();
		} else {
			rusList = reportService.getUnreportStat(paramMap, query);
		}
		resultMap.put("success", true);
		resultMap.put("data", rusList);
		resultMap.putAll(commResp.getCommonRespMap());
		return resultMap;
	}
	
	@RequestMapping("/locatefault")
	@ResponseBody
	public String getLocatefault(ReportCommonQuery query,
			@RequestParam Map<String, Object> paramMap,
			String[] status,
			HttpServletRequest request) throws JSONException {
		return JSONUtil.serialize(getLocatefaultMap(paramMap, query, status, request));
	}
	
	@RequestMapping("/locatefault/export")
	public ModelAndView exportLocatefault(ReportCommonQuery query,
			@RequestParam Map<String, Object> paramMap,
			String[] status,
			HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = getLocatefaultMap(paramMap, query, status, request);
		return new ModelAndView("exportLocateFault", resultMap);
	}
	
	private Map<String, Object> getLocatefaultMap(
			Map<String, Object> paramMap, ReportCommonQuery query,
			String[] status, HttpServletRequest request) {
		if (query.getCompanyNo() == null) {
			query.setCompanyNo(getLoginUserCompanyNo(request));
		}
		// spring默认不会存数组，需要手工存入map
		paramMap.put("status", status);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ReportCommonResponse commResp = reportService.getLocateFaultCommResp(paramMap, query);
		List<ReportLocateFault> usList;
		if (commResp.getRecordsTotal() == 0) {
			usList = Collections.emptyList();
		} else {
			usList = reportService.getLocateFault(paramMap, query);
		}
		resultMap.put("success", true);
		resultMap.put("data", usList);
		resultMap.putAll(commResp.getCommonRespMap());
		return resultMap;
	}
	
	@RequestMapping("/sendcmd")
	@ResponseBody
	public String getSendCmd(SendCmdTable sendCmd,
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam Map<String, String> param) throws JSONException {	
		String partNo = param.get("partNo");
		System.out.println(partNo);
		Map<String, Object> resultMap = getSendCmdMap(
				sendCmd, query, request, param);
		return JSONUtil.serialize(resultMap);
	}

	@RequestMapping("/sendcmd/export")
	public ModelAndView exportSendCmd(SendCmdTable sendCmd, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam Map<String, String> param) {
		Map<String, Object> resultMap = getSendCmdMap(
				sendCmd, query, request, param);
		return new ModelAndView("exportSendCmd", resultMap);
	}
	
	private Map<String, Object> getSendCmdMap(SendCmdTable sendCmd,
			ReportCommonQuery query, 
			HttpServletRequest request,
			Map<String, String> param) {		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ReportSendCmd> sendCmdList;
		ReportCommonResponse commResp = reportService.getSendCmdCommonResp(
				sendCmd, query, param);
		if (commResp.getRecordsTotal() == 0) {
			sendCmdList = new ArrayList<ReportSendCmd>();
		} else {
			sendCmdList = reportService.getSendCmd(sendCmd, query, param);
		}
		resultMap.putAll(commResp.getCommonRespMap());
		resultMap.put("data", sendCmdList);
		resultMap.put("success", true);
		return resultMap;
	}
	
	@RequestMapping("/queryalarm")
	@ResponseBody
	public String getAlarmAll(AlarmTable alarm, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam Map<String, String> param) throws JSONException {
		Map<String, Object> resultMap = getAlarmsMap(
				alarm, query, request, param);
		return JSONUtil.serialize(resultMap);
	}

	@RequestMapping("/queryalarm/export")
	public ModelAndView exportSendCmd(AlarmTable alarm, 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam Map<String, String> param) {
		Map<String, Object> resultMap = getAlarmsMap(
				alarm, query, request, param);
		return new ModelAndView("exportAlarm", resultMap);
	}
	
	private Map<String, Object> getAlarmsMap(AlarmTable alarm,
			ReportCommonQuery query, 
			HttpServletRequest request,
			Map<String, String> param) {		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ReportAlarm> alarmList;
		ReportCommonResponse commResp = reportService.getAlarmAllCommonResp(alarm, query, param);
		if (commResp.getRecordsTotal() == 0) {
			alarmList = new ArrayList<ReportAlarm>();
		} else {
			alarmList = reportService.getAlarmAll(alarm, query, param);
		}
		resultMap.putAll(commResp.getCommonRespMap());
		resultMap.put("data", alarmList);
		resultMap.put("success", true);
		return resultMap;
	}
	
	@RequestMapping("/queryManyGps")
	@ResponseBody
	public String queryManyGps( 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam Map<String, String> param) throws JSONException {
		Map<String, Object> resultMap = getManyGpsMap(query, request, param);
		return JSONUtil.serialize(resultMap);
	}

	@RequestMapping("/queryManyGps/export")
	public ModelAndView exportManyGps(
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam Map<String, String> param) {
		Map<String, Object> resultMap = getManyGpsMap(query, request, param);
		return new ModelAndView("exportManyGps", resultMap);
	}
	
	private Map<String, Object> getManyGpsMap(
			ReportCommonQuery query, 
			HttpServletRequest request,
			Map<String, String> param) {		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ReportManyGps> list;
		ReportCommonResponse commResp = reportService.getManyGpsCommonResp(query, param);
		if (commResp.getRecordsTotal() == 0) {
			list = new ArrayList<ReportManyGps>();
		} else {
			list = reportService.getManyGps(query, param);
		}
		List<ReportManyGps> resulList = new ArrayList<ReportManyGps>();
		if(list.size()>0){
			int max = 10;
			if(query.getLength()+query.getStart() <list.size()){
				max = query.getLength()+query.getStart();
			}else{
				max = list.size();
			}
			System.out.println(max);
			for(int i=query.getStart();i<max;i++){
				resulList.add(list.get(i));
			}
		}
		resultMap.putAll(commResp.getCommonRespMap());
		resultMap.put("data", resulList);
		resultMap.put("success", true);
		return resultMap;
	}
	
	@RequestMapping("/queryUnitCom")
	@ResponseBody
	public String queryUnitCom( 
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam Map<String, String> param) throws JSONException {
		Map<String, Object> resultMap = getUnitComMap(query, request, param);
		return JSONUtil.serialize(resultMap);
	}

	@RequestMapping("/queryUnitCom/export")
	public ModelAndView exportUnitCom(
			@Valid ReportCommonQuery query,
			HttpServletRequest request,
			@RequestParam Map<String, String> param) {
		Map<String, Object> resultMap = getUnitComMap(query, request, param);
		return new ModelAndView("exportUnitCom", resultMap);
	}
	
	private Map<String, Object> getUnitComMap(
			ReportCommonQuery query, 
			HttpServletRequest request,
			Map<String, String> param) {		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ReportUnitCom> list = new ArrayList<ReportUnitCom>();		
		List<ReportUnitCom> resulList = new ArrayList<ReportUnitCom>();
		try {
			list = reportService.getUnitCom(query, param);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(list.size()>0 && query.getLength()>0){
			int max = 10;
			if(query.getLength()+query.getStart() <list.size()){
				max = query.getLength()+query.getStart();
			}else{
				max = list.size();
			}
			for(int i=query.getStart();i<max;i++){
				resulList.add(list.get(i));
			}
		}else{
			resulList = list;
		}
		resultMap.put("data", resulList);
		resultMap.put("recordsTotal", list.size());
		resultMap.put("recordsFiltered", list.size());
		resultMap.put("success", true);
		return resultMap;
	}
}
