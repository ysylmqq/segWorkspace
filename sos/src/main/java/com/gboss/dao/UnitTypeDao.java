package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.SysValue;
import com.gboss.pojo.UnitType;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.dao
 * @ClassName:UnitTypeDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-30 上午11:21:32
 */
public interface UnitTypeDao extends BaseDao {
	
	public Page<UnitType> findUnitType(PageSelect<UnitType> pageSelect)throws SystemException;
	
	public Boolean isExist(String name)throws SystemException;
	
	public Boolean isExist(String name, Long id)throws SystemException;

	public List<SysValue> getSysValueList(Integer typeId)throws SystemException;
	
	public String getUnittypeByid(Long id)throws SystemException;
	
	public int countUnitTypes(Map<String, Object> conditionMap) throws SystemException;
	
	public List<HashMap<String, Object>> findUnitTypes(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;
	
}

