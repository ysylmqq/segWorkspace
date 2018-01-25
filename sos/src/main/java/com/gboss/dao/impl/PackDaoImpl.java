package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.PackDao;
import com.gboss.pojo.Pack;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:PackDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-11-5 下午3:54:40
 */
@Repository("packDao")  
@Transactional  
public class PackDaoImpl extends BaseDaoImpl implements PackDao {

	@Override
	public List<HashMap<String, Object>> findPackList(Long companyno,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select  p.pack_id, p.pack_name, p.fee_cycle,p.price,p.flag,p.combo_id,c.combo_name ");
		sb=getConditionHql(sb,conditionMap, companyno);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	 private StringBuffer getConditionHql(StringBuffer sb,Map conditionMap,Long companyno){
		 	sb.append(" FROM t_fee_sim_pack p LEFT JOIN t_fee_sim_combo c on p.combo_id = c.combo_id  ");
		 	sb.append(" where p.subco_no=").append(companyno);
		 	sb.append(" and p.flag <> 9");
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("pack_name"))){
					sb.append(" and p.pack_name like '%").append(conditionMap.get("pack_name")).append("%'");
				}
			}
			return sb;
	    }
	 

	@Override
	public int countPack(Long companyno, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(p.pack_id) ");
		sb=getConditionHql(sb,conditionMap,companyno);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	
	@Override
	public boolean isExist(Pack pack)throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(p.id) from Pack as p");
		sb.append(" where 1=1 ");
		if(pack!=null){
			if(StringUtils.isNotBlank(pack.getPack_name())){
				sb.append(" and p.pack_name='").append(pack.getPack_name()).append("'");
			}
			if(pack.getPack_id()!=null){
				sb.append(" and p.pack_id <> ").append(pack.getPack_id());
			}
			if(pack.getSubco_no() != null){
				sb.append(" and p.subco_no = ").append(pack.getSubco_no());
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
	public int delete(List<Long> ids) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Pack set flag=:st where pack_id in (:ids) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("st", 9);
		query.setParameterList("ids", ids); 
		return query.executeUpdate();
	}

}

