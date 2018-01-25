package com.hm.bigdata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hm.bigdata.dao.AlarmDao;
import com.hm.bigdata.dao.VehicleDao;
import com.hm.bigdata.entity.po.Alarm;
import com.hm.bigdata.entity.po.Vehicle;
import com.hm.bigdata.service.AlarmService;
import com.hm.bigdata.service.VehicleService;
import com.hm.bigdata.util.PageSelect;
import com.hm.bigdata.util.page.Page;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:VehicleServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:22:09
 */

@Service("alarmService")
@Transactional(value="mysql1TxManager")
public class AlarmServiceImpl extends BaseServiceImpl implements AlarmService {

	@Autowired
	private AlarmDao alarmDao;
	

	@Override
	public Page<HashMap<String, Object>> search(PageSelect<Alarm> pageSelect, Long subco_no) {
		return alarmDao.search(pageSelect, subco_no);
	}

	@Override
	public List<Map<String, Object>> findAllAlarms(Map<String, Object> map) {
		return alarmDao.findAllAlarms(map);
	}

	@Override
	public void saveAlarmInfo(Alarm alarm) {
		alarmDao.save(alarm);
	}

	@Override
	public  List<Map<String, Object>> alarmCount(Map<String, Object> map) {
		return alarmDao.alarmCount(map);
	}

	@Override
	public List<Map<String, Object>> faultCount(Map<String, Object> map) {
		return alarmDao.faultCount(map);
	}

}

