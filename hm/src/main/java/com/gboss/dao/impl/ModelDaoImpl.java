package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ModelDao;
import com.gboss.pojo.Model;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ModelDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-24 下午5:08:07
 */
@Repository("modelDao")  
@Transactional
public class ModelDaoImpl extends BaseDaoImpl implements ModelDao {

	@Override
	public List<Model> getModelList(Long series_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Model.class); 
		if(series_id !=null){
			criteria.add(Restrictions.eq("seriesId", series_id));
			criteria.add(Restrictions.eq("is_alid", 1));
		}
		List<Model> modelList =  criteria.list();
		return modelList;
	}

	@Override
	public Boolean isExist(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Model.class); 
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("is_alid", 1));
		if(criteria.list().size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public HashMap<String, Object> getMaintainRuleMsg(Long model_id)
			throws SystemException {
	/*	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MaintainRule.class); 
		if(model_id!=null){
			criteria.add(Restrictions.eq("model", model_id));
		}
		MaintainRule main = (MaintainRule) criteria.list().get(0);
		if(null != main){
			HashMap<String, Object>  map = new HashMap<String, Object>();
			map.put("first_mileage", main.getFirst_service_mileage());
			map.put("second_mileage", main.getSecond_service_mileage());
			map.put("interval_mileage", main.getInterval_mileage());
			return map;
		}*/
		return null;
	}

	@Override
	public int delete(Long id) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Model m set m.is_alid=0 where m.id=:id ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Override
	public Boolean isExist(String name, Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Model.class); 
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("is_alid", 1));
		criteria.add(Restrictions.not(Restrictions.eq("id", id)));
		if(criteria.list().size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public String getModelByid(Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Model.class);
		criteria.add(Restrictions.eq("id", id));
		if(criteria.list().size() > 0){
			Model model = (Model)criteria.list().get(0);
			return model.getName();
		}
		return null;
	}

}

