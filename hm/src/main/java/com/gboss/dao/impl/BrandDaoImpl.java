package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.BrandDao;
import com.gboss.pojo.Brand;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:BrandDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-9 下午2:49:57
 */
@Repository("BrandDao")  
@Transactional 
public class BrandDaoImpl extends BaseDaoImpl implements BrandDao {

	@Override
	public List<Brand> getBrandList() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Brand.class); 
		criteria.add(Restrictions.eq("is_valid", 1));
		List<Brand> brandList =  criteria.list();
		return brandList;
	}

	@Override
	public Boolean isExist(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Brand.class); 
		criteria.add(Restrictions.eq("is_valid", 1));
		criteria.add(Restrictions.eq("name", name));
		if(criteria.list().size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public int delete(Long id) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Brand b set b.is_valid=0 where b.id=:id ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Override
	public Boolean isExist(String name, Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Brand.class); 
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("is_valid", 1));
		criteria.add(Restrictions.not(Restrictions.eq("id", id)));
		if(criteria.list().size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<Brand> serachBrandList(String keyword) {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" from Brand as br ");
		hqlSb.append(" where br.id in ( SELECT DISTINCT b.id FROM Brand b,Series s,Model m WHERE ");
		hqlSb.append(" b.name like '%").append(keyword).append("%'");
		hqlSb.append(" OR ( ");
		hqlSb.append(" s.name like '%").append(keyword).append("%'");
		hqlSb.append(" AND s.brand_id = b.id ) ");
		hqlSb.append(" OR ( ");
		hqlSb.append(" m.name  like '%").append(keyword).append("%'");
		hqlSb.append(" AND m.seriesId = s.id  AND s.brand_id = b.id ) ) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}

}

