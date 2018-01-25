package com.chinagps.center.dao;

import java.util.Map;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.pojo.Unit;

public interface UnitDao extends BaseDao {

	public Map<String, Object> getByCallLetter(String call_letter)throws SystemException;

	public Unit getUnitById(Long unit_id)throws SystemException;

	public Long add(Unit unit)throws SystemException;

	public Unit getUnitByCallLetter(String call_letter)throws SystemException;

}
