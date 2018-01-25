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
 * @ClassName:UnitService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:40:48
 */
public interface UnitService extends BaseService {
	
	/**
	 * 添加车台终端
	 * @param unit
	 * @return
	 */
	public Long add(Unit unit);
	
	/**
	 * 验证终端车载电话是否重复
	 * @param unit
	 * @return
	 */
	public boolean is_repeat(Unit unit);
	
	/**
	 * 通过车辆id获取车台列表
	 * @param vehicle_id
	 * @return
	 */
	public List<Unit> getByVehicle_id(Long vehicle_id);
	
	public Page<Unit> search(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search2(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search3(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search4(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search5(PageSelect<Unit> pageSelect, Long subco_no);
	
	/**
	 * 通过终端id获取终端信息
	 * @param id
	 * @return
	 */
	public Unit getUnitByid(Long id);
	
	/**
	 * 通过客户id、车辆id获取车台
	 * @param cust_id
	 * @param vehicle_id
	 * @return
	 */
	public Unit getByCustAndVehicle(Long cust_id, Long vehicle_id);
	
	/**
	 * 删除终端信息
	 * @param id
	 */
	public void delete(Long id);
	
	/**
	 * 分页查询终端信息
	 * @param commpanyId
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> getUnitMsgBypage(Long commpanyId,PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	/**
	 * 获取查询导出终端信息
	 * @param commpanyId
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> getexportExcelUnits(Long commpanyId, Map<String, Object> map) throws SystemException;
	
	/**
	 * 查询地市下终端列表
	 * @param companyId
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findUnits(Long companyId, Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * 查询地市下在网终端
	 * @param subco_no
	 * @param time
	 * @return
	 */
	public HashMap<String, Object> getInnetwork(Long subco_no, String time);
	
	/**
	 * @Title:findUnitInNetsBypage
	 * @Description:分页查询入网车辆列表  服务开通日报表
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findUnitInNetsBypage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	/**
	 * @Title:findAllUnitInNets
	 * @Description:查询所有入网车辆列表  服务开通日报表
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllUnitInNets(Map<String, Object> conditionMap) throws SystemException;
}

