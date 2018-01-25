package com.gboss.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.SeriesDao;
import com.gboss.pojo.Series;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:SeriesDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-9 下午2:54:09
 */
@Repository("SeriesDao")  
 
public class SeriesDaoImpl extends BaseDaoImpl implements SeriesDao {
	public List<Series> getSeriesList(Long brand_id) {
		String sql = " select * from t_ba_series t where 1=1  ";
		if(brand_id!=null){
			sql +=" and brand_id = "+brand_id ;
			sql +=" and is_valid = 1 ";
		}
		List<Series> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Series>(Series.class));
		return list;
	}

	 
	public Boolean isExist(String name) {
		String sql = " select * from t_ba_series t where 1=1  ";
		if(name!=null && !"".equals(name)){
			sql +=" and series_name = '"+name +"'" ;
			sql +=" and is_valid = 1 ";
		}
		List<Series> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Series>(Series.class));
		if(list.size() > 0){
			return true;
		}
		return false;
	}

	 
	 
	public int delete(Long id) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update t_ba_series s set s.is_valid=0 where s.id=" + id); 
		return jdbcTemplate.update(hqlSb.toString());
	}
	 
	public Boolean isExist(String name, Long id) {
		String sql = " select * from t_ba_series t where 1=1  ";
		if(name!=null && !"".equals(name)){
			sql +=" and series_name = "+name ;
			sql +=" and is_valid = 1 ";
		}
		if(id!=null && !"".equals(id)){
			sql +=" and id <> "+id ;
		}
		List<Series> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Series>(Series.class));
		if(list.size() > 0){
			return true;
		}
		return false;
	}
	 
	public Series getSeriesBySync_id(Long sync_id) {
		String sql = " select * from t_ba_series t where 1=1  ";
		if(sync_id!=null){
			sql +=" and sync_id="+sync_id ;
		}
		List<Series> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Series>(Series.class));
		if(list.size() > 0){
			return (Series) list.get(0);
		}
		return null;
	}

	 
	public Series getSeriesByName(String name) {
		String sql = " select * from t_ba_series t where 1=1  ";
		if(name!=null){
			sql +=" and series_name='"+name +"'" ;
		}
		List<Series> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Series>(Series.class));
		if(list.size() > 0){
			return (Series) list.get(0);
		}
		return null;
	}

}

