package cc.chinagps.seat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.chinagps.seat.bean.table.SmsTemplateTable;
import cc.chinagps.seat.service.SmsTemplateService;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

@RequestMapping("/sms_temp")
@Controller
public class SmsTemplateController extends BasicController {
	
	//@Autowired
	private SmsTemplateService smsTemplateService;
	
	@RequestMapping("/codes")
	@ResponseBody
	public String codes(SmsTemplateTable request_stt,HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		
		List<SmsTemplateTable> query_rets = smsTemplateService.getAllSmsTemps(params);
		Map<String,String> temp_type = new HashMap<String, String>();
		
		//安照st_code分好类
		for(SmsTemplateTable stt:query_rets){
			String st_Code = String.valueOf(stt.getStCode());
			if(st_Code!=null && !"".equals(st_Code)){
				if(temp_type.containsKey(st_Code)){
					continue;
				}else{
					temp_type.put(st_Code, stt.getStName());
				}
			}
		}
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		ret.add(temp_type);
		resultMap.put("data", ret);
		resultMap.put("success", true);
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public String list(SmsTemplateTable request_stt,HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		
		List<SmsTemplateTable> query_rets = smsTemplateService.getAllSmsTemps(params);
		Map<String,List<SmsTemplateTable>> temp_type = new HashMap<String, List<SmsTemplateTable>>();
		
		//安照st_code分好类
		for(SmsTemplateTable stt:query_rets){
			String st_Code = String.valueOf(stt.getStCode());
			if(st_Code!=null && !"".equals(st_Code)){
				List<SmsTemplateTable> sms_data = new ArrayList<SmsTemplateTable>(); 
				if(temp_type.containsKey(st_Code)){
					sms_data = temp_type.get(st_Code);
					sms_data.add(stt);
				}else{
					sms_data.add(stt);
					temp_type.put(st_Code, sms_data);
				}
			}
		}
		
		Set<String> keys = temp_type.keySet();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		for(Iterator<String> it = keys.iterator();it.hasNext();){
			String key = it.next();
			List<SmsTemplateTable> val = temp_type.get(key);
			if(val!=null){
				Map<String,Object> temp_map = new HashMap<String, Object>();
				temp_map.put("sms", val);
				SmsTemplateTable stt = val.get(0);
				temp_map.put("stType", stt.getSt_type());
				temp_map.put("stName", getStName(stt.getSt_type()));//0为自定义
				data.add(temp_map);
			}
		}
		
		resultMap.put("data", data);
		resultMap.put("success", true);
		return JSONUtil.serialize(resultMap);
	}
	
	
	
	private Object getStName(Integer st_type) {
		String ret_name = "自定义";
		switch(st_type){
		case 1000:ret_name="自定义";break;
		case 2000:ret_name="公司资料";break;
		case 3000:ret_name="祝福短信";break;
		case 4000:ret_name="业务短信";break;
		case 5000:ret_name="营销短信";break;
		default:ret_name="其他";break;
		}
		return ret_name;
	}



	@RequestMapping(value="/edit",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String saveOrUpdate(SmsTemplateTable stt,HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			smsTemplateService.saveOrUpdate(stt);
			resultMap.put("success", true);
		}catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("message","编辑失败");
		}
		return JSONUtil.serialize(resultMap);
	}
	
	/**
	 * 保存自定义模板内容
	 * @param smses
	 * @param stCode
	 * @param stName
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(String[] smses, String stCode,String stName,HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			if(smses!=null && stCode != null && !"".equals(stCode)){
				int save_num = 0;
				for(String sms :smses){
					SmsTemplateTable stt = new SmsTemplateTable();
					stt.setSms(sms);
					stt.setPCount((short) 0);
					stt.setStName(stName);
					stt.setStCode(Integer.parseInt(stCode));
					smsTemplateService.saveOrUpdate(stt);
					save_num++;
				}
				resultMap.put("success", true);
			}
		}catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("message","编辑或保存失败");
		}
		return JSONUtil.serialize(resultMap);
	}
	
	/**
	 * 删除模板
	 * @param stIds 1,11,12,33
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(Integer[] stIds,HttpServletRequest request) throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			smsTemplateService.batchSmsDel(stIds);
			resultMap.put("success", true);
		}catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("message","删除失败");
		}
		return JSONUtil.serialize(resultMap);
	}
	
}
