package cc.chinagps.seat.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.chinagps.seat.bean.AlarmResponse;
import cc.chinagps.seat.comm.SeatException;
import cc.chinagps.seat.service.AlarmResponseService;
import cc.chinagps.seat.util.WordGenerator;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

@Controller
@RequestMapping(value="/alarm/response", produces="text/plain;charset=UTF-8")
public class AlarmResponseController extends BasicController {
	
	//@Autowired
	private AlarmResponseService alarmResponseService;
	
	@RequestMapping("/report")
	@ResponseBody
	public String getEventReport(@RequestParam(value="vehicle_id" )Long vehicle_id,@RequestParam(value="stolen_id" )Long stolen_id) throws JSONException {
		Map<String, Object>  result = new HashMap<String, Object>();
		result.put("success", true);
		try {
			Map<String, Object>  query_result  = alarmResponseService.getAlarmResponse(vehicle_id, stolen_id);
			result.put("data", query_result);
		} catch (SeatException e) {
			result.put("success", false);
			result.put("message", "获取出错");
		}
		return JSONUtil.serialize(result);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(@Valid AlarmResponse alarmResponse,Long stolen_id,HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		try {
			cc.chinagps.seat.auth.User user = getLoginUser(request);
//			//事件编号
//			if(alarmResponse.getRes_id() == null){
//				alarmResponse.setOp_id(user.getOpId());//操作人
//				alarmResponse.setOp_name(user.getOpName());
//			}
			alarmResponse.setOp_id(user.getOpId());//操作人
			alarmResponse.setOp_name(user.getOpName());
//			if(){// 处警响应类型1重大事件2真实警情
//				alarmResponse.setMapId(mapId)
//			}
			alarmResponseService.saveAlarmResponse(alarmResponse,stolen_id);
			
		} catch (SeatException e) {
			resultMap.put("success", false);
			resultMap.put("message", "操作失败");
		}
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/report/export")
	public void exoprt(@RequestParam(value="vehicle_id" )Long vehicle_id, @RequestParam(value="stolen_id" )Long stolen_id, HttpServletResponse response) throws JSONException {
		//事件编号
		File file = null;  
		Map<String, Object> query_result = null ;
		try {
			query_result = alarmResponseService.getAlarmResponse(vehicle_id,stolen_id);
			file = WordGenerator.createDoc(query_result, "alarmResponse");  
			exportWord(response, file, "处警响应表");
		} catch (SeatException e) {
			e.printStackTrace();
		}
	}
	
}
