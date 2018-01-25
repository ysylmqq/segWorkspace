package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Unit;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:BoundService
 * @Description:TODO
 * @author:hansm
 * @date:2014-3-24 下午3:40:48
 */
public interface BoundService extends BaseService {
	
	/**
	 * @Title:findBoundInNetsBypage
	 * @Description:分页查询入网车辆列表  服务开通日报表
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findBoundInNetsBypage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	/**
	 * @Title:findAllBoundInNets
	 * @Description:查询所有入网车辆列表  服务开通日报表
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllBoundInNets(Map<String, Object> conditionMap) throws SystemException;

	/**
	 * @Title:findTBoxServerExpire
	 * @Description:海马TBOX服务期满的客户报表
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findTBoxServerExpire(Map<String, Object> conditionMap) throws SystemException;
	
	/**
	 * 导入海马TBOX续费表单
	 * 
	 * @param dataList
	 * @param compannyId
	 * @param userId
	 * @param companyName
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> importHMTBOX(List<String[]> dataList, Long compannyId, Long userId, String companyName, String companyCode)
			throws SystemException;
	
	public Page<Map<String, Object>> findTBOXByPage(PageSelect<Map<String, Object>> pageSelect)throws SystemException;

}

