package cc.chinagps.seat.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.table.AlarmStatusTable;
import cc.chinagps.seat.bean.table.AlarmTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;
import cc.chinagps.seat.bean.table.VehicleStatusTable;

public interface AlarmService {
	/**
	 * 获取通信中心配置信息。需要根据ip判断内外网然后返回不同的通信中心地址
	 * @param ip ip地址
	 * @return
	 */
	Map<String, Object> getCCConfig(String ip);
	
	/**
	 * 获取所有警情记录
	 * @param vehicleId
	 * @param count
	 * @return
	 */
	List<AlarmTable> getAlarms(BigInteger vehicleId, int count);
	/**
	 * 结束处警
	 * @param alarm
	 * @param stolenVehicle 
	 */
	int alarmFinish(AlarmTable alarm, StolenVehicleTable stolenVehicle);
	
	/**
	 * 挂警
	 * @param alarm
	 */
	int alarmSuspend(AlarmTable alarm);
	
	/**
	 * 取消挂警，继续处理
	 * @param alarm
	 */
	int alarmResume(AlarmTable alarm);
	
	/**
	 * 跟踪警情
	 * @param alarm
	 * @return
	 */
	void updateAlarm(AlarmTable alarm);
	/**
	 * 转警
	 * @param alarm
	 */
	int alarmTransfer(AlarmTable alarm);
	
	/**
	 * 获取所有警情状态信息
	 * @return
	 */
	Map<Long, AlarmStatusTable> getAlarmStatus();
	
	/**
	 * 手工添加案发车辆
	 * @param vehicleStatus
	 * @param stolenVehicle
	 */
	int addVehicleStatus(VehicleStatusTable vehicleStatus,
			StolenVehicleTable stolenVehicle);
	
	/**
	 * 修改案发车辆
	 * @param unitId
	 * @param stolenVehicle
	 * @return
	 */
	int modifyStolenVehicle(BigInteger unitId, StolenVehicleTable stolenVehicle);
	
	/**
	 * 删除案发车辆
	 * @param unitId
	 * @param stolenVehicle 
	 */
	int delStolenVehicle(BigInteger unitId, StolenVehicleTable stolenVehicle);

	/**
	 * 结案
	 * @param unitId
	 * @param stolenVehicle
	 * @return
	 */
	int caseFinish(BigInteger unitId, StolenVehicleTable stolenVehicle);
	
	/**
	 * 获取未结案车辆
	 * @return
	 */
	List<StolenVehicleTable> getStolenVehicle();

	/**
	 * 更新车辆状态信息
	 * @param unitId
	 * @param stolenVehicle
	 */
	void updateVehicleStatus(BigInteger unitId, StolenVehicleTable stolenVehicle);
	
	/**
	 * 获取指定的警情
	 * @param alarmSn
	 * @return
	 */
	AlarmTable getAlarm(String alarmSn);
}
