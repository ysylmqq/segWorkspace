package com.gboss.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.VehicleConfDao;
import com.gboss.pojo.VehicleConf;

@Repository("vehicleConfDao")
@Transactional
public class VehicleConfDaoImpl extends BaseDaoImpl implements VehicleConfDao {

	public VehicleConf getVehicleConfByCallLetter(String call_letter) {
		String  sql = " SELECT * FROM t_ba_vehicle_conf where call_letter = '"+call_letter+"'";
		List<VehicleConf> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<VehicleConf>(VehicleConf.class));
		if( list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
}
