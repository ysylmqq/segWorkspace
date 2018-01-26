package cc.chinagps.seat.dao;

import cc.chinagps.seat.bean.EventSummary;
import cc.chinagps.seat.comm.SeatException;

/**
 * 
 *	1、车门被撬/车玻璃被砸/车内东西被盗/高速启动不了属于重大事件，需要填写《重大事件报告》；
 * 	2、车辆怀疑被盗属于真实警情，需要填写警情报告，根据车辆报失时实际情况需要填写车台报警未截获/车台报警已截获/客户来电报车辆丢失未截获/客户来电报车辆丢失已截获四个不同表格其中一种，
 *	也有可能需要从未截获表格转为已截获表格；
 *	3、重大事件中车辆有引发报警时，需要填写警情动态；
 *	4、重大事件和真实警情中安排电工出车后需要填写处警响应表。
 *
 */
public interface EventSummaryDao {
	
	/**
	 * 保存/编辑警情处理纪要
	 * @param eventSummary
	 * @return
	 * @throws SeatException
	 */
	public Long saveOrUpdateEventSummary(EventSummary eventSummary) throws SeatException;
	
	/**
	 * 查询警情处理纪要
	 * @param eventSummary
	 * @return
	 * @throws SeatException
	 */
	public EventSummary getEventSummaryByStolentId(Long  stolen_id) throws SeatException;
	
}
