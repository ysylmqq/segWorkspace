package cc.chinagps.seat.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.EventReport;
import cc.chinagps.seat.bean.EventSummary;
import cc.chinagps.seat.comm.SeatException;
import cc.chinagps.seat.dao.EventReportDao;
import cc.chinagps.seat.dao.EventSummaryDao;
import cc.chinagps.seat.service.EventReportService;

@Service("eventReportService")
public class EventReportServiceImpl extends BasicService implements EventReportService {

	@Autowired
	private EventReportDao eventReportDao;
	
	@Autowired
	private EventSummaryDao eventSummaryDao;
	
	@Override
	public Long saveOrUpdateEventReport( Long stolen_id , EventReport eventReport) 
			throws SeatException {
		if(stolen_id == null ){
			throw new SeatException("基础警情ID为空!");
		}
		EventSummary eventSummary = eventSummaryDao.getEventSummaryByStolentId(stolen_id) ;
		Long e_id = eventReportDao.saveImpEventReport(eventReport);
		if(eventSummary == null){
			eventSummary = new EventSummary();
		}
		eventSummary.setE_id(e_id);
		eventSummaryDao.saveOrUpdateEventSummary(eventSummary);
		return e_id;
	}

	@Override
	public Map<String, Object> getEventReport(Long vehicle_id ,Long stolen_id)
			throws SeatException {
		if( (vehicle_id == null || vehicle_id == 0 )||(stolen_id == null || stolen_id == 0))
			throw new SeatException("非法参数");
		return eventReportDao.getEventReport(vehicle_id,stolen_id);
	}

}
