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
	
	public Long add(Unit unit);
	
	public boolean is_repeat(Unit unit);
	
	public List<Unit> getByVehicle_id(Long vehicle_id);
	
	public Page<Unit> search(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search2(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search3(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search4(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search5(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Unit getUnitByid(Long id);
	
	public Unit getByCustAndVehicle(Long cust_id, Long vehicle_id);
	
	public void delete(Long id);
	
	public Page<HashMap<String, Object>> getUnitMsgBypage(Long commpanyId,PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	public List<HashMap<String, Object>> getexportExcelUnits(Long commpanyId, Map<String, Object> map) throws SystemException;
	
	public List<HashMap<String, Object>> findUnits(Long companyId, Map<String,Object> conditionMap) throws SystemException;
	
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

