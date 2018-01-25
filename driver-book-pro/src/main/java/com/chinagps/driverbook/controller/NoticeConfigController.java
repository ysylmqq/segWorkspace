package com.chinagps.driverbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.NoticeConfig;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.INoticeConfigService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;

/**
 * 消息设置Controller
 * @author Ben
 *
 */
@RestController
@RequestMapping(value="/noticecfg")
public class NoticeConfigController {
	private static Logger logger = LoggerFactory.getLogger(NoticeConfigController.class);
	
	@Autowired
	@Qualifier("noticeConfigServiceImpl")
	private INoticeConfigService noticeConfigService;
	
	/**
	 * 消息设置主界面接口
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 */
	@RequestMapping(value="/show")
	public String show(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Long customerId = RequestUtil.getLongValue(encryptStr, "customerId");
			NoticeConfig noticeConfig = noticeConfigService.findById(customerId);
			rv.setDatas(noticeConfig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 消息设置接口
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 */
	@RequestMapping(value="/edit")
	public String edit(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			NoticeConfig notice = RequestUtil.getParameters(encryptStr, NoticeConfig.class);
			noticeConfigService.addOrEditNoticeConfig(notice, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
}