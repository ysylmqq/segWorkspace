package cc.chinagps.seat.dao;

import java.util.Map;

import cc.chinagps.seat.bean.EventReport;
import cc.chinagps.seat.comm.SeatException;


public interface EventReportDao {
	
	/**
	 * 保存重大事件信息
	 * @param eventReport
	 * @return
	 * @throws SeatException
	 */
	public long saveImpEventReport( EventReport eventReport) throws SeatException;
	
	/**
	 * 获取重大事件对象
	 * @param vehicle_id
	 * @return
	 * @throws SeatException
	 */
	public Map<String,Object> getEventReport( Long vehicle_id , Long stolen_id )throws SeatException;
	
	
	
}
