package cc.chinagps.seat.dao;

import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.table.BriefTable;

public interface CallBriefDao {
	
	/**
	 * 来电简报明细
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getCallInBriefs(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);
	

	/**
	 * 来电简报明细数目
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	ReportCommonResponse getCallInBriefsCount(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);
	
	/**
	 * 大类统计
	 * @param brief
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
	public ReportCommonResponse getCalloutStatisticsCount(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param);
	
}
