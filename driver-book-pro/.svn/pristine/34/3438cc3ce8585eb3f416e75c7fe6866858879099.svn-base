package com.chinagps.driverbook.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IStatisticsService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 统计Controller
 * @author Ben
 *
 */
@RestController
@RequestMapping(value="/statistics")
public class StatisticsController {
	private static Logger logger = LoggerFactory.getLogger(StatisticsController.class);
	
	@Autowired
	@Qualifier("statisticsServiceImpl")
	private IStatisticsService statisticsService;
	
	/**
	 * 汇总统计接口
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 */
	@RequestMapping(value="/summary")
	public String summary(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			HashMap<String, String> params = RequestUtil.getParameters(encryptStr, new TypeReference<HashMap<String, String>>() {});
			rv = statisticsService.summary(params, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
}
