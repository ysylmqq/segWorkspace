package com.chinagps.center.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.dao.ModelDao;
import com.chinagps.center.pojo.Model;
import com.chinagps.center.utils.StringUtils;

@Repository("ModelDao")
public class ModelDaoImpl extends BaseDaoImpl implements ModelDao {

	@Override
	public List<Model> listByModelName(String name) throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Model.class); 
		if(StringUtils.isNotBlank(name)){
			criteria.add(Restrictions.eq("name", name));
		}
		List<Model> models=criteria.list();
		return models;
	}
}
