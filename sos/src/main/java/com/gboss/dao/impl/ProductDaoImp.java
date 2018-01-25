package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ProductDao;
import com.gboss.pojo.Product;
import com.gboss.pojo.ProductOften;
import com.gboss.pojo.ProductRelation;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ProductDaoImp
 * @Description:商品数据持久化层实现类
 * @author:zfy
 * @date:2013-7-31 下午3:40:18
 */
@Repository("productDao")  
@Transactional  
public class ProductDaoImp extends BaseDaoImpl implements ProductDao {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoImp.class);
	 private StringBuffer getConditionHql(StringBuffer hqlSb,Map conditionMap){
			hqlSb.append(" where 1=1");
			if(conditionMap!=null){
				if(conditionMap.get("code")!=null){
					hqlSb.append(" and e.code like '%").append(StringUtils.replaceSqlKey(conditionMap.get("code"))).append("%'");
				}
				if(conditionMap.get("codes")!=null && !((List<String>)conditionMap.get("codes")).isEmpty()){
					hqlSb.append(" and e.code IN (:alist)");
				}
				if(conditionMap.get("name")!=null){
					hqlSb.append(" and e.name like '%").append(StringUtils.replaceSqlKey(conditionMap.get("name"))).append("%'");
				}
				if(conditionMap.get("nameOrCode")!=null){
					hqlSb.append(" and ((e.code like '%").append(StringUtils.replaceSqlKey(conditionMap.get("nameOrCode"))).append("%')");
					hqlSb.append("or (trim(replace(e.name,' ','')) like '%").append(StringUtils.replaceSqlKey(conditionMap.get("nameOrCode"))).append("%' ))");
				}
				if(conditionMap.get("norm")!=null){
					hqlSb.append(" and e.norm like '%").append(StringUtils.replaceSqlKey(conditionMap.get("norm"))).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司和总部的商品
					hqlSb.append(" and (e.company_id=").append(conditionMap.get("companyId"));
					hqlSb.append(" or e.source_type=0)");
				}
				if(conditionMap.get("issell")!=null){
					hqlSb.append(" and e.issell=").append(conditionMap.get("issell"));
				}
				if(conditionMap.get("type")!=null){
					hqlSb.append(" and e.type=").append(conditionMap.get("type"));
				}
				if(conditionMap.get("status")!=null){
					hqlSb.append(" and e.status=").append(conditionMap.get("status"));
				}
				if(conditionMap.get("sourceType")!=null){
					hqlSb.append(" and e.source_type=").append(conditionMap.get("sourceType"));
				}
				if(conditionMap.get("notSetup")!=null && Boolean.valueOf(conditionMap.get("notSetup").toString()) && conditionMap.get("whsId")!=null){//未设置最小库存、积压时长的商品
					hqlSb.append(" and not exists ( select s.id from t_whs_setup as s where s.product_id=e.id and s.whs_id="+conditionMap.get("whsId")+")");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("isOften")) && "0".equals(conditionMap.get("isOften").toString())){//如果不是常用商品
					hqlSb.append(" and not exists ( select s.id from t_sel_product_often as s where s.product_id=e.id ");
					if(conditionMap.get("type")!=null){
						hqlSb.append(" and s.type=").append(conditionMap.get("type"));
					}
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
						hqlSb.append(" and s.company_id=").append(conditionMap.get("companyId"));
					}
					hqlSb.append(")");
				}
			}
			return hqlSb;
	    }
	@Override
	public List<HashMap<String, Object>> findProducts(Map<String, Object> conditionMap, String order,boolean isDesc, int pn,
			int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from (");
		sb.append(" select e.id,e.code,e.name,e.norm,e.type,e.unit");
		sb.append(",e.price,e.issell,e.status,e.source_type as sourceType");
		sb.append(",e.user_id as userId,e.stamp,e.remark");
		sb.append(",(case when po.order_no is null then 100000000 else po.order_no end) as orderNo");
		sb.append(" ,po.id as oftenId");
		sb.append(" from t_sel_product as e");
		//如果查询常用商品
		if(StringUtils.isNotNullOrEmpty(conditionMap.get("isOften")) && "1".equals(conditionMap.get("isOften").toString())){//是否是常用商品
			sb.append(" right join t_sel_product_often as po on po.product_id=e.id");
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
				sb.append(" and po.type=").append(conditionMap.get("type"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
				sb.append(" and po.company_id=").append(conditionMap.get("companyId"));;
			}
		}else{
			sb.append(" left join t_sel_product_often as po on po.product_id=e.id");
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
				sb.append(" and po.type=").append(conditionMap.get("type"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
				sb.append(" and po.company_id=").append(conditionMap.get("companyId"));;
			}
		}
		sb=getConditionHql(sb,conditionMap);
		sb.append(" ) as t");
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append(" order by t.orderNo,t.type");
			//如果查询常用商品
			//if(StringUtils.isNotNullOrEmpty(conditionMap.get("isOften")) && "1".equals(conditionMap.get("isOften").toString())){//是否是常用商品
			//}
			sb.append(" asc");
		}
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString()); 
		query.addScalar("id",LongType.INSTANCE);
		query.addScalar("name",StringType.INSTANCE);
		query.addScalar("code",StringType.INSTANCE);
		query.addScalar("norm",StringType.INSTANCE);
		query.addScalar("unit",StringType.INSTANCE);
		query.addScalar("type",IntegerType.INSTANCE);
		query.addScalar("price",DoubleType.INSTANCE);
		query.addScalar("issell",IntegerType.INSTANCE);
		query.addScalar("status",IntegerType.INSTANCE);
		query.addScalar("sourceType",IntegerType.INSTANCE);
		query.addScalar("userId",StringType.INSTANCE);
		query.addScalar("remark",StringType.INSTANCE);
		query.addScalar("stamp",DateType.INSTANCE);
		query.addScalar("orderNo",IntegerType.INSTANCE);
		query.addScalar("oftenId",LongType.INSTANCE);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(conditionMap!=null && conditionMap.get("codes")!=null && !((List<String>)conditionMap.get("codes")).isEmpty()){
			query.setParameterList("alist", (List<String>)conditionMap.get("codes")); 
		}
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		 //即使全局打开了查询缓存，此处也是必须的  
	   // query.setCacheable(true); 
		return query.list();
	}
	
	@Override
	public boolean checkProductNorm(Product product) throws SystemException{
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(e.id) from Product as e");
		sb.append(" where 1=1 ");
		if(product!=null){
			if(StringUtils.isNotBlank(product.getNorm())){
				sb.append(" and e.norm='").append(product.getNorm()).append("'");
			}
			if(product.getId()!=null){
				sb.append(" and e.id!=").append(product.getId());
			}
			if(product.getCompanyId()!=null){
				sb.append(" and e.companyId=").append(product.getCompanyId());
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
	public boolean checkProductCode(Product product) throws SystemException{
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(e.id) from Product as e");
		sb.append(" where 1=1 ");
		if(product!=null){
			if(StringUtils.isNotBlank(product.getCode())){
				sb.append(" and e.code='").append(product.getCode()).append("'");
			}
			if(product.getId()!=null){
				sb.append(" and e.id!=").append(product.getId());
			}
			if(product.getCompanyId()!=null){
				sb.append(" and e.companyId=").append(product.getCompanyId());
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
	public int countProducts(Map<String, Object> conditionMap) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select count(*) as c from t_sel_product as e");
		//如果查询常用商品
		if(StringUtils.isNotNullOrEmpty(conditionMap.get("isOften")) && "1".equals(conditionMap.get("isOften").toString())){//是否是常用商品
			hqlSb.append(" right join t_sel_product_often as po on po.product_id=e.id");
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
				hqlSb.append(" and po.type=").append(conditionMap.get("type"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
				hqlSb.append(" and po.company_id=").append(conditionMap.get("companyId"));;
			}
		}
		hqlSb=getConditionHql(hqlSb,conditionMap);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hqlSb.toString());  
		query.addScalar("c",IntegerType.INSTANCE);
		 //即使全局打开了查询缓存，此处也是必须的  
	    //query.setCacheable(true); 
		return ((Integer)query.uniqueResult()).intValue();
	}
	/**
	 * @Title:findProductsCondition
	 * @Description:查找所有分公司商品的条件
	 * @param product
	 * @return
	 * @throws
	 */
	private Criteria findProductsCondition(Product product) throws SystemException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class); 
		if(product!=null){
			if(product.getId()!=null){
				criteria.add(Restrictions.eq("id", product.getId()));
			}
			
			if(StringUtils.isNotBlank(product.getCode())){
				criteria.add(Restrictions.like("code", product.getCode(),MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(product.getName())){
				criteria.add(Restrictions.like("name", product.getName(),MatchMode.ANYWHERE));
			}
			if(product.getType()!=null){
				criteria.add(Restrictions.eq("type", product.getType()));
			}
			if(product.getIssell()!=null){
				criteria.add(Restrictions.eq("issell", product.getIssell()));
			}
			if(product.getSourceType()!=null){
				criteria.add(Restrictions.eq("sourceType", product.getSourceType()));
			}
			if(product.getStatus()!=null){
				criteria.add(Restrictions.eq("status", product.getStatus()));
			}
			if(product.getUserId()!=null){
				criteria.add(Restrictions.eq("userId", product.getUserId()));
			}
			if(product.getCompanyId()!=null){
				criteria.add(Restrictions.eq("companyId", product.getCompanyId()));
			}
			/*if(product.getType()!=null && product.getType()==1 && StringUtils.isNotBlank(product.getId())){//查询某个成品的配件
				criteria.add(Restrictions.sqlRestriction(" exists ( select epr.id from T_SEL_Product_RELATION as epr where epr.item_Id={alias}.id and epr.product_Id='"+product.getId()+"')"));
			} else*/ 
			if(product.getId()!=null){
				criteria.add(Restrictions.eq("Id", product.getId()));
			}
		}
		return criteria;
	}
	@Override
	public Product getProduct(Product product)
			throws SystemException {
		Criteria criteria=findProductsCondition(product);
		List<Product> results=criteria.list();
		if(results!=null&&results.size()>0){
			Product result=results.get(0);
			sessionFactory.getCurrentSession().evict(result);
			return result;
		}
		return null;
	}
	@Override
	public int updateStatusByIds(List<Long> ids,Integer status, Long userId, String stamp)
			throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Product ");
		hqlSb.append(" set status=").append(status);
		if(userId!=null){
			hqlSb.append(",userId=").append(userId);
		}
		if(StringUtils.isNotBlank(stamp)){
			hqlSb.append(",stamp='").append(stamp).append("'");
		}
		if(ids!=null&&!ids.isEmpty()){
			hqlSb.append(" where id in (:ids)");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		if(ids!=null&&!ids.isEmpty()){
			query.setParameterList("ids", ids); 
		}
		return query.executeUpdate();
	}
	@Override
	public int deleteProductRelationByProductId(Long Id)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" delete ProductRelation");
		sb.append(" where productId=").append(Id);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}
	
	public List<HashMap<String, Object>> findParts(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from (");
		sb.append(" select");
		sb.append(" ep.id as id");
		sb.append(" ,ep.name as name,ep.code as code,ep.norm as norm,ep.unit as unit");
		sb.append(" ,epr.num as num,epr.version,epr.remark as remark,epr.level");
		sb.append(",(case when po.order_no is null then 100000000 else po.order_no end) as orderNo");
		sb.append(" ,po.id as oftenId");
		sb.append(" from t_sel_product_relation as epr ");
		sb.append(" left join t_sel_product as ep on epr.item_id=ep.id ");
		sb.append(" left join t_sel_product_often as po on po.product_id=epr.item_id");
		if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
			sb.append(" and po.type=").append(conditionMap.get("type"));
		}
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//成品id
				sb.append(" and epr.product_id=").append(conditionMap.get("productId"));
			}
			if(conditionMap.get("type")!=null){
				sb.append(" and ep.type=").append(conditionMap.get("type"));
			}
			if(conditionMap.get("code")!=null){
				sb.append(" and ep.code like '%").append(StringUtils.replaceSqlKey(conditionMap.get("code"))).append("%'");
			}
			if(conditionMap.get("name")!=null){
				sb.append(" and ep.name like '%").append(StringUtils.replaceSqlKey(conditionMap.get("name"))).append("%'");
			}
			if(conditionMap.get("norm")!=null){
				sb.append(" and ep.norm like '%").append(StringUtils.replaceSqlKey(conditionMap.get("norm"))).append("%'");
			}
		}
		sb.append(") as t");
		sb.append(" order by t.orderNo,t.version desc");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.addScalar("id",LongType.INSTANCE);
		query.addScalar("name",StringType.INSTANCE);
		query.addScalar("code",StringType.INSTANCE);
		query.addScalar("norm",StringType.INSTANCE);
		query.addScalar("unit",StringType.INSTANCE);
		query.addScalar("num",IntegerType.INSTANCE);
		query.addScalar("level",IntegerType.INSTANCE);
		query.addScalar("remark",StringType.INSTANCE);
		query.addScalar("version",IntegerType.INSTANCE);
		query.addScalar("orderNo",IntegerType.INSTANCE);
		query.addScalar("oftenId",LongType.INSTANCE);
		//query.setCacheable(true);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	@Override
	public Product getProductByCode(Product product) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" from Product as e");
		hqlSb.append(" where 1=1");
		if(product!=null){
			if(StringUtils.isNotBlank(product.getCode())){
				hqlSb.append(" and e.code='").append(product.getCode()).append("'");
			}
			if(product.getType()!=null){
				hqlSb.append(" and e.type=").append(product.getType());
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());
		List<Product> list=query.list();
		
		Product productResult=null;
		if(list!=null && !list.isEmpty()){
			productResult=list.get(0);
		}
		//将result从session中移除，则持久化对象，转成托管状态，再后面的修改或者重新赋值添加，就不会出现脏数据的情况
		//避免出现错误：identifier of an instance of com.gboss.pojo.Supplycontracts was altered from xx to yy
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().clear();
		//sessionFactory.getCurrentSession().evict(list);
		return productResult;
	}
	@Override
	public int getPartMaxVersion(Long productId) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select max(p.version) from ProductRelation as p where 1=1");
		if(productId!=null){
			hqlSb.append(" and p.productId=").append(productId);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());
		if(query.uniqueResult()!=null){
			return ((Integer)query.uniqueResult()).intValue();
		} else {
			return 0;
		}
	}
	@Override
	public ProductRelation getProductRalation(Long productId, Long itemId)
			throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" from ProductRelation as p where 1=1 ");
		if(productId!=null){
			hqlSb.append(" and p.productId=").append(productId);
		}
		if(itemId!=null){
			hqlSb.append(" and p.itemId=").append(itemId);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString()); 
		List<ProductRelation> list=query.list();
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	@Override
	public ProductOften getProductOften(ProductOften productOften)
			throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" from ProductOften as p where 1=1 ");
		if(StringUtils.isNotNullOrEmpty(productOften.getProductId())){
			hqlSb.append(" and p.productId=").append(productOften.getProductId());
		}
		if(StringUtils.isNotNullOrEmpty(productOften.getCompanyId())){
			hqlSb.append(" and p.companyId=").append(productOften.getCompanyId());
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString()); 
		List<ProductOften> list=query.list();
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
}

