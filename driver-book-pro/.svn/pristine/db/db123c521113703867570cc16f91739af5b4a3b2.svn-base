package com.chinagps.driverbook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.Insurance;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IInsuranceService;
import com.chinagps.driverbook.util.ResponseUtil;

/**
 * 保险Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value="/insurance")
public class InsuranceController {
	private static Logger logger = LoggerFactory.getLogger(InsuranceController.class);
	
	@Autowired
	@Qualifier("insuranceServiceImpl")
	private IInsuranceService insuranceService;
	
	/**
	 * 获取保险公司列表
	 * @param rv
	 * @return
	 */
	@RequestMapping
	public String index(ReturnValue rv) {
		try {
			List<Insurance> datas = insuranceService.findList();
			rv.setDatas(datas);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
}
