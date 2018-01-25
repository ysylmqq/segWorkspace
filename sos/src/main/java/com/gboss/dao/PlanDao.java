package com.gboss.dao;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Plan;

/**
 * @Package:com.gboss.dao
 * @ClassName:PlanDao
 * @Description:销售计划数据持久层接口
 * @author:zfy
 * @date:2013-8-20 上午11:28:57
 */
public interface PlanDao extends BaseDao{
	/**
	 * @Title:deletePlan
	 * @Description:根据条件删除销售计划
	 * @param plan
	 * @return
	 * @throws
	 */
	public int deletePlan(Plan plan) throws SystemException;
	/**
	 * @Title:findPlans
	 * @Description:查询销售计划
	 * @param plan
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<Plan> findPlans(Plan plan) throws SystemException;
}

