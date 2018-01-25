package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Unit;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.dao
 * @ClassName:UnitDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:07:06
 */
public interface UnitDao extends BaseDao {
	
	public Long add(Unit unit);
	
	public Unit getUnitByid(Long id);
	
	public boolean is_repeat(Unit unit);
	
	public List<Unit> getByVehicle_id(Long vehicle_id);
	
	public Unit getByCustAndVehicle(Long cust_id, Long vehicle_id);
	
	public Page<Unit> search(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search2(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search3(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search4(PageSelect<Unit> pageSelect, Long subco_no);
	
	public Page<Unit> search5(PageSelect<Unit> pageSelect, Long subco_no);
	
	public List<HashMap<String, Object>> getUnitMsgBypage(Long commpanyId, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;
	
	public int countUnits(Long commpanyId, Map<String, Object> conditionMap) throws SystemException;
	
	public List<HashMap<String, Object>> getUnitList(Long commpanyId,
			Map<String, Object> conditionMap) throws SystemException ;
	
	public HashMap<String, Object> getInnetwork(Long subco_no, String time);
	/**
	 * @Title:findUnitInNets
	 * @Description:查询入网车辆列表  服务开通日报表
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findUnitInNets(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	
	/**
	 * @Title:countUnitInNets
	 * @Description:查询入网车辆记录数  服务开通日报表
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countUnitInNets(Map<String,Object> conditionMap) throws SystemException;
	
	public String getHmCall_letters(Long companyId)throws SystemException;
}

