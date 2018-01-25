package com.gboss.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.PlanDao;
import com.gboss.pojo.Plan;
import com.gboss.service.PlanService;
import com.gboss.util.DateUtil;
import com.gboss.util.UUIDCreater;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:PlanServiceImp
 * @Description:销售计划业务层实现类
 * @author:zfy
 * @date:2013-8-20 上午11:49:26
 */
@Service("planService")
@Transactional
public class PlanServiceImp extends BaseServiceImpl implements PlanService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(PlanServiceImp.class);
	
	@Autowired  
	@Qualifier("planDao")
	private PlanDao planDao;

	@Override
	public List<Plan> findPlans(Plan plan) throws SystemException {
		return planDao.findPlans(plan);
	}

	@Override
	public void insertPlans(List<Plan> plans,Long userId) throws SystemException {
		//先删除
		if(plans!=null&&!plans.isEmpty()){
			Plan plan=plans.get(0);
			planDao.deletePlan(plan);
		}
		//再添加
		if(plans!=null&&!plans.isEmpty()){
			for (Plan plan : plans) {
				plan.setUserId(userId);
				planDao.save(plan);
			}
		}
		
	}

}

