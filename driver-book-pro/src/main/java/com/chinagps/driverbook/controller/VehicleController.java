package com.chinagps.driverbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.OpUnit;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.pojo.Vehicle;
import com.chinagps.driverbook.service.IUnitService;
import com.chinagps.driverbook.service.IVehicleService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;
import com.chinagps.driverbook.util.SGErrorInfoConstants;

/**
 * 车辆Controller
 * @author Ben
 *
 */
@RestController
@RequestMapping(value="/vehicle")
public class VehicleController {
	private static Logger logger = LoggerFactory.getLogger(VehicleController.class);
	
	@Autowired
	@Qualifier("vehicleServiceImpl")
	private IVehicleService vehicleService;
	@Autowired
	@Qualifier("unitServiceImpl")
	private IUnitService unitService;
	
	/**
	 * 车辆绑定接口
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/bind", method=RequestMethod.POST)
	public String bind(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Long opId = RequestUtil.getLongValue(encryptStr, "opId");
			String callLetter = RequestUtil.getStringValue(encryptStr, "callLetter");
			String servPwd = RequestUtil.getStringValue(encryptStr, "servPwd");
			vehicleService.bind(opId, callLetter, servPwd, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 车辆解绑接口
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/unbind", method=RequestMethod.POST)
	public String unbind(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			OpUnit opUnit = RequestUtil.getParameters(encryptStr, OpUnit.class);
			vehicleService.unbind(opUnit);
			rv.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 车辆列表接口
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(@RequestBody String encryptStr, ReturnValue rv) { // 用户 注册功能开放后，启用此接口
		try {
			Integer origin = RequestUtil.getIntegerValue(encryptStr, "origin");
			Long opId = RequestUtil.getLongValue(encryptStr, "opId");
			
			if (origin == null || opId == null) {
				rv.setErrorCode(SGErrorInfoConstants.SG_CODE_403);
				rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_403);
				return ResponseUtil.encrypt(rv);
			}
			
			vehicleService.listByOpIdAndOrigin(opId, origin, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 车辆资料详情接口
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/show")
	public String show(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			String vehicleId = RequestUtil.getStringValue(encryptStr, "vehicleId");
			Vehicle vehicle = vehicleService.findById(vehicleId);
			rv.setDatas(vehicle);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 编辑车辆资料接口
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/edit")
	public String edit(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Vehicle vehicle = RequestUtil.getParameters(encryptStr, Vehicle.class);
			Long subcoNo = RequestUtil.getLongValue(encryptStr, "subcoNo");
			String customerName = RequestUtil.getStringValue(encryptStr, "customerName");
			rv = vehicleService.editVehicleAndCustOilPrice(vehicle, subcoNo, customerName, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 服务密码校验接口
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value = "/servpwd/verify")
	public String verifyServPwd(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Vehicle vehicle = RequestUtil.getParameters(encryptStr, Vehicle.class);
			vehicleService.verifyServicePassword(vehicle, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 测试APP扫码获取车台呼号
	 * @param encryptStr 密文参数
	 * @param rv
	 * @return
	 */
	@RequestMapping(value = "/barcode")
	public String barcode(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			String imei = RequestUtil.getStringValue(encryptStr, "imei");
			String barcode = RequestUtil.getStringValue(encryptStr, "barcode");
			unitService.getCallLetterByBarcode(imei, barcode, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 测试APP通过VIN码获取车台呼号
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value = "/vin")
	public String vin(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			String imei = RequestUtil.getStringValue(encryptStr, "imei");
			String vin = RequestUtil.getStringValue(encryptStr, "vin");
			unitService.getCallLetterByVin(imei, vin, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
}
