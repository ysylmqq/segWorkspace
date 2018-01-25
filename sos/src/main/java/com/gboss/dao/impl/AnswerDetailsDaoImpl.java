package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.AnswerDetailsDao;
import com.gboss.pojo.AnswerDetails;
import com.gboss.pojo.Barcode;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:AnswerDetailsDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-30 下午2:34:57
 */
@Repository("AnswerDetailsDao")  
@Transactional 
public class AnswerDetailsDaoImpl extends BaseDaoImpl implements AnswerDetailsDao {

	@Override
	public List<AnswerDetails> getByAnswerid(Long answer_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AnswerDetails.class); 
		if(answer_id!=null){
			criteria.add(Restrictions.eq("answer_id", answer_id));
		}
		List<AnswerDetails> list = criteria.list();
		return list;
	}

}

