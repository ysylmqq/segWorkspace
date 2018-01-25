package com.gboss.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.dao.InsurerDao;
import com.gboss.pojo.Insurer;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:InsurerDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-12-24 上午11:20:38
 */
@Repository("InsurerDao")  
public class InsurerDaoImpl extends BaseDaoImpl implements InsurerDao {

	 
	public List<Insurer> getInsurersByname(String insurer_name) {
		String sql = "select * from t_ba_ic where   s_name like '%"+insurer_name+"%' order by stamp";
		List<Insurer> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Insurer>(Insurer.class));
		return list;
	}

	 
	public Insurer getInsurerBySync_id(Long sync_id) {
		String sql = "select * from t_ba_ic where  sync_id ="+sync_id;
		List<Insurer> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Insurer>(Insurer.class));
		if(list.size() > 0){
			return  list.get(0);
		}
		return null;
	}

	 
	public Insurer getInsurerByName(String name) {
		String sql = "select * from t_ba_ic where  c_name ='"+name+"'";
		List<Insurer> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Insurer>(Insurer.class));
		if(list.size() > 0){
			return  list.get(0);
		}
		return null;
	}
	
	public static void main(String[] args) {
		
	}

}

