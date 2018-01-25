package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.MaxidDao;
import com.gboss.pojo.Maxid;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:MaxidDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-6 下午2:41:11
 */
@Repository("MaxidDao")  
@Transactional
public class MaxidDaoImpl extends BaseDaoImpl implements MaxidDao {

	@Override
	public Long getAndAddMaxid(Long subco_no) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Maxid.class); 
		if(subco_no !=null){
			subco_no = 101L;
			criteria.add(Restrictions.eq("subco_no", subco_no));
		}
		List<Maxid> list =  criteria.list();
		if(list.size()>0){
			Maxid maxid = list.get(0);
			Long ct_no = maxid.getCt_no();
			Long add_ctno = ct_no + 1;
			maxid.setCt_no(add_ctno);
			update(maxid);
			return add_ctno;
		}
		return 0L;
	}

}

