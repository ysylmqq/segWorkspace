package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.CollectionDao;
import com.gboss.pojo.Collection;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:CollectionDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-9 下午3:27:52
 */

@Repository("CollectionDao")  
@Transactional
public class CollectionDaoImpl extends BaseDaoImpl implements CollectionDao {

	@Override
	public Collection getCollectionByCustId(Long cust_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Collection.class);
		criteria.add(Restrictions.eq("customer_id", cust_id));
		if(criteria.list().size()>0){
			return (Collection)criteria.list().get(0);
		}
		return null;
	}

	@Override
	public Collection getCollectionByctno(Collection collection) {
		if(collection==null){
			return null;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Collection.class);
		if(StringUtils.isNotBlank(collection.getPay_ct_no())){
			criteria.add(Restrictions.eq("pay_ct_no", collection.getPay_ct_no()));
		}else{
			return null;
		}
		if(collection.getCollection_id()!=null){
			criteria.add(Restrictions.not(Restrictions.eq("collection_id", collection.getCollection_id())));
		}
		if(criteria.list().size()>0){
			return (Collection)criteria.list().get(0);
		}
		return null;
	}

	@Override
	public Long addCollection(Collection collection) {
		save(collection);
		return collection.getCollection_id();
	}

	@Override
	public List<Collection> getCollections(Long cust_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Collection.class);
		criteria.add(Restrictions.eq("customer_id", cust_id));
		return criteria.list();
	}

	@Override
	public Collection getCollectionByCondition(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" from Collection co");
		sb.append(" where co.pay_ct_no='").append(param.get("payCtNo")).append("'");
		if(Utils.isNotNullOrEmpty(param.get("customerId"))){
			sb.append(" and co.customer_id=").append(param.get("customerId"));
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		List list = query.list();
		if(list != null){
			return (Collection) list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getCollectionByUnit(Long unitId, Long customerId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select c.collection_id, c.customer_id, c.pay_ct_no, c.ac_no, c.ac_type");
		sb.append(" , c.ac_name, c.ac_id_no, c.bank, c.bank_code");
		sb.append(" , c.agency, c.fee_cycle, c.is_delivery, c.addressee , c.print_freq");
		sb.append(" , c.province, c.city, c.area, c.address, c.post_code, c.transactor, c.create_date, c.tel, c.subco_no,c.tax_payer_seq ");
		sb.append(" from t_ba_collection c inner join t_fee_info f on c.collection_id=f.collection_id");
		sb.append(" where c.customer_id=").append(customerId);
		sb.append(" and f.unit_id=").append(unitId);
		sb.append(" group by c.collection_id");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		if(list == null || list.isEmpty()){
			return new HashMap<String, Object>();
		}
		return list.get(0);
	}

}

