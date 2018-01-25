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
	
	public Unit getUnitByCL(String call_letter) throws SystemException ;
	
//	public Long add(Unit unit);
	
	public Unit getUnitByid(Long id);
	
	public boolean is_repeat(Unit unit);
	
	public List<Unit> getByVehicle_id(Long vehicle_id);
	
	public Unit getByCustAndVehicle(Long cust_id, Long vehicle_id);
	
	public String getHmCall_letters(Long companyId)throws SystemException;
}

