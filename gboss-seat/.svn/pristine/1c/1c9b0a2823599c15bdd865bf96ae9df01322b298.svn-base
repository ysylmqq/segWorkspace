package cc.chinagps.seat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.dao.CallOutReportDao;
import cc.chinagps.seat.service.CallOutReportService;

@Service("callOutReportService")
public class CallOutReportServiceImpl extends BasicService implements CallOutReportService {

	@Autowired
	private CallOutReportDao callOutReportDao;
	
	@Override
	public List<Map<String, Object>> getResults(ReportCommonQuery query,
			Map<String, String> param) {
		int start = query.getStart();
		List<Map<String, Object>> ret = callOutReportDao.getResults(query, param);
		List<Map<String, Object>> _ret = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> bean : ret){
			bean.put("sn", ++start);
			_ret.add(bean);
		}
		return _ret;
	}

	@Override
	public ReportCommonResponse getCount(ReportCommonQuery query,
			Map<String, String> param) {
		return callOutReportDao.getCount(query, param);
	}

}
