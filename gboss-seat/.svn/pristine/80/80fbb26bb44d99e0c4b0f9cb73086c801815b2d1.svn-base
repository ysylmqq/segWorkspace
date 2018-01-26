package cc.chinagps.seat.dao.impl;

import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.table.AlarmLogTable;
import cc.chinagps.seat.bean.table.AlarmLogTablePK;
import cc.chinagps.seat.dao.AlarmLogDao;

@Repository("alarmLogDao")
public class AlarmLogDaoImpl extends BasicDao implements AlarmLogDao {

	public Integer save(AlarmLogTable alt) {
		return ((AlarmLogTablePK) getSession().save(alt)).getAlarm_sn()==null?0:1;
	}

}
