package cc.chinagps.seat.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.EventSummary;
import cc.chinagps.seat.comm.SeatException;
import cc.chinagps.seat.dao.EventSummaryDao;

@Repository("eventSummaryDao")
public class EventSummaryDaoImpl extends BasicDao implements EventSummaryDao {

	@Override
	public Long saveOrUpdateEventSummary(EventSummary eventSummary)
			throws SeatException {
		getSession().saveOrUpdate(eventSummary);
		return eventSummary.getS_id();
	}

	@SuppressWarnings("unchecked")
	@Override
	public EventSummary getEventSummaryByStolentId(Long stolen_id) throws SeatException {
		Query query = getSession().createSQLQuery(" select *  from  t_seat_event_summary where stolen_id = " +stolen_id.longValue()+" order by stamp desc ").addEntity(EventSummary.class);
		List<EventSummary> result = query.list();
		if( result != null && result.size() > 0  ){
			EventSummary es = result.get(0);
			return es;
		}
		return null;
	}

}
