package cc.chinagps.seat.dao;

import java.util.Map;

import cc.chinagps.seat.bean.AlarmResponse;
import cc.chinagps.seat.comm.SeatException;

public interface AlarmResponseDao {

	/**
	 * 获取处警信息
	 * @param vehicle_id
	 * @param stolen_id
	 * @throws SeatException
	 */
	public Map<String,Object> getAlarmResponse(Long vehicle_id,Long stolen_id) throws SeatException ;
	
	/**
	 * 保存处警响应信息
	 * @param alarmResponse
	 * @return
	 * @throws SeatException
	 */
	public Long saveAlarmResponse(AlarmResponse alarmResponse) throws SeatException;
	
}
