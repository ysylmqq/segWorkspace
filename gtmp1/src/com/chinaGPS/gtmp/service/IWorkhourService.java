package com.chinaGPS.gtmp.service;

import java.util.List;
import java.util.Map;

import com.chinaGPS.gtmp.entity.VehicleWorkPOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

public interface IWorkhourService extends BaseService<VehicleWorkPOJO> {
	
	public List<Map<String, String>> totalWorkhour(VehicleWorkPOJO vehicleWorkPOJO) throws Exception;
	
	/**
	 * 分页统计
	 * @param vehicleWorkPOJO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> totalWorkhourPage(VehicleWorkPOJO vehicleWorkPOJO, PageSelect pageSelect) throws Exception;
	
	public Map<String, Object> totalWorkhourDetail(VehicleWorkPOJO vehicleWorkPOJO, PageSelect pageSelect) throws Exception;
	
	public List<Map<String, String>> dailyWorkhour(VehicleWorkPOJO vehicleWorkPOJO) throws Exception;
	
	public Map<String, Object> dailyWorkhourDetail(VehicleWorkPOJO vehicleWorkPOJO, PageSelect pageSelect) throws Exception;
	
}