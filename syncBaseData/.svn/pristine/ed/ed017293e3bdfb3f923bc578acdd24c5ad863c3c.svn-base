package com.gboss.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.CallletterDao;
import com.gboss.pojo.Preload;
import com.gboss.util.StringUtils;

@Repository("callletterDao")
public class CallletterDaoImpl extends BaseDaoImpl implements CallletterDao {

	/**
	 * 根据给定的barcode获取车载电话
	 * 
	 * @param barcode
	 * @return
	 * @throws SystemException
	 */
	public Preload getCallLetterByBarcode(String barcode) throws SystemException {
		String sql = " SELECT s.barcode, s.call_letter FROM t_ba_sim s WHERE s.subco_no = 201 AND s.barcode = '" + barcode + "' and LENGTH(s.barcode) > 0 ";
		List<Preload> sims = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Preload>(Preload.class));
		if (sims != null && sims.size() > 0) {
			return sims.get(0);
		}
		return null;
	}

	/**
	 * 根据给定的时间段批量获取车载电话
	 * 
	 * @param begintime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @return
	 * @throws SystemException
	 */
	public List<Preload> getCallLettersByTimes(String begintime, String endtime, String barcode) throws SystemException {

		String sql = "SELECT s.barcode, s.call_letter FROM t_ba_sim s WHERE s.subco_no = 201 and LENGTH(s.barcode) > 0 ";
		if (StringUtils.isNotNullOrEmpty(barcode)) {
			sql += " AND s.barcode='" + barcode + "'";
		}

		if (StringUtils.isNotNullOrEmpty(begintime) && StringUtils.isNotNullOrEmpty(endtime)) {
			sql += " AND s.stamp BETWEEN FROM_UNIXTIME(" + begintime + ") AND FROM_UNIXTIME(" + endtime + ")";
		}

		// System.out.println(sql);
		List<Preload> sims = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Preload>(Preload.class));
		if (sims != null && sims.size() > 0) {
			return sims;
		}
		return null;
	}

	public static void main(String[] args) {
		 ApplicationContext beanFactory = new
		 ClassPathXmlApplicationContext("applicationContext.xml");
		
		 CallletterDao callletterDao =
		 beanFactory.getBean(CallletterDao.class);
		 try {
		
		 String barcode = "0203443F41511800060";
		 // Preload sim = callletterDao.getCallLetterByBarcode(barcode);
		 // System.out.println(sim.getCall_letter());
		
		 String begintime = "1427786231",endtime="1432051200";
		 // List<Preload> list =
//		 callletterDao.getCallLettersByTimes(begintime, endtime);
		 // System.out.println(list.size());
		 //
		 }finally {
			 
		 }
		
	}

	public List<Preload> getCallLettersByTimes(Map<String, String> params) throws SystemException {
		String sql = "SELECT s.barcode, s.call_letter FROM t_ba_sim s WHERE s.subco_no = 201 and LENGTH(s.barcode) > 0 ";

		String barcode = params.get("barcode");
		if (StringUtils.isNotNullOrEmpty(barcode)) {
			sql += " AND s.barcode='" + barcode + "'";
		}

		String begintime = params.get("begintime");
		String endtime = params.get("endtime");
		if (StringUtils.isNotNullOrEmpty(begintime)) {
			sql += " AND s.stamp >= FROM_UNIXTIME(" + begintime + ") ";
		}

		if (StringUtils.isNotNullOrEmpty(endtime)) {
			sql += " AND s.stamp <=  FROM_UNIXTIME(" + endtime + ")";
		}

		// System.out.println(sql);
		List<Preload> sims = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Preload>(Preload.class));
		if (sims != null && sims.size() > 0) {
			return sims;
		}
		return null;
	}

}
