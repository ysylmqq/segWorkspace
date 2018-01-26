package cc.chinagps.seat.dao;

import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;

public interface CallOutReportDao {
	
	/**
	 * 营销记录、服务费催缴
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getResults(ReportCommonQuery query, Map<String, String> param);
	
	/**
	 *营销记录、服务费催缴 数目
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	ReportCommonResponse getCount(ReportCommonQuery query, Map<String, String> param);
	
}
