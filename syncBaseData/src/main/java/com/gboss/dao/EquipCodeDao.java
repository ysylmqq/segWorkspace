package com.gboss.dao;

import java.util.HashMap;
import java.util.List;

import com.gboss.pojo.Equipcode;

public interface EquipCodeDao extends BaseDao {
	
	/**
	 * 获取所有code
	 * @return
	 */
	public List<Equipcode> getAllEquipCode(HashMap<String,Object> pramas);
	
	public List<Equipcode> getEquipCodeList();
	
	public List<Equipcode> getEquipCodeListByCode(String equipCode);
	
}
