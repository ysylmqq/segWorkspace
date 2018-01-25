package com.hm.bigdata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hm.bigdata.entity.po.Vehicle;
import com.hm.bigdata.util.PageSelect;
import com.hm.bigdata.util.page.Page;

/**
 * @Package:com.gboss.dao
 * @ClassName:VehicleDao
 * @Description:TODO 从门店copy过来，以后可能会改成静态数据（搜索引擎）
 * @author:xiaoke
 * @date:2014-3-24 下午2:56:45
 */
public interface VehicleDao extends BaseDao {
	
	
	public Page<HashMap<String, Object>> search(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	/**
	 *查询所有的车辆信心
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findAllVehicles(Map<String, Object> map);
	
	/**
	 * 查询海马车辆
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findHmVehicles(Map<String,Object> map);
	
}

