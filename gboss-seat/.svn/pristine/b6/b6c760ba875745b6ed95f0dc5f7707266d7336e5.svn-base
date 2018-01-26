package cc.chinagps.seat.service;

import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;

/**
 * 发送类
 * @author Administrator
 *
 */
public interface SenderService {
	
	public String send(Map<String,String> params);
	
	
	/**
	 * 短信发送明细
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getSendsmsLists(ReportCommonQuery query, Map<String, String> param);
	

	/**
	 * 短信发送明细数目
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	ReportCommonResponse getSendsmsListsCount(ReportCommonQuery query, Map<String, String> param);
	

	/**
	 * 短信统计
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getSendsmsStatisticsLists(ReportCommonQuery query, Map<String, String> param);
	

	/**
	 * 短信统计数目
	 * @param brief
	 * @param query
	 * @param param
	 * @return
	 */
	ReportCommonResponse getSendsmsStatisticsCount(ReportCommonQuery query, Map<String, String> param);
}
