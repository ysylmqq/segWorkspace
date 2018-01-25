package com.gboss.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.dao.BarcodeDao;
import com.gboss.pojo.Barcode;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:BarcodeDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-15 下午7:54:13
 */
@Repository("BarcodeDao")  
public class BarcodeDaoImpl extends BaseDaoImpl implements BarcodeDao {

	 
	public List<Barcode> getByUnit_id(Long unit_id) {
		
		String sql = "select * from t_ba_barcode t where t.unit_id = " + unit_id ;
		
		List<Barcode> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper< Barcode>(Barcode.class));
		
		if( list !=null && list.size()> 0){
			return list;
		}
		return null;
	}
	 
	public void deleteByUnit_id(Long unit_id) {
		String hql = "delete from t_ba_barcode where unit_id = " + unit_id;
		jdbcTemplate.update(hql);
	}

	 
	public Barcode getByUnit_idAndType(Long unit_id, Integer type) {
		String sql = " select * from t_ba_barcode  t where 1=1  ";
		
		if(unit_id!=null){
			sql+=" and unit_id = "  + unit_id;
		}
		
		if(type != null){
			sql+=" and bc_type = "  + type;
		}
//		System.out.println("====>"+sql);
		List<Barcode> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper< Barcode>(Barcode.class));
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}
}

