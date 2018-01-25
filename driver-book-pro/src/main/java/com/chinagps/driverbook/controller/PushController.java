package com.chinagps.driverbook.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinagps.driverbook.service.IPushService;

@Controller
@RequestMapping(value="/push")
public class PushController {
	
	private static final Logger logger = Logger.getLogger(PushController.class);
	
//	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private IPushService pushService;
	
	@RequestMapping(value="/offnet")
	@ResponseBody
	public Map<String, Object> offnet(String title,String content,String call_letter, HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.info("脱网提醒推送开始");
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("title", title);
			params.put("content", content);
			params.put("call_letter", call_letter);
			result = pushService.pushOffLine(params);
			
		} catch (Exception e) {
			//错误处理
			e.printStackTrace();
			result.put("code", 500);//
			result.put("success", false);
			logger.error("脱网提醒推送失败" + e.getCause() + ":" + e.getMessage());
		}
		logger.info("脱网提醒推送结束");
		return result;
	}
	
}
