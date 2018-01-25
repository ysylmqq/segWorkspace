package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ServiceItemDao;
import com.gboss.pojo.Serviceitem;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ServiceItemDaoImp
 * @Description:服务项管理持久层实现类
 * @author:zfy
 * @date:2013-8-9 下午2:21:52
 */
@Repository("ServiceItemDao")  
@Transactional  
public class ServiceItemDaoImp extends BaseDaoImpl implements ServiceItemDao {
	protected static final Logger logger = LoggerFactory.getLogger(ServiceItemDaoImp.class);

	@Override
	public List<Serviceitem> findServiceitem(Serviceitem serviceitem) throws SystemException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Serviceitem.class); 
		if(serviceitem!=null){
			if(StringUtils.isNotBlank(serviceitem.getItem_code())){
				criteria.add(Restrictions.like("code", serviceitem.getItem_code(),MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(serviceitem.getItem_name())){
				criteria.add(Restrictions.like("name", serviceitem.getItem_name(),MatchMode.ANYWHERE));
			}
		}
		return criteria.list();
	}

	@Override
	public boolean checkSeviceItemCode(Serviceitem serviceitem)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.item_id) from Serviceitem as s");
		sb.append(" where 1=1 ");
		if(serviceitem!=null){
			if(StringUtils.isNotBlank(serviceitem.getItem_code())){
				sb.append(" and s.item_code='").append(serviceitem.getItem_code()).append("'");
			}
			if(serviceitem.getItem_id()!=null){
				sb.append(" and s.item_id!=").append(serviceitem.getItem_id());
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		if((Long)query.uniqueResult()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean checkSeviceItemName(Serviceitem serviceitem)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.item_id) from Serviceitem as s");
		sb.append(" where 1=1 ");
		if(serviceitem!=null){
			if(StringUtils.isNotBlank(serviceitem.getItem_name())){
				sb.append(" and s.item_name='").append(serviceitem.getItem_name()).append("'");
			}
			if(serviceitem.getItem_id()!=null){
				sb.append(" and s.item_id!=").append(serviceitem.getItem_id());
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		if((Long)query.uniqueResult()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean checkItemIsUsing(Long serviceItemId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(p.id) from PackItem as p");
		if(serviceItemId!=null){
			sb.append(" where p.itemId=").append(serviceItemId);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		if((Long)query.uniqueResult()>0){
			return true;
		}else{
			return false;
		}
	}

}

