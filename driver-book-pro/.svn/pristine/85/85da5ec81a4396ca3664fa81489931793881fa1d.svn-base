package com.chinagps.driverbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IAppVersionService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;

/**
 * 版本更新Controller
 * @author Ben
 *
 */
@RestController
@RequestMapping(value="/app")
public class AppVersionController {
	private static Logger logger = LoggerFactory.getLogger(AppVersionController.class);
	
	@Autowired
	@Qualifier("appVersionServiceImpl")
	private IAppVersionService versionService;
	
	@RequestMapping(value = "/version", method = RequestMethod.POST)
	public String show(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Integer origin = RequestUtil.getIntegerValue(encryptStr, "origin");
			if(origin == null) { // 兼容车圣互联5.0.0版本，后期可以删除
				origin = 1;
			}
			rv.setDatas(versionService.latest(origin));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
}
