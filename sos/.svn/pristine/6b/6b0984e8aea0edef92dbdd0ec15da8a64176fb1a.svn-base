package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.ReportDao;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.page.PageUtil;

@Repository("reportDao")
public class ReportDaoImpl extends BaseDaoImpl implements ReportDao {

	@Override
	public int countJoinNetSum(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from (");
		sb.append(" select  v.4s_id");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb = buildJoinNetSumSql(sb, param);
		sb.append(" group by v.4s_id");
		sb.append(") td");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	// 4S店名称、入网数量（该时间段）、在网总数,在网统计在getOnNetCount方法中
	private StringBuffer buildJoinNetSumSql(StringBuffer sb,
			Map<String, Object> param) {
		sb.append(" where 1=1");
		if(Utils.isNotNullOrEmpty(param)){
			if(Utils.isNotNullOrEmpty(param.get("startTime"))){
				sb.append(" and u.create_date>='").append(param.get("startTime")).append(" 00:00:00'");
			}
			if(Utils.isNotNullOrEmpty(param.get("endTime"))){
				sb.append(" and u.create_date<='").append(param.get("endTime")).append(" 23:59:59'");
			}
			if(Utils.isNotNullOrEmpty(param.get("modelId"))){
				sb.append(" and v.model=").append(param.get("modelId"));
			}
			if(Utils.isNotNullOrEmpty(param.get("modelName"))){
				sb.append(" and v.model_name='").append(param.get("modelName")).append("'");
			}
			if(Utils.isNotNullOrEmpty(param.get("4sId"))){
				sb.append(" and v.4s_id=").append(param.get("4sId"));
			}
		}
		return sb;
	}

	@Override
	public List<Map<String, Object>> listJoinNetSum(Map<String, Object> param,
			String order, boolean is_desc, int pageNo, int pageSize)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select  v.4s_id as s_id, count(u.unit_id) join_count, ");
		sb.append(" sum(case when u.reg_status = 0 then  1 else 0 end ) as totalOnlineNum ");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb = buildJoinNetSumSql(sb, param);
		sb.append(" group by v.4s_id");
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countJoinNetDetail(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(v.vehicle_id)");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb = buildJoinNetDetailSql(sb, param);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	// 4S店名称、入网数量（该时间段）、在网总数,在网统计在getOnNetCount方法中
	private StringBuffer buildJoinNetDetailSql(StringBuffer sb, Map<String, Object> param) {
		sb.append(" where 1=1");
		if(Utils.isNotNullOrEmpty(param)){
			if(Utils.isNotNullOrEmpty(param.get("startTime"))){
				sb.append(" and u.create_date>='").append(param.get("startTime")).append(" 00:00:00'");
			}
			if(Utils.isNotNullOrEmpty(param.get("endTime"))){
				sb.append(" and u.create_date<='").append(param.get("endTime")).append(" 23:59:59'");
			}
			if(Utils.isNotNullOrEmpty(param.get("modelId"))){
				sb.append(" and v.model=").append(param.get("modelId"));
			}
			if(Utils.isNotNullOrEmpty(param.get("modelName"))){
				sb.append(" and v.model_name='").append(param.get("modelName")).append("'");
			}
			if(Utils.isNotNullOrEmpty(param.get("s_id"))){
				sb.append(" and v.4s_id=").append(param.get("s_id"));
			}
		}
		return sb;
	}

	@Override
	public List<Map<String, Object>> listJoinNetDetail(
			Map<String, Object> param, String order, boolean is_desc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select v.4s_id as s_id, v.plate_no, u.call_letter, v.vin, date_format(u.create_date, '%Y-%m-%d') create_date ");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb = buildJoinNetDetailSql(sb, param);
		sb.append(" order by u.create_date DESC");
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countOffNetSum(Map<String, Object> param) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from (");
		sb.append(" select  v.4s_id");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb = buildOffNetSumSql(sb, param);	//入网匹配
		sb.append(" group by v.4s_id");
		sb.append(") td");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	private StringBuffer buildOffNetSumSql(StringBuffer sb,
			Map<String, Object> param) {
		sb.append(" where 1=1");
		if(Utils.isNotNullOrEmpty(param)){
			if(Utils.isNotNullOrEmpty(param.get("startTime"))){
				sb.append(" and u.create_date>='").append(param.get("startTime")).append(" 00:00:00'");
			}
			if(Utils.isNotNullOrEmpty(param.get("endTime"))){
				sb.append(" and u.create_date<='").append(param.get("endTime")).append(" 23:59:59'");
			}
			if(Utils.isNotNullOrEmpty(param.get("modelId"))){
				sb.append(" and v.model=").append(param.get("modelId"));
			}
			if(Utils.isNotNullOrEmpty(param.get("modelName"))){
				sb.append(" and v.model_name='").append(param.get("modelName")).append("'");
			}
			if(Utils.isNotNullOrEmpty(param.get("4sId"))){
				sb.append(" and v.4s_id=").append(param.get("4sId"));
			}
		}
		return sb;
	}

	@Override
	public List<Map<String, Object>> listOffNetSum(Map<String, Object> param,
			String order, boolean is_desc, int pageNo, int pageSize)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select  v.4s_id as s_id, count(v.vehicle_id) join_count, ");
		sb.append(" sum(case when u.reg_status = 0 then  1 else 0 end ) as totalOnlineNum, ");
		sb.append(" sum(case when u.reg_status = 1 then  1 else 0 end ) as totalOutlineNum ");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb = buildOffNetSumSql(sb, param);
		sb.append(" group by v.4s_id");
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countOffNetDetail(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select v.vehicle_id ");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb.append(" left join (select st1.vehicle_id, st1.remark, max(st1.stamp) stamp, st1.type  from t_ba_stop st1 group by st1.vehicle_id, st1.stamp) st on v.vehicle_id=st.vehicle_id");
		sb = buildOffNetDetailSql(sb, param);
		sb.append(" group by v.vehicle_id");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		if(list != null ){
			return list.size();
		}
		return 0;
	}

	private StringBuffer buildOffNetDetailSql(StringBuffer sb, Map<String, Object> param) {
		sb.append(" where 1=1 and u.reg_status =1 ");	//暂未考虑重新开通的情况
		if(Utils.isNotNullOrEmpty(param)){
			if(Utils.isNotNullOrEmpty(param.get("startTime"))){
				sb.append(" and st.stamp>='").append(param.get("startTime")).append(" 00:00:00'");
			}
			if(Utils.isNotNullOrEmpty(param.get("endTime"))){
				sb.append(" and st.stamp<='").append(param.get("endTime")).append(" 23:59:59'");
			}
			if(Utils.isNotNullOrEmpty(param.get("modelId"))){
				sb.append(" and v.model=").append(param.get("modelId"));
			}
			if(Utils.isNotNullOrEmpty(param.get("modelName"))){
				sb.append(" and v.model_name='").append(param.get("modelName")).append("'");
			}
			if(Utils.isNotNullOrEmpty(param.get("s_id"))){
				sb.append(" and v.4s_id=").append(param.get("s_id"));
			}
		}
		return sb;
	}

	@Override
	public List<Map<String, Object>> listOffNetDetail(
			Map<String, Object> param, String order, boolean is_desc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select v.vehicle_id, u.unit_id, v.4s_id , v.plate_no, u.call_letter, v.vin, date_format(u.create_date, '%Y-%m-%d') create_date");
		sb.append(" , date_format(st.stamp, '%Y-%m-%d') stamp, st.remark, st.type ");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb.append(" inner join (select st1.vehicle_id, st1.remark, max(st1.stamp) stamp, st1.type  from t_ba_stop st1 group by st1.vehicle_id, st1.stamp) st on v.vehicle_id=st.vehicle_id");
		sb = buildOffNetDetailSql(sb, param);
		sb.append(" group by v.vehicle_id");
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countUnServiceSum(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) from(");
		sb.append(" select v.vehicle_id");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb = buildUnServiceSumSql(sb, param);
		sb.append(" group by v.4s_id");
		sb.append(" )td");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	private StringBuffer buildUnServiceSumSql(StringBuffer sb,
			Map<String, Object> param) {
		sb.append(" where 1=1 and u.flag=0 and u.reg_status=7");	//车辆正常，但未开通
		if(Utils.isNotNullOrEmpty(param)){
			if(Utils.isNotNullOrEmpty(param.get("startTime"))){
				sb.append(" and v.register_date>='").append(param.get("startTime")).append(" 00:00:00'");
			}
			if(Utils.isNotNullOrEmpty(param.get("endTime"))){
				sb.append(" and v.register_date<='").append(param.get("endTime")).append(" 23:59:59'");
			}
			if(Utils.isNotNullOrEmpty(param.get("modelId"))){
				sb.append(" and v.model=").append(param.get("modelId"));
			}
			if(Utils.isNotNullOrEmpty(param.get("modelName"))){
				sb.append(" and v.model_name='").append(param.get("modelName")).append("'");
			}
			if(Utils.isNotNullOrEmpty(param.get("s_id"))){
				sb.append(" and v.4s_id=").append(param.get("s_id"));
			}
		}
		return sb;
	}

	@Override
	public List<Map<String, Object>> listUnServiceSum(
			Map<String, Object> param, String order, boolean is_desc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select v.4s_id as s_id, count(u.unit_id) unservice_count ");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb = buildUnServiceSumSql(sb, param);
		sb.append(" group by v.4s_id");
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countUnServiceDetail(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(v.vehicle_id)");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb = buildUnServiceDetailSql(sb, param);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	private StringBuffer buildUnServiceDetailSql(StringBuffer sb,
			Map<String, Object> param) {
		sb.append(" where 1=1 and u.flag=0 and u.reg_status=7");	//车辆正常，但未开通
		if(Utils.isNotNullOrEmpty(param)){
			if(Utils.isNotNullOrEmpty(param.get("startTime"))){
				sb.append(" and v.register_date>='").append(param.get("startTime")).append(" 00:00:00'");
			}
			if(Utils.isNotNullOrEmpty(param.get("endTime"))){
				sb.append(" and v.register_date<='").append(param.get("endTime")).append(" 23:59:59'");
			}
			if(Utils.isNotNullOrEmpty(param.get("modelId"))){
				sb.append(" and v.model=").append(param.get("modelId"));
			}
			if(Utils.isNotNullOrEmpty(param.get("modelName"))){
				sb.append(" and v.model_name='").append(param.get("modelName")).append("'");
			}
			if(Utils.isNotNullOrEmpty(param.get("4sId"))){
				sb.append(" and v.4s_id=").append(param.get("4sId"));
			}
		}
		return sb;
	}

	@Override
	public List<Map<String, Object>> listUnServiceDetail(
			Map<String, Object> param, String order, boolean is_desc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select v.vehicle_id, u.unit_id, v.4s_id, v.plate_no, u.call_letter, v.vin, date_format(u.create_date, '%Y-%m-%d') create_date, ");
		sb.append(" date_format(u.stamp, '%Y-%m-%d') stamp, date_format(v.production_date, '%Y-%m-%d') production_date ");
		sb.append(" from t_ba_unit u inner join t_ba_vehicle v on u.vehicle_id=v.vehicle_id ");
		sb = buildUnServiceDetailSql(sb, param);
		sb.append(" order by v.register_date DESC");
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public Integer getOnNetCount(Integer fcId, Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(DISTINCT(u.unit_id))");
		sb.append(" from t_ba_unit u INNER JOIN t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb.append(" LEFT JOIN t_ba_stop st on st.unit_id=u.unit_id");
		sb.append(" where 1=1");
		if(Utils.isNotNullOrEmpty(param.get("endTime"))){
			sb.append(" and u.create_date<='").append(param.get("endTime")).append(" 23:59:59'");//入网时间在截止日期之前
			//一直在网、现在不在网，但是办停在查询截止后
			sb.append(" and (u.flag=0 or (u.flag!=0 and u.reg_status in(1, 2) and st.stamp>='").append(param.get("endTime")).append(" 23:59:59'))");
		}
		if(Utils.isNotNullOrEmpty(param.get("modelId"))){
			sb.append(" and v.model=").append(param.get("modelId"));
		}
		if(Utils.isNotNullOrEmpty(param.get("modelName"))){
			sb.append(" and v.model_name='").append(param.get("modelName")).append("'");
		}
		if(Utils.isNotNullOrEmpty(fcId)){
			sb.append(" and v.4s_id=").append(fcId);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public Integer getOffCount(Integer fcId, Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(DISTINCT(v.vehicle_id))");
		sb.append(" from t_ba_unit u INNER JOIN t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb.append(" INNER JOIN t_ba_stop st on u.unit_id=st.unit_id");
		sb.append(" where 1=1 and  u.flag!=0 ");	//已办停
		sb.append(" and u.reg_status in(1, 2)");	//已离网
		if(Utils.isNotNullOrEmpty(param.get("startTime"))){
			sb.append(" and st.stamp>='").append(param.get("startTime")).append(" 00:00:00'");
		}
		if(Utils.isNotNullOrEmpty(param.get("endTime"))){
			sb.append(" and st.stamp<='").append(param.get("endTime")).append(" 23:59:59'");
		}
		if(Utils.isNotNullOrEmpty(param.get("modelId"))){
			sb.append(" and v.model=").append(param.get("modelId"));
		}
		if(Utils.isNotNullOrEmpty(param.get("modelName"))){
			sb.append(" and v.model_name='").append(param.get("modelName")).append("'");
		}
		if(Utils.isNotNullOrEmpty(fcId)){
			sb.append(" and v.4s_id=").append(fcId);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<Map<String, Object>> findModelList(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select m.model_id, m.model_name");
		sb.append(" from t_ba_model m");
		sb.append(" inner join t_ba_series s on m.series_id=s.series_id");
		sb.append(" inner join t_ba_brand b on b.brand_id=s.brand_id");
		sb.append(" where m.is_valid=1 and s.is_valid=1 and b.is_valid=1");
		if(Utils.isNotNullOrEmpty(param)){
			if(Utils.isNotNullOrEmpty(param.get("brandId"))){
				sb.append(" and b.brand_id=").append(param.get("brandId"));
			}
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countStockStatistics(Long companyId,Map<String, Object> param)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) ");
		sb=getConditionHql(companyId, sb,param);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<Map<String, Object>> listStockStatistics(Long companyId,
			Map<String, Object> param, String order, boolean is_desc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select  s.sim_id,s.vin,s.engine_no,s.call_letter,s.barcode,date_format(s.production_date,'%Y-%m-%d')as production_date,u.unittype ");
		sb=getConditionHql(companyId, sb,param);
		sb.append(" order by s.sim_id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	

	 private StringBuffer getConditionHql(Long id, StringBuffer sb,Map conditionMap){
		 sb.append(" from t_ba_sim s LEFT JOIN t_ba_unittype u on s.unittype_id = u.unittype_id where 1=1  ");
		 if(null != id){
				sb.append(" and s.subco_no=").append(id);
			}
		 sb.append(" and s.vin is not null and s.unit_id = 0  ");
		 if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate"))) {
				sb.append(" and s.production_date >='").append(DateUtil.dateforSearch(conditionMap, "startDate")).append("'");
			}
		 
		 if (StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
				sb.append(" and s.production_date <='").append(DateUtil.dateforSearch(conditionMap, "endDate")).append("'");
			}
			return sb;
	    }
	 
	 private StringBuffer getConditionHql2(Long id, StringBuffer sb,Map conditionMap){
		 sb.append(" from t_ba_sim s LEFT JOIN t_ba_unittype u on s.unittype_id = u.unittype_id where 1=1  ");
		 if(null != id){
				sb.append(" and s.subco_no=").append(id);
			}
		 sb.append(" and s.vin is null and s.unit_id = 0  ");
		 if (StringUtils.isNotNullOrEmpty(conditionMap.get("call_letter"))) {
			 	sb.append(" and s.call_letter like '%").append(conditionMap.get("call_letter")).append("%'");
			}
		 
		 if (StringUtils.isNotNullOrEmpty(conditionMap.get("barcode"))) {
			 	sb.append(" and s.barcode like '%").append(conditionMap.get("barcode")).append("%'");
			}
			return sb;
	    }

	@Override
	public int countUnfixedStatistics(Long companyId, Map<String, Object> param)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) ");
		sb=getConditionHql2(companyId, sb,param);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<Map<String, Object>> listUnfixedStatistics(Long companyId,
			Map<String, Object> param, String order, boolean is_desc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select  s.sim_id,s.call_letter,s.barcode,u.unittype ");
		sb=getConditionHql2(companyId, sb,param);
		sb.append(" order by s.sim_id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public Map<String, Object> findPrivateWeekList(Map<String, Object> params)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		//在网数总数
		sb.append(" sum(case when (u.reg_status=0 and u.create_date<='").append(params.get("endDate")).append(" 23:59:59') or ");	//入网时间在查询截止时间之前且一直在网
		sb.append(" (u.reg_status=1 and u.create_date<='").append(params.get("endDate")).append(" 23:59:59' and st.stop_time>='").append(params.get("endDate")).append(" 23:59:59')");	//入网时间在查询截止时间之前离网时间在查询时间之后
		sb.append(" then 1 else 0 end) innet,");//查询时间内入网且在查询时间内离网（在网数=前期数+时间段内）
		sb.append(" sum(case when u.reg_status=1 and st.stop_time<='").append(params.get("endDate")).append(" 23:59:59' then 1 else 0 end) offnet,");	//离网数总数
		sb.append(" sum(case when (u.reg_status=0 or (u.reg_status!=0 and st.stop_time>='").append(params.get("endDate")).append(" 23:59:59'))");
		sb.append(" and u.create_date<='").append(params.get("startDate")).append(" 00:00:00' then 1 else 0 end) lastinnet,");	//上次在网总数
		sb.append(" sum(case when u.create_date>='").append(params.get("startDate")).append(" 00:00:00' and u.create_date<='").append(params.get("endDate")).append(" 23:59:59' then 1 else 0 end) newjoin,");	//新增入网数
		sb.append(" sum(case when st.stop_time>='").append(params.get("startDate")).append(" 00:00:00' and st.stop_time<='").append(params.get("endDate")).append(" 23:59:59' then 1 else 0 end) nowoff");	//时间段离网数
		sb.append(" from t_ba_unit u");
		sb.append(" inner join t_ba_customer c on u.customer_id=c.customer_id");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT s.unit_id, s.vehicle_id, s.customer_id, s.subco_no, Max(s.stamp) stop_time from t_ba_stop s GROUP BY s.unit_id) st");
		sb.append(" on u.unit_id=st.unit_id");
		sb.append(" where u.subco_no=").append(params.get("subco_no"));
		sb.append(" and c.cust_type=0");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		if(list != null && list.size()>0){
			return (Map<String, Object>) list.get(0);
		}
		return new HashMap<String, Object>();
	}

}
