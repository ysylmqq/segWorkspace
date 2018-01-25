package com.chinagps.driverbook.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinagps.driverbook.dao.AppBindMapper;
import com.chinagps.driverbook.dao.CustomerMapper;
import com.chinagps.driverbook.dao.DriverMapper;
import com.chinagps.driverbook.dao.LinkmanMapper;
import com.chinagps.driverbook.dao.OperatelogMapper;
import com.chinagps.driverbook.dao.VehicleMapper;
import com.chinagps.driverbook.pojo.AppBind;
import com.chinagps.driverbook.pojo.Customer;
import com.chinagps.driverbook.pojo.Driver;
import com.chinagps.driverbook.pojo.Linkman;
import com.chinagps.driverbook.pojo.Operatelog;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.pojo.Vehicle;
import com.chinagps.driverbook.service.ICustomerService;
import com.chinagps.driverbook.util.DESPlus;
import com.chinagps.driverbook.util.MemcachedUtil;
import com.chinagps.driverbook.util.SGErrorInfoConstants;

@Service
@Scope("prototype")
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements ICustomerService {
	private MemcachedClient mc = MemcachedUtil.getClient(false);
	
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private VehicleMapper vehicleMapper;
	@Autowired
	private AppBindMapper appBindMapper;
	@Autowired
	private LinkmanMapper linkmanMapper;
	@Autowired
	private DriverMapper driverMapper;
	@Autowired
	private OperatelogMapper operatelogMapper;

	public ReturnValue bind(AppBind appBind, ReturnValue rv) throws Exception {
		AppBind ab = appBindMapper.find(appBind);
		if (ab == null) {
			appBindMapper.add(appBind);
		} else {
			appBindMapper.edit(appBind);
		}
		rv.setSuccess(true);
		return rv;
	}

	public ReturnValue showBind(String callLetter, ReturnValue rv) throws Exception {
		AppBind appBind = appBindMapper.findForICE(callLetter);
		rv.setDatas(appBind);
		return rv;
	}

	public ReturnValue checkServicePassword(Customer customer, ReturnValue rv) throws Exception {
		// 连续5次输错服务密码，该功能将冻结一天
		String key = "_check_servpwd_" + customer.getCustomerId();
		int errorCount = mc.get(key) == null ? 0 : (Integer) mc.get(key);
		
		if (errorCount >= 5) {
			rv.setErrorCode(SGErrorInfoConstants.SG_CODE_402);
			rv.setErrorMsg(String.format(SGErrorInfoConstants.SG_MSG_402, "5"));
			return rv;
		}
		
		DESPlus des = new DESPlus("seg12345");
		Customer c = customerMapper.findById(customer.getCustomerId());
		if (!des.Encode(customer.getServicePwd()).equals(c.getServicePwd())) {
			rv.setErrorCode(SGErrorInfoConstants.SG_CODE_401);
			rv.setErrorMsg("服务密码错误！");
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
	
	public ReturnValue editServicePassword(Customer customer, ReturnValue rv) throws Exception {
		DESPlus des = new DESPlus("seg12345");
		Customer c = customerMapper.findById(customer.getCustomerId());
		// 校验原始服务密码
		if (!des.Encode(customer.getServicePwd()).equals(c.getServicePwd())) {
			rv.setErrorCode(SGErrorInfoConstants.SG_CODE_411);
			rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_411);
		} else {
			// 加密新服务密码后保存
			customer.setNewServicePwd(des.Encode(customer.getNewServicePwd()));
			customerMapper.editServicePwd(customer);
			rv.setSuccess(true);
		}
		return rv;
	}

	public ReturnValue findVehiclesByCustomerId(String customerId, Long subcoNo, ReturnValue rv) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("subcoNo", subcoNo);
		List<Vehicle> vehicleList = vehicleMapper.findListByCustomerId(params);
		rv.setDatas(vehicleList);
		return rv;
	}

	@Transactional
	public void saveLinkmanAndDriver(Linkman linkman, Driver driver, String customerName) throws Exception {
		Operatelog oplog = new Operatelog();
		String remark = "";
		if (linkman.getLinkmanId() == null || linkman.getLinkmanId() == 0) {
			linkmanMapper.add(linkman);
			remark += "新増资料：";
			remark += "客户ID：" + linkman.getCustomerId() + ";";
			remark += "车辆ID：" + linkman.getVehicleId() + ";";
			remark += "联系人类型：" + linkman.getLinkmanType() + ";";
			remark += "联系人：" + linkman.getLinkman() + ";";
			remark += "联系电话：" + linkman.getPhone() + ";";
			oplog.setOpType(2);
		} else {
			remark += "资料修改：";
			Linkman l = linkmanMapper.findById(linkman.getLinkmanId());
			if (!l.getLinkman().equals(linkman.getLinkman())) {
				remark += "联系人：" + l.getLinkman() + "->" + linkman.getLinkman() + ";";
			}
			if (!l.getPhone().equals(linkman.getPhone())) {
				remark += "联系电话：" + l.getPhone() + "->" + linkman.getPhone() + ";";
			}
			oplog.setOpType(3);
			linkmanMapper.edit(linkman);
		}
		oplog.setSubcoNo(driver.getSubcoNo());
		oplog.setUserId(linkman.getCustomerId());
		oplog.setUserName(customerName);
		oplog.setRemark(remark);
		operatelogMapper.add(oplog);
		
		Operatelog operatelog = new Operatelog();
		String driverRemark = "";
		if (driver.getDriverId() == null || driver.getDriverId() == 0) {
			driverMapper.add(driver);
			driverRemark += "新増资料：";
			driverRemark += "分公司ID：" + driver.getSubcoNo() + ";";
			driverRemark += "客户ID：" + driver.getCustomerId() + ";";
			driverRemark += "司机姓名：" + driver.getDriverName() + ";";
			driverRemark += "证件类型：" + driver.getIdtype() + ";";
			driverRemark += "准驾车型：" + driver.getDrClass() + ";";
			driverRemark += "驾驶证开始有效日期：" + driver.getDrBdate() + ";";
			driverRemark += "驾驶证到期日期：" + driver.getDrEdate() + ";";
			operatelog.setOpType(2);
		} else {
			driverRemark += "资料修改：";
			Driver d = driverMapper.findById(driver.getDriverId());
			if (!d.getDrClass().equals(driver.getDrClass())) {
				driverRemark += "准驾车型：" + d.getDrClass() + "->" + driver.getDrClass() + ";";
			}
			if (!d.getDrBdate().equals(driver.getDrBdate())) {
				driverRemark += "驾驶证开始有效日期：" + d.getDrBdate() + "->" + driver.getDrBdate() + ";";
			}
			if (!d.getDrEdate().equals(driver.getDrEdate())) {
				driverRemark += "驾驶证到期日期：" + d.getDrEdate() + "->" + driver.getDrEdate() + ";";
			}
			operatelog.setOpType(3);
			driverMapper.edit(driver);
		}
		operatelog.setSubcoNo(driver.getSubcoNo());
		operatelog.setUserId(linkman.getCustomerId());
		operatelog.setUserName(customerName);
		operatelog.setRemark(driverRemark);
		operatelogMapper.add(operatelog);
	}

}
