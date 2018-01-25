package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Combo;
import com.gboss.pojo.Meal;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:ComboService
 * @Description:TODO
 * @author:hansm
 * @date:2016-6-08 下午3:20:13
 */
public interface MealService extends BaseService {
	
	/**
	 * 验证套餐名是否已存在，true:存在，false:不存在
	 * @param meal
	 * @return
	 * @throws SystemException
	 */
	public boolean isExist(Meal meal)throws SystemException ;
	
	/**
	 * 查询套餐列表
	 * @param conpanyId
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pn
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findMeales(Long conpanyId, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;
}

