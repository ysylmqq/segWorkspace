package com.chinagps.driverbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.Maintain;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IMaintainService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;

/**
 * 保养Controller
 * @author Ben
 *
 */
@RestController
@RequestMapping(value="/maintain")
public class MaintainController {
	private static Logger logger = LoggerFactory.getLogger(MaintainController.class);
	
	@Autowired
	@Qualifier("maintainServiceImpl")
	private IMaintainService maintainService; 
	
	@RequestMapping(value="/show")
	public String show(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Maintain maintain = RequestUtil.getParameters(encryptStr, Maintain.class);
			rv = maintainService.initAndFind(maintain, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	@RequestMapping(value="/edit")
	public String edit(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Maintain m = RequestUtil.getParameters(encryptStr, Maintain.class);
			rv = maintainService.edit(m);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	@RequestMapping(value="/confirm")
	public String confirm(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			String vehicleId = RequestUtil.getStringValue(encryptStr, "vehicleId");
			rv = maintainService.nextPeriod(vehicleId, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	@RequestMapping(value="/period")
	public String period(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			String model = RequestUtil.getStringValue(encryptStr, "model");
			rv = maintainService.findItemsByModel(model, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
} 
