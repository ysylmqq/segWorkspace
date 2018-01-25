package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.HMReportDao;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.page.PageUtil;

@Repository("hmReportDao")
public class HMReportDaoImpl extends BaseDaoImpl implements HMReportDao {

	@Override
	public Integer countPeriod(Map<String, Object> params) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(r.call_letter) from t_fee_sim_p r");
		sb = buildPeriodSb(params, sb);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)(query.uniqueResult())).intValue();
	}

	private StringBuffer buildPeriodSb(Map<String, Object> params, StringBuffer sb){
		sb.append(" where r.flag=1");
		if(Utils.isNotNullOrEmpty(params.get("subcoNo"))){
			sb.append(" and r.subco_no=").append(params.get("subcoNo"));
		}
		if(Utils.isNotNullOrEmpty(params.get("period"))){
			sb.append(" and r.period=").append(params.get("period"));
		}
		if(Utils.isNotNullOrEmpty(params.get("startTime"))){
			sb.append(" and r.stamp >='").append(params.get("startTime")).append(" 00:00:00'");
		}
		if(Utils.isNotNullOrEmpty(params.get("endTime"))){
			sb.append(" and r.stamp <='").append(params.get("endTime")).append(" 23:59:59'");
		}
		if(Utils.isNotNullOrEmpty(params.get("callLetter"))){
			sb.append(" and r.call_letter='").append(params.get("callLetter")).append("'");
		}
		return sb;
	}
	
	@Override
	public List<Map<String, Object>> findPeriodList(Map<String, Object> params, String order, boolean isDesc, int pageNo, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.subco_no, r.call_letter, r.period, r.s_date, r.e_date, r.days, r.voice_time, r.data, r.flag, r.stamp");
		sb.append(" ,(case when r.period=1 then '入网前' when r.period=2 then '销售前' when r.period=3 then '客户使用' when r.period=1 then '客户停用' end) periodName");
		sb.append(" from t_fee_sim_p r");
		sb = buildPeriodSb(params, sb);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append("  order by r.stamp desc");
		}
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public Integer countSimHistory(Map<String, Object> params) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(r.call_letter) from t_fee_sim_h r");
		sb = buildSimHistory(params, sb);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)(query.uniqueResult())).intValue();
	}

	private StringBuffer buildSimHistory(Map<String, Object> params, StringBuffer sb){
		sb.append(" where r.flag=1");
		if(Utils.isNotNullOrEmpty(params.get("subcoNo"))){
			sb.append(" and r.subco_no=").append(params.get("subcoNo"));
		}
		if(Utils.isNotNullOrEmpty(params.get("startMonth"))){
			String startMonth = params.get("startMonth").toString().replaceAll("-", "");
			sb.append(" and r.month >='").append(startMonth).append("'");
		}
		if(Utils.isNotNullOrEmpty(params.get("endMonth"))){
			String endMonth = params.get("endMonth").toString().replaceAll("-", "");
			sb.append(" and r.stamp <='").append(endMonth).append("'");
		}
		if(Utils.isNotNullOrEmpty(params.get("callLetter"))){
			sb.append(" and r.call_letter='").append(params.get("callLetter")).append("'");
		}
		return sb;
	}
	
	@Override
	public List<Map<String, Object>> findSimHistoryList(Map<String, Object> params, String order, boolean isDesc, int pageNo, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.subco_no, r.call_letter, r.month, r.days, r.voice_time, r.data, r.flag, r.stamp");
		sb.append(" from t_fee_sim_h r");
		sb = buildSimHistory(params, sb);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append("  order by r.stamp desc");
		}
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public Integer countSimCurrent(Map<String, Object> params) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(r.call_letter) from t_fee_sim_m r");
		sb = buildSimCurrent(params, sb);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)(query.uniqueResult())).intValue();
	}

	private StringBuffer buildSimCurrent(Map<String, Object> params, StringBuffer sb){
		sb.append(" where r.flag=1");
		if(Utils.isNotNullOrEmpty(params.get("subcoNo"))){
			sb.append(" and r.subco_no=").append(params.get("subcoNo"));
		}
		if(Utils.isNotNullOrEmpty(params.get("period"))){
			sb.append(" and r.period=").append(params.get("period"));
		}
		if(Utils.isNotNullOrEmpty(params.get("callLetter"))){
			sb.append(" and r.call_letter='").append(params.get("callLetter")).append("'");
		}
		return sb;
	}

	@Override
	public List<Map<String, Object>> findSimCurrentList(Map<String, Object> params, String order, boolean isDesc, int pageNo, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.subco_no, r.call_letter, r.period, r.month, r.days, r.voice_time, r.data, r.flag, r.stamp");
		sb.append(" ,(case when r.period=1 then '入网前' when r.period=2 then '销售前' when r.period=3 then '客户使用' when r.period=1 then '客户停用' end) periodName");
		sb.append(" from t_fee_sim_m r");
		sb = buildSimCurrent(params, sb);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append("  order by r.stamp desc");
		}
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
}
