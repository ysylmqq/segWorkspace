package cc.chinagps.seat.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.table.AlarmStatusTable;
import cc.chinagps.seat.bean.table.AlarmTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;
import cc.chinagps.seat.bean.table.VehicleStatusTable;
import cc.chinagps.seat.dao.AlarmDao;

@Repository
public class AlarmDaoImpl extends BasicDao implements AlarmDao {

	@Override
	public Date getCurrentTime() {
		Timestamp timestamp = (Timestamp) getSession().getNamedQuery(
				"SelectCurrentTimeStamp").uniqueResult();
		Date date = new Date();
		date.setTime(timestamp.getTime());
		return date;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AlarmTable> getAlarms(BigInteger vehicleId, int handleStatus, int count) {
		return getSession().getNamedQuery("SelectAlarmByVehicleID")
				.setBigInteger("vehicleId", vehicleId)
				.setParameter("handleStatus", handleStatus)
				.setMaxResults(count).list();
	}
	
	@Override
	public AlarmTable getAlarm(String alarmSn) {
		return (AlarmTable) getSession().get(AlarmTable.class,
				alarmSn);
	}
	
	@Override
	public void updateAlarm(AlarmTable alarmStatus) {
		getWriteSession().update(alarmStatus);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Cacheable("alarmStatus")
	public Map<Long, AlarmStatusTable> getAlarmStatus() {
		List<AlarmStatusTable> statusList = 
				getSession().getNamedQuery("SelectAlarmStatus").list();
		Map<Long, AlarmStatusTable> statusMap = 
				new HashMap<Long, AlarmStatusTable>(statusList.size());
		for (AlarmStatusTable status : statusList) {
			statusMap.put(status.getId(), status);
		}
		
		return statusMap;
	}
	
	@Override
	public VehicleStatusTable getVehicleStatus(BigInteger unitId) {
		return (VehicleStatusTable) getSession().get(
				VehicleStatusTable.class, unitId);
	}
	
	@Override
	public void addVehicleStatus(VehicleStatusTable vehicleStatus) {
		getWriteSession().save(vehicleStatus);
	}
	
	@Override
	public void updateVehicleStatus(VehicleStatusTable vehicleStatus) {
		getWriteSession().update(vehicleStatus);
	}
	
	@Override
	public long addStolenVehicle(StolenVehicleTable stolenVehicle) {
		return (Long) getWriteSession().save(stolenVehicle);
	}
	
	@Override
	public void updateStolenVehicle(StolenVehicleTable stolenVehicle) {
		getWriteSession().update(stolenVehicle);
	}
	
	@Override
	public void delStolenVehicle(StolenVehicleTable stolenVehicle) {
		getWriteSession().delete(stolenVehicle);
	}
	
	@Override
	public StolenVehicleTable getStolenVehicle(long id) {
		return (StolenVehicleTable) getSession().get(
				StolenVehicleTable.class, id);
	}
	
	@Override
	public long getCaseNotFinishedStolenVehicleCount(BigInteger vehicleId) {
		return (Long) getSession()
				.getNamedQuery("SelectNotFinishedStolenVehicleCount")
				.setParameter("vehicleId", vehicleId).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStolenVehicleList() {
		return getSession()
				.getNamedQuery("SelectNotFinishedStolenVehicle")
				.list();
	}
}
