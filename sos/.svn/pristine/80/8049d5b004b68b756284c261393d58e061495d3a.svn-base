package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.FeeInfoDao;
import com.gboss.pojo.Collection;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.Linkman;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:FeeInfoDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-29 上午10:03:36
 */

@Repository("FeeInfoDao")  
@Transactional
public class FeeInfoDaoImpl extends BaseDaoImpl implements FeeInfoDao {

	@Override
	public FeeInfo getFeeInfo(Long unit_id, Integer feetype_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FeeInfo.class); 
		if(unit_id!=null){
			criteria.add(Restrictions.eq("unit_id", unit_id));
		}
		criteria.add(Restrictions.eq("feetype_id", feetype_id));
		List<FeeInfo> list = criteria.list();
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public void deleteByUnitid(Long unit_id) {
		String hql = "delete from FeeInfo where unit_id = " + unit_id;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public List<HashMap<String, Object>> getFeeInfoList(Long vehicle_id)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select t.remark as remark from t_fee_pack_item t where t.id in( ");
		sb.append(" select f.item_id   FROM t_fee_info f where f.fee_id in( ");
		sb.append(" select Max(fee_id) from t_fee_info  ");
		sb.append(" where  vehicle_id=").append(vehicle_id);
		sb.append(" GROUP BY item_id ) ) ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public FeeInfo getinsuranceInfo(Long vehicle_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FeeInfo.class); 
		if(vehicle_id!=null){
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		criteria.add(Restrictions.eq("feetype_id", 3));
		List<FeeInfo> list = criteria.list();
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<FeeInfo> getListByid(Long unit_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FeeInfo.class); 
		if(unit_id!=null){
			criteria.add(Restrictions.eq("unit_id", unit_id));
		}
		List<FeeInfo> list = criteria.list();
		return list;
	}

	@Override
	public void deleteFeeInfo(FeeInfo feeInfo) {
		Long customer_id = feeInfo.getCustomer_id();
		Long vehicle_id = feeInfo.getVehicle_id();
		Integer feetype_id = feeInfo.getFeetype_id();
		if(customer_id==null||vehicle_id==null||feetype_id==null){
			return;
		}
		String hql = "delete from FeeInfo where vehicle_id=" + vehicle_id
				   + " and feetype_id=" 
				   + feetype_id;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public FeeInfo findFeeInfo(Long vehicle_id, Integer feetype_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FeeInfo.class); 
		if(vehicle_id!=null){
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		criteria.add(Restrictions.eq("feetype_id", feetype_id));
		List<FeeInfo> list = criteria.list();
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}
	

	@Override
	public List<FeeInfo> getListBycustId(Long cust_id, String lockTime) {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select f  from FeeInfo f, Unit t where f.unit_id = t.unit_id and t.flag = 0 and f.month_fee <> 0 ");
		hqlSb.append(" and f.customer_id = ").append(cust_id);
		hqlSb.append(" and t.create_date <='").append(lockTime).append("'");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}

	@Override
	public void updateFeeInfo(Long newCust_id, Long oldCust_id) {
		Long collection_id = 0L;
		String pay_ct_no = "";
		Collection collection = getCollectionByCustId(newCust_id);
		if(collection!=null){
			collection_id = collection.getCollection_id();
			pay_ct_no = collection.getPay_ct_no();
		}
		StringBuffer hql = new StringBuffer();
		hql.append(" update FeeInfo set customer_id=").append(newCust_id)
		   .append(" ,collection_id=").append(collection_id)
		   .append(" ,pay_ct_no='").append(pay_ct_no).append("'")
		   .append(" where customer_id=").append(oldCust_id);
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.executeUpdate();
	}
	
	private Collection getCollectionByCustId(Long cust_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Collection.class);
		criteria.add(Restrictions.eq("customer_id", cust_id));
		if(criteria.list().size()>0){
			return (Collection)criteria.list().get(0);
		}
		return null;
	}

}

