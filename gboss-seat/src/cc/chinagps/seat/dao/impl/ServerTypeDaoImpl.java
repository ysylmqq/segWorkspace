package cc.chinagps.seat.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.table.ServerTypeCompanyTable;
import cc.chinagps.seat.bean.table.ServerTypeTable;
import cc.chinagps.seat.bean.table.SysValueTable;
import cc.chinagps.seat.dao.ServerTypeDao;

@Repository
public class ServerTypeDaoImpl extends BasicDao implements ServerTypeDao {

	@Override
	public List<ServerTypeTable> getServerTypeList(ServerTypeTable serverType) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select DISTINCT ss.* from  t_seat_servicetype_company sc");
		sb.append(" left join t_seat_servicetype ss on ss.st_id = sc.st_id");
		sb.append(" where sc.subco_no in (select org_id from t_ba_op_org o where o.op_id=:operatorId)");
		sb.append(" and ss.call_type=:callType");
		Query baseInfo = getSession().createSQLQuery(sb.toString())
				.setParameter("operatorId", serverType.getOp_id())
				.setParameter("callType", serverType.getCall_type());
		baseInfo.setResultTransformer(Transformers.aliasToBean(ServerTypeTable.class));
		return baseInfo.list();
	}
	
	@Override
	public List<ServerTypeTable> getServerTypeAllList(ServerTypeTable serverType) {
		String sqlString="select * from t_seat_servicetype where call_type=:callType and p_id <> 0";
		Query baseInfo = getSession().createSQLQuery(sqlString)
				.setParameter("callType", serverType.getCall_type());
		baseInfo.setResultTransformer(Transformers.aliasToBean(ServerTypeTable.class));
		return baseInfo.list();
	}

	@Override
	public Integer saveServerType(ServerTypeTable table) {
		return (Integer)getSession().save(table);
	}

	@Override
	public Integer deleteServerType(Integer id) {
		String sqlString = "delete from t_seat_servicetype where serverTypeId=:id";
		Query query = getSession().createSQLQuery(sqlString).setParameter("id", id);
		return query.executeUpdate();
	}

	@Override
	public List<ServerTypeCompanyTable> getServerTypeCompany(Integer companyId) {
		String sqlString = "select * from t_seat_servicetype_company where companyId=:companyId";
		Query baseInfo = getSession().createSQLQuery(sqlString)
				.setParameter("companyId", companyId);
		baseInfo.setResultTransformer(Transformers.aliasToBean(ServerTypeCompanyTable.class));
		return baseInfo.list();
	}

	@Override
	public Integer saveServerTypeCompany(ServerTypeCompanyTable table) {
		getSession().save(table);
		return (Integer)1;
	}

	@Override
	public Integer deleteServerTypeCompany(Integer companyId) {
		String sqlString = "delete from t_seat_servicetype_company where subco_no=:companyId";
		Query query = getSession().createSQLQuery(sqlString).setParameter("companyId", companyId);
		return query.executeUpdate();
	}

	@Override
	public List<ServerTypeTable> getBaseServerTypeList(Integer callType) {	
		String sqlString = "select * from t_seat_servicetype where p_id=0 and call_type=" + callType;
		Query baseInfo = getSession().createSQLQuery(sqlString).setResultTransformer(Transformers.aliasToBean(ServerTypeTable.class));
		return baseInfo.list();
	}
	
	public static void main(String[] args){
		
	}

	@Override
	public List<SysValueTable> getOrgList() {
		String sqlString = "select value_id as valueId,svalue as value,sname as name,stype as type from t_sys_value where stype=1010 and is_del=0";
		Query baseInfo = getSession().createSQLQuery(sqlString);
		baseInfo.setResultTransformer(Transformers.aliasToBean(SysValueTable.class));
		return baseInfo.list();
	}

	@Override
	public List<ServerTypeTable> getServerByOrgList(Integer orgId,Integer calltype) {
		StringBuffer sb = new StringBuffer();
		sb.append("select DISTINCT ss.* from  t_seat_servicetype_company sc");
		sb.append(" left join t_seat_servicetype ss on ss.st_id = sc.st_id");
		sb.append(" where sc.subco_no=:orgId");
		sb.append(" and ss.call_type=:callType");
		Query baseInfo = getSession().createSQLQuery(sb.toString())
				.setParameter("orgId", orgId)
				.setParameter("callType", calltype);
		baseInfo.setResultTransformer(Transformers.aliasToBean(ServerTypeTable.class));
		return baseInfo.list();
	}

	@Override
	public void clearServers(Integer orgId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from t_seat_servicetype_company where subco_no = :orgId" );
		Query baseInfo = getSession().createSQLQuery(sb.toString())
		.setParameter("orgId", orgId);
		System.out.println("==>"+baseInfo.executeUpdate());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServerTypeTable> getServertypes() {
		String sql = " select * from t_seat_servicetype t   ";
		Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ServerTypeTable.class));
		return query.list();
	}
    
}
