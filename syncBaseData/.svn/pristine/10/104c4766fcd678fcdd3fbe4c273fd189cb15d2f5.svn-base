package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.ModelDao;
import com.gboss.pojo.Model;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ModelDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-24 下午5:08:07
 */
@Repository("modelDao")  
 
public class ModelDaoImpl extends BaseDaoImpl implements ModelDao {

	 
	public List<Model> getModelList(Long series_id) {
		String sql = " select * from t_ba_model t where 1=1 ";
		if(series_id !=null){
			sql += " and  seriesId="+series_id;
			sql += " and  is_valid=1 ";
		}
		List<Model> modelList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Model>(Model.class));
		return modelList;
	}

	 
	public Boolean isExist(String name) {
		String sql = " select * from t_ba_model t where 1=1 ";
		if(name !=null){
			sql += " and  model_name='"+name+"'";
			sql += " and  is_valid=1 ";
		}
		List<Model> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Model>(Model.class));
		if(list.size() > 0){
			return true;
		}
		return false;
	}

	 
	public HashMap<String, Object> getMaintainRuleMsg(Long model_id)
			throws SystemException {
	/*	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MaintainRule.class); 
		if(model_id!=null){
			criteria.add(Restrictions.eq("model", model_id));
		}
		MaintainRule main = (MaintainRule) criteria.list().get(0);
		if(null != main){
			HashMap<String, Object>  map = new HashMap<String, Object>();
			map.put("first_mileage", main.getFirst_service_mileage());
			map.put("second_mileage", main.getSecond_service_mileage());
			map.put("interval_mileage", main.getInterval_mileage());
			return map;
		}*/
		return null;
	}

	 
	 
	public int delete(Long id) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update t_ba_model m set m.is_valid=0 where m.id=? ");
		return jdbcTemplate.update(hqlSb.toString(), id);
	}

	 
	public Boolean isExist(String name, Long id) {
		
		
		String sql = " select * from t_ba_model t where 1=1 ";
		if(name !=null){
			sql += " and  seriesId='"+name +"'";
			sql += " and  is_valid=1 ";
		}
		List<Model> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Model>(Model.class));
		if(list.size() > 0){
			return true;
		}
		return false;
	}

	 
	public String getModelByid(Long id) {
		
		String sql = " select * from t_ba_model t where 1=1 ";
		if(id !=null){
			sql += " and  id='"+id +"'";
		}
		List<Model> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Model>(Model.class));
		if(list.size() > 0){
			Model model = list.get(0);
			return model.getName();
		}
		return null;
	}
	
	 
	public Model getModelBySync_id(Long sync_id) {
		
		String sql = " select * from t_ba_model t where 1=1 ";
		if(sync_id !=null){
			sql += " and  sync_id="+sync_id ;
		}
		
		List<Model> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Model>(Model.class));
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	 
	public Model getModelByName(String name) {
		
		String sql = " select * from t_ba_model t where 1=1 ";
		if(name !=null){
			sql += " and  name='"+name +"'" ;
		}
		List<Model> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Model>(Model.class));
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}

