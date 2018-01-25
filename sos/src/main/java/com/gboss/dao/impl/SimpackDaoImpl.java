package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.SimpackDao;
import com.gboss.pojo.Combo;
import com.gboss.pojo.Simpack;
import com.gboss.pojo.Subcoft;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:SimpackDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-12-11 下午4:19:25
 */
@Repository("SimpackDao")  
@Transactional 
public class SimpackDaoImpl extends BaseDaoImpl implements SimpackDao {

	@Override
	public List<Subcoft> getSubsofts(Long subco_no) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Subcoft.class); 
		if(subco_no!=null){
			criteria.add(Restrictions.eq("subco_no", subco_no));
			criteria.add(Restrictions.eq("flag", 1));
		}
		List<Subcoft> list = criteria.list();
		return list;
	}

	@Override
	public List<Simpack> getSimpacks(Long subco_no, Integer feetype_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Simpack.class); 
		if(subco_no!=null){
			criteria.add(Restrictions.eq("subco_no", subco_no));
		}
		if(feetype_id!=null){
			criteria.add(Restrictions.eq("feetype_id", feetype_id));
		}
		List<Simpack> list = criteria.list();
		return list;
	}

	@Override
	public Combo getComboByid(Long combo_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Combo.class); 
		if(combo_id!=null){
			criteria.add(Restrictions.eq("combo_id", combo_id));
		}
		List<Combo> list = criteria.list();
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public Simpack getSimpackByid(Long pack_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Simpack.class); 
		if(pack_id!=null){
			criteria.add(Restrictions.eq("pack_id", pack_id));
		}
		List<Simpack> list = criteria.list();
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}

}

