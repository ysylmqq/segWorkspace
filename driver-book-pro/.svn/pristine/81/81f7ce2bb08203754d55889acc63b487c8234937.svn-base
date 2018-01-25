package com.chinagps.driverbook.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.CustOilPriceMapper;
import com.chinagps.driverbook.dao.OpUnitMapper;
import com.chinagps.driverbook.dao.OperatelogMapper;
import com.chinagps.driverbook.dao.UnitMapper;
import com.chinagps.driverbook.dao.VehicleMapper;
import com.chinagps.driverbook.pojo.CustOilPrice;
import com.chinagps.driverbook.pojo.OpUnit;
import com.chinagps.driverbook.pojo.Operatelog;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.pojo.Unit;
import com.chinagps.driverbook.pojo.Vehicle;
import com.chinagps.driverbook.service.IVehicleService;
import com.chinagps.driverbook.util.DESPlus;
import com.chinagps.driverbook.util.MemcachedUtil;
import com.chinagps.driverbook.util.SGErrorInfoConstants;

@Service
@Scope("prototype")
public class VehicleServiceImpl extends BaseServiceImpl<Vehicle> implements IVehicleService {
	private MemcachedClient mc = MemcachedUtil.getClient(false);
	
	@Autowired
	private VehicleMapper vehicleMapper;
	@Autowired
	private CustOilPriceMapper custOilPriceMapper;
	@Autowired
	private OperatelogMapper operatelogMapper;
	@Autowired
	private UnitMapper unitMapper;
	@Autowired
	private OpUnitMapper opUnitMapper;
	
	@Override
	public ReturnValue bind(Long opId, String callLetter, String servPwd, ReturnValue rv) throws Exception {
		DESPlus des = new DESPlus("seg12345");
		Unit unit = unitMapper.findForRegister(callLetter, des.Encode(servPwd));
		if (unit == null) {
			rv.setErrorCode(405); // 为了防止恶意查找车台呼号，不做明确的提示
			rv.setErrorMsg("车台呼号或服务密码不正确！");
		} else  {
			Map<String, Long> validateMap = opUnitMapper.validateByOpidAndUnitId(opId, unit.getUnitId());
			if (validateMap.get("isBind") > 0) { // 该终端是否已经绑定过
				rv.setErrorCode(406);
				rv.setErrorMsg("车辆已被绑定到其他帐号，请检查您输入的车台信息！");
			} else if (validateMap.get("bindNum") >= 50) { // 该用户绑定数量是否已达上限
				rv.setErrorCode(SGErrorInfoConstants.SG_CODE_407);
				rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_407);
			} else {
				OpUnit opUnit = new OpUnit();
				opUnit.setOpId(opId);
				opUnit.setVehicleId(unit.getVehicleId());
				opUnit.setUnitId(unit.getUnitId());
				opUnitMapper.add(opUnit);
				rv.setSuccess(true);
			}
		}
		return rv;
	}

	@Override
	public int unbind(OpUnit opUnit) throws Exception {
		return opUnitMapper.remove(opUnit);
	}
	
	@Override
	public ReturnValue listByOpIdAndOrigin(Long opId, Integer origin, ReturnValue rv) throws Exception {
		List<Vehicle> vehicles = vehicleMapper.findByOpId(origin, opId);
		if (vehicles == null || vehicles.size() == 0) {
			rv.setErrorCode(SGErrorInfoConstants.SG_CODE_408);
			rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_408);
		} else {
			rv.setDatas(vehicles);
		}
		return rv;
	}
	
	public ReturnValue editVehicleAndCustOilPrice(Vehicle vehicle, Long subcoNo, String customerName, ReturnValue rv) throws Exception {
		String remark = "资料修改：";
		Vehicle v = vehicleMapper.findById(vehicle.getVehicleId());
		if (v.getInsuranceId() != null) { // 日志记录
			if (!v.getInsuranceId().equals(vehicle.getInsuranceId())) {
				remark += "保险公司：" + v.getInsuranceId() + "->" + vehicle.getInsuranceId() + ";";
			}
		} else {
			if (vehicle.getInsuranceId() != null) {
				remark += "保险公司：空->" + vehicle.getInsuranceId() + ";";
			}
		}
		if (v.getRegisterDate() != null) {
			if (!v.getRegisterDate().equals(vehicle.getRegisterDate())) {
				remark += "车辆登录日期：" + v.getRegisterDate() + "->" + vehicle.getRegisterDate() + ";";
			}
		} else {
			if (vehicle.getRegisterDate() != null) {
				remark += "车辆登录日期：空->" + vehicle.getRegisterDate() + ";";
			}
		}
		if (v.getVlBdate() != null) {
			if (!v.getVlBdate().equals(vehicle.getVlBdate())) {
				remark += "行驶证发证日期：" + v.getVlBdate() + "->" + vehicle.getVlBdate() + ";";
			}
		} else {
			if (vehicle.getVlBdate() != null) {
				remark += "行驶证发证日期：空->" + vehicle.getVlBdate() + ";";
			}
		}
		if (v.getOilGrade() != null) {
			if (!v.getOilGrade().equals(vehicle.getOilGrade())) {
				remark += "用油标号：" + v.getOilGrade() + "->" + vehicle.getOilGrade() + ";";
			}
		} else {
			if (vehicle.getOilGrade() != null) {
				remark += "用油标号：空->" + vehicle.getOilGrade() + ";";
			}
		}
		Operatelog oplog = new Operatelog();
		oplog.setOpType(3);
		oplog.setSubcoNo(subcoNo);
		oplog.setUserId(Long.parseLong(vehicle.getCustomerId()));
		oplog.setUserName(customerName);
		oplog.setRemark(remark);
		operatelogMapper.add(oplog);
		
		vehicleMapper.edit(vehicle);
		CustOilPrice cop = new CustOilPrice();
		cop.setCustomerId(vehicle.getCustomerId());
		cop.setVehicleId(vehicle.getVehicleId());
		// 根据燃油标号，给不同的字段赋值
		if (vehicle.getOilGrade() == 190) {
			cop.setPrice90(vehicle.getPrice());
		} else if (vehicle.getOilGrade() == 193) {
			cop.setPrice93(vehicle.getPrice());
		} else if (vehicle.getOilGrade() == 197) {
			cop.setPrice97(vehicle.getPrice());
		} else if (vehicle.getOilGrade() == 200) {
			cop.setPrice0(vehicle.getPrice());
		}
		CustOilPrice custOilPrice = custOilPriceMapper.find(cop);
		if (custOilPrice == null) {
			custOilPriceMapper.add(cop);
		} else {
			// 根据燃油标号，补完其他字段的赋值
			if (vehicle.getOilGrade() == 190) {
				cop.setPrice93(custOilPrice.getPrice93());
				cop.setPrice97(custOilPrice.getPrice97());
				cop.setPrice0(custOilPrice.getPrice0());
			} else if (vehicle.getOilGrade() == 193) {
				cop.setPrice90(custOilPrice.getPrice90());
				cop.setPrice97(custOilPrice.getPrice97());
				cop.setPrice0(custOilPrice.getPrice0());
			} else if (vehicle.getOilGrade() == 197) {
				cop.setPrice90(custOilPrice.getPrice90());
				cop.setPrice93(custOilPrice.getPrice93());
				cop.setPrice0(custOilPrice.getPrice0());
			} else if (vehicle.getOilGrade() == 200) {
				cop.setPrice90(custOilPrice.getPrice90());
				cop.setPrice93(custOilPrice.getPrice93());
				cop.setPrice97(custOilPrice.getPrice97());
			}
			custOilPriceMapper.edit(cop);
		}
		rv.setSuccess(true);
		return rv;
	}

	@Override
	public ReturnValue verifyServicePassword(Vehicle vehicle, ReturnValue rv) throws Exception {
		// 连续5次输错服务密码，该功能将冻结一天
		String key = "_check_servpwd_" + vehicle.getVehicleId();
		int errorCount = mc.get(key) == null ? 0 : (Integer) mc.get(key);
		
		if (errorCount >= 5) {
			rv.setErrorCode(SGErrorInfoConstants.SG_CODE_402);
			rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_402);
			return rv;
		}
		
		DESPlus des = new DESPlus("seg12345");
		Vehicle v = vehicleMapper.findById(vehicle.getVehicleId());
		if (!des.Encode(vehicle.getServicePwd()).equals(v.getServicePwd())) {
			rv.setErrorCode(SGErrorInfoConstants.SG_CODE_401);
			rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_401);
			// 验证失败时开始计数
			Calendar cal = Calendar.getInstance();
			long basic = cal.getTimeInMillis();
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			int exp = (int) ((cal.getTimeInMillis() - basic) / 1000);
			mc.set(key, exp, errorCount + 1);
		} else {
			rv.setSuccess(true);
			// 验证成功清空计数
			mc.delete(key);
		}
		return rv;
	}

}
