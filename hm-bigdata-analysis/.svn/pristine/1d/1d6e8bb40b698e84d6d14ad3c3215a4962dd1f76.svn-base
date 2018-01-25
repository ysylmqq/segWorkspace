package com.hm.bigdata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hm.bigdata.entity.po.Alarm;
import com.hm.bigdata.entity.po.Fault;
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
public interface FaultDao extends BaseDao {
	
	
	public Page<HashMap<String, Object>> search(PageSelect<Fault> pageSelect, Long subco_no);
	
	/**
	 *查询所有的警情
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findAllFaults(Map<String, Object> map);

	/**
	 * 查询故障码
	 * @param faultName
	 * @return
	 */
	public List<Map<String, Object>> getFalutCodeByName(String faultName);
	
	
}

