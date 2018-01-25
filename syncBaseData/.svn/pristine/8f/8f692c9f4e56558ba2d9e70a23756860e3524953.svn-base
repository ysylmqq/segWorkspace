package com.gboss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import com.gboss.pojo.SyncDate;
import com.gboss.service.SyncDateService;

@Controller
public class LogController extends BaseController {
	
	@Autowired
	protected SyncDateService syncDateService;
	
	
	@RequestMapping("/logs/synclogs.do")
	public @ResponseBody JSONObject synclogs(HttpServletRequest request,HttpServletResponse response){
		List<SyncDate> list= syncDateService.getSyncDates();
		JSONObject  json = new JSONObject();
		json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(list));
		json.put("total", list.size());
//		System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(list));
		
		return json;
	}
	
}