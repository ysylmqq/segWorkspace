package cc.chinagps.seat.dao;

import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.table.SmsTemplateTable;

public interface SmsTemplateDao {
	
	/**
	 * 自定义短信保存
	 * @param stt
	 */
	public void saveOrUpdate(SmsTemplateTable stt);
	
	/**
	 * 短信删除
	 * @param id
	 */
	public void delteSms(SmsTemplateTable obj);
	
	/**
	 * 查询所有短信模板
	 * @param params
	 * @return
	 */
	public List<SmsTemplateTable> getAllSmsTemps(Map<String,Object> params);
	
	/**
	 * 根据ID获取短信模板
	 * @param stId
	 * @return
	 */
	public SmsTemplateTable getSmsTemplateByID(Integer stId);
	
	/**
	 * 批量删除短信模板
	 * @param stIds
	 */
	public int batchSmsDel(Integer[] stIds);

	/**
	 * 获取stcode最大值
	 * @return
	 */
	public int getNewStCode();
	
	/**
	 * 编辑短信模板名称
	 * @param stCode
	 * @param stName
	 * @return
	 */
	public int updateType(String stCode,String stName);
	
	/**
	 * 根据stCode删除短信模板
	 * @param stCode
	 */
	public void delType(String stCode);
	
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
