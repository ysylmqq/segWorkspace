package com.gboss.service;

import java.util.HashMap;
import java.util.List;

/**
 * @Package:com.gboss.service
 * @ClassName:DispatchElectricianService
 * @Description:派工单与电工业务层接口
 * @author:bzhang
 * @date:2014-3-27 下午6:36:40
 */
public interface DispatchElectricianService extends BaseService {

	public HashMap<String, Object> getElectriciansDetail(Long userId, String time);
	
	public List<HashMap<String, Object>> getElectriciansBytaskId(Long taks_id);
	
	public List<HashMap<String, Object>> getElectriciansByDispatchId(Long dispatch_id);
}



