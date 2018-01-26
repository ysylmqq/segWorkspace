package cc.chinagps.seat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.table.AlarmLogTable;
import cc.chinagps.seat.dao.AlarmLogDao;
import cc.chinagps.seat.service.AlarmLogService;

@Service("alarmLogService")
public class AlarmLogServiceImpl implements AlarmLogService {

	@Autowired
	private AlarmLogDao alarmLogDao;
	
	public Integer save(AlarmLogTable alt) {
		return alarmLogDao.save(alt);
	}

}
