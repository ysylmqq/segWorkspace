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
	
	public Unit getUnitByCL(String call_letter) throws SystemException ;
	
	public boolean is_repeat(Unit unit);
	
	public List<Unit> getByVehicle_id(Long vehicle_id);
	
	public Unit getUnitByid(Long id);
	
	public Unit getByCustAndVehicle(Long cust_id, Long vehicle_id);
	
}

