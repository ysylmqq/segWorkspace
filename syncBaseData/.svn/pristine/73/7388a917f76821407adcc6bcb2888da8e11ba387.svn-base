package com.gboss.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.gboss.dao.FeeInfoDao;
import com.gboss.pojo.FeeInfo;

@Service("feeInfoDao")
public class FeeInfoDaoImpl extends BaseDaoImpl implements FeeInfoDao {

	public FeeInfo getFeeInfo(FeeInfo fi) throws Exception {
		String sql = " select  * from t_fee_info t where 1=1 AND subco_no = 201 and t.feetype_id = 101 ";
		if(fi != null){
			if(hasText(fi.getCustomer_id())){
				sql += " and t.customer_id =  " + fi.getCustomer_id();
			}
			
			if(hasText(fi.getVehicle_id())){
				sql += " and t.vehicle_id =  " + fi.getVehicle_id();
			}
			
			if(hasText(fi.getUnit_id())){
				sql += " and t.unit_id =  " + fi.getUnit_id();
			}
			
			List<FeeInfo> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<FeeInfo>(FeeInfo.class));
			if(list!=null && list.size()>0){
				return list.get(0);
			}
		}
		
		return null;
	}
	
	private static boolean hasText(Object input){
		if(input==null ){
			return false;
		}
		if("".equals(input.toString())){
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		FeeInfoDao dao = context.getBean("feeInfoDao", FeeInfoDao.class);
		try {
			FeeInfo fi = new FeeInfo();
			fi.setCustomer_id(40620L);
			fi.setVehicle_id(60388);
			fi.setUnit_id(60186);
			System.out.println(dao.getFeeInfo(fi));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<FeeInfo> getFeeInfo(Map<String, Long> queryCondition)
			throws Exception {
		if(queryCondition != null){
			String sql = " SELECT  DISTINCT a.* from  ( ";
			sql += " select  * from t_fee_info t where subco_no = 201 and t.feetype_id = 101  and t.vehicle_id = " + queryCondition.get("vehicle_id");
			sql += " union ";
			sql += " select  * from t_fee_info t where subco_no = 201 and t.feetype_id = 101  and t.unit_id = " + queryCondition.get("unit_id");
			sql += " union ";
			sql += " select  * from t_fee_info t where subco_no = 201 and t.feetype_id = 101  and t.unit_id = " + queryCondition.get("unit_id") + " and t.vehicle_id =" + queryCondition.get("vehicle_id") ;
			sql += " ) a " ;
			List<FeeInfo> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<FeeInfo>(FeeInfo.class));
			if(list!=null && list.size()>0){
				return list;
			}
		}
		return null;
	}

	@Override
	public List<FeeInfo> queryFeeInfo(Map<String, Long> queryCondition) throws Exception {
		// TODO Auto-generated method stub
		if(queryCondition != null){
			StringBuffer sql = new StringBuffer();
			sql.append( " select  * from t_fee_info t where subco_no = 201 and t.feetype_id = 101  and t.unit_id = " + queryCondition.get("unit_id") + " and t.vehicle_id =" + queryCondition.get("vehicle_id")) ;
			List<FeeInfo> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<FeeInfo>(FeeInfo.class));
			if(list!=null && list.size()>0){
				return list;
			}
		}
		return null;
	}

}