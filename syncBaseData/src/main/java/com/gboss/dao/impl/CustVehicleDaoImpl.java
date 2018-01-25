package com.gboss.dao.impl;


import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.CustVehicleDao;
import com.gboss.pojo.CustVehicle;
import com.gboss.util.StringUtils;

@Repository("CustVehicleDao")
public class CustVehicleDaoImpl extends BaseDaoImpl  implements CustVehicleDao {

	public CustVehicle getCustVehicleByCVID(String c_id,String v_id) throws SystemException {
		
		String sql =" select * from t_ba_cust_vehicle t where 1=1 ";
		if(StringUtils.isNotNullOrEmpty(c_id)){
			 sql +=" and customer_id=" + c_id;
		}
		
		if(StringUtils.isNotNullOrEmpty(v_id)){
			 sql +=" and vehicle_id=" + v_id;
		}
		
		List<CustVehicle> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<CustVehicle>(CustVehicle.class));
		
		if (list!=null && list.size() >0) {
			return list.get(0);
		}
		
		return null;
	}

}
