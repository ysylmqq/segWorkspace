package com.gboss.dao.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.PreloadDao;
import com.gboss.pojo.Preload;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:SimDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-10-10 下午3:00:46
 */
@Repository("preloadDao")  
 
public class PreloadDaoImpl extends BaseDaoImpl implements PreloadDao {

	public Boolean isExist_phone(Preload sim) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) from t_ba_sim as s");
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
		List<Preload> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Preload>(Preload.class));
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	 
	public Boolean isExist_vin(Preload sim) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) from t_ba_sim as s");
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
		List<Preload> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Preload>(Preload.class));
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	 
	public Boolean isExist_barcode(Preload sim) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) from t_ba_sim as s");
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
		List<Preload> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Preload>(Preload.class));
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	 
	public Preload getPreloadByTbox(String barcode) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append("SELECT *  from t_ba_sim as c");
		hqlSb.append(" where c.barcode='").append(barcode).append("'");
		List<Preload> list = jdbcTemplate.query(hqlSb.toString(), new BeanPropertyRowMapper<Preload>(Preload.class));
		if(list != null && list.size() ==1)
			return list.get(0);
		return null;
	}

	 
	public Boolean isExist_imei(Preload sim) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) from t_ba_sim as s");
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
		List<Preload> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Preload>(Preload.class));
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	 
	public Boolean isExist_call_letter(Preload sim) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) from t_ba_sim as s");
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
		List<Preload> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Preload>(Preload.class));
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	 
	public Preload getPreloadByCl(String call_letter) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select *  from t_ba_sim as c");
		hqlSb.append(" where c.call_letter='").append(call_letter).append("'");
		List<Preload> list =jdbcTemplate.query(hqlSb.toString(), new BeanPropertyRowMapper(Preload.class));
		if(list != null && list.size() ==1)
			return list.get(0);
		return null;
	}

	 
	public Preload getPreloadByVin(String vin) throws SystemException {
		String sql = "select * from t_ba_sim t where t.vin = '"+ vin+"'";
		List<Preload> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Preload>(Preload.class));
		
		if(list != null && list.size() ==1)
			return (Preload)list.get(0);
		return null;
	}

	public Preload getPreloadByVinBarcode(String vin, String barcode) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select * from t_ba_sim as c where 1=1 ");
		
		if(StringUtils.isNotNullOrEmpty(vin)){
			hqlSb.append(" and c.vin='").append(vin).append("'");
		}
		
		if(StringUtils.isNotNullOrEmpty(barcode)){
			hqlSb.append(" and c.barcode='").append(barcode).append("'");
		}
		
		List<Preload> list = jdbcTemplate.query(hqlSb.toString(), new BeanPropertyRowMapper<Preload>(Preload.class));
		if(list != null && list.size() ==1)
			return list.get(0);
		return null;
	}

	 
	public void batchUpdateSim(List<Preload> sims) throws SystemException {
	}

	 
	public Preload getPreloadByBarcode(String barcode) throws SystemException {
		String sql = "select * from t_ba_sim t where t.barcode = '"+ barcode+"'";
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "getPreloadByBarcode(" + barcode+"),sql=" + sql);
		List<Preload> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Preload>(Preload.class));
		if(list != null && list.size() ==1)
			return  list.get(0);
		return null;
	}
	
	 
	public void updateSim(Preload sim) {
		String sql = "update t_ba_sim t set t.vin=? , t.barcode=?,t.scan_time=? where t.sim_id = ? ";
		jdbcTemplate.update(sql, new Object[]{sim.getVin(),sim.getBarcode(),sim.getScan_time(),sim.getSim_id()});
	}

	 
	public void saveSim(Preload sim) {
		//TO-DO
	}

	 
	public List<Preload> getPreloadsByVin(String vin) throws SystemException {
		String sql = "select * from t_ba_sim t where t.vin = '"+ vin+"'";
		List<Preload> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Preload>(Preload.class));
		if(list != null && list.size() > 0 ){
			return list;
		}
		return null;
	}
	
	public static void main(String[] args) {
		ApplicationContext beanfactory = new ClassPathXmlApplicationContext("applicationContext.xml");
//	System.out.println(beanfactory);
	
		PreloadDao syncHelper = (PreloadDao)beanfactory.getBean("preloadDao");
		try {
			System.out.println(syncHelper.getPreloadsByVin("LMVAFLFC0FA003198").size());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}

