package com.gboss.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemException;
import com.gboss.dao.FeeSimPDao;
import com.gboss.pojo.FeeSimP;
import com.gboss.service.FeeSimPService;

@Service("feeSimPService")
public class FeeSimPSerivceImpl extends BaseServiceImpl implements FeeSimPService {
	
	@Autowired
	private FeeSimPDao feeSimPDao;

	public void addFeeSimP(FeeSimP fsp) throws SystemException {
		feeSimPDao.addFeeSimP(fsp);
	}

	public void modifyFeeSimP(FeeSimP fsp) throws SystemException {
		feeSimPDao.modifyFeeSimP(fsp);
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		FeeSimPService service = (FeeSimPService) beanFactory.getBean("feeSimPSerivce");
		FeeSimP fsp = new FeeSimP();
		fsp.setCall_letter("13466778899");
		//subco_no,call_letter,period,s_date,e_date,days
		fsp.setData(0);
		fsp.setDays(2);
		fsp.setPeriod(1);
		fsp.setSubco_no(201);
		fsp.setS_date(new Date());
		fsp.setE_date(new Date());
		service.addFeeSimP(fsp);
	}

	@Override
	public FeeSimP getFeeSimPByCL(String call_letter,int period) {
		return feeSimPDao.getFeeSimPByCL(call_letter,period);
	}

	@Override
	public List<FeeSimP> getFeeSimsPByCL(String call_letter, int period) {
		return feeSimPDao.getFeeSimsPByCL(call_letter, period);
	}

}
