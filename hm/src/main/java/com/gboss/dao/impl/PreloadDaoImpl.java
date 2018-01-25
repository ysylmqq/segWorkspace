package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.PreloadDao;
import com.gboss.pojo.Preload;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:SimDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-10-10 下午3:00:46
 */
@Repository("preloadDao")  
@Transactional
public class PreloadDaoImpl extends BaseDaoImpl implements PreloadDao {

	@Override
	public List<HashMap<String, Object>> findPreloadList(Long companyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select a.sim_id,a.akey,a.barcode,a.call_letter,a.color,a.combo_id,a.combo_status,date_format(a.production_date,'%Y-%m-%d')as production_date,a.pack_id,");
		sb.append(" a.engine_no,a.esn,a.s_date,a.e_date,a.iccid,a.imei,a.imsi,a.flag,a.telco,a.vin,a.unittype_id,a.w_password,a.w_user,a.unittype,c.combo_name as name,a.subco_no,a.unit_id ");
		sb=getConditionHql(companyId, sb,conditionMap);
		sb.append(" order by a.sim_id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countPreload(Long companyId, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(a.sim_id) ");
		sb=getConditionHql(companyId, sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	
	 private StringBuffer getConditionHql(Long id, StringBuffer sb,Map conditionMap){
		 	sb.append(" from( select  s.sim_id,s.akey,s.barcode,s.call_letter,s.color,s.combo_id,s.combo_status,date_format(s.production_date,'%Y-%m-%d')as production_date,s.pack_id,  ");
		 	sb.append(" s.engine_no,s.esn,date_format(s.s_date,'%Y-%m-%d')as s_date,date_format(s.e_date,'%Y-%m-%d')as e_date,s.iccid,s.imei,s.imsi,s.flag,s.telco,s.vin,s.unittype_id,s.w_password,s.w_user,u.unittype,s.subco_no,s.unit_id  ");
		 	sb.append(" FROM t_ba_sim s LEFT JOIN t_ba_unittype u on s.unittype_id = u.unittype_id  ");
		 	sb.append(" where s.subco_no=").append(id);
		 	
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("call_letter"))){
				sb.append(" and s.call_letter like '%").append(conditionMap.get("call_letter")).append("%'");
			}
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("vin"))){
				sb.append(" and s.vin like '%").append(conditionMap.get("vin")).append("%'");
			}
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("imei"))){
				sb.append(" and s.imei like '%").append(conditionMap.get("imei")).append("%'");
			}
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("barcode"))){
		 		sb.append(" and s.barcode like '%").append(conditionMap.get("barcode")).append("%'");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("telco"))){
		 		sb.append(" and s.telco = ").append(conditionMap.get("telco"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("flag"))){
		 		sb.append(" and s.flag = ").append(conditionMap.get("flag"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("combo_status"))){
		 		sb.append(" and s.combo_status = ").append(conditionMap.get("combo_status"));
			}
			sb.append(" ) a left join  t_fee_sim_combo c on a.combo_id = c.combo_id");
			return sb;
	    }

	@Override
	public int operate(List<Long> ids, Integer type) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Preload set flag=:st where sim_id in (:ids) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("st", type);
		query.setParameterList("ids", ids); 
		return query.executeUpdate();
	}
	
	
	@Override
	public int operateCombo(List<Long> ids, Integer type) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Preload set combo_status=:st where sim_id in (:ids) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("st", type);
		query.setParameterList("ids", ids); 
		return query.executeUpdate();
	}

	@Override
	public Boolean isExist_phone(Preload sim) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) from Preload as s");
		sb.append(" where 1=1 ");
		if(sim!=null){
			if(StringUtils.isNotBlank(sim.getCall_letter())){
				sb.append(" and s.call_letter='").append(sim.getCall_letter()).append("'");
				if(sim.getSim_id()!=null){
					sb.append(" and s.sim_id <> ").append(sim.getSim_id());
				}
			}else{
				return false;
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
	public Boolean isExist_vin(Preload sim) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) from Preload as s");
		sb.append(" where 1=1 ");
		if(sim!=null){
			if(StringUtils.isNotBlank(sim.getVin())){
				sb.append(" and s.vin='").append(sim.getVin()).append("'");
				if(sim.getSim_id()!=null){
					sb.append(" and s.sim_id <> ").append(sim.getSim_id());
				}
			}else{
				return false;
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
	public Boolean isExist_barcode(Preload sim) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) from Preload as s");
		sb.append(" where 1=1 ");
		if(sim!=null){
			if(StringUtils.isNotBlank(sim.getBarcode())){
				sb.append(" and s.barcode='").append(sim.getBarcode()).append("'");
				if(sim.getSim_id()!=null){
					sb.append(" and s.sim_id <> ").append(sim.getSim_id());
				}
			}else{
				return false;
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
	public Preload getPreloadByTbox(String barcode) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" from Preload as c");
		hqlSb.append(" where c.barcode='").append(barcode).append("'");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		List<Preload> list = query.list();
		if(list != null && list.size() ==1)
			return list.get(0);
		return null;
	}

	@Override
	public Boolean isExist_imei(Preload sim) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) from Preload as s");
		sb.append(" where 1=1 ");
		if(sim!=null){
			if(StringUtils.isNotBlank(sim.getImei())){
				sb.append(" and s.imei='").append(sim.getImei()).append("'");
				if(sim.getSim_id()!=null){
					sb.append(" and s.sim_id <> ").append(sim.getSim_id());
				}
			}else{
				return false;
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
	public Boolean isExist_call_letter(Preload sim) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) from Preload as s");
		sb.append(" where 1=1 ");
		if(sim!=null){
			if(StringUtils.isNotBlank(sim.getCall_letter())){
				sb.append(" and s.call_letter='").append(sim.getCall_letter()).append("'");
				if(sim.getSim_id()!=null){
					sb.append(" and s.sim_id <> ").append(sim.getSim_id());
				}
			}else{
				return false;
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
	public Preload getPreloadByCl(String call_letter) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" from Preload as c");
		hqlSb.append(" where c.call_letter='").append(call_letter).append("'");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		List<Preload> list = query.list();
		if(list != null && list.size() ==1)
			return list.get(0);
		return null;
	}

	@Override
	public List<Preload> getSims(Map<String, Object> params)
			throws SystemException {
		String sql = " SELECT * FROM t_ba_sim t WHERE t.subco_no = 201 ";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Preload.class);  
		List<Preload> list = query.list();
		if(list != null && list.size() > 0)
			return list ;
		return null;
	}

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		PreloadDao preloadDao = (PreloadDao) context.getBean("preloadDao");
		try {
			for(Preload sim : preloadDao.getSims(null)){
			}
			System.out.println(preloadDao.getSims(null));
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

