package cc.chinagps.seat.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.table.AlarmStatusTable;
import cc.chinagps.seat.bean.table.AlarmTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;
import cc.chinagps.seat.bean.table.VehicleStatusTable;

public interface AlarmDao {
	
	/**
	 * 获取数据库时间
	 * @return
	 */
	Date getCurrentTime();
	
	/**
	 * 获取所有结束处警的警情记录
	 * @param vehicleId
	 * @param handleStatus 处警状态
	 * @param count
	 * @return
	 */
	List<AlarmTable> getAlarms(BigInteger vehicleId, int handleStatus, int count);
	
	/**
	 * 获取指定的警情
	 * @param alarmSn
	 * @return
	 */
	AlarmTable getAlarm(String alarmSn);
	
	/**
	 * 更新指定警情
	 * @param alarmStatus
	 */
	void updateAlarm(AlarmTable alarmStatus);
	
	/**
	 * 获取所有警情状态信息
	 * @return
	 */
	Map<Long, AlarmStatusTable> getAlarmStatus();
	
	/**
	 * 获取案发车辆信息
	 * @param unitId
	 * @return
	 */
	VehicleStatusTable getVehicleStatus(BigInteger unitId);
	
	/**
	 * 手工添加案发车辆
	 * @param vehicleStatus
	 */
	void addVehicleStatus(VehicleStatusTable vehicleStatus);
	
	/**
	 * 更新案发车辆
	 * @param vehicleStatus
	 */
	void updateVehicleStatus(VehicleStatusTable vehicleStatus);

	void delStolenVehicle(StolenVehicleTable stolenVehicle);

	void updateStolenVehicle(StolenVehicleTable stolenVehicle);

	long addStolenVehicle(StolenVehicleTable stolenVehicle);
	
	StolenVehicleTable getStolenVehicle(long id);
	
	/**
	 * 获取未结案车辆数(结案时间为null表明还未结案)
	 * @param vehicleId
	 * @return
	 */
	long getCaseNotFinishedStolenVehicleCount(BigInteger vehicleId);

	/**
	 * 获取未结案车辆(结案时间为null表明还未结案)
	 * @return
	 */
	List<Object[]> getStolenVehicleList();
}
