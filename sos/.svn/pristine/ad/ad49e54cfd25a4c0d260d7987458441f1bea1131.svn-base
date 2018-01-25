package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Stop;
import com.gboss.pojo.web.DoOpen;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:StopService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-16 下午5:33:14
 */
public interface StopService extends BaseService {
	
	/**
	 * 增加办停记录
	 * @param stop
	 * @return
	 */
	public Long addStop(Stop stop);
	
	/**
	 * 获取车辆办停信息
	 * @param vehicle_id
	 * @return
	 */
	public List<Stop> getListByid(Long vehicle_id);
	
	/**
	 * 删除办停记录
	 * @param vehicle_id
	 * @param type
	 */
	public void deleteStop(Long vehicle_id, Integer type);
	
	/**
	 * 查询车辆是否办停
	 * @param vehicle_id
	 * @param type
	 * @return
	 */
	public boolean isExist(Long vehicle_id, Integer type);
	
	/**
	 * 获取分公司办停信息
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStops(Map<String, Object> params) throws SystemException;

	/**
	 * 办停处理
	 * @param params
	 * @param stops
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> doStop(Map<String, String> params, List<Stop> stops)throws SystemException;

	/**
	 * 重新开通
	 * @param params
	 * @param doOpen
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> doOpen(Map<String, String> params, DoOpen doOpen)throws SystemException;

	/**
	 * 分页查询办停汇总
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findStopsPage(PageSelect<Map<String, Object>> pageSelect)throws SystemException;

}

