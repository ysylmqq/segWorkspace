package cc.chinagps.seat.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.chinagps.seat.auth.User;
import cc.chinagps.seat.bean.table.AlarmLogTable;
import cc.chinagps.seat.bean.table.AlarmTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;
import cc.chinagps.seat.bean.table.VehicleStatusTable;
import cc.chinagps.seat.service.AlarmService;
import cc.chinagps.seat.service.AlarmLogService;
import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

@Controller
public class AlarmController extends BasicController {
	
	//@Autowired
	private AlarmService alarmService;
	//@Autowired
	private AlarmLogService alarmLogService;
	
	@RequestMapping("/alarm/config")
	@ResponseBody
	public String getCCConfig(HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		
		resultMap.putAll(alarmService.getCCConfig(request.getRemoteAddr()));
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/alarm")
	@ResponseBody
	public String getAlarms(
			@RequestParam("vehicleId") BigInteger vehicleId,
			@RequestParam() int count) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		resultMap.put("info", alarmService.getAlarms(vehicleId, count));
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/alarm/finish")
	@ResponseBody
	public String alarmFinish(@Valid final AlarmTable alarm,
			HttpServletRequest request,
			Locale locale) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		populateAlarm(alarm, request);
		
		StolenVehicleTable stolenVehicle = new StolenVehicleTable();
		stolenVehicle.setSource(0);
		int result = alarmService.alarmFinish(alarm, stolenVehicle);
		if (result != Consts.SUCCEED) {
			resultMap.put("success", false);
			resultMap.put("message", 
					getText("alarm_finish_" + result, 
							locale, alarm.getAlarmSn()));
		} else {
			resultMap.put("success", true);
			resultMap.put("stamp", Consts.formatDate(alarm.getHandleTime()));
			resultMap.put("id", stolenVehicle.getId());
		}
		return JSONUtil.serialize(resultMap);
	}
	
	/**
	 * 挂警
	 * @param alarmSn
	 * @param request
	 * @param locale
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/alarm/suspend")
	@ResponseBody
	public String alarmSuspend(@RequestParam String alarmSn,
			HttpServletRequest request,
			Locale locale) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		AlarmTable alarm = new AlarmTable();
		alarm.setAlarmSn(alarmSn);
		populateAlarm(alarm, request);
		int result = alarmService.alarmSuspend(alarm);
		if (result != Consts.SUCCEED) {
			resultMap.put("success", false);
			resultMap.put("message", 
					getText("alarm_suspend_" + result, 
							locale, alarm.getAlarmSn()));
		} else {
			resultMap.put("success", true);
		}
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/alarm/log")
	@ResponseBody
	public String alarmTrace(@RequestParam String alarmSn,@RequestParam Integer log_flag,
			@RequestParam String log_phone,
			@RequestParam Integer log_res,
			HttpServletRequest request,
			Locale locale) throws JSONException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		/**警情更新开始*/
		AlarmTable alarm = new AlarmTable();
		alarm.setAlarmSn(alarmSn);
		alarm = alarmService.getAlarm(alarmSn);
		User user = getLoginUser(request);
		alarm.setLog_op_id(BigInteger.valueOf(user.getOpId()));
		alarm.setLog_res(log_res);
		alarm.setLog_phone(log_phone);
		alarm.setLog_flag(log_flag);
		alarmService.updateAlarm(alarm);
		/**警情更新结束*/
		
		/**明细插入*/
		Date trace_stamp = new Date();
		AlarmLogTable att = new AlarmLogTable(alarmSn,trace_stamp);
		att.setCall_letter(alarm.getCallLetter());
		att.setLog_phone(log_phone);
		att.setLog_flag(log_flag);
		att.setLog_res(log_res);
		att.setLog_op_id(BigInteger.valueOf(user.getOpId()));
		int result = 0;
		result = alarmLogService.save(att);
		/**明细插入*/
		
		/**操作结果提示*/
		if (result == 0 ) {
			resultMap.put("success", false);
			resultMap.put("message", "警情追踪出错!");
		} else {
			resultMap.put("success", true);
		}
		/**操作结果提示*/
		
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/alarm/resume")
	@ResponseBody
	public String alarmResume(@RequestParam String alarmSn,
			HttpServletRequest request,
			Locale locale) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		AlarmTable alarm = new AlarmTable();
		alarm.setAlarmSn(alarmSn);
		populateAlarm(alarm, request);
		int result = alarmService.alarmResume(alarm);
		if (result != Consts.SUCCEED) {
			resultMap.put("success", false);
			resultMap.put("message", 
					getText("alarm_resume_" + result, 
							locale, alarm.getAlarmSn()));
		} else {
			resultMap.put("success", true);
		}
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/alarm/transfer")
	@ResponseBody
	public String alarmTransfer(@RequestParam String alarmSn,
			HttpServletRequest request,
			Locale locale) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		AlarmTable alarm = new AlarmTable();
		alarm.setAlarmSn(alarmSn);
		populateAlarm(alarm, request);
		int result = alarmService.alarmTransfer(alarm);
		if (result != Consts.SUCCEED) {
			resultMap.put("success", false);
			resultMap.put("message", 
					getText("alarm_transfer_" + result, 
							locale, alarm.getAlarmSn()));
		} else {
			resultMap.put("success", true);
		}
		return JSONUtil.serialize(resultMap);
	}

	private void populateAlarm(AlarmTable alarm,
			HttpServletRequest request) {
		User user = getLoginUser(request);
		alarm.setOpId(user.getOpId());
		alarm.setOpName(user.getOpName());
	}
	
	@RequestMapping("/alarmstatus")
	@ResponseBody
	public String getAlarmStatus(
			HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		resultMap.put("alarmStatus", alarmService.getAlarmStatus().values());
		return JSONUtil.serialize(resultMap);
	}
	
	/**
	 * 手工添加案发车辆
	 * @param vehicleStatus
	 * @param locale
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/alarm/vehicle/add")
	@ResponseBody
	public String addVehicleStatus(
			@Valid VehicleStatusTable vehicleStatus,
			Locale locale) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		StolenVehicleTable stolenVehicle = new StolenVehicleTable();
		stolenVehicle.setSource(1);//0车台自动报警，1来电报警
		int result = alarmService.addVehicleStatus(vehicleStatus, stolenVehicle);
		if (result != Consts.SUCCEED) {
			resultMap.put("message", 
					getText("alarm_vehicle_" + result, 
							locale,
							vehicleStatus.getUnitId()));
		} else {
			resultMap.put("success", true);
			resultMap.put("stamp", Consts.formatDate(stolenVehicle.getBeginTime()));
			resultMap.put("id", stolenVehicle.getId());
		}
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/alarm/vehicle/modify")
	@ResponseBody
	public String modifyStolenVehicle(
			@RequestParam BigInteger unitId,
			@Valid StolenVehicleTable stolenVehicle,
			Locale locale) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = alarmService.modifyStolenVehicle(unitId, stolenVehicle);
		if (result != Consts.SUCCEED) {
			resultMap.put("success", false);
			resultMap.put("message", 
					getText("alarm_vehicle_" + result, 
							locale, unitId));
		} else {
			alarmService.updateVehicleStatus(unitId, stolenVehicle);
			resultMap.put("success", true);
		}
		return JSONUtil.serialize(resultMap);
	}
	
	/**
	 * 删除案发车辆
	 * @param unitId
	 * @param stolenVehicle
	 * @param locale
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/alarm/vehicle/del")
	@ResponseBody
	public String delStolenVehicle(
			@RequestParam BigInteger unitId,
			@Valid StolenVehicleTable stolenVehicle,
			Locale locale) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = alarmService.delStolenVehicle(unitId, stolenVehicle);
		if (result != Consts.SUCCEED) {
			resultMap.put("success", false);
			resultMap.put("message", 
					getText("alarm_vehicle_" + result, 
							locale, unitId));
		} else {
			alarmService.updateVehicleStatus(unitId, stolenVehicle);
			resultMap.put("success", true);
		}
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/alarm/vehicle/caseFinish")
	@ResponseBody
	public String caseFinish(
			@RequestParam BigInteger unitId,
			@Valid StolenVehicleTable stolenVehicle,
			Locale locale) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = alarmService.caseFinish(unitId, stolenVehicle);
		if (result != Consts.SUCCEED) {
			resultMap.put("success", false);
			resultMap.put("message", 
					getText("alarm_vehicle_" + result, 
							locale, unitId));
		} else {
			alarmService.updateVehicleStatus(unitId, stolenVehicle);
			resultMap.put("success", true);
		}
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/alarm/vehicle")
	@ResponseBody
	public String getStolenVehicle() throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<StolenVehicleTable> svList = alarmService.getStolenVehicle();
		resultMap.put("success", true);
		resultMap.put("stolenVehicle", svList);
		return JSONUtil.serialize(resultMap);
	}
}
