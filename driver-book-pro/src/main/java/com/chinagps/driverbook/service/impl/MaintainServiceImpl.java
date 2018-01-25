package com.chinagps.driverbook.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.MaintainMapper;
import com.chinagps.driverbook.pojo.Maintain;
import com.chinagps.driverbook.pojo.MaintainItems;
import com.chinagps.driverbook.pojo.MaintainRule;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IMaintainService;

@Service
@Scope("prototype")
public class MaintainServiceImpl extends BaseServiceImpl<Maintain> implements IMaintainService {

	@Autowired
	private MaintainMapper maintainMapper;
	
	public ReturnValue initAndFind(Maintain maintain, ReturnValue rv) throws Exception {
		Maintain m = maintainMapper.find(maintain);
		if (m == null) { // 如果无保养信息
			MaintainRule mr = maintainMapper.findRuleByVehicleId(maintain.getVehicleId());
			if (mr == null) { // 如果无保养规则，按默认值进行计算
				mr = new MaintainRule();
				mr.setFirstServiceMileage(5000);
				mr.setFirstServiceTime(3);
				mr.setSecondServiceMileage(10000);
				mr.setSecondServiceTime(6);
				mr.setIntervalMileage(5000);
				mr.setIntervalTime(3);
			}
			
			// 计算保养信息
			if (maintain.getTotalMileage() < mr.getFirstServiceMileage()) {
				maintain.setMaintainMileage(mr.getFirstServiceMileage());
			} else if (maintain.getTotalMileage() < mr.getSecondServiceMileage()) {
				maintain.setMaintainMileage(mr.getSecondServiceMileage());
			} else {
				int next = (int) (Math.ceil((maintain.getTotalMileage() - mr.getSecondServiceMileage()) / mr.getIntervalMileage()) * mr.getIntervalMileage() + mr.getSecondServiceMileage());
				maintain.setMaintainMileage(next);
			}
			maintain.setMaintainTime(3);
			maintain.setNotice(0);
			int addCount = maintainMapper.add(maintain);
			if (addCount > 0) {
				Calendar c = Calendar.getInstance();
				long now = c.getTimeInMillis();
				c.add(Calendar.MONTH, maintain.getMaintainTime());
				long after3Month = c.getTimeInMillis();
				maintain.setMaintainTime((int) ((after3Month - now) / (1000*3600*24)));
				rv.setDatas(maintain);
			} else {
				rv.saveErrror();
			}
		} else {
			rv.setDatas(m);
		}
		return rv;
	}
	
	public ReturnValue nextPeriod(String vehicleId, ReturnValue rv) throws Exception {
		int editCount = maintainMapper.nextPeriod(vehicleId);
		if (editCount > 0) {
			rv.setSuccess(true);
		} else {
			rv.editError();
		}
		return rv;
	}
	
	public ReturnValue findItemsByModel(String model, ReturnValue rv) throws Exception {
		MaintainItems mi = maintainMapper.findItemsByModel(model);
		rv.setDatas(mi);
		return rv;
	}

}
