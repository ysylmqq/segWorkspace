package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ComboDao;
import com.gboss.dao.MealDao;
import com.gboss.pojo.Combo;
import com.gboss.pojo.Meal;
import com.gboss.service.ComboService;
import com.gboss.service.MealService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ComboServiceImpl
 * @Description:私家车入网获取套餐名称
 * @author:hansm
 * @date:2016-6-08 下午3:20:13
 */
@Service("mealService")
@Transactional
public class MealServiceImpl extends BaseServiceImpl implements MealService {
	
	@Autowired
	@Qualifier("mealDao")
	private MealDao mealDao;



	@Override
	public boolean isExist(Meal meal) throws SystemException {
		return mealDao.isExist(meal);
	}


	@Override
	public List<HashMap<String, Object>> findMeales(Long companyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		return mealDao.findMeales(companyId, conditionMap, order, isDesc, pn, pageSize);
	}

}

