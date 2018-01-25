package com.gboss.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.FeeSimPDao;
import com.gboss.pojo.FeeSimP;

@Repository("feeSimPDao")
public class FeeSimPDaoImpl extends BaseDaoImpl implements FeeSimPDao {
	
	public void addFeeSimP(FeeSimP fsp) throws SystemException {
		String addsql = " insert into t_fee_sim_p(subco_no,call_letter,period,s_date,e_date,days) ";
		addsql +=" values(?,?,?,?,?,?) ";
		jdbcTemplate.update(addsql, new Object[]{fsp.getSubco_no(),fsp.getCall_letter(),fsp.getPeriod(),fsp.getS_date(),fsp.getE_date(),fsp.getDays()});
	}

	public void modifyFeeSimP(FeeSimP fsp) throws SystemException {
		update(fsp);
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		FeeSimPDao dao = (FeeSimPDao) beanFactory.getBean("feeSimPDao");
		FeeSimP fsp = new FeeSimP();
		fsp.setCall_letter("13466778899");
		//subco_no,call_letter,period,s_date,e_date,days
		fsp.setData(0);
		fsp.setDays(2);
		fsp.setPeriod(1);
		fsp.setSubco_no(201);
		fsp.setS_date(new Date());
		fsp.setE_date(new Date());
		dao.addFeeSimP(fsp);
	}

	@Override
	public FeeSimP getFeeSimPByCL(String call_letter,int period) {
		String sql = " select * from t_fee_sim_p t where call_letter ='" + call_letter + "' and period=" + period;
		List<FeeSimP> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<FeeSimP>(FeeSimP.class));
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<FeeSimP> getFeeSimsPByCL(String call_letter, int period) {
		String sql = " select * from t_fee_sim_p t where call_letter='"+call_letter+"' and period=" + period;
		List<FeeSimP> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<FeeSimP>(FeeSimP.class));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
		
	
	
	
}
