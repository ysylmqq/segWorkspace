package com.chinagps.driverbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.LoginInfo;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.ILoginInfoService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;

/**
 * 登录日志Controller
 * @author Ben
 *
 */
@RestController
@RequestMapping(value="/logininfo")
public class LoginInfoController {
	private static Logger logger = LoggerFactory.getLogger(LoginInfoController.class);
	
	@Autowired
	@Qualifier("loginInfoServiceImpl")
	private ILoginInfoService loginInfoService;
	
	/**
	 * 保存登录日志
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/new")
	public String _new(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			LoginInfo loginInfo = RequestUtil.getParameters(encryptStr, LoginInfo.class);
			loginInfoService.add(loginInfo);
			rv.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
}
