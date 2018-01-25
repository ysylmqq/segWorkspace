package com.gboss.dao.impl;

import java.math.BigInteger;
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
import com.gboss.dao.UpgradeFileDao;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.UpgradeFile;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:UpgradeDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2015-1-27 上午8:52:51
 */
@Repository("upgradeFileDao")  
@Transactional
public class UpgradeFileDaoImpl extends BaseDaoImpl implements UpgradeFileDao {

	@Override
	public List<HashMap<String, Object>> getUpgradeFileList(Long companyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select u.id,u.filename,u.ip,u.port,date_format(u.stamp,'%Y-%m-%d') as stamp ");
		sb=getConditionHql(companyId, sb,conditionMap);
		sb.append(" order by u.id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countpgradeFile(Long companyId, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(u.id) ");
		sb=getConditionHql(companyId, sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	
	 private StringBuffer getConditionHql(Long id, StringBuffer sb,Map conditionMap){
		 	sb.append(" FROM t_st_upgrade_file u ");
		 	sb.append(" where u.subco_no=").append(id);
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("filename"))){
				sb.append(" and u.filename like '%").append(conditionMap.get("filename")).append("%'");
			}
		 	
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate"))) {
				sb.append(" and u.stamp >='").append(DateUtil.dateforSearch(conditionMap, "startDate")).append("'");
			}
		 	
		 	if (StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
				sb.append(" and u.stamp <='").append(DateUtil.dateforSearch(conditionMap, "endDate")).append("'");
			}
			return sb;
	    }

	@Override
	public List<UpgradeFile> getUpgradeFileList() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UpgradeFile.class); 
		criteria.add(Restrictions.eq("is_del", 0));
		List<UpgradeFile>  list = criteria.list();
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	@Override
	public UpgradeFile getUpgradeFileByname(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UpgradeFile.class); 
		//criteria.add(Restrictions.eq("filename", name));
		criteria.add(Restrictions.like("filename", "%"+name+"%"));
		List<UpgradeFile>  list = criteria.list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}

