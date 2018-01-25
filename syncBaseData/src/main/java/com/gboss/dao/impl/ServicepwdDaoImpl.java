package com.gboss.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.ServicepwdDao;
import com.gboss.util.StringUtils;

@Repository("servicepwdDao")  
public class ServicepwdDaoImpl extends BaseDaoImpl implements ServicepwdDao {

	public List<Map<String, Object>> getServicePwdByBV(String barcode,String vin) throws SystemException {
		StringBuffer sb=new StringBuffer();
		
		
		sb.append(" SELECT v.vin, IFNULL(v.service_pwd, '') AS servicepwd FROM t_ba_vehicle v WHERE v.subco_no = 201  ");
		if(StringUtils.isNotNullOrEmpty(barcode)){
			sb.append(" AND v.barcode = '"+barcode+"' ");
		}
		if(StringUtils.isNotNullOrEmpty(vin)){
			sb.append(" AND v.vin = '"+vin+"' ");
		}
		
		return jdbcTemplate.queryForList(sb.toString());
	}
	
	public static void main(String[] args) {
		ApplicationContext beanfactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		ServicepwdDao syncHelper = (ServicepwdDao)beanfactory.getBean("servicepwdDao");
		try {
			System.out.println(syncHelper.getServicePwdByTimes("1403457136","1457182522").size());
			System.out.println(syncHelper.getServicePwdByBV(null, "LMVAFLFCXFA000101"));
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}

	 
	public List<Map<String, Object>> getServicePwdByTimes(String begintime, String endtime) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT v.vin, IFNULL(v.service_pwd, '') AS servicepwd FROM t_ba_vehicle v WHERE v.subco_no = 201 AND length(v.vin) = 17 AND v.stamp BETWEEN FROM_UNIXTIME("+begintime+") AND FROM_UNIXTIME("+endtime+") ");
//		System.out.println(sb);
		return jdbcTemplate.queryForList(sb.toString());
	}

	 
	public List<Map<String, Object>> getServicePwdByTimes(Map<String, String> params) throws SystemException {

		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT v.vin, IFNULL(v.service_pwd, '') AS servicepwd FROM t_ba_vehicle v WHERE v.subco_no = 201 and length(v.vin) > 0 ");
		
		String vin = params.get("vin"); 
		if(StringUtils.isNotNullOrEmpty(vin)){
			sb.append(" AND v.vin = '"+vin+"' ");
		}
		
		String begintime = params.get("begintime"); 
		if(StringUtils.isNotNullOrEmpty(begintime)){
			sb.append(" AND v.stamp >= FROM_UNIXTIME("+begintime+")");
		}
		
		String endtime = params.get("endtime"); 
		if(StringUtils.isNotNullOrEmpty(endtime)){
			sb.append(" AND v.stamp <= FROM_UNIXTIME("+endtime+")");
		}
		
		return jdbcTemplate.queryForList(sb.toString());
	}

}
