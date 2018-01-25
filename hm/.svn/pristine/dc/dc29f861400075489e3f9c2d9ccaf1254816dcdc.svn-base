package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.SeriesDao;
import com.gboss.pojo.Series;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:SeriesDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-9 下午2:54:09
 */
@Repository("SeriesDao")  
@Transactional
public class SeriesDaoImpl extends BaseDaoImpl implements SeriesDao {

	@Override
	public List<Series> getSeriesList(Long brand_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Series.class); 
		if(brand_id!=null){
			criteria.add(Restrictions.eq("brand_id", brand_id));
			criteria.add(Restrictions.eq("is_valid", 1));
		}
		List<Series> seriesList =  criteria.list();
		return seriesList;
	}

	@Override
	public Boolean isExist(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Series.class); 
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("is_valid", 1));
		if(criteria.list().size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public int delete(Long id) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Series s set s.is_valid=0 where s.id=:id ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("id", id);
		return query.executeUpdate();
		
	}

	@Override
	public Boolean isExist(String name, Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Series.class); 
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("is_valid", 1));
		criteria.add(Restrictions.not(Restrictions.eq("id", id)));
		if(criteria.list().size() > 0){
			return true;
		}
		return false;
	}

}

