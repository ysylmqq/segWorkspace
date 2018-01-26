package com.chinaGPS.gtmp.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.SimReplaceLogPOJO;
@Component
public interface SimReplaceLogMapper<T extends SimReplaceLogPOJO> extends BaseSqlMapper<T> {
	
	/**
	 * 插入simReplaceLog
	 * @param simReplaceLog
	 * @throws Exception
	 */
	public void insertSelective(SimReplaceLogPOJO simReplaceLog) throws Exception;
	
	/**
	 * 根据条件导出
	 * @param simReplaceLog
	 * @throws Exception
	 */
	public List<SimReplaceLogPOJO> selectAll(Map<String,Object> map);
	
	/**
	 * count
	 * @param simReplaceLog
	 * @throws Exception
	 */
	public int count();
	
	
}