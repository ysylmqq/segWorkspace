package com.gboss.dao;

import java.util.HashMap;
import java.util.List;


/**
 * @Package:com.gboss.dao
 * @ClassName:DispatchElectricianDao
 * @Description:派工单和电工数据持久层接口
 * @author:bzhang
 * @date:2014-3-27 下午6:24:35
 */
public interface DispatchElectricianDao extends BaseDao {
	
	public HashMap<String, Object> getElectriciansDetail(Long userId, String time);

	public List<HashMap<String, Object>> getElectriciansBytaskId(Long taks_id);
	
	public List<HashMap<String, Object>> getElectriciansByDispatchId(Long dispatch_id);
}

