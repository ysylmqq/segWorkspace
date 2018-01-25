package com.chinagps.driverbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.util.ResponseUtil;

/**
 * 综合评分Controller
 * @author Ben
 *
 */
@RestController
@RequestMapping(value="/score")
public class ScoreController {
	private static Logger logger = LoggerFactory.getLogger(ScoreController.class);
	
	/**
	 * 综合评分接口
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 */
	@RequestMapping
	public String index(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	@RequestMapping(value="/consume")
	public String consume(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
}
