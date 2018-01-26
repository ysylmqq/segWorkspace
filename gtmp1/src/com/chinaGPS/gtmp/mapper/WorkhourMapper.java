package com.chinaGPS.gtmp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.VehicleWorkPOJO;
@Component
public interface WorkhourMapper<T extends VehicleWorkPOJO> extends BaseSqlMapper<T> {
	
	public List<Map<String, String>> totalWorkhour(VehicleWorkPOJO vehicleWorkPOJO) throws Exception;
	
	/**
	 * 分页
	 * @param map
	 * @param rowBounds
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> totalWorkhourPage(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	/**
	 * countAll
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer totalWorkhourPageCountAll(Map<String, Object> map) throws Exception;
	
	public Integer totalWorkhourDetailCountAll(Map<String, Object> map) throws Exception;
	
	public List<Map<String, String>> totalWorkhourDetail(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	public List<Map<String, String>> dailyWorkhour(VehicleWorkPOJO vehicleWorkPOJO) throws Exception;
	
	public Integer dailyWorkhourDetailCountAll(Map<String, Object> map) throws Exception;
	
	public List<Map<String, String>> dailyWorkhourDetail(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
}
