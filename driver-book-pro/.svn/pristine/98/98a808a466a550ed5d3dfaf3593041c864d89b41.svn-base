package com.chinagps.driverbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.CustOilPrice;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IFuelService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;

/**
 * 燃油检查Controller
 * @author Ben
 *
 */
@RestController
@RequestMapping(value="/fuel")
public class FuelController {
	private static Logger logger = LoggerFactory.getLogger(FuelController.class);
	
	@Autowired
	@Qualifier("fuelServiceImpl")
	private IFuelService fuelService;
	
	/**
	 * 获取今日油价
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 */
	@RequestMapping
	public String index(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			String province = RequestUtil.getStringValue(encryptStr, "province");
			rv = fuelService.findPriceByProvince(province, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 自定义油价查询接口
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 */
	@RequestMapping(value="/price")
	public String price(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			CustOilPrice custOilPrice = RequestUtil.getParameters(encryptStr, CustOilPrice.class);
			rv = fuelService.findCustPrice(custOilPrice, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 自定义油价修改
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 */
	@RequestMapping(value="/price/edit")
	public String editPrice(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			CustOilPrice custOilPrice = RequestUtil.getParameters(encryptStr, CustOilPrice.class);
			rv = fuelService.addOrEditPrice(custOilPrice, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
}
