package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.PlanDao;
import com.gboss.pojo.Plan;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:PlanDaoImpl
 * @Description:销售计划数据持久层显示类
 * @author:zfy
 * @date:2013-8-20 上午11:37:36
 */
@Repository("planDao")  
@Transactional 
public class PlanDaoImpl extends BaseDaoImpl implements PlanDao {

	@Override
	public int deletePlan(Plan plan) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		if(plan!=null){
			hqlSb.append(" delete from Plan as p");
			hqlSb.append(" where 1=1 ");
			if(StringUtils.isNotBlank(plan.getYear())){
				hqlSb.append(" and p.year='").append(plan.getYear()).append("'");
			}
			if(plan.getLevel()!=null){
				hqlSb.append(" and p.level=").append(plan.getLevel());
			}
			if(plan.getOrgUserId()!=null){
				hqlSb.append(" and p.orgUserId=").append(plan.getOrgUserId());
			}
			Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());
			return query.executeUpdate();
		}
		return 0;
	}

	@Override
	public List<Plan> findPlans(Plan plan) throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Plan.class); 
		if(plan!=null){
			if(StringUtils.isNotBlank(plan.getYear())){
				criteria.add(Restrictions.eq("year", plan.getYear()));
			}
			if(StringUtils.isNotBlank(plan.getMonthQuota())){
				criteria.add(Restrictions.eq("monthQuota", plan.getMonthQuota()));
			}
			if(plan.getLevel()!=null){
				criteria.add(Restrictions.eq("level", plan.getLevel()));
			}
			if(plan.getOrgUserId()!=null){
				criteria.add(Restrictions.eq("orgUserId", plan.getOrgUserId()));
			}
		}
		return criteria.list();
	}

}

