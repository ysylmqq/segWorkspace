package com.gboss.service;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Plan;

/**
 * @Package:com.gboss.service
 * @ClassName:PlanService
 * @Description:销售计划业务层接口
 * @author:zfy
 * @date:2013-8-20 上午11:48:46
 */
public interface PlanService extends BaseService {
	/**
	 * @Title:findPlans
	 * @Description:查询销售计划
	 * @param plan
	 * @return
	 * @throws
	 */
	public List<Plan> findPlans(Plan plan) throws SystemException;
	
	/**
	 * @Title:insertPlans
	 * @Description:批量添加销售计划
	 * @param plans
	 * @param userId
	 * @throws
	 */
	public void insertPlans(List<Plan> plans,Long userId) throws SystemException;
}

