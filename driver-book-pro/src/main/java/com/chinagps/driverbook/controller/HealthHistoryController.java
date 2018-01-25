package com.chinagps.driverbook.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.HealthHistory;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IHealthHistoryService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping(value = "/health")
public class HealthHistoryController {
	private static Logger logger = LoggerFactory.getLogger(HealthHistoryController.class);
	
	@Autowired
	private IHealthHistoryService healthHistoryService;
	
	/**
	 * 获取历史体检分数
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String index(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Map<String, Object> params = RequestUtil.getParameters(encryptStr, new TypeReference<HashMap<String, Object>>() {});
			healthHistoryService.healthHistory(params, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 保存历史体检分数
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String _new(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			HealthHistory healthHistory = RequestUtil.getParameters(encryptStr, HealthHistory.class);
			healthHistoryService.add(healthHistory);
			rv.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
 	
}
