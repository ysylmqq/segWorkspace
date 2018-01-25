package com.gboss.dao.impl;

import java.util.HashMap;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ReservationDao;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Reservation;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ReservationDaoImpl
 * @Description:预约数据持久化层实现类
 * @author:bzhang
 * @date:2014-4-3 下午3:15:01
 */
@Repository("reservationDao")  
@Transactional 
public class ReservationDaoImpl extends BaseDaoImpl implements ReservationDao {

	@Override
	public  HashMap<String, Object> getReservationBytask(Long taskId) {
		StringBuffer sb=new StringBuffer();
		sb.append(" select r.id  as rid, r.stamp as time ");
		sb.append(" from t_ba_reservation r ");
		sb.append(" where r.task_id = ").append(taskId);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(query.list().size() > 0){
			return (HashMap<String, Object>) query.list().get(0);
		}else
			return null;
	}

}

