package com.hm.bigdata.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hm.bigdata.dao.AlarmDao;
import com.hm.bigdata.dao.FaultDao;
import com.hm.bigdata.dao.VehicleDao;
import com.hm.bigdata.entity.po.Alarm;
import com.hm.bigdata.entity.po.Fault;
import com.hm.bigdata.entity.po.Vehicle;
import com.hm.bigdata.util.PageSelect;
import com.hm.bigdata.util.StringUtils;
import com.hm.bigdata.util.Utils;
import com.hm.bigdata.util.page.Page;
import com.hm.bigdata.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:VehicleDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:01:56
 */

@Repository("faultDao")
public class FaultDaoImpl extends BaseDaoImpl implements FaultDao {


	@Override
	public Page<HashMap<String, Object>> search(PageSelect<Fault> pageSelect, Long subco_no) {
		StringBuffer sbQuery =new StringBuffer( " SELECT a.*,b.`faulttypename`,c.`faultname`   ");
		StringBuffer sbCount =new StringBuffer( " SELECT count(1)  ");

		StringBuffer sb =new StringBuffer();
		sb.append(" FROM t_hm_fault_analysis a LEFT JOIN `t_hm_faulttype` b ON a.`faulttypeid` = b.`faulttypeid` ");
		sb.append(" LEFT JOIN  `t_hm_faultcode` c ON c.`faultcode` = a.`faultcode` AND a.`faulttypeid` = c.`faulttypeid`  WHERE 1=1 ");
		
		Map filter = pageSelect.getFilter();
		String callLetter = (String) filter.get("call_letter");
		if (StringUtils.isNotBlank(callLetter)) {
			sb.append(" and a.call_letter like  '%").append(callLetter).append("%' ");
		}
		
		String faulttypeid = (String) filter.get("fault_type");
		if (StringUtils.isNotBlank(faulttypeid)) {
			if( !"0".equals(faulttypeid) ){
				sb.append(" and a.faulttypeid = ").append(faulttypeid);
			}
		}
		
		/*String plate_no = (String) filter.get("plate_no");
		if (StringUtils.isNotBlank(plate_no)) {
			sb.append(" and veh.plate_no like  '%").append(plate_no).append("%' ");
		}*/
		
		String start_date = (String) filter.get("start_date");
		if (StringUtils.isNotBlank(start_date)) {
			start_date = start_date+" 00:00:00";
			sb.append(" and a.starttime >  '").append(start_date).append("' ");
		}
		
		
		String end_date = (String) filter.get("end_date");
		if (StringUtils.isNotBlank(end_date)) {
			end_date = end_date+" 00:00:00";
			sb.append(" and a.starttime < '").append(end_date).append("' ");
		}
		
		if (StringUtils.isNotBlank(pageSelect.getOrder())) {
			sb.append(" order by ").append(pageSelect.getOrder());
			if (pageSelect.getIs_desc()) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append(" order by a.analysis_id desc");
		}
		Query query = mysql1SessionFactory.getCurrentSession().createSQLQuery(sbQuery.append(sb).toString());  
		query.setFirstResult(PageUtil.getPageStart(pageSelect.getPageNo(), pageSelect.getPageSize()));
		query.setMaxResults(pageSelect.getPageSize());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list=query.list();
		
		int count =((BigInteger)mysql1SessionFactory.getCurrentSession().createSQLQuery(sbCount.append(sb).toString()).uniqueResult()).intValue();//countAll(sb);
	    return PageUtil.getPage(count, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public List<Map<String, Object>> findAllFaults(Map<String, Object> map) {

		StringBuffer sb =new StringBuffer(" SELECT a.*,b.`faulttypename`,c.`faultname` ");
		sb.append(" FROM t_hm_fault_analysis a LEFT JOIN `t_hm_faulttype` b ON a.`faulttypeid` = b.`faulttypeid` ");
		sb.append(" LEFT JOIN  `t_hm_faultcode` c ON c.`faultcode` = a.`faultcode` AND a.`faulttypeid` = c.`faulttypeid`  WHERE 1=1 ");
		
		
		String callLetter = (String) map.get("call_letter");
		if (StringUtils.isNotBlank(callLetter)) {
			sb.append(" and a.call_letter like  '%").append(callLetter).append("%' ");
		}
		
		String faulttypeid = (String) map.get("fault_type");
		if (StringUtils.isNotBlank(faulttypeid)) {
			if( !"0".equals(faulttypeid) ){
				sb.append(" and a.faulttypeid = ").append(faulttypeid);
			}
		}
		
		/*String plate_no = (String) filter.get("plate_no");
		if (StringUtils.isNotBlank(plate_no)) {
			sb.append(" and veh.plate_no like  '%").append(plate_no).append("%' ");
		}*/
		
		String start_date = (String) map.get("start_date");
		if (StringUtils.isNotBlank(start_date)) {
			start_date = start_date+" 00:00:00";
			sb.append(" and a.starttime >  '").append(start_date).append("' ");
		}
		
		
		String end_date = (String) map.get("end_date");
		if (StringUtils.isNotBlank(end_date)) {
			end_date = end_date+" 00:00:00";
			sb.append(" and a.starttime < '").append(end_date).append("' ");
		}
		
		sb.append(" order by a.analysis_id desc");
		
		Query query = mysql1SessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list=query.list();
		return list;
	}

	@Override
	public List<Map<String, Object>> getFalutCodeByName(String faultName) {
		StringBuffer sb =new StringBuffer( "SELECT  faulttypeid,faulttypename FROM `t_hm_faulttype`  ");
		Query query = mysql1SessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

}
