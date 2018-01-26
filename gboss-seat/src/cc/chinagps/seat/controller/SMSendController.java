package cc.chinagps.seat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.chinagps.seat.auth.User;
import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.service.SenderService;
import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.JSONUtil;


@Controller
@RequestMapping("/sms")
@Configuration
//@PropertySource("/config/misc.properties")
public class SMSendController  extends BasicController {

	private static final Logger LOG = Logger.getLogger(SMSendController.class);
	
	
	private void setMap(HttpServletRequest request, Map<String, String> param) {
		param.put("mobile", request.getParameter("mobile"));//联系电话
		param.put("content", request.getParameter("content"));//短信内容
		param.put("vehicleId", request.getParameter("vehicleId"));//车辆ID
		param.put("st_code", request.getParameter("st_code"));//短信类型
	}
	
	private Map<String, Object> getRetMap(ReportCommonQuery query, HttpServletRequest request,
			Map<String, String> param) {
		
		if (query.getCompanyNo() == null) {
			query.setCompanyNo(getLoginUserCompanyNo(request));
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rbList;
		ReportCommonResponse commResp = senderService.getSendsmsListsCount(query, param);
		if (commResp.getRecordsTotal() == 0) {
			rbList = new ArrayList<Map<String, Object>>();
		} else {
			rbList = senderService.getSendsmsLists(query, param);
		}
		resultMap.putAll(commResp.getCommonRespMap());
		resultMap.put("data", rbList);
		resultMap.put("success", true);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/sms_detail")
	public String sms_detail(@Valid ReportCommonQuery query,HttpServletRequest request,HttpServletResponse response){
		try{
			//接口参数map
			Map<String, String> sms_params = new HashMap<String, String>();
			setMap(request, sms_params);
			Map<String, Object> resultMap = getRetMap(query, request, sms_params);
			return JSONUtil.serialize(resultMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/sms_detail/export")
	public ModelAndView sms_detail_export(@Valid ReportCommonQuery query,
			HttpServletRequest request,Map<String, String> param){
		setMap(request, param);
		Map<String, Object> resultMap = getRetMap(query, request, param);
		return new ModelAndView("exportsmsdetail", resultMap);
	}
	
	@ResponseBody
	@RequestMapping("/sms_statistics")
	public String sms_statistics(@Valid ReportCommonQuery query,HttpServletRequest request,HttpServletResponse response){
		try{
			//接口参数map
			Map<String, String> sms_params = new HashMap<String, String>();
			setMap(request, sms_params);
			Map<String, Object> resultMap = getSRetMap(query, request, sms_params);
			return JSONUtil.serialize(resultMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Map<String, Object> getSRetMap(ReportCommonQuery query,
			HttpServletRequest request, Map<String, String> param) {
		if (query.getCompanyNo() == null) {
			query.setCompanyNo(getLoginUserCompanyNo(request));
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rbList;
		ReportCommonResponse commResp = senderService.getSendsmsStatisticsCount(query, param);
		if (commResp.getRecordsTotal() == 0) {
			rbList = new ArrayList<Map<String, Object>>();
		} else {
			rbList = senderService.getSendsmsStatisticsLists(query, param);
		}
		resultMap.putAll(commResp.getCommonRespMap());
		resultMap.put("data", rbList);
		resultMap.put("success", true);
		return resultMap;
	}

	@RequestMapping("/sms_statistics/export")
	public ModelAndView sms_statistics_export(@Valid ReportCommonQuery query,
			HttpServletRequest request,Map<String, String> param){
		setMap(request, param);
		Map<String, Object> resultMap = getSRetMap(query, request, param);
		return new ModelAndView("exportsmstatistics", resultMap);
	}
	
	//@Autowired
	private Environment env;
	
	//@Autowired
	private SenderService senderService;
	
	
	@ResponseBody
	@RequestMapping(value="/send",produces="text/plain;charset=UTF-8")
	public String send(
			@RequestBody(required=false) String encryptStr,
			@RequestParam(required=false,value="mobile") String mobile,
			@RequestParam(required=false,value="content") String content,
			@RequestParam(required=false,value="op_name") String op_name,
			@RequestParam(required=false,value="op_src") Short op_src,
			@RequestParam(required=false,value="unitId") Long unitId,
			@RequestParam(required=false,value="api_type") Integer api_type,
			@RequestParam(required=false,value="st_code") Integer st_code,
			HttpServletRequest request,HttpServletResponse response,Locale locale){
		LOG.info("--短信发送开始--");
		try{
			//接口参数map
			Map<String, String> sms_params = new HashMap<String, String>();
			String api_url =  env.getProperty("sms.url.send.utf");
			sms_params.put(Consts.SMS_MOBILE,  	mobile);
			sms_params.put(Consts.SMS_CONTENT,  content);
			sms_params.put(Consts.SMS_API_URL,  api_url);
			sms_params.put("unitId", String.valueOf( unitId==null ? 0L : unitId ));
			sms_params.put("st_code",String.valueOf( st_code ));
			/*****************登录用户姓名*******************/
			User user = getLoginUser(request);
			op_name = user.getOpName();
			/*****************登录用户姓名*******************/
			sms_params.put("op_name",op_name);
			LOG.info(String.format("[操作人员：%s,接收号码：%s,短信内容：%s]",op_name, mobile,content));
			String jsonstr = senderService.send(sms_params);
			
			if(jsonstr!=null){
				LOG.info("--短信发送成功--");
				return JSONObject.fromObject(jsonstr).toString();
			}else{
				LOG.info("--短信发送失败--");
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("returnstatus", "Faild");
				resultMap.put("message", getText("send.sms.info", locale));
				return JSONObject.fromObject(resultMap).toString();
			}
			
		}catch (Exception e) {
			LOG.info("--短信发送出错--" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
}
