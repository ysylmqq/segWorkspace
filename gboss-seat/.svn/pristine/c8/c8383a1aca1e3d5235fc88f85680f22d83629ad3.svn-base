package cc.chinagps.seat.service;

import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.table.SmsTemplateTable;

public interface SmsTemplateService {
	
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
	 * 获取stCode
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

}
