package cc.chinagps.seat.service;

import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.table.BriefTable;

public interface CallBriefService {
	List<Map<String, Object>> getCallInBriefs(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);
	
	ReportCommonResponse getCallInBriefsCount(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);
	/**
	 @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getCallInSuperStatistics(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);
	
	/**
	 * 小类统计
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getCallInSubStatistics(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);
	
	/**
	 * 呼出日志统计表
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getCalloutFlagStatistics(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);
	
	/**
	 * 外呼统计报表
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getCalloutStatistics(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);
	/**
	 * 外呼统计报表总数
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	ReportCommonResponse getCalloutStatisticsCount(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);
}
