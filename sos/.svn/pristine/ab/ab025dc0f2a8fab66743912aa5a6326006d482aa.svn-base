package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Combo;

/**
 * @Package:com.gboss.dao
 * @ClassName:ComboDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-18 下午2:54:25
 */
public interface ComboDao extends BaseDao {
	public boolean isExist(Combo combo)throws SystemException ;
	
	public List<HashMap<String, Object>> findComboes(Long conpanyId, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countComboes(Long conpanyId, Map<String, Object> conditionMap) throws SystemException;
	
	public int delete(List<Long> ids) throws SystemException;
	
	public Combo getComboByname(String name)throws SystemException;
	
	public List<Combo> getComboList()throws SystemException;
}

