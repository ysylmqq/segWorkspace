package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.SysNodeDao;
import com.gboss.pojo.SysNode;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:SysNodeDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-25 下午3:38:24
 */
@Repository("SysNodeDao")  
@Transactional
public class SysNodeDaoImpl extends BaseDaoImpl implements SysNodeDao {

	@Override
	public List<SysNode> findSysNode(SysNode sysNode) {
		StringBuffer sb=new StringBuffer();
		sb.append(" from SysNode");
		sb.append(" where 1=1");
		if(sysNode!=null){
			if(StringUtils.isNotNullOrEmpty(sysNode.getSubco_id())){
				sb.append(" and (subco_id=").append(sysNode.getSubco_id())
				  .append(" or subco_id=0)");
			}else{
				sb.append(" and subco_id=0");
			}
			if(StringUtils.isNotNullOrEmpty(sysNode.getNodetype_id())){
				sb.append(" and nodetype_id=").append(sysNode.getNodetype_id());
			}
			if(StringUtils.isNotNullOrEmpty(sysNode.getFlag())){
				sb.append(" and flag=").append(sysNode.getFlag());
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.list();
	}

}

