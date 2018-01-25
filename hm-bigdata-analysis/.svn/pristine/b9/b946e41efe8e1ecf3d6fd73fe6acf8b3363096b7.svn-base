package com.hm.bigdata.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.hm.bigdata.comm.SystemException;
import com.hm.bigdata.dao.SubcoDao;
import com.hm.bigdata.entity.po.Subco;
import com.hm.bigdata.util.Utils;

/**
 * @Package:com.chinagps.fee.dao.impl
 * @ClassName:SubcoDaoImpl
 * @Description:公司托收账号信息 数据持久层实现类
 * @author:zfy
 * @date:2014-5-27 下午2:33:47
 */
@Repository("subcoDao")  
public class SubcoDaoImpl extends BaseDaoImpl implements SubcoDao {

	@Override
	public int updateFlagOthers(Subco subco) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" update Subco");
		sb.append(" set flag=0 where 1=1 ");
		if(subco!=null){
			if(Utils.isNotNullOrEmpty(subco.getSubcoNo())){
				sb.append(" and subcoNo=").append(subco.getSubcoNo());
			}
			if(Utils.isNotNullOrEmpty(subco.getAgency())){
				sb.append(" and agency=").append(subco.getAgency());
			}
			if(Utils.isNotNullOrEmpty(subco.getAcId())){
				sb.append(" and acId<>").append(subco.getAcId());
			}
		}
		Query query = mysql1SessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public List<Subco> findAllSubcos(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" from Subco");
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(Utils.isNotNullOrEmpty(conditionMap.get("subcoNo"))){
				sb.append(" and subcoNo=").append(conditionMap.get("subcoNo"));
			}
			if(Utils.isNotNullOrEmpty(conditionMap.get("flag"))){
				sb.append(" and flag=").append(conditionMap.get("flag"));
			}
			if(Utils.isNotNullOrEmpty(conditionMap.get("agency"))){
				sb.append(" and agency=").append(conditionMap.get("agency"));
			}
		}
		Query query = mysql1SessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.list();
	}


}

