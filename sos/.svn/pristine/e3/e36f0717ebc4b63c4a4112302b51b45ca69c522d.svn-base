package com.gboss.dao.impl;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.OrgDao;
import com.gboss.pojo.Org;

@Repository("OrgDao")
public class OrgDaoImpl extends BaseDaoImpl implements OrgDao {

	@Override
	public Integer saveOrg(Org org) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into t_ba_org(org_id, org_type, org_no, org_code, org_name, op_id, remark)");
		sb.append(" values(").append(org.getOrgId()).append(", ").append(org.getOrgType())
		.append(", ").append(org.getOrgNo()).append(", '").append(org.getOrgCode()).append("', '").append(org.getOrgName())
		.append("', ").append(org.getOpId()).append(", null)");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return query.executeUpdate();
	}

}
