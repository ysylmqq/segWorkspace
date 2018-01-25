package com.gboss.dao.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.FeeSimMDao;
import com.gboss.pojo.FeeSimM;

@Repository("feeSimMDao")
public class FeeSimMDaoImpl extends BaseDaoImpl implements FeeSimMDao {
	
	public void addFeeSimM(FeeSimM fsm) throws SystemException {
		String sql= "insert into t_fee_sim_m (subco_no,call_letter,period,month) values(?,?,?,?) ";
		jdbcTemplate.update(sql, new Object[]{fsm.getSubco_no(),fsm.getCall_letter(),fsm.getPeriod(),fsm.getMonth()});
		
	}

	public void updateFeeSimM(FeeSimM fsm) throws SystemException {
		String updatesql = "update t_fee_sim_m t set t.period = ? where t.call_letter = ? ";
		jdbcTemplate.update(updatesql, new Object[]{fsm.getPeriod(),fsm.getCall_letter()});
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
//		System.out.println(beanFactory);
		FeeSimMDao dao = (FeeSimMDao) beanFactory.getBean("feeSimMDao");
		FeeSimM fsm = new FeeSimM();
//		fsm.setCall_letter("13466778899");
//		fsm.setMonth("201506");
//		fsm.setDays(0);
//		fsm.setSubco_no(201);
//		fsm.setPeriod(1);
//		fsm.setData(0);
//		fsm.setVoice_time(0);
//		dao.addFeeSimM(fsm);
		
		//update
//		fsm.setPeriod(2);
//		fsm.setCall_letter("13466778899");
//		dao.updateFeeSimM(fsm);
	}

	public FeeSimM getFeeSimMByCL(String call_letter,int period) throws SystemException {
		String sql = " select * from t_fee_sim_m t where call_letter ='" + call_letter + "' and period = " + period;
		List<FeeSimM> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<FeeSimM>(FeeSimM.class));
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
