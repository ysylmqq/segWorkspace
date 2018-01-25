package com.hm.bigdata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hm.bigdata.entity.po.Alarm;
import com.hm.bigdata.entity.po.Fault;
import com.hm.bigdata.entity.po.Vehicle;
import com.hm.bigdata.util.PageSelect;
import com.hm.bigdata.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:VehicleService
 * @Description:从门店copy过来，以后可能会改成静态数据（搜索引擎）
 * @author:xiaoke
 * @date:2014-3-24 下午3:13:03
 */
public interface FaultService extends BaseService {
	
	public Page<HashMap<String, Object>> search(PageSelect<Fault> pageSelect, Long subco_no);
	
	/**
	 * 导出所有的警情信息
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findAllAlarms(Map<String,Object> map);
	
	/**
	 * 导出所有的警情信息
	 * @param map
	 * @return
	 */
	public void saveFaultInfo(Fault fault);
	
	
	/**
	 * 查询故障类型
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getFalutCodeByName(String faultName);

}

