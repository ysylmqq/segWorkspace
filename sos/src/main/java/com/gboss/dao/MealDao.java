package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Meal;

/**
 * @Package:com.gboss.dao
 * @ClassName:ComboDao
 * @Description:查询套餐名称
 * @author:hansm
 * @date:2016-6-08 下午3:20:13
 */
public interface MealDao extends BaseDao {
	public boolean isExist(Meal meal)throws SystemException ;
	
	public List<HashMap<String, Object>> findMeales(Long companyId, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	//public int countComboes(Long conpanyId, Map<String, Object> conditionMap) throws SystemException;
	
	//public int delete(List<Long> ids) throws SystemException;
	
	//public Combo getMealByname(String name)throws SystemException;
	
	public List<Meal> getMealList()throws SystemException;
}

