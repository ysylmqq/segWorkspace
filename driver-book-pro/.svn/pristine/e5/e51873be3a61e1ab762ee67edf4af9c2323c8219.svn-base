package com.chinagps.driverbook.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.chinagps.driverbook.pojo.AppBind;
import com.chinagps.driverbook.pojo.Customer;
import com.chinagps.driverbook.pojo.Driver;
import com.chinagps.driverbook.pojo.Linkman;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.ICustomerService;
import com.chinagps.driverbook.service.IDriverService;
import com.chinagps.driverbook.service.ILinkmanService;
import com.chinagps.driverbook.util.DateUtil;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 用户Controller
 * @author Ben
 *
 */
@RestController
@RequestMapping(value="/customer")
public class CustomerController {
	private static Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	@Qualifier("customerServiceImpl")
	private ICustomerService customerService;
	@Autowired
	@Qualifier("linkmanServiceImpl")
	private ILinkmanService linkmanService;
	@Autowired
	@Qualifier("driverServiceImpl")
	private IDriverService driverService;
	@Autowired
	private RestTemplate rest;
	
	/**
	 * APP推送绑定接口
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue返回值实例
	 * @return ReturnValue返回值密文
	 */
	@RequestMapping(value="/bind")
	public String bind(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			AppBind appBind = RequestUtil.getParameters(encryptStr, AppBind.class);
			if (appBind.getOrigin() == null) { // 兼容车圣互联5.0.0版本
				appBind.setOrigin(1);
			}
			rv = customerService.bind(appBind, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 获取APP推送绑定关系
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue返回值实例
	 * @return ReturnValue返回值密文
	 */
	@RequestMapping(value="/bind/show")
	public String showBind(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			String callLetter = RequestUtil.getStringValue(encryptStr, "callLetter");
			rv = customerService.showBind(callLetter, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 获取客户基础资料
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue返回值实例
	 * @return ReturnValue返回值密文
	 */
	@RequestMapping(value="/show")
	public String show(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Long customerId = RequestUtil.getLongValue(encryptStr, "customerId");
			Linkman linkman = linkmanService.findByCustomerId(customerId);
			Driver driver = driverService.findByCustomerId(customerId);
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("customerId", customerId);
			resultMap.put("linkman", linkman);
			resultMap.put("driver", driver);
			rv.setDatas(resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 修改客户资料
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue返回值实例
	 * @return ReturnValue返回值密文
	 */
	@RequestMapping(value="/edit")
	public String edit(@RequestBody String encryptStr, ReturnValue rv) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Map<String, Object> params = RequestUtil.getParameters(encryptStr, new TypeReference<HashMap<String, Object>>(){});
			Linkman linkman = new Linkman();
			linkman.setLinkmanId(params.get("linkmanId") == null ? null : Long.parseLong(params.get("linkmanId").toString()));
			linkman.setCustomerId(Long.parseLong(params.get("customerId").toString()));
			linkman.setVehicleId(0l);
			linkman.setLinkmanType(1);
			linkman.setLinkman(params.get("linkman") == null ? null : (String) params.get("linkman"));
			linkman.setPhone(params.get("phone") == null ? null : (String) params.get("phone"));
			Driver driver = new Driver();
			driver.setDriverId(params.get("driverId") == null ? null : Long.parseLong(params.get("driverId").toString()));
			driver.setDriverName(params.get("driverName") == null ? null : (String)params.get("driverName"));
			driver.setCustomerId(Long.parseLong(params.get("customerId").toString()));
			driver.setDrBdate(params.get("drBdate") == null ? null : df.parse(params.get("drBdate").toString()));
			driver.setDrEdate(params.get("period") == null ? null : DateUtil.addYear(driver.getDrBdate(), (Integer)params.get("period")));
			driver.setDrClass(params.get("drClass") == null ? null : (String) params.get("drClass"));
			driver.setIdtype(99);
			driver.setSubcoNo(params.get("subcoNo") == null ? null : Long.parseLong(params.get("subcoNo").toString()));
			customerService.saveLinkmanAndDriver(linkman, driver, params.get("customerName") + "");
			rv.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 服务密码校验
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue返回值实例
	 * @return ReturnValue返回值密文
	 */
	@RequestMapping(value="/servpwd/check")
	public String checkServPwd(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Customer customer = RequestUtil.getParameters(encryptStr, Customer.class);
			customerService.checkServicePassword(customer, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
//	@RequestMapping(value="/servpwd")
//	public String editServPwd(@RequestBody String encryptStr, ReturnValue rv) {
//		try {
//			Customer customer = RequestUtil.getParameters(encryptStr, Customer.class);
//			rv = customerService.editServicePassword(customer, rv);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			rv.systemError();
//		}
//		return ResponseUtil.getEncryptString(rv);
//	}
	
	/**
	 * 用户车辆列表
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue返回值实例
	 * @return ReturnValue返回值密文
	 */
	@RequestMapping(value="/vehicle")
	public String vehicle(@RequestBody String encryptStr, ReturnValue rv) { // 用户注册功能上线以后，可以弃用此接口
		try {
			String customerId = RequestUtil.getStringValue(encryptStr, "customerId");
			Long subcoNo = RequestUtil.getLongValue(encryptStr, "subcoNo");
			customerService.findVehiclesByCustomerId(customerId, subcoNo, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
}
