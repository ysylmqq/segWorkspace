package com.gboss.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.UnitDao;
import com.gboss.pojo.Unit;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:UnitDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:09:01
 */

@Repository("UnitDao")  
public class UnitDaoImpl extends BaseDaoImpl implements UnitDao {

	public boolean is_repeat(Unit unit) {
		String sql = " select * from t_ba_unit t where 1=1 ";
		int count=0;
		if(unit!=null){
			if(unit.getUnit_id()!=null){
				sql +=" and  unit_id <> " + unit.getUnit_id() ;
			}
			if(unit.getCall_letter()!=null){
				sql +=" and  call_letter = " + unit.getCall_letter() ;
			}
		    count= jdbcTemplate.queryForObject(sql, Integer.class);
		}
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	 
	public List<Unit> getByVehicle_id(Long vehicle_id) {
		String sql = " select * from t_ba_unit t where 1=1 ";
		if(vehicle_id!=null){
			sql +=" and  vehicle_id = " + vehicle_id ;
		}else{
			return new ArrayList<Unit>();
		}
		List<Unit> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Unit>(Unit.class));
		return list;
	}

	 
	public Unit getUnitByid(Long id) {
		String sql = " select * from t_ba_unit t where 1=1 ";
		if(id!=null){
			sql +=" and  unit_id = " + id ;
		}
		List<Unit> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Unit>(Unit.class));
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}

	 
	public Unit getByCustAndVehicle(Long cust_id, Long vehicle_id) {
		
		String sql = " select * from t_ba_unit t where 1=1 ";
		
		if(cust_id!=null){
			sql +=" and  customer_id = " + cust_id ;
		}
		
		if(vehicle_id!=null){
			sql +=" and  vehicle_id = " + vehicle_id ;
		}
		List<Unit> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Unit>(Unit.class));
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public String getHmCall_letters(Long companyId) throws SystemException {
		String sql =  "  SELECT group_concat(call_letter) as call_letter FROM t_ba_unit  where 1=1  ";
		if(companyId!=null && !"".equals(companyId)){
			sql +=" and  subco_no =" + companyId ;
		}
		List<String> list = jdbcTemplate.queryForList(sql,String.class);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	 
	public Unit getUnitByCL(String call_letter) throws SystemException {
		String sql =  " select * from t_ba_unit t where 1=1  ";
		if(call_letter!=null && !"".equals(call_letter)){
			sql +=" and  call_letter ='" + call_letter +"'" ;
		}
		List<Unit> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Unit>(Unit.class));
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	} 
	
	public static void main(String[] args) {
		ApplicationContext beanfactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		UnitDaoImpl dao = (UnitDaoImpl)beanfactory.getBean("UnitDao");
		try {
			Unit unit = dao.getUnitByCL("18116317074");
			System.out.println(dao.getUnitByCL("18116317074").getCall_letter());
			System.out.println("cc"+"\u0000\u0000"+"dd");
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
}

