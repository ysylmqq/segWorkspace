package cc.chinagps.seat.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.table.AlarmStatusTable;
import cc.chinagps.seat.bean.table.AlarmTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;
import cc.chinagps.seat.bean.table.UnitTable;
import cc.chinagps.seat.bean.table.VehicleStatusTable;
import cc.chinagps.seat.dao.AlarmDao;
import cc.chinagps.seat.dao.InfoDao;
import cc.chinagps.seat.service.AlarmService;
import cc.chinagps.seat.util.Consts;
import cc.chinagps.seat.util.PropertiesReader;

@Service
public class AlarmServiceImpl implements AlarmService {

//	private static final AtomicInteger CLIENT_ID = new AtomicInteger(1);
	
	private static final Logger LOGGER = Logger.getLogger(
			AlarmServiceImpl.class);
	
	@Autowired
	private AlarmDao alarmDao;
	
	@Autowired
	private InfoDao infoDao;
	
	private enum SVEnum {
		CASE_FINISH,UPDATE_SV,DELETE_SV
	}
	
	@Override
	public Map<String, Object> getCCConfig(String ip) {
		Properties prop = PropertiesReader.readProperties(
				Consts.PROPERTIES_MISC);
		Map<String, Object> result = new HashMap<String, Object>();
		String hosts;
		boolean isIntranet = Consts.isIntranet(ip);
		if (isIntranet) {
			hosts = prop.getProperty("commcenter.hosts.intra");
		} else {
			hosts = prop.getProperty("commcenter.hosts.outer");
		}
		result.put("hosts", hosts);
//		result.put("clientid", CLIENT_ID.getAndIncrement());
		return result;
	}

	@Override
	public List<AlarmTable> getAlarms(BigInteger vehicleId, int count) {
		return alarmDao.getAlarms(vehicleId, AlarmTable.HANDLE_STATUS_FINISH, count);
	}
	
	@Override
	public int alarmFinish(AlarmTable alarm, StolenVehicleTable stolenVehicle) {
		AlarmTable alarmInDB = alarmDao.getAlarm(
				alarm.getAlarmSn());
		
		if (alarmInDB == null) {
			return Consts.ALARM_ERROR_NO_ALARM;
		}
		if (alarm.getContent() == null || 
				alarm.getContent().trim().length() == 0) {
			return Consts.ALARM_ERROR_ALARM_CONTENT_IS_NULL;
		}
		alarmInDB.setOpId(alarm.getOpId());
		alarmInDB.setOpName(alarm.getOpName());
		Date currentTime = alarmDao.getCurrentTime();
		alarmInDB.setHandleTime(currentTime);
		
		// 计算handleTimeSpan
		Calendar handleTime = Calendar.getInstance();
		handleTime.setTime(alarmInDB.getHandleTime());
		Calendar acceptTime = Calendar.getInstance();
		acceptTime.setTime(alarmInDB.getAcceptTime());
		long handleTimeSec = TimeUnit.MILLISECONDS.toSeconds(handleTime.getTimeInMillis());
		long acceptTimeSec = TimeUnit.MILLISECONDS.toSeconds(acceptTime.getTimeInMillis());
		long handleTimeSpan = handleTimeSec - acceptTimeSec;
		if (handleTimeSpan < 0) {
			handleTimeSpan = 0;
		}
		alarmInDB.setHandleTimeSpan(handleTimeSpan);
		if (alarm.getBrief() == null) {
			alarm.setBrief("");
		}
		
		//TODO:目前没有英文版，可以使用句号合并
		alarmInDB.setBrief(alarm.getContent() + "。" + 
				alarm.getBrief());
		alarmInDB.setHandleStatus(AlarmTable.HANDLE_STATUS_FINISH);
		alarmDao.updateAlarm(alarmInDB);
		alarm.setHandleTime(alarmInDB.getHandleTime());
		
		//如果为真实警情，需要更新呼号警情判断表
		if (alarm.isRealAlarm()) {
			VehicleStatusTable vehicleStatus = new VehicleStatusTable();
			vehicleStatus.setUnitId(alarmInDB.getUnitId());
			vehicleStatus.setStatusId(alarm.getAlarmStatusId());
			vehicleStatus.setFlag(0);
			addVehicleStatus(vehicleStatus, stolenVehicle);
		}
		//TODO:少一步判断是否是测试车辆的逻辑。如果为测试车辆，需要增加一条总不为警情的记录
		//但目前还没有判断测试车辆的方法
		
		return Consts.SUCCEED;
	}
	
	@Override
	public int alarmSuspend(AlarmTable alarm) {
		AlarmTable alarmInDB = alarmDao.getAlarm(
				alarm.getAlarmSn());
		if (alarmInDB == null) {
			return Consts.ALARM_ERROR_NO_ALARM;
		}
		alarmInDB.setOpId(alarm.getOpId());
		alarmInDB.setOpName(alarm.getOpName());
		alarmInDB.setHandleStatus(AlarmTable.HANDLE_STATUS_SUSPEND);
		alarmDao.updateAlarm(alarmInDB);
		
		return Consts.SUCCEED;
	}
	
	@Override
	public int alarmResume(AlarmTable alarm) {
		AlarmTable alarmInDB = alarmDao.getAlarm(
				alarm.getAlarmSn());
		if (alarmInDB == null) {
			return Consts.ALARM_ERROR_NO_ALARM;
		}
		alarmInDB.setOpId(alarm.getOpId());
		alarmInDB.setOpName(alarm.getOpName());
		alarmInDB.setHandleStatus(AlarmTable.HANDLE_STATUS_OPERATIONG);
		alarmDao.updateAlarm(alarmInDB);
		
		return Consts.SUCCEED;
	}
	
	@Override
	public int alarmTransfer(AlarmTable alarm) {
		AlarmTable alarmInDB = alarmDao.getAlarm(
				alarm.getAlarmSn());
		if (alarmInDB == null) {
			return Consts.ALARM_ERROR_NO_ALARM;
		}
		alarmInDB.setOpId(alarm.getOpId());
		alarmInDB.setOpName(alarm.getOpName());
		alarmDao.updateAlarm(alarmInDB);
		
		return Consts.SUCCEED;
	}

	@Override
	public Map<Long, AlarmStatusTable> getAlarmStatus() {
		return alarmDao.getAlarmStatus();
	}
	
	@Override
	public int addVehicleStatus(VehicleStatusTable vehicleStatus,
			StolenVehicleTable stolenVehicle) {
		UnitTable unit = infoDao.getUnit(vehicleStatus.getUnitId());
		if (unit == null) {
			LOGGER.warn("No unit was found and record cannot be "
					+ "added to vehicle status table."
					+ "The unit id is " + vehicleStatus.getUnitId());
			return Consts.ALARM_VEHICLE_ERROR_NO_VEHICLE_STATUS;
		}
		VehicleStatusTable vehicleStatusInDb = 
				alarmDao.getVehicleStatus(vehicleStatus.getUnitId());
		
		// 存在记录
		if (vehicleStatusInDb != null) { 
			// 呼号警情表车辆是否失效
			if (vehicleStatusInDb.isInValid()) {
				vehicleStatusInDb.setDeleted(false);
				vehicleStatusInDb.setEndTime(null);
				alarmDao.updateVehicleStatus(vehicleStatusInDb);
			}
		} else {
			AlarmStatusTable tmpStatus = new AlarmStatusTable();
			tmpStatus.setId(vehicleStatus.getStatusId());
			if (!getAlarmStatus().values().contains(tmpStatus)) {
				return Consts.ALARM_VEHICLE_ERROR_ALARM_STATUS_ERROR;
			}
			
			vehicleStatus.setCallLetter(unit.getCallLetter());
			alarmDao.addVehicleStatus(vehicleStatus); 
		}
		
		if (stolenVehicle == null) {
			stolenVehicle = new StolenVehicleTable();
		}
		stolenVehicle.setBeginTime(alarmDao.getCurrentTime());
		stolenVehicle.setVehicleId(unit.getVehicle().getId());
		if(stolenVehicle.getCaseType() == null)
		stolenVehicle.setCaseType(1);
		if(stolenVehicle.getIsCallPolice() == null)
		stolenVehicle.setIsCallPolice(0);
		
		long id = alarmDao.addStolenVehicle(stolenVehicle);
		stolenVehicle.setId(id);
		return Consts.SUCCEED;
	}
	
	@Override
	public int modifyStolenVehicle(BigInteger unitId,
			StolenVehicleTable stolenVehicle) {
		return operateStolenVehicle(unitId, stolenVehicle, SVEnum.UPDATE_SV);
	}
	
	@Override
	public int delStolenVehicle(BigInteger unitId,
			StolenVehicleTable stolenVehicle) {
		return operateStolenVehicle(unitId, stolenVehicle, SVEnum.DELETE_SV);
	}
	
	@Override
	public int caseFinish(BigInteger unitId, StolenVehicleTable stolenVehicle) {
		return operateStolenVehicle(unitId, stolenVehicle, SVEnum.CASE_FINISH);
	}
	
	private int operateStolenVehicle(BigInteger unitId, 
			StolenVehicleTable stolenVehicle, SVEnum svEnum) {
		VehicleStatusTable vehicleStatusInDB = 
				alarmDao.getVehicleStatus(unitId);
		if (vehicleStatusInDB == null) {
			return Consts.ALARM_VEHICLE_ERROR_NO_VEHICLE_STATUS;
		}
		StolenVehicleTable stolenVehicleInDB = 
				alarmDao.getStolenVehicle(stolenVehicle.getId());
		if (stolenVehicleInDB == null) {
			return Consts.ALARM_VEHICLE_ERROR_NO_VEHICLE_STATUS;
		} else if (stolenVehicleInDB.getEndTime() != null && svEnum == SVEnum.CASE_FINISH) {
			return Consts.ALARM_VEHICLE_ERROR_CASE_FINISHED;
		}
		Date endTime = stolenVehicle.getEndTime();
		Date beginTime = stolenVehicle.getBeginTime();
		if (endTime != null && beginTime != null 
				&& endTime.before(beginTime)) {
			return Consts.ALARM_VEHICLE_ERROR_END_TIME_BEFORE_BEGIN_TIME;
		}
		if (beginTime != null) {
			stolenVehicleInDB.setBeginTime(beginTime);
		}
		if (endTime != null) {
			stolenVehicleInDB.setEndTime(endTime);
		}
		stolenVehicleInDB.setCaseType(stolenVehicle.getCaseType());
		stolenVehicleInDB.setIsCallPolice(stolenVehicle.getIsCallPolice());
		stolenVehicleInDB.setIsCapture(stolenVehicle.getIsCapture());
		
		if (svEnum == SVEnum.DELETE_SV) {
			alarmDao.delStolenVehicle(stolenVehicleInDB);
		} else {
			alarmDao.updateStolenVehicle(stolenVehicleInDB);
		}
		
		return Consts.SUCCEED;
	}

	public void updateVehicleStatus(BigInteger unitId, StolenVehicleTable stolenVehicle) {
		VehicleStatusTable vehicleStatusInDB = 
				alarmDao.getVehicleStatus(unitId);
		if (vehicleStatusInDB == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("There is no vehicle status for unit, the unit id is:" + unitId);
			}
			return;
		}
		long count = alarmDao.getCaseNotFinishedStolenVehicleCount(
				stolenVehicle.getVehicleId());
		if (count == 0) {
			vehicleStatusInDB.setDeleted(true);
			alarmDao.updateVehicleStatus(vehicleStatusInDB);
		}
	}
	
	@Override
	public List<StolenVehicleTable> getStolenVehicle() {
		List<Object[]> objList = alarmDao.getStolenVehicleList();
		List<StolenVehicleTable> svList = new ArrayList<StolenVehicleTable>(objList.size());
		for (Object[] objs : objList) {
			StolenVehicleTable svt = (StolenVehicleTable) objs[0];
			svt.setUnitId((BigInteger) objs[1]);
			svt.setPlateNo((String) objs[2]);
			svList.add(svt);
		}
		return svList;
	}

	@Override
	public AlarmTable getAlarm(String alarmSn) {
		return alarmDao.getAlarm(alarmSn);
	}

	@Override
	public void updateAlarm(AlarmTable alarm) {
		alarmDao.updateAlarm(alarm);
	}
}
