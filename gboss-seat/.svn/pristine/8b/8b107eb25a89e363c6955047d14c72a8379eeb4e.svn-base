package cc.chinagps.seat.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.AlarmResponse;
import cc.chinagps.seat.bean.EventSummary;
import cc.chinagps.seat.comm.SeatException;
import cc.chinagps.seat.dao.AlarmResponseDao;
import cc.chinagps.seat.dao.EventSummaryDao;
import cc.chinagps.seat.service.AlarmResponseService;

@Service("alarmResponseService")
public class AlarmResponseServiceImpl extends BasicService implements
		AlarmResponseService {

	@Autowired
	private AlarmResponseDao alarmResponseDao;
	
	@Autowired
	private EventSummaryDao eventSummaryDao;
	
	@Override
	public Map<String, Object> getAlarmResponse(Long vehicle_id, Long stolen_id)
			throws SeatException {
		if( ( vehicle_id==null || vehicle_id == 0L )||
				( stolen_id==null || stolen_id == 0L ) ){
			throw new SeatException("参数异常！");
		}
		return alarmResponseDao.getAlarmResponse(vehicle_id, stolen_id);
	}

	@Override
	public Long saveAlarmResponse(AlarmResponse alarmResponse,Long stolen_id)
			throws SeatException {
		
		if(stolen_id == null ){
			throw new SeatException("基础警情ID为空!");
		}
		EventSummary eventSummary = eventSummaryDao.getEventSummaryByStolentId(stolen_id) ;
		Long res_id = alarmResponseDao.saveAlarmResponse(alarmResponse);
		if(eventSummary == null){
			eventSummary = new EventSummary();
		}
		
		eventSummary.setRes_id(res_id);
		eventSummaryDao.saveOrUpdateEventSummary(eventSummary);
		return res_id;
	}

}
