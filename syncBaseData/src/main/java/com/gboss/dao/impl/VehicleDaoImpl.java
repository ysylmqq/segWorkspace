package com.gboss.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.dao.VehicleDao;
import com.gboss.pojo.Vehicle;
import com.jayway.jsonpath.Criteria;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:VehicleDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:01:56
 */

@Repository("VehicleDao")
public class VehicleDaoImpl extends BaseDaoImpl implements VehicleDao {
	 
	public boolean is_repeat(Vehicle vehicle) {
		int count = 0;
		String sql =" select * from t_ba_vehicle t where 1=1  ";
		if (vehicle != null) {
			if (vehicle.getVehicle_id() != null) {
				sql += " and vehicle_id <>" + vehicle.getVehicle_id();
			}
			if (vehicle.getPlate_no() != null) {
				sql += " and plate_no ='" + vehicle.getPlate_no() + "'";
			}
			count = jdbcTemplate.queryForObject(sql, Integer.class);
		}
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	 
	public List<Vehicle> getVehiclesByCustid(Long cust_id) {
		String sql = "select from t_ba_vehicle "
				+ "where 1=1 and vehicle_id in "
				+ "(select vehicle_id from t_ba_cust_vehicle where customer_id="
				+ cust_id + ")";
		List<Vehicle> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Vehicle>(Vehicle.class));
		return list;
	}

	 
	public Vehicle getVehicleByid(Long id) {
		String sql = " select * from t_ba_vehicle t where 1=1 ";
		if (id != null) {
			sql += " and vehicle_id=" + id ;
		}
		List<Vehicle> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Vehicle>(Vehicle.class));
		if (list.size() > 0) {
			return  list.get(0);
		}
		return null;
	}

	 
	public Long getIdByPlate(String plate_no) {
		
		String sql = " select * from t_ba_vehicle t where 1=1 ";
		
		if (plate_no != null) {
			sql += " and plate_no=" + plate_no ;
		}
		List<Vehicle> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Vehicle>(Vehicle.class));
		if (list.size() > 0) {
			Vehicle vehicle = list.get(0);
			return vehicle.getVehicle_id();
		}
		return 0L;
	}

	 
	public Long add(Vehicle vehicle) {
//		return save(vehicle);
		return null;
	}

	 
	public boolean is_exist(Vehicle vehicle) {
		String sql = " select * from t_ba_vehicle t where 1=1 ";
		if(vehicle.getVin() != null && !vehicle.getVin().equals("")){
			sql += " and vin='"+vehicle.getVin()+"' ";
		}else{
			return false;
		}
		
		if(vehicle.getVehicle_id() != null){
			sql += " and vehicle_id<>"+vehicle.getVehicle_id();
		}
		
		List<Vehicle> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Vehicle>(Vehicle.class));
		if (list!=null && list.size() > 0) {
			return true;
		}
		return false;
	}

	 
	 
	public Vehicle getVehicleByCallLetter(String call_letter) {
		String sql= " select * from t_ba_vehicle t , t_ba_unit u where    where u.vehicle_id = v.vehicle_id and u.call_letter='" + call_letter+"'";
		List<Vehicle> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Vehicle>(Vehicle.class));
		if(list != null && list.size() ==1)
			return list.get(0);
		return null;
	}

	 
	public Long getIdByVin(String vin) {
		String sql =  " select * from t_ba_vehicle t where 1=1 ";
		if (vin != null) {
			sql += " and vin='" + vin +"'";
		}
		List<Vehicle> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Vehicle>(Vehicle.class));
		if (list.size() > 0) {
			Vehicle vehicle = (Vehicle) list.get(0);
			return vehicle.getVehicle_id();
		}
		return 0L;
	}

}
