package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemException;
import com.gboss.dao.FeeSimMDao;
import com.gboss.pojo.FeeSimM;
import com.gboss.service.FeeSimMService;

@Service("feeSimMService")
public class FeeSimMServiceImpl extends BaseServiceImpl implements FeeSimMService {

	@Autowired
	private FeeSimMDao feeSimMDao;
	
	public void addFeeSimM(FeeSimM fsm) throws SystemException {
		feeSimMDao.addFeeSimM(fsm);
	}

	 
	public void updateFeeSimM(FeeSimM fsm) throws SystemException {
		feeSimMDao.updateFeeSimM(fsm);
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
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
		fsm.setPeriod(2);
		fsm.setCall_letter("13466778899");
		dao.updateFeeSimM(fsm);
	}


	public FeeSimM getFeeSimMByCL(String call_letter,int period) throws SystemException {
		return feeSimMDao.getFeeSimMByCL(call_letter,period);
	}

}
